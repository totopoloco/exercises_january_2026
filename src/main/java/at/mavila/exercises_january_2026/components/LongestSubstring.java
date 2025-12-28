package at.mavila.exercises_january_2026.components;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class LongestSubstring {

    /**
     * Finds the length of the longest substring without repeating characters.
     *
     * <p>
     * This method implements the <strong>Sliding Window</strong> algorithm to
     * efficiently
     * find the longest contiguous sequence of characters in a string where no
     * character
     * appears more than once. This is a classic problem in string manipulation and
     * is
     * commonly asked in technical interviews.
     * </p>
     *
     * <h2>Algorithm Explanation</h2>
     * <p>
     * The algorithm uses two pointers ({@code left} and {@code right}) to maintain
     * a "window"
     * of characters currently being considered. As we iterate through the string:
     * </p>
     * <ol>
     * <li><strong>Expand the window:</strong> Move the {@code right} pointer
     * forward to include
     * a new character.</li>
     * <li><strong>Check for duplicates:</strong> If the new character was already
     * seen within
     * the current window, shrink the window by moving {@code left} just past the
     * previous
     * occurrence of that character.</li>
     * <li><strong>Track the maximum:</strong> After each step, calculate the
     * current window
     * length and update the maximum if this window is larger.</li>
     * </ol>
     *
     * <h2>Time and Space Complexity</h2>
     * <ul>
     * <li><strong>Time Complexity:</strong> O(n) where n is the length of the input
     * string.
     * Each character is visited at most twice (once by the right pointer, once by
     * the left).</li>
     * <li><strong>Space Complexity:</strong> O(1) or O(256) = O(1) since we use a
     * fixed-size
     * map for ASCII characters (256 possible values).</li>
     * </ul>
     *
     * <h2>Supported Characters</h2>
     * <p>
     * This implementation supports all ASCII characters (codes 0-255), including:
     * </p>
     * <ul>
     * <li>Lowercase letters: a-z</li>
     * <li>Uppercase letters: A-Z (case-sensitive, so 'A' and 'a' are
     * different)</li>
     * <li>Digits: 0-9</li>
     * <li>Special characters: !@#$%^&*() etc.</li>
     * <li>Whitespace: spaces, tabs, etc.</li>
     * </ul>
     *
     * <h2>Usage Examples</h2>
     *
     * <h3>Example 1: Basic case with repeating characters</h3>
     * 
     * <pre>{@code
     * // Input: "abcabcbb"
     * // The longest substring without repeating characters is "abc"
     * int result = longestUniqueSubstringLength("abcabcbb");
     * // result = 3
     * }</pre>
     *
     * <h3>Example 2: All same characters</h3>
     * 
     * <pre>{@code
     * // Input: "bbbbb"
     * // Every character is the same, so the longest unique substring is just
     * // "b"
     * int result = longestUniqueSubstringLength("bbbbb");
     * // result = 1
     * }</pre>
     *
     * <h3>Example 3: Unique substring at the end</h3>
     * 
     * <pre>{@code
     * // Input: "pwwkew"
     * // The longest substring is "wke" (or "kew"), both have length 3
     * // Note: "pwke" is a subsequence, not a substring (must be contiguous)
     * int result = longestUniqueSubstringLength("pwwkew");
     * // result = 3
     * }</pre>
     *
     * <h3>Example 4: All unique characters</h3>
     * 
     * <pre>{@code
     * // Input: "abcdefg"
     * // All characters are unique, so the entire string is the answer
     * int result = longestUniqueSubstringLength("abcdefg");
     * // result = 7
     * }</pre>
     *
     * <h3>Example 5: Mixed alphanumeric</h3>
     * 
     * <pre>{@code
     * // Input: "abc123abc"
     * // The longest unique substring is "abc123" or "123abc" (both length 6)
     * int result = longestUniqueSubstringLength("abc123abc");
     * // result = 6
     * }</pre>
     *
     * <h3>Example 6: With spaces</h3>
     * 
     * <pre>{@code
     * // Input: "a b c d"
     * // Spaces count as characters! The space repeats, so max unique is "a b"
     * // = 3
     * int result = longestUniqueSubstringLength("a b c d");
     * // result = 3
     * }</pre>
     *
     * <h3>Example 7: Special characters</h3>
     * 
     * <pre>{@code
     * // Input: "a!@#$%b"
     * // All characters are unique
     * int result = longestUniqueSubstringLength("a!@#$%b");
     * // result = 7
     * }</pre>
     *
     * <h3>Example 8: Single character</h3>
     * 
     * <pre>{@code
     * // Input: "a"
     * // Only one character, so the answer is 1
     * int result = longestUniqueSubstringLength("a");
     * // result = 1
     * }</pre>
     *
     * <h3>Example 9: Case sensitivity</h3>
     * 
     * <pre>{@code
     * // Input: "aAbBcC"
     * // Uppercase and lowercase are treated as different characters
     * // All 6 characters are unique
     * int result = longestUniqueSubstringLength("aAbBcC");
     * // result = 6
     * }</pre>
     *
     * <h2>Edge Cases</h2>
     * <ul>
     * <li><strong>Null input:</strong> Returns 0</li>
     * <li><strong>Empty string:</strong> Returns 0</li>
     * <li><strong>Single character:</strong> Returns 1</li>
     * <li><strong>All same characters:</strong> Returns 1</li>
     * <li><strong>All unique characters:</strong> Returns the string length</li>
     * </ul>
     *
     * @param input the string to analyze. May contain any ASCII characters
     *              including
     *              letters, digits, spaces, and special characters. The comparison
     *              is case-sensitive.
     * @return the length of the longest substring containing only unique
     *         (non-repeating)
     *         characters. Returns 0 if the input is {@code null} or empty.
     *
     * @see <a href=
     *      "https://leetcode.com/problems/longest-substring-without-repeating-characters/">
     *      LeetCode Problem #3: Longest Substring Without Repeating Characters</a>
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
        // Consists of English letters, digits, symbols and spaces.
        IntStream.range(0, 256).forEach(i -> lastSeen.put((char) i, -1));

        int left = 0; // start of the sliding window
        int maxLen = 0;

        for (int right = 0; right < input.length(); right++) {
            Character currentChar = Character.valueOf(input.charAt(right));
            // -------------------------------------------------
            // 2a. Duplicate detected inside the current window?
            // -------------------------------------------------
            Integer lastSeenIndex = lastSeen.get(currentChar);
            if (lastSeenIndex >= left) {
                // Move the left border just after the previous occurrence
                left = lastSeenIndex + 1;
            }
            //
            // -------------------------------------------------
            // 2b. Record the newest position of the character
            // -------------------------------------------------
            lastSeen.put(currentChar, right);
            // -------------------------------------------------
            // 2c. Update the answer if this window is longer
            // -------------------------------------------------
            int windowLength = right - left + 1;
            maxLen = Math.max(maxLen, windowLength);

        }

        return maxLen;

    }

}
