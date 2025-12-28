package at.mavila.exercises_january_2026.components;

import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class LongestSubstring {

    public int longestUniqueSubstringLength(final String input) {
        if (Objects.isNull(input)) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        if (input.length() == 0) {
            throw new IllegalArgumentException("Input cannot be empty");
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
