/**
 * This class represents an abstract clock hand.
 */
public abstract class Hand implements Runnable {
    protected static final long granularity = 50; // The amount of ms to sleep before updating again. A lower-than-one-second granularity is set to compensate for other reasons a clocks hands may need to be rendered again, such as the frame being resized.
    protected ClockPanel panel;

    /**
     * Constructs an abstrct hand.
     * @param panel The panel the clock is being rendered on; contains information necessary for the Hand's functioning.
     */
    public Hand(ClockPanel panel) {
        this.panel = panel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void run();

    /**
     * Gets the endpoint coordinate to a hand.
     * @return The endpoint, with respect to the center.
     */
    public abstract Coordinate getEndpoint();
}