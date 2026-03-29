package at.mavila.exercises_january_2026.domain.calculus.exception;

/**
 * Exception thrown when scale setting is invalid.
 *
 * @author mavila
 * @since 2026-03-22
 */
public class InvalidScaleException extends RuntimeException {

  private final int scale;

  /**
   * Creates a new exception with scale context.
   *
   * @param scale   invalid scale value
   * @param message validation message
   */
  public InvalidScaleException(final int scale, final String message) {
    super(message);
    this.scale = scale;
  }

  /**
   * Returns invalid scale value.
   *
   * @return scale
   */
  public int getScale() {
    return scale;
  }
}
