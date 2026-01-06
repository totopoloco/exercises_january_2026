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
        log.debug("STEP 1: PREPROCESSING");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("  Why? Insert '#' between chars so ALL palindromes have ODD length.");
        log.debug("  This unifies handling of 'aba' (odd) and 'bb' (even) palindromes.");
        log.debug("");
        log.debug("  Original:     \"{}\" (length {})", input, input.length());
        log.debug("  Preprocessed: \"{}\" (length {})", new String(preprocessed), preprocessed.length);

        // 2. Initialize variables for Manacher's algorithm
        int n = preprocessed.length;
        int[] pradii = new int[n]; // palindrome radii
        int center = 0, right = 0;// current rightmost palindrome
        int maxLen = 0, maxCenter = 0; // best palindrome seen

        log.debug("");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("STEP 2: INITIALIZE VARIABLES");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("  pradii[]       = array of {} zeros (stores palindrome radius at each index)", n);
        log.debug("  center         = {} (center of rightmost palindrome found)", center);
        log.debug("  right          = {} (right boundary of rightmost palindrome)", right);
        log.debug("  maxLen         = {} (longest palindrome radius found)", maxLen);
        log.debug("  maxCenter      = {} (center of longest palindrome)", maxCenter);

        log.debug("");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("STEP 3: MAIN LOOP (currentIndex from 1 to {})", n - 2);
        log.debug("═══════════════════════════════════════════════════════════════════════════════");

        for (int currentIndex = 1; currentIndex < n - 1; currentIndex++) {
            log.debug("");
            log.debug("┌─────────────────────────────────────────────────────────────────────────────┐");
            log.debug("│ ITERATION: currentIndex = {} (char = '{}')", currentIndex, preprocessed[currentIndex]);
            log.debug("└─────────────────────────────────────────────────────────────────────────────┘");
            log.debug("  State: center={}, right={}", center, right);

            final int mirror = 2 * center - currentIndex;
            log.debug("");
            log.debug("  ┌─ MIRROR CALCULATION ───────────────────────────────────────────────────┐");
            log.debug("  │  mirror = 2 * center - currentIndex");
            log.debug("  │        = 2 * {} - {} = {}", center, currentIndex, mirror);
            log.debug("  └────────────────────────────────────────────────────────────────────────┘");

            if (currentIndex < right) {
                // SCENARIO: Mirroring - we can reuse information from mirror position
                log.debug("");
                log.debug("  ┌─ MIRRORING (currentIndex < right) ─────────────────────────────────────┐");
                log.debug("  │  {} < {} → We're INSIDE the rightmost palindrome!", currentIndex, right);
                log.debug("  │");
                log.debug("  │  Key insight: Due to symmetry around 'center', the palindrome at");
                log.debug("  │  'currentIndex' mirrors the one at 'mirror'. But we can only trust");
                log.debug("  │  information up to 'right' boundary.");
                log.debug("  │");
                int distanceToRight = right - currentIndex;
                int mirrorRadius = pradii[mirror];
                log.debug("  │  distanceToRight = right - currentIndex = {} - {} = {}", right, currentIndex,
                        distanceToRight);
                log.debug("  │  mirrorRadius    = pradii[mirror] = pradii[{}] = {}", mirror, mirrorRadius);
                pradii[currentIndex] = Math.min(distanceToRight, mirrorRadius);
                log.debug("  │");
                log.debug("  │  pradii[{}] = min({}, {}) = {}", currentIndex, distanceToRight, mirrorRadius,
                        pradii[currentIndex]);
                log.debug("  │  (We take minimum because mirror's palindrome might extend beyond 'right')");
                log.debug("  └────────────────────────────────────────────────────────────────────────┘");
            } else {
                // SCENARIO: Expansion Only - starting fresh
                log.debug("");
                log.debug("  ┌─ NO MIRRORING (currentIndex >= right) ─────────────────────────────────┐");
                log.debug("  │  {} >= {} → We're OUTSIDE any known palindrome", currentIndex, right);
                log.debug("  │  Starting fresh with pradii[{}] = 0", currentIndex);
                log.debug("  └────────────────────────────────────────────────────────────────────────┘");
            }

            // Expansion phase
            log.debug("");
            log.debug("  ┌─ EXPANSION ────────────────────────────────────────────────────────────┐");
            log.debug("  │  Try to expand palindrome centered at currentIndex={}", currentIndex);
            palindromeDetector(preprocessed, pradii, currentIndex);
            log.debug("  │  Final radius: pradii[{}] = {}", currentIndex, pradii[currentIndex]);
            log.debug("  └────────────────────────────────────────────────────────────────────────┘");

            final int currentPalindromeRadius = pradii[currentIndex];
            final int expandedIndex = currentIndex + currentPalindromeRadius;

            // Update rightmost palindrome if needed
            log.debug("");
            log.debug("  ┌─ CHECK: Update rightmost palindrome? ──────────────────────────────────┐");
            log.debug("  │  expandedIndex = currentIndex + pradii[currentIndex]");
            log.debug("  │               = {} + {} = {}", currentIndex, currentPalindromeRadius, expandedIndex);
            log.debug("  │  Compare: expandedIndex ({}) > right ({}) ?", expandedIndex, right);
            if (expandedIndex > right) {
                log.debug("  │  → YES! This palindrome extends further right.");
                int oldCenter = center;
                int oldRight = right;
                center = currentIndex;
                right = expandedIndex;
                log.debug("  │    center: {} → {}", oldCenter, center);
                log.debug("  │    right:  {} → {}", oldRight, right);
            } else {
                log.debug("  │  → NO, keeping current center={}, right={}", center, right);
            }
            log.debug("  └────────────────────────────────────────────────────────────────────────┘");

            // Track longest palindrome
            log.debug("");
            log.debug("  ┌─ CHECK: New longest palindrome? ───────────────────────────────────────┐");
            log.debug("  │  Compare: pradii[{}] ({}) > maxLen ({}) ?", currentIndex, currentPalindromeRadius, maxLen);
            if (currentPalindromeRadius > maxLen) {
                int oldMaxLen = maxLen;
                maxLen = currentPalindromeRadius;
                maxCenter = currentIndex;
                int tempStart = (maxCenter - maxLen) / 2;
                String currentBest = input.substring(tempStart, tempStart + maxLen);
                log.debug("  │  → YES! New longest found.");
                log.debug("  │    maxLen:    {} → {}", oldMaxLen, maxLen);
                log.debug("  │    maxCenter: {}", maxCenter);
                log.debug("  │    palindrome: \"{}\"", currentBest);
            } else {
                log.debug("  │  → NO, keeping maxLen={}, maxCenter={}", maxLen, maxCenter);
            }
            log.debug("  └────────────────────────────────────────────────────────────────────────┘");

            // Show visual animation of current state
            if (log.isDebugEnabled()) {
                logVisualState(preprocessed, pradii, currentIndex, center, right, maxLen, maxCenter);
            }
        }

        // 3. Extract the original substring
        log.debug("");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        log.debug("STEP 4: EXTRACT RESULT");
        log.debug("═══════════════════════════════════════════════════════════════════════════════");
        final int start = (maxCenter - maxLen) / 2;
        log.debug("  maxCenter = {}, maxLen = {}", maxCenter, maxLen);
        log.debug("");
        log.debug("  Formula: start = (maxCenter - maxLen) / 2");
        log.debug("                 = ({} - {}) / 2 = {}", maxCenter, maxLen, start);
        log.debug("");
        log.debug("  Why divide by 2? Because preprocessed string has '#' between each char,");
        log.debug("  so indices in preprocessed are ~2x the indices in original string.");

        String result = input.substring(start, start + maxLen);
        log.debug("");
        log.debug("╔══════════════════════════════════════════════════════════════════════════════╗");
        log.debug("║ RESULT: \"{}\"", result);
        log.debug("║ Length: {}", result.length());
        log.debug("╚══════════════════════════════════════════════════════════════════════════════╝");

        return result;
    }

    /**
     * Logs a visual "animation" frame showing the current state of the algorithm.
     * Displays the preprocessed string, pradii array, position markers, and all key
     * variables.
     */
    private void logVisualState(char[] preprocessed, int[] pradii, int currentIndex,
            int center, int right, int maxLen, int maxCenter) {
        log.debug("");
        log.debug("  ═══════════════════════════════════════════════════════════════════════════");
        log.debug("  📊 VISUAL STATE");
        log.debug("  ═══════════════════════════════════════════════════════════════════════════");

        // Build index row
        StringBuilder indexRow = new StringBuilder("  idx:  ");
        for (int i = 0; i < preprocessed.length; i++) {
            indexRow.append(String.format("%3d", i));
        }
        log.debug("{}", indexRow.toString());

        // Build character row
        StringBuilder charRow = new StringBuilder("  char: ");
        for (int i = 0; i < preprocessed.length; i++) {
            charRow.append(String.format("%3c", preprocessed[i]));
        }
        log.debug("{}", charRow.toString());

        // Build pradii row
        StringBuilder pradiiRow = new StringBuilder("  P[i]: ");
        for (int i = 0; i < pradii.length; i++) {
            pradiiRow.append(String.format("%3d", pradii[i]));
        }
        log.debug("{}", pradiiRow.toString());

        // Build arrow row pointing to currentIndex
        StringBuilder arrowRow = new StringBuilder("        ");
        for (int i = 0; i < preprocessed.length; i++) {
            if (i == currentIndex) {
                arrowRow.append("  ▲"); // Current position
            } else {
                arrowRow.append("   ");
            }
        }
        log.debug("{}", arrowRow.toString());

        // Build marker row showing center (C), right (R), maxCenter (M)
        StringBuilder markerRow = new StringBuilder("        ");
        for (int i = 0; i < preprocessed.length; i++) {
            String marker = "   ";
            if (i == center && i == right && i == maxCenter) {
                marker = "CRM";
            } else if (i == center && i == right) {
                marker = " CR";
            } else if (i == center && i == maxCenter) {
                marker = " CM";
            } else if (i == right && i == maxCenter) {
                marker = " RM";
            } else if (i == center) {
                marker = "  C";
            } else if (i == right) {
                marker = "  R";
            } else if (i == maxCenter) {
                marker = "  M";
            }
            markerRow.append(marker);
        }
        log.debug("{}", markerRow.toString());

        log.debug("  ───────────────────────────────────────────────────────────────────────────");
        log.debug("  LEGEND: ▲ = currentIndex, C = center, R = right, M = maxCenter");
        log.debug("  ───────────────────────────────────────────────────────────────────────────");
        log.debug("  VARIABLES:");
        log.debug("    currentIndex = {}  │  center    = {}  │  right      = {}", currentIndex, center, right);
        log.debug("    maxLen       = {}  │  maxCenter = {}  │  pradii[{}]  = {}", maxLen, maxCenter, currentIndex,
                pradii[currentIndex]);

        // Show the palindrome range if any
        int palindromeLeft = currentIndex - pradii[currentIndex];
        int palindromeRight = currentIndex + pradii[currentIndex];
        if (pradii[currentIndex] > 0) {
            log.debug("  ───────────────────────────────────────────────────────────────────────────");
            log.debug("  CURRENT PALINDROME RANGE: [{}..{}] centered at {}", palindromeLeft, palindromeRight,
                    currentIndex);

            // Build visual palindrome highlight
            StringBuilder palindromeHighlight = new StringBuilder("        ");
            for (int i = 0; i < preprocessed.length; i++) {
                if (i == currentIndex) {
                    palindromeHighlight.append("  ●"); // Center
                } else if (i >= palindromeLeft && i <= palindromeRight) {
                    palindromeHighlight.append("  ─"); // Part of palindrome
                } else {
                    palindromeHighlight.append("   ");
                }
            }
            log.debug("{}", palindromeHighlight.toString());
        }

        // Show rightmost palindrome range
        if (right > 0) {
            int leftBoundary = 2 * center - right;
            log.debug("  ───────────────────────────────────────────────────────────────────────────");
            log.debug("  RIGHTMOST PALINDROME: [{}..{}] centered at {}", leftBoundary, right, center);

            StringBuilder rightmostHighlight = new StringBuilder("        ");
            for (int i = 0; i < preprocessed.length; i++) {
                if (i == center) {
                    rightmostHighlight.append("  ◆"); // Center of rightmost
                } else if (i >= leftBoundary && i <= right) {
                    rightmostHighlight.append("  ═"); // Part of rightmost palindrome
                } else {
                    rightmostHighlight.append("   ");
                }
            }
            log.debug("{}", rightmostHighlight.toString());
        }

        log.debug("  ═══════════════════════════════════════════════════════════════════════════");
    }

    private void palindromeDetector(char[] preprocessed, int[] pradii, int currentIndex) {
        int expansionCount = 0;
        while (true) {
            int offset = pradii[currentIndex] + 1;
            char charRight = preprocessed[currentIndex + offset];
            char charLeft = preprocessed[currentIndex - offset];

            log.debug("  │  Expansion #{}: offset={}", expansionCount + 1, offset);
            log.debug("  │    charLeft  = preprocessed[{} - {}] = preprocessed[{}] = '{}'",
                    currentIndex, offset, currentIndex - offset, charLeft);
            log.debug("  │    charRight = preprocessed[{} + {}] = preprocessed[{}] = '{}'",
                    currentIndex, offset, currentIndex + offset, charRight);

            if (charRight == charLeft) {
                pradii[currentIndex]++;
                log.debug("  │    '{}' == '{}' → MATCH! pradii[{}] incremented to {}",
                        charLeft, charRight, currentIndex, pradii[currentIndex]);
                expansionCount++;
            } else {
                log.debug("  │    '{}' != '{}' → MISMATCH. Expansion stops.", charLeft, charRight);
                break;
            }
        }
        if (expansionCount == 0) {
            log.debug("  │  No expansion possible from this position.");
        } else {
            log.debug("  │  Expanded {} time(s).", expansionCount);
        }
    }

    private char[] preprocess(String input) {
        log.debug("  Preprocessing: Adding '^' at start, '#' between each char, '#$' at end");
        log.debug("  The '^' and '$' are sentinel characters that will never match,");
        log.debug("  preventing bounds checking in the expansion loop.");
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
