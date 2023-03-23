package dev.greenadine.utils.exception;

/**
 * Thrown when a value is passed into a switch statement which case is not handled (implemented).
 *
 * @since 0.1
 * @author Greenadine
 */
public class NotImplementedException extends RuntimeException {

    public NotImplementedException(String message) {
        super(message);
    }
}
