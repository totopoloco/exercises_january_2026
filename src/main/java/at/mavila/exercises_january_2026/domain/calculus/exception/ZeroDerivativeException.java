package at.mavila.exercises_january_2026.domain.calculus.exception;

import java.math.BigDecimal;

/**
 * Exception thrown when derivative is zero during Newton-Raphson iteration.
 *
 * @author mavila
 * @since 2026-03-22
 */
public class ZeroDerivativeException extends RuntimeException {

  private final BigDecimal x;
  private final int iteration;

  /**
   * Creates a new exception with derivative-zero context.
   *
   * @param x         current x value where derivative is zero
   * @param iteration iteration count where error occurred
   * @param message   validation message
   */
  public ZeroDerivativeException(final BigDecimal x, final int iteration, final String message) {
    super(message);
    this.x = x;
    this.iteration = iteration;
  }

  /**
   * Returns x value where derivative is zero.
   *
   * @return x value
   */
  public BigDecimal getX() {
    return x;
  }

  /**
   * Returns iteration number where derivative became zero.
   *
   * @return iteration number
   */
  public int getIteration() {
    return iteration;
  }
}
