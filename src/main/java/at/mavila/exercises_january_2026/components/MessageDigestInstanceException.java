package at.mavila.exercises_january_2026.components;

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
