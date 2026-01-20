package at.mavila.exercises_january_2026.domain.security;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Domain service for cracking numeric PINs from their MD5 hashes.
 *
 * <h2>Overview</h2>
 * <p>
 * This component performs an exhaustive search over all numeric PIN candidates
 * of lengths 1 through {@code maxLen} (default 5), computing the MD5 hash of
 * each
 * candidate and comparing it against the target hash.
 * </p>
 *
 * <h2>Mathematical Background</h2>
 * <p>
 * For a given maximum PIN length {@code M}, the algorithm searches through all
 * numeric strings from "0" to "99...9" (M digits). The total number of
 * candidates
 * is given by:
 * </p>
 *
 * <pre>
 * Total candidates = (10^(M+1) - 10) / 9
 * </pre>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O((10^(M+1) - 10) / 9) ≈ O(10^M) — exponential in PIN
 * length</li>
 * <li><b>Space:</b> O(M) for storing the candidate string</li>
 * </ul>
 *
 * <h2>Thread Safety</h2>
 * <p>
 * The MD5 {@link MessageDigest} instance is shared and accessed within a
 * synchronized block, making this component thread-safe for concurrent use.
 * </p>
 *
 * @author mavila
 * @since January 2026
 * @see MessageDigest
 */
@Component
@Slf4j
public class PinCracker {

  private static final MessageDigest MD5;

  static {
    try {
      MD5 = MessageDigest.getInstance("MD5");
    } catch (final Exception e) {
      throw new MessageDigestInstanceException(e.getMessage(), e);
    }
  }

  /**
   * For callbacks during the cracking process.
   */
  @FunctionalInterface
  public interface ProgressListener {
    /**
     * Called after each candidate has been tried.
     *
     * @param candidate  the PIN string that has just been tried
     * @param triedSoFar number of candidates examined so far
     * @return true to continue, false to abort the search
     */
    boolean onTrial(final String candidate, final long triedSoFar);
  }

  /**
   * Cracks a PIN hash using default maximum length of 5 digits.
   *
   * @param hash the MD5 hash (hex string) of the target PIN
   * @return the recovered PIN string, or {@code null} if not found
   */
  public String crack(final String hash) {
    return this.crack(hash, 5, createProgressListener());
  }

  /**
   * Cracks a PIN hash with a specified maximum length.
   *
   * @param hash   the MD5 hash (hex string) of the target PIN
   * @param maxLen the maximum PIN length to search (must be ≥ 1)
   * @return the recovered PIN string, or {@code null} if not found
   * @throws IllegalArgumentException if maxLen is less than 1
   */
  public String crack(final String hash, final int maxLen) {
    return this.crack(hash, maxLen, createProgressListener());
  }

  /**
   * Cracks a PIN hash with progress reporting using default maximum length of 5.
   *
   * @param hash     the MD5 hash (hex string) of the target PIN
   * @param listener callback for progress updates; may return {@code false} to
   *                 abort
   * @return the recovered PIN string, or {@code null} if not found or aborted
   */
  public String crack(final String hash, final ProgressListener listener) {
    return this.crack(hash, 5, listener);
  }

  /**
   * Cracks a PIN hash by exhaustive search over all numeric PINs up to
   * {@code maxLen} digits.
   *
   * @param hash     the MD5 hash (hex string) of the target PIN
   *                 (case-insensitive)
   * @param maxLen   the maximum PIN length to search (must be ≥ 1)
   * @param listener optional callback for progress updates; may return
   *                 {@code false} to abort
   * @return the recovered PIN string (with leading zeros preserved), or
   *         {@code null} if not found
   * @throws IllegalArgumentException if maxLen is less than 1
   */
  public String crack(final String hash, final int maxLen, final ProgressListener listener) {
    if (maxLen < 1) {
      throw new IllegalArgumentException("maxLen must be at least 1");
    }

    final String target = hash.trim().toLowerCase();
    long trials = 0L;

    for (int len = 1; len <= maxLen; len++) {
      int limit = (int) Math.pow(10, len) - 1;
      for (int i = 0; i <= limit; i++) {
        final String candidate = String.format("%0" + len + "d", i);
        trials = trials + 1;

        // Optional progress hook - abort if listener returns false
        if (Objects.nonNull(listener) && !listener.onTrial(candidate, trials)) {
          return null; // Aborted by caller
        }
        // Compute MD5 hex of the candidate
        final String digest = md5Hex(candidate);

        if (digest.equals(target)) {
          return candidate;
        }
      }
    }
    // Exhausted the search space without finding a match
    return null;
  }

  /**
   * Returns the 32 character lower-case representation of the MD5 (text).
   *
   * @param text the input text
   * @return the MD5 hash as hex string
   */
  private static String md5Hex(String text) {
    byte[] bytes;
    synchronized (MD5) { // MessageDigest is not thread‑safe
      MD5.update(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      bytes = MD5.digest();
      MD5.reset();
    }
    // Convert the 16‑byte array to a hex string
    Formatter fmt = new Formatter();
    for (byte b : bytes) {
      fmt.format("%02x", b);
    }
    String result = fmt.toString();
    fmt.close();
    return result;
  }

  private ProgressListener createProgressListener() {
    return (candidate, triedSoFar) -> {
      if (triedSoFar % 100000 == 0) {
        log.info("Tried so far: {} (latest: {})", triedSoFar, candidate);
      }
      return true; // continue searching
    };
  }
}
