package exception;

/**
 * Base Validator exception class.
 *
 * @author Dani
 */

public class ValidatorException extends CarRentalsAppException {
    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
