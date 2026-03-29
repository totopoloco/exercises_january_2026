package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Exception thrown when polynomial coefficients are invalid.
 *
 * @author mavila
 * @since 2026-03-22
 */
public class InvalidPolynomialException extends RuntimeException {

  private final String reason;

  /**
   * Creates a new exception with a reason message.
   *
   * @param reason the validation reason
   */
  public InvalidPolynomialException(final String reason) {
    super(reason);
    this.reason = reason;
  }

  /**
   * Returns the validation reason.
   *
   * @return reason
   */
  public String getReason() {
    return reason;
  }
}
