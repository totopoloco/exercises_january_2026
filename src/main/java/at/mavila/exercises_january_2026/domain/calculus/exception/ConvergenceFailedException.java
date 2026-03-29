package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Exception thrown when Newton-Raphson does not converge within the iteration
 * limit.
 *
 * @author mavila
 * @since 2026-03-22
 */
public class ConvergenceFailedException extends RuntimeException {

  private final int maxIterations;

  /**
   * Creates a new exception with max iteration limit context.
   *
   * @param maxIterations iteration limit used
   * @param message       validation message
   */
  public ConvergenceFailedException(final int maxIterations, final String message) {
    super(message);
    this.maxIterations = maxIterations;
  }

  /**
   * Returns max iterations limit used.
   *
   * @return max iterations
   */
  public int getMaxIterations() {
    return maxIterations;
  }
}
