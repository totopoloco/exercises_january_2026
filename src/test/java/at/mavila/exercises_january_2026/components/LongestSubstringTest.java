package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LongestSubstringTest {
    @Autowired
    private LongestSubstring longestSubstring;

    // ==================== Basic Examples ====================

    @Test
    void testLongestUniqueSubstringLength_BasicExample() {
        String input = "abcabcbb";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(3); // The answer is "abc", with the length of 3.
    }

    @Test
    void testLongestUniqueSubstringLength_AllSameCharacters() {
        String input = "bbbbb";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(1); // The answer is "b", with the length of 1.
    }

    @Test
    void testLongestUniqueSubstringLength_UniqueAtEnd() {
        String input = "pwwkew";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(3); // The answer is "wke", with the length of 3.
    }

    // ==================== Null and Empty Input ====================

    @Test
    void testLongestUniqueSubstringLength_NullInput_ThrowsException() {
        assertThatThrownBy(() -> longestSubstring.longestUniqueSubstringLength(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input cannot be null");
    }

    @Test
    void testLongestUniqueSubstringLength_EmptyString_ThrowsException() {
        assertThatThrownBy(() -> longestSubstring.longestUniqueSubstringLength(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input cannot be empty");
    }

    @Test
    void testLongestUniqueSubstringLength_WhitespaceOnly() {
        // Single space is NOT empty - should return 1, not 0
        int result = longestSubstring.longestUniqueSubstringLength(" ");
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testLongestUniqueSubstringLength_MultipleSpaces() {
        // Multiple spaces - all same character
        int result = longestSubstring.longestUniqueSubstringLength("   ");
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testLongestUniqueSubstringLength_NullAndEmptyBothThrow() {
        // Both null and empty throw IllegalArgumentException
        assertThatThrownBy(() -> longestSubstring.longestUniqueSubstringLength(null))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> longestSubstring.longestUniqueSubstringLength(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ==================== Single Character ====================

    @Test
    void testLongestUniqueSubstringLength_SingleCharacter() {
        int result = longestSubstring.longestUniqueSubstringLength("a");
        assertThat(result).isEqualTo(1);
    }

    // ==================== All Unique Characters ====================

    @Test
    void testLongestUniqueSubstringLength_AllUnique() {
        String input = "abcdefg";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(7);
    }

    @Test
    void testLongestUniqueSubstringLength_AlphabetUnique() {
        String input = "abcdefghijklmnopqrstuvwxyz";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(26);
    }

    // ==================== Numbers and Special Characters ====================

    @Test
    void testLongestUniqueSubstringLength_WithDigits() {
        String input = "abc123abc";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(6); // "abc123" or "123abc"
    }

    @Test
    void testLongestUniqueSubstringLength_OnlyDigits() {
        String input = "1234567890";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(10);
    }

    @Test
    void testLongestUniqueSubstringLength_WithSpaces() {
        String input = "a b c d";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(3); // "a b" or "b c" or "c d"
    }

    @Test
    void testLongestUniqueSubstringLength_WithSpecialCharacters() {
        String input = "a!@#$%b";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(7);
    }

    // ==================== Duplicate Patterns ====================

    @Test
    void testLongestUniqueSubstringLength_DuplicateAtStart() {
        String input = "aabcdef";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(6); // "abcdef"
    }

    @Test
    void testLongestUniqueSubstringLength_DuplicateAtEnd() {
        String input = "abcdeff";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(6); // "abcdef"
    }

    @Test
    void testLongestUniqueSubstringLength_DuplicateInMiddle() {
        String input = "abccdef";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(4); // "cdef"
    }

    @Test
    void testLongestUniqueSubstringLength_MultipleDuplicates() {
        String input = "abcadefbghij";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        // a(0)b(1)c(2)a(3) -> when 'a' at 3, lastSeen['a']=0 >= left=0, so left=1
        // d(4)e(5)f(6)b(7) -> when 'b' at 7, lastSeen['b']=1 >= left=1, so left=2
        // g(8)h(9)i(10)j(11) -> no duplicates in window
        // Final window from index 2 to 11 = "cadefbghij" = 10 characters
        assertThat(result).isEqualTo(10);
    }

    @Test
    void testLongestUniqueSubstringLength_AlternatingPattern() {
        String input = "ababab";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(2); // "ab" or "ba"
    }

    // ==================== Edge Cases ====================

    @Test
    void testLongestUniqueSubstringLength_TwoCharactersSame() {
        String input = "aa";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testLongestUniqueSubstringLength_TwoCharactersDifferent() {
        String input = "ab";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testLongestUniqueSubstringLength_RepeatingPairs() {
        String input = "aabb";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(2); // "ab"
    }

    // ==================== Mixed Case ====================

    @Test
    void testLongestUniqueSubstringLength_MixedCase() {
        String input = "aAbBcC";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(6); // All unique since case-sensitive
    }

    @Test
    void testLongestUniqueSubstringLength_UppercaseOnly() {
        String input = "ABCABC";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(3); // "ABC"
    }

    // ==================== Long Strings ====================

    @Test
    void testLongestUniqueSubstringLength_LongString() {
        // Create a string with all ASCII printable characters (95 unique)
        StringBuilder sb = new StringBuilder();
        for (int i = 32; i < 127; i++) {
            sb.append((char) i);
        }
        String input = sb.toString();
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(95);
    }

    @Test
    void testLongestUniqueSubstringLength_LongRepeatingPattern() {
        String input = "abcdefghij".repeat(100);
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(10); // "abcdefghij"
    }

    // ==================== Window Sliding Scenarios ====================

    @Test
    void testLongestUniqueSubstringLength_WindowSlidesCorrectly() {
        String input = "dvdf";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(3); // "vdf"
    }

    @Test
    void testLongestUniqueSubstringLength_DuplicateNotInCurrentWindow() {
        // Tests the case where lastSeen has a value but it's before the current window
        String input = "abba";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(2); // "ab" or "ba"
    }

    @Test
    void testLongestUniqueSubstringLength_ComplexPattern() {
        String input = "tmmzuxt";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(5); // "mzuxt"
    }

    // ==================== Boundary Condition Tests for Mutation Coverage
    // ====================

    @Test
    void testLongestUniqueSubstringLength_DuplicateAtExactWindowStart() {
        // Tests case where lastSeenIndex == left (boundary condition for >= vs >)
        // "aba" -> a(0), b(1), a(2): when seeing 'a' at index 2, lastSeen['a']=0,
        // left=0
        // So lastSeenIndex(0) >= left(0) is true, but lastSeenIndex(0) > left(0) is
        // false
        // If mutation changes >= to >, the window wouldn't shrink properly
        String input = "aba";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(2); // "ab" or "ba", NOT "aba"
    }

    @Test
    void testLongestUniqueSubstringLength_DuplicateExactlyAtLeft() {
        // "aa" tests lastSeenIndex == left case directly
        // a(0): lastSeen['a']=-1, left=0, -1 >= 0 is false, maxLen=1
        // a(1): lastSeen['a']=0, left=0, 0 >= 0 is true, so left=1, maxLen stays 1
        // If >= becomes >, then 0 > 0 is false, left stays 0, which is wrong
        String input = "aa";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testLongestUniqueSubstringLength_ConsecutiveDuplicatesTriggersEquality() {
        // "abab" - tests multiple boundary hits
        // a(0): left=0, maxLen=1
        // b(1): left=0, maxLen=2
        // a(2): lastSeen['a']=0, left=0, 0>=0 true, left=1, maxLen=2
        // b(3): lastSeen['b']=1, left=1, 1>=1 true, left=2, maxLen=2
        String input = "abab";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void testLongestUniqueSubstringLength_ImmediateRepeat() {
        // "aab" - immediate repeat at position 1
        // a(0): lastSeen['a']=-1 < 0, left=0, maxLen=1
        // a(1): lastSeen['a']=0 >= left(0), left=1, maxLen=1
        // b(2): lastSeen['b']=-1 < 1, left=1, maxLen=2
        String input = "aab";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(2); // "ab"
    }

    @Test
    void testLongestUniqueSubstringLength_ThreeConsecutiveSame() {
        // "aaa" - all same character, tests boundary repeatedly
        String input = "aaa";
        int result = longestSubstring.longestUniqueSubstringLength(input);
        assertThat(result).isEqualTo(1);
    }
}
