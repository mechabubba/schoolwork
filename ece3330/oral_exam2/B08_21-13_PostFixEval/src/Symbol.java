/**
 * This class is the base representation of a Symbol, extended by OperatorSymbol and NumberSymbol.
 */
public interface Symbol {
    /**
     * An enum representing the different supported operators.
     */
    enum OpType {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        EXPONENTIATION,
        MODULATION,
        END // ')'
    }
}
