package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Thrown when the initial guess supplied to Newton-Raphson is {@code null}.
 *
 * @author mavila
 * @since 2026-03-30
 */
public class InvalidInitialGuessException extends RuntimeException {

  /**
   * Constructs a new exception with a fixed descriptive message.
   */
  public InvalidInitialGuessException() {
    super("Initial guess must not be null");
  }
}
