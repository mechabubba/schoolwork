/**
 * This class represents a number (a signed integer, to be specific) in the equation.
 */
public class NumberSymbol implements Symbol {
    private final int number;

    /**
     * Attempts to parse the provided number and constructs a number symbol.
     * @param input The value attempted to parse.
     * @throws NumberFormatException Thrown if the number cant be parsed.
     */
    public NumberSymbol(String input) throws NumberFormatException {
        this.number = Integer.parseInt(input); // This will throw an exception if an overflow occurs.
    }

    /**
     * Gets the number the symbol represents.
     * @return The number.
     */
    public int get() {
        return this.number;
    }
}