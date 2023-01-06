/**
 * This class represents an ordered list of three values.
 */
public class ThreeTuple {
    private final int a;
    private final int b;
    private final int c;

    /**
     * Creates a tuple with a length of 3.
     * @param a Value 1, "a".
     * @param b Value 2, "b".
     * @param c Value 3, "c".
     */
    public ThreeTuple(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Gets the first value.
     * @return The first value.
     */
    public int getA() {
        return this.a;
    }

    /**
     * Gets the second value.
     * @return The second value.
     */
    public int getB() {
        return this.b;
    }

    /**
     * Gets the third value.
     * @return The third value.
     */
    public int getC() {
        return this.c;
    }

    /**
     * Returns the values of this tuple in polynomial form, as a String.
     * @return The polynomial of the three values.
     */
    public String getPolynomial() {
        return String.format("%dx^2 %s %dx %s %d", this.a, this.b < 0 ? "-" : "+", Math.abs(this.b), this.c < 0 ? "-" : "+", Math.abs(this.c));
    }
}
