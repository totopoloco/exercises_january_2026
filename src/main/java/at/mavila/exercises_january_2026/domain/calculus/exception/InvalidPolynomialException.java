package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Thrown when the polynomial coefficient list violates one of the input rules: null or empty list, fewer than two
 * coefficients, null element, or zero leading coefficient.
 *
 * @author mavila
 * @since 2026-03-30
 */
public class InvalidPolynomialException extends RuntimeException {

  private final String reason;

  /**
   * Constructs a new exception whose message is the validation reason itself.
   *
   * @param reason
   *                 human-readable description of the violated constraint
   */
  public InvalidPolynomialException(final String reason) {
    super(reason);
    this.reason = reason;
  }

  /**
   * Returns the reason the polynomial was considered invalid.
   *
   * @return the violation reason
   */
  public String getReason() {
    return reason;
  }
}
