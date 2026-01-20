package at.mavila.exercises_january_2026.domain.string;

import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Domain service for extracting the longest palindromic substring.
 *
 * <p>
 * This component implements <strong>Manacher's Algorithm</strong> to find
 * the longest palindromic substring in linear time.
 * </p>
 *
 * <h2>Algorithm Overview</h2>
 * <ol>
 * <li>Preprocess string by inserting separators (handles odd/even lengths
 * uniformly)</li>
 * <li>Maintain rightmost palindrome boundary and center</li>
 * <li>Use mirror property to skip redundant comparisons</li>
 * <li>Expand around each center as needed</li>
 * </ol>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(n) - each character processed at most twice</li>
 * <li><b>Space:</b> O(n) for the preprocessed string and radii array</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
@Slf4j
public class PalindromeExtractor {

  /**
   * Finds the longest palindromic substring in the given string.
   *
   * @param input the input string (length must be between 1 and 1000)
   * @return the longest palindromic substring
   * @throws IllegalArgumentException if input is null, empty, or exceeds 1000
   *                                  characters
   */
  public String longestPalindrome(String input) {

    if (Objects.isNull(input) || input.length() < 1 || input.length() > 1000) {
      throw new IllegalArgumentException("Input string must not be null or empty");
    }

    log.debug("╔══════════════════════════════════════════════════════════════════════════════╗");
    log.debug("║           MANACHER'S ALGORITHM - LONGEST PALINDROMIC SUBSTRING               ║");
    log.debug("╚══════════════════════════════════════════════════════════════════════════════╝");
    log.debug("INPUT: \"{}\"", input);

    // 1. Preprocess the string to insert separators
    char[] preprocessed = preprocess(input);

    log.debug("");
    log.debug("═══════════════════════════════════════════════════════════════════════════════");
    log.debug("STEP 1: PREPROCESSING");
    log.debug("═══════════════════════════════════════════════════════════════════════════════");
    log.debug("  Why? Insert '#' between chars so ALL palindromes have ODD length.");
    log.debug("  Original:     \"{}\" (length {})", input, input.length());
    log.debug("  Preprocessed: \"{}\" (length {})", new String(preprocessed), preprocessed.length);

    // 2. Initialize variables for Manacher's algorithm
    int n = preprocessed.length;
    int[] pradii = new int[n]; // palindrome radii
    int center = 0, right = 0; // current rightmost palindrome
    int maxLen = 0, maxCenter = 0; // best palindrome seen

    log.debug("");
    log.debug("═══════════════════════════════════════════════════════════════════════════════");
    log.debug("STEP 2: INITIALIZE VARIABLES");
    log.debug("═══════════════════════════════════════════════════════════════════════════════");
    log.debug("  pradii[]       = array of {} zeros (stores palindrome radius at each index)", n);

    log.debug("");
    log.debug("═══════════════════════════════════════════════════════════════════════════════");
    log.debug("STEP 3: MAIN LOOP (currentIndex from 1 to {})", n - 2);
    log.debug("═══════════════════════════════════════════════════════════════════════════════");

    for (int currentIndex = 1; currentIndex < n - 1; currentIndex++) {
      log.debug("");
      log.debug("┌─────────────────────────────────────────────────────────────────────────────┐");
      log.debug("│ ITERATION: currentIndex = {} (char = '{}')", currentIndex, preprocessed[currentIndex]);
      log.debug("└─────────────────────────────────────────────────────────────────────────────┘");

      final int mirror = 2 * center - currentIndex;

      if (currentIndex < right) {
        // SCENARIO: Mirroring - we can reuse information from mirror position
        int distanceToRight = right - currentIndex;
        int mirrorRadius = pradii[mirror];
        pradii[currentIndex] = Math.min(distanceToRight, mirrorRadius);
      }

      // Expansion phase
      palindromeDetector(preprocessed, pradii, currentIndex);

      final int currentPalindromeRadius = pradii[currentIndex];
      final int expandedIndex = currentIndex + currentPalindromeRadius;

      // Update rightmost palindrome if needed
      if (expandedIndex > right) {
        center = currentIndex;
        right = expandedIndex;
      }

      // Track longest palindrome
      if (currentPalindromeRadius > maxLen) {
        maxLen = currentPalindromeRadius;
        maxCenter = currentIndex;
      }
    }

    // 3. Extract the original substring
    log.debug("");
    log.debug("═══════════════════════════════════════════════════════════════════════════════");
    log.debug("STEP 4: EXTRACT RESULT");
    log.debug("═══════════════════════════════════════════════════════════════════════════════");
    final int start = (maxCenter - maxLen) / 2;
    log.debug("  maxCenter = {}, maxLen = {}", maxCenter, maxLen);
    log.debug("  Formula: start = (maxCenter - maxLen) / 2 = {}", start);

    String result = input.substring(start, start + maxLen);
    log.debug("");
    log.debug("╔══════════════════════════════════════════════════════════════════════════════╗");
    log.debug("║ RESULT: \"{}\"", result);
    log.debug("║ Length: {}", result.length());
    log.debug("╚══════════════════════════════════════════════════════════════════════════════╝");

    return result;
  }

  private void palindromeDetector(char[] preprocessed, int[] pradii, int currentIndex) {
    while (true) {
      int offset = pradii[currentIndex] + 1;
      char charRight = preprocessed[currentIndex + offset];
      char charLeft = preprocessed[currentIndex - offset];

      if (charRight == charLeft) {
        pradii[currentIndex]++;
      } else {
        break;
      }
    }
  }

  private char[] preprocess(String input) {
    log.debug("  Preprocessing: Adding '^' at start, '#' between each char, '#$' at end");
    StringBuilder sb = new StringBuilder("^");
    char[] charArray = input.toCharArray();
    for (int n = 0; n < charArray.length; n++) {
      sb.append("#").append(charArray[n]);
    }
    sb.append("#$");
    char[] preprocessed = sb.toString().toCharArray();
    return preprocessed;
  }

}
