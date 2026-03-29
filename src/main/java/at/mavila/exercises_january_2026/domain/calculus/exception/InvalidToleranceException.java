package at.mavila.exercises_january_2026.domain.calculus.exception;

import java.math.BigDecimal;

/**
 * Exception thrown when epsilon tolerance is invalid.
 *
 * @author mavila
 * @since 2026-03-22
 */
public class InvalidToleranceException extends RuntimeException {

  private final BigDecimal epsilon;

  /**
   * Creates a new exception with epsilon context.
   *
   * @param epsilon invalid epsilon value
   * @param message validation message
   */
  public InvalidToleranceException(final BigDecimal epsilon, final String message) {
    super(message);
    this.epsilon = epsilon;
  }

  /**
   * Returns invalid epsilon value.
   *
   * @return epsilon
   */
  public BigDecimal getEpsilon() {
    return epsilon;
  }
}
