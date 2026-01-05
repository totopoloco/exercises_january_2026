package at.mavila.exercises_january_2026.components;

import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LongestPalindromeSubstringExtractor {

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
        log.debug("STEP 1: PREPROCESSING COMPLETE");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("  Original:     \"{}\"", input);
        log.debug("  Preprocessed: \"{}\"", new String(preprocessed));
        log.debug("  Length:       {} → {}", input.length(), preprocessed.length);

        // 2. Initialize variables for Manacher's algorithm
        int n = preprocessed.length;
        int[] palindromeRadii = new int[n]; // palindrome_radii array
        int center = 0, right = 0; // center and right_boundary of rightmost palindrome
        int maxLen = 0, maxCenter = 0; // track longest palindrome

        log.debug("");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("STEP 2: INITIALIZE VARIABLES");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("  n (length)        = {}", n);
        log.debug("  palindrome_radii  = new int[{}] (all zeros)", n);
        log.debug("  center            = {}", center);
        log.debug("  right_boundary    = {}", right);
        log.debug("  maxLen            = {}", maxLen);
        log.debug("  maxCenter         = {}", maxCenter);

        log.debug("");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("STEP 3: MAIN LOOP - ITERATE FROM i=1 TO i={}", n - 2);
        log.debug("═══════════════════════════════════════════════════════════════════════════════");

        for (int i = 1; i < n - 1; i++) {
            log.debug("");
            log.debug("┌─────────────────────────────────────────────────────────────────────────────┐");
            log.debug("│ ITERATION {}: i = {} (char = '{}')", i, i, preprocessed[i]);
            log.debug("└─────────────────────────────────────────────────────────────────────────────┘");

            // Determine which scenario applies
            final int mirror = 2 * center - i;
            log.debug("  mirror = 2 * center - i = 2 * {} - {} = {}", center, i, mirror);
            boolean insideBoundary = i < right;

            if (insideBoundary) {
                log.debug("  Mirroring (i < right_boundary)");
                log.debug("");
                log.debug("  ┌─ MIRRORING ─────────────────────────────────────────────────────────────┐");
                log.debug("  │  Calculating initial p[{}]:", i);
                log.debug("  │    right_boundary - i = {} - {} = {}", right, i, right - i);
                log.debug("  │    p[mirror]          = p[{}] = {}", mirror, palindromeRadii[mirror]);
                palindromeRadii[i] = Math.min(right - i, palindromeRadii[mirror]);
                log.debug("  │    p[{}] = min({}, {}) = {}", i, right - i, palindromeRadii[mirror], palindromeRadii[i]);
                log.debug("  └────────────────────────────────────────────────────────────────────────┘");
            } else {
                // Expansion Only (+ possible Update)
                log.debug("  SCENARIO of Expansion Only (i >= right_boundary): i = {}, right_boundary = {}", i, right);
            }

            // EXPANSION PHASE (common to all scenarios)
            log.debug("");
            log.debug("  ┌─ EXPANSION ─────────────────────────────────────────────────────────────┐");
            int expansionCount = 0;
            while (true) {
                // Calculate indices for comparison
                int offset = palindromeRadii[i] + 1;
                int leftIndex = i - offset;
                int rightIndex = i + offset;
                char leftChar = preprocessed[leftIndex];
                char rightChar = preprocessed[rightIndex];

                log.debug("  │  Attempt #{}:", expansionCount + 1);
                log.debug("  │    offset     = palindromeRadii[{}] + 1 = {} + 1 = {}", i, palindromeRadii[i], offset);
                log.debug("  │    leftIndex  = i - offset = {} - {} = {}", i, offset, leftIndex);
                log.debug("  │    rightIndex = i + offset = {} + {} = {}", i, offset, rightIndex);
                log.debug("  │    s[{}]='{}' vs s[{}]='{}'", leftIndex, leftChar, rightIndex, rightChar);

                if (leftChar == rightChar) {
                    expansionCount++;
                    palindromeRadii[i]++;
                    log.debug("  │    → MATCH! palindromeRadii[{}]++ = {}", i, palindromeRadii[i]);
                } else {
                    log.debug("  │    → DON'T MATCH, stop expansion");
                    break;
                }
            }
            log.debug("  │  Result: palindromeRadii[{}] = {}", i, palindromeRadii[i]);
            log.debug("  └────────────────────────────────────────────────────────────────────────┘");

            // UPDATE center/right_boundary if palindrome extends past current boundary
            log.debug("");
            int newRightBoundary = i + palindromeRadii[i];
            log.debug("  ┌─ CHECK UPDATE ─────────────────────────────────────────────────────────┐");
            log.debug("  │  newRightBoundary = i + palindromeRadii[i]");
            log.debug("  │                   = {} + {}", i, palindromeRadii[i]);
            log.debug("  │                   = {}", newRightBoundary);
            log.debug("  │  Compare: newRightBoundary ({}) > right_boundary ({}) ?", newRightBoundary, right);
            if (newRightBoundary > right) {
                log.debug("  │  → YES! Update center and right_boundary");
                int oldCenter = center;
                int oldRight = right;
                center = i;
                right = newRightBoundary;
                log.debug("  │    center:         {} → {}", oldCenter, center);
                log.debug("  │    right_boundary: {} → {}", oldRight, right);
            } else {
                log.debug("  │  → NO, keep current values");
            }
            log.debug("  └────────────────────────────────────────────────────────────────────────┘");

            // Track longest palindrome found
            if (palindromeRadii[i] > maxLen) {
                int oldMaxLen = maxLen;
                maxLen = palindromeRadii[i];
                maxCenter = i;
                int tempStart = (maxCenter - maxLen) / 2;
                String currentBest = input.substring(tempStart, tempStart + maxLen);
                log.debug("");
                log.debug("  ★★ NEW LONGEST PALINDROME: maxLen {} → {}, palindrome = \"{}\"",
                        oldMaxLen, maxLen, currentBest);
            }

            printPArray(n, palindromeRadii);
        }

        // 3. Extract the original substring
        log.debug("");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("STEP 4: EXTRACT RESULT");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        final int start = (maxCenter - maxLen) / 2; // Convert preprocessed index to original
        log.debug("  maxCenter = {}, maxLen = {}", maxCenter, maxLen);
        log.debug("");
        log.debug("  Formula: start = (maxCenter - maxLen) / 2");
        log.debug("                 = ({} - {}) / 2 = {}", maxCenter, maxLen, start);
        log.debug("");
        log.debug("  Result: s.substring({}, {} + {}) = s.substring({}, {})", start, start, maxLen, start,
                start + maxLen);

        String result = input.substring(start, start + maxLen);
        log.debug("");
        log.debug("╔══════════════════════════════════════════════════════════════════════════════╗");
        log.debug("║ RESULT: \"{}\"", result);
        log.debug("║ Length: {}", result.length());
        log.debug("╚══════════════════════════════════════════════════════════════════════════════╝");

        return result;
    }

    private void printPArray(int n, int[] palindromeRadii) {
        if (!log.isDebugEnabled()) {
            return;
        }
        // Print current palindrome_radii array state
        StringBuilder pArray = new StringBuilder("  palindrome_radii = [");
        for (int k = 0; k < n; k++) {
            pArray.append(palindromeRadii[k]);
            if (k < n - 1)
                pArray.append(", ");
        }
        pArray.append("]");
        log.debug("{}", pArray.toString());
    }

    private char[] preprocess(String input) {
        StringBuilder sb = new StringBuilder("^");
        char[] charArray = input.toCharArray();
        for (int n = 0; n < charArray.length; n++) {
            sb.append("#").append(charArray[n]);
        }
        sb.append("#$");
        final String extractedString = sb.toString();
        log.debug("Preprocessed string: {}", extractedString);
        final char[] preprocessed = extractedString.toCharArray();
        return preprocessed;
    }

}
