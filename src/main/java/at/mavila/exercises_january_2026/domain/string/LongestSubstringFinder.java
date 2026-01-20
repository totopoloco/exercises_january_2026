package at.mavila.exercises_january_2026.domain.string;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

/**
 * Domain service for finding the longest substring without repeating
 * characters.
 * 
 * <p>
 * This component implements the <strong>Sliding Window</strong> algorithm to
 * efficiently find the longest contiguous sequence of characters in a string
 * where
 * no character appears more than once.
 * </p>
 * 
 * <h2>Algorithm</h2>
 * <p>
 * Uses two pointers (left and right) to maintain a "window" of unique
 * characters:
 * </p>
 * <ol>
 * <li>Expand the window by moving the right pointer forward</li>
 * <li>If a duplicate is found, shrink by moving left past the previous
 * occurrence</li>
 * <li>Track the maximum window length seen</li>
 * </ol>
 * 
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(n) where n is the string length</li>
 * <li><b>Space:</b> O(1) or O(256) for ASCII character map</li>
 * </ul>
 * 
 * @author mavila
 * @since January 2026
 */
@Component
public class LongestSubstringFinder {

    /**
     * Finds the length of the longest substring without repeating characters.
     *
     * @param input the string to analyze (may contain any ASCII characters)
     * @return the length of the longest unique substring, or 0 if input is
     *         null/empty
     */
    public int longestUniqueSubstringLength(final String input) {
        if (Objects.isNull(input)) {
            return 0;
        }

        if (input.length() == 0) {
            return 0;
        }

        final Map<Character, Integer> lastSeen = new java.util.HashMap<>(256);
        // Fill the map with -1 for each ASCII character (256 characters)
        IntStream.range(0, 256).forEach(i -> lastSeen.put((char) i, -1));

        int left = 0; // start of the sliding window
        int maxLen = 0;

        for (int right = 0; right < input.length(); right++) {
            Character currentChar = Character.valueOf(input.charAt(right));
            // Duplicate detected inside the current window?
            Integer lastSeenIndex = lastSeen.get(currentChar);
            if (lastSeenIndex >= left) {
                // Move the left border just after the previous occurrence
                left = lastSeenIndex + 1;
            }
            // Record the newest position of the character
            lastSeen.put(currentChar, right);
            // Update the answer if this window is longer
            int windowLength = right - left + 1;
            maxLen = Math.max(maxLen, windowLength);
        }

        return maxLen;
    }

}
