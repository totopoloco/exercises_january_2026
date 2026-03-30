package at.mavila.exercises_january_2026.domain.calculus.exception;

import java.math.BigDecimal;

/**
 * Thrown when the convergence tolerance (epsilon) is {@code null} or not strictly positive.
 *
 * @author mavila
 * @since 2026-03-30
 */
public class InvalidToleranceException extends RuntimeException {

  private final BigDecimal epsilon;

  /**
   * Constructs a new exception embedding the invalid epsilon value.
   *
   * @param epsilon
   *                  the invalid tolerance value (may be {@code null})
   */
  public InvalidToleranceException(final BigDecimal epsilon) {
    super("Epsilon must be a positive number, got: %s".formatted(epsilon));
    this.epsilon = epsilon;
  }

  /**
   * Returns the invalid epsilon that triggered this exception.
   *
   * @return the epsilon value, possibly {@code null}
   */
  public BigDecimal getEpsilon() {
    return epsilon;
  }
}
