package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Thrown when Newton-Raphson iteration exhausts the maximum number of iterations without satisfying the convergence
 * criterion.
 *
 * @author mavila
 * @since 2026-03-30
 */
public class ConvergenceFailedException extends RuntimeException {

  private final int maxIterations;

  /**
   * Constructs a new exception embedding the iteration limit that was exhausted.
   *
   * @param maxIterations
   *                        the iteration limit that was reached
   */
  public ConvergenceFailedException(final int maxIterations) {
    super("Newton-Raphson did not converge within %d iterations".formatted(maxIterations));
    this.maxIterations = maxIterations;
  }

  /**
   * Returns the maximum iteration count that was exhausted.
   *
   * @return the max-iterations value
   */
  public int getMaxIterations() {
    return maxIterations;
  }
}
