/**
 * A simple class which represents an error in arithmetic. No exception with a better description was offered, so this class describes something more specific.
 */
public class ArithmaticException extends Exception {
    public ArithmaticException() {
        super();
    }

    public ArithmaticException(String message) {
        super(message);
    }

    public ArithmaticException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArithmaticException(Throwable cause) {
        super(cause);
    }
}
