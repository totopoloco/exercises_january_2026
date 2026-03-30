package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Thrown when the maximum iteration count is {@code null} or not strictly positive.
 *
 * @author mavila
 * @since 2026-03-30
 */
public class InvalidMaxIterationsException extends RuntimeException {

  private final Integer maxIterations;

  /**
   * Constructs a new exception embedding the invalid iteration limit.
   *
   * @param maxIterations
   *                        the invalid value (may be {@code null})
   */
  public InvalidMaxIterationsException(final Integer maxIterations) {
    super("Max iterations must be a positive integer, got: %s".formatted(maxIterations));
    this.maxIterations = maxIterations;
  }

  /**
   * Returns the invalid max-iterations value that triggered this exception.
   *
   * @return the max-iterations value, possibly {@code null}
   */
  public Integer getMaxIterations() {
    return maxIterations;
  }
}
