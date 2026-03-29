package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Exception thrown when maximum iterations setting is invalid.
 *
 * @author mavila
 * @since 2026-03-22
 */
public class InvalidMaxIterationsException extends RuntimeException {

  private final int maxIterations;

  /**
   * Creates a new exception with max iteration context.
   *
   * @param maxIterations invalid max iteration value
   * @param message       validation message
   */
  public InvalidMaxIterationsException(final int maxIterations, final String message) {
    super(message);
    this.maxIterations = maxIterations;
  }

  /**
   * Returns invalid max iterations value.
   *
   * @return max iterations
   */
  public int getMaxIterations() {
    return maxIterations;
  }
}
