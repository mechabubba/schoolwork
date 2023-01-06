import java.time.ZonedDateTime;

/**
 * This class represents a minute hand, the second-longest hand on the clock.
 */
public class MinuteHand extends Hand {
    private static final double length = 0.7; // Length of the minute hand, relative to the radius of the clock in ClockPanel.
    private final Coordinate endpoint;

    /**
     * Constructs a minute hand.
     * @param panel The panel the clock is being rendered on; contains information necessary for the Hand's functioning.
     */
    public MinuteHand(ClockPanel panel) {
        super(panel);
        this.endpoint = new Coordinate();
    }

    /**
     * Infinitely updates the position of the minute hand.
     */
    public void run() {
        while(true) {
            // Get the local time of the clock.
            ZonedDateTime date = ZonedDateTime.now(this.panel.getClock());
            int minute = date.getMinute();

            // Determine where the hand should be and set the endpoint.
            double rad = ((double)minute / 60) * (2 * Math.PI) - (Math.PI / 2);
            Coordinate center = this.panel.getCenter();
            this.endpoint.setX((int)(Math.cos(rad) * this.panel.getTickRadius() * length) + center.getX());
            this.endpoint.setY((int)(Math.sin(rad) * this.panel.getTickRadius() * length) + center.getY());

            // We don't want the thread running as fast as it can gobbling up resources.
            // Throw in some sleep time.
            try {
                Thread.sleep(granularity);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets the endpoint of the clock hand, from the clocks center.
     * @return The endpoint of the clock hand.
     */
    public Coordinate getEndpoint() {
        return endpoint;
    }
}