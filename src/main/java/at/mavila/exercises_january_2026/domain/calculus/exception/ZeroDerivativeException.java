package at.mavila.exercises_january_2026.domain.calculus.exception;

import java.math.BigDecimal;

/**
 * Thrown when the polynomial's derivative evaluates to exactly zero at the current iterate, making the Newton-Raphson
 * division undefined.
 *
 * @author mavila
 * @since 2026-03-30
 */
public class ZeroDerivativeException extends RuntimeException {

  private final BigDecimal x;
  private final int iteration;

  /**
   * Constructs a new exception with the iterate and iteration index where the derivative vanished.
   *
   * @param x
   *                    the iterate at which f′(x) = 0
   * @param iteration
   *                    the zero-based iteration index
   */
  public ZeroDerivativeException(final BigDecimal x, final int iteration) {
    super("Derivative is zero at x = %s. Newton-Raphson cannot continue.".formatted(x));
    this.x = x;
    this.iteration = iteration;
  }

  /**
   * Returns the iterate at which the derivative was zero.
   *
   * @return the x value
   */
  public BigDecimal getX() {
    return x;
  }

  /**
   * Returns the zero-based iteration index at which the derivative vanished.
   *
   * @return the iteration number
   */
  public int getIteration() {
    return iteration;
  }
}
