package CapstoneProject.app.exceptions;

/**
 * Exception to throw when a given ExpenseId is not found
 * in the database.
 */
public class ExpenseNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1230785223023147290L;

    /**
     * Exception with no message or cause.
     */
    public ExpenseNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public ExpenseNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public ExpenseNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public ExpenseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
