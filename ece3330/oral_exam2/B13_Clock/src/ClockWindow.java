import javax.swing.*;
import java.awt.*;

/**
 * The ClockWindow class represents the frame that our clock grid will be on.
 */
public class ClockWindow extends JFrame {
    /**
     * An array of the most populated US cities in all the US' timezones (and London). Data from <a href="https://qr.ae/pvQiPx">here</a>.
     * <p>
     * Exceptions;
     * <ul>
     * <li>London.</li>
     * <li>Chicago, IL was replaced with Iowa City, Iowa (go hawks).</li>
     * </ul>
     */
    private static final ClockPanelOptions[] clocks = {
        new ClockPanelOptions("Pago Pago, American Samoa", "UTC-11"),
        new ClockPanelOptions("Honolulu, Hawaii", "UTC-10"),
        new ClockPanelOptions("Anchorage, Alaska", "UTC-9"),
        new ClockPanelOptions("Los Angeles, California", "UTC-8"),
        new ClockPanelOptions("Phoenix, Arizona", "UTC-7"),
        new ClockPanelOptions("Iowa City, Iowa", "UTC-6"),
        new ClockPanelOptions("New York, New York", "UTC-5"),
        new ClockPanelOptions("San Juan, Puerto Rico", "UTC-4"),
        new ClockPanelOptions("Saipan, Northern Mariana Islands", "UTC+10"),
        new ClockPanelOptions("London, UK", "GMT")
    };

    public ClockWindow() {
        super("Clocks");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set a grid layout for all the clocks.
        setLayout(new GridLayout(2, 5));
        setSize(new Dimension(5 * 175, 2 * 175));
        setResizable(false);

        // Throw 'em in.
        render();
        setVisible(true);
    }

    /**
     * Renders Clocks based on the options in the static array above, and places them in the frame.
     */
    public void render() {
        for (ClockPanelOptions clock : clocks) {
            clock.setStartTime(System.currentTimeMillis());
            ClockPanel panel = new ClockPanel(clock);
            add(panel);
        }
    }
}
