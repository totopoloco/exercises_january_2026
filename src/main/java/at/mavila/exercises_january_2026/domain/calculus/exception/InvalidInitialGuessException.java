package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Exception thrown when the initial guess is invalid.
 *
 * @author mavila
 * @since 2026-03-22
 */
public class InvalidInitialGuessException extends RuntimeException {

  /**
   * Creates a new exception with a message.
   *
   * @param message validation message
   */
  public InvalidInitialGuessException(final String message) {
    super(message);
  }
}
