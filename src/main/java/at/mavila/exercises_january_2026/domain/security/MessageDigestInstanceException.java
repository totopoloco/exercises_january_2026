package at.mavila.exercises_january_2026.domain.security;

/**
 * Exception thrown when MessageDigest instance creation fails.
 *
 * <p>
 * This typically occurs when the requested algorithm (e.g., MD5)
 * is not available in the security provider.
 * </p>
 *
 * @author mavila
 * @since January 2026
 */
public class MessageDigestInstanceException extends RuntimeException {

  public MessageDigestInstanceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public MessageDigestInstanceException(final Throwable cause) {
    super(cause);
  }

  public MessageDigestInstanceException(final String message) {
    super(message);
  }
}
