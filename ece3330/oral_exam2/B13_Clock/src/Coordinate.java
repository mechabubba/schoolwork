/**
 * A very simple coordinate class.
 * <p>
 * Originally I utilized java.awt.Point to handle 2D coordinates, however this class was not suitable for dealing with
 * integer coordinates. It took in integers, however it returned doubles, meaning if I wanted to interact with the
 * Graphics library at all, I'd have to cast to an integer <i>every time.</i>
 * <p>
 * This class simply takes in an X and Y integer coordinate, and returns them as integers.
 * <p>
 * Some notes;
 * <ul>
 * <li>This is not thread-safe; there is no synchronization among the instance variables here. This is, for the most part, not a problem; the data coordinates use in this project is not mutable.</li>
 * <li>Exception: Clock hand endpoints. These are only mutated by one class; the hand itself. They're mutated on an even schedule of 1 second, so there is only a downside of the hand being behind.</li>
 * </ul>
 */
public class Coordinate {
    private int x;
    private int y;

    /**
     * Constructs a coordinate; by default, it is set to (0, 0).
     */
    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructs a coordinate with provided values.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the X coordinate.
     * @return An int representing the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate.
     * @return An int representing the Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the X coordinate.
     * @param x The value you want to set X to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y coordinate.
     * @param y The value you want to set Y to.
     */
    public void setY(int y) {
        this.y = y;
    }
}
