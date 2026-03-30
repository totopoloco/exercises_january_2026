package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Thrown when the decimal scale parameter is {@code null} or not strictly positive.
 *
 * @author mavila
 * @since 2026-03-30
 */
public class InvalidScaleException extends RuntimeException {

  private final Integer scale;

  /**
   * Constructs a new exception embedding the invalid scale value.
   *
   * @param scale
   *                the invalid value (may be {@code null})
   */
  public InvalidScaleException(final Integer scale) {
    super("Scale must be a positive integer, got: %s".formatted(scale));
    this.scale = scale;
  }

  /**
   * Returns the invalid scale value that triggered this exception.
   *
   * @return the scale value, possibly {@code null}
   */
  public Integer getScale() {
    return scale;
  }
}
