/**
 * A simple class which represents an error with an operator. No exception with a better description was offered, so this class describes something more specific.
 */
public class OperatorException extends Exception {
    public OperatorException() {
        super();
    }

    public OperatorException(String message) {
        super(message);
    }

    public OperatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperatorException(Throwable cause) {
        super(cause);
    }
}
