/**
 * This class represents a operator in the equation.
 */
public class OperatorSymbol implements Symbol {
    private final OpType type;

    /**
     * Attempts to construct an operator based on the symbol.
     * @param input The operator to construct the symbol from.
     * @throws OperatorException Thrown if the symbol is unknown.
     */
    public OperatorSymbol(String input) throws OperatorException {
        char op = input.charAt(0);
        switch(op) {
            case '+': this.type = OpType.ADDITION; break;
            case '-': this.type = OpType.SUBTRACTION; break;
            case '*': this.type = OpType.MULTIPLICATION; break;
            case '/': this.type = OpType.DIVISION; break;
            case '^': this.type = OpType.EXPONENTIATION; break;
            case '%': this.type = OpType.MODULATION; break;
            case ')': this.type = OpType.END; break;
            default: {
                throw new OperatorException(String.format("Unknown operator %s.", input));
            }
        }
    }

    /**
     * Gets an enum representing the operator representing this symbol.
     * @return The operators respective enum.
     */
    public OpType get() {
        return type;
    }
}