import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Clock;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ClockPanel class renders a clock based on its provided options.
 */
public class ClockPanel extends JPanel {
    private static final int tickRadius = 75; // The radius in pixels away from the center.

    private Clock clock;
    private Hand[] hands;
    private final ClockPanelOptions options;
    private final Coordinate center;

    /**
     * Constructs a ClockPanel.
     * @param options The options used to initialize this clock.
     */
    public ClockPanel(ClockPanelOptions options) {
        this.options = options;
        this.center = new Coordinate();
        init();
    }

    /**
     * Initiates the clock. Does everything to make it functional; sets up the internal clock, initiates the hand threads, and sets up a timer to repaint the clock every second.
     */
    public void init() {
        // Get the clock for the timezone we're looking for.
        this.clock = Clock.system(this.options.getZoneID()); // Get the clock based on our zone ID.
        this.clock = Clock.offset(this.clock, Duration.ofMillis((System.currentTimeMillis() - this.options.getStartTime()))); // Apply the millisecond offset.

        // Initialize the panel.
        setSize(175, 175);
        this.hands = new Hand[]{
            new HourHand(this),
            new MinuteHand(this),
            new SecondHand(this)
        };

        // Set the center, and add a listener for when the panel is resized.
        // Source: https://stackoverflow.com/a/2303329
        setCenter();
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                setCenter();
            }
        });

        // Start a timer that will rerender the clock every second. This timer runs on its own thread with all the other timers.
        // Assistance from: https://stackoverflow.com/a/53686385
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This function (extended from Component) will eventually call the paintComponent method (overridden below).
                // This method has been overridden because it is called via a number of events, such as the resizing of a window.
                repaint();
            }
        });
        timer.start();

        // Start each hand on its own thread.
        ExecutorService service = Executors.newCachedThreadPool();
        for (Hand hand : hands) {
            service.execute(hand);
        }
        service.shutdown(); // Shuts down when threads stop; they never will, so this doesn't really do anything.
    }

    /**
     * Gets the radius from the center in which the "clock ticks" are drawn.
     * @return A number (in pixels) representing this radius.
     */
    public int getTickRadius() {
        return tickRadius;
    }

    /**
     * Gets the center coordinate.
     * @return A coordinate representing the center of the clock.
     */
    public Coordinate getCenter() {
        return this.center;
    }

    /**
     * Sets the center of the clock, based on the dimensions of the JPanel.
     */
    private void setCenter() {
        Dimension panelSize = getSize();
        center.setX((int)(panelSize.getWidth()) / 2);
        center.setY((int)(panelSize.getHeight()) / 2);
    }

    /**
     * Gets the <i>internal</i> clock that helps us keep time for the <i>rendered</i> clock.
     * @return A Clock object that has predetermined timezone and offset data.
     */
    public Clock getClock() {
        return this.clock;
    }

    /**
     * "Paints" the panel, allowing us to render our clock.
     * @param g the <code>Graphics</code> object used for graphical operations, such as line drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        // First, call the super class to do whatever it needs to do.
        super.paintComponent(g);

        // Draw a blue center-point.
        g.setColor(Color.BLUE);
        g.fillRect(this.center.getX(), this.center.getY(), 1, 1);

        // Render ticks around the clock. A tick appears every second.
        for(int i = 0; i < 60; i++) {
            // We want to make a circle around the center at a specific distance away.
            // Effectively this radian value is going backwards, from seconds 0 (pi/2) to 59 clockwise.
            double rad = ((double)(-i)/60 * (2 * Math.PI)) + (Math.PI / 2);

            // Determine line length. Longer ticks appear every 5 seconds.
            int length;
            if(i % 5 == 0) {
                length = 4;
            } else {
                length = 1;
            }

            // Find the points at which the tick begins and ends.
            int startX = (int)(tickRadius * Math.cos(rad)) + this.center.getX();
            int startY = (int)(tickRadius * Math.sin(rad)) + this.center.getY();
            int endX = (int)((tickRadius - length) * Math.cos(rad)) + this.center.getX();
            int endY = (int)((tickRadius - length) * Math.sin(rad)) + this.center.getY();

            // Now draw a line.
            g.setColor(Color.GRAY);
            g.drawLine(startX, startY, endX, endY);
        }

        // Render the hands of the clock.
        for (Hand hand : hands) {
            Coordinate endpoint = hand.getEndpoint();
            if (hand instanceof SecondHand) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawLine(this.center.getX(), this.center.getY(), endpoint.getX(), endpoint.getY());
        }

        // Throw in some text in the middle.
        // Serious assistance from: https://stackoverflow.com/a/27740330
        Dimension panelSize = getSize();
        Font font = new Font("Arial", Font.BOLD, 10);
        FontMetrics metrics = g.getFontMetrics(font); // This class includes methods that help us size our piece of text (in pixels).

        // This is for the date.
        ZonedDateTime date = ZonedDateTime.now(this.getClock());
        String datestr = date.format(DateTimeFormatter.ofPattern("MMM d, uuuu"));
        int datestr_x = (int)(panelSize.getWidth() - metrics.stringWidth(datestr)) / 2;

        // This is for the location/timezone.
        String loc = String.format("%s (%s)", this.options.getFormalLocation(), this.options.getZoneID().getId());
        int loc_x = (int)(panelSize.getWidth() - metrics.stringWidth(loc)) / 2; // Get the x position in which the text will start.

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(datestr, datestr_x, 40);
        g.drawString(loc, loc_x, 50);
    }
}