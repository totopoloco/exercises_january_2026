package at.mavila.exercises_january_2026.domain.string;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PalindromeExtractorTest {

    @Autowired
    private PalindromeExtractor extractor;

    @Test
    void testLongestPalindrome() {
        final String input = "ardeeseloco";
        final String expected = "ese";
        final String result = this.extractor.longestPalindrome(input);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testLongestPalindrome_FiveCharactersValid() {
        final String input = "babad";
        final String expected = "bab";
        final String result = this.extractor.longestPalindrome(input);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testLongestPalindrome_FourCharacters() {
        final String input = "cbbd";
        final String expected = "bb";
        final String result = this.extractor.longestPalindrome(input);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testLongestPalindrome_Uppercase() {
        final String input = "ABCBABCDD";
        final String expected = "ABCBA";
        final String result = this.extractor.longestPalindrome(input);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testLongestPalindrome_LongPalindrome() {
        final String input = "zutseffiloveyouuoyevolikdiffer";
        final String expected = "iloveyouuoyevoli";
        final String result = this.extractor.longestPalindrome(input);
        assertThat(result).isEqualTo(expected);
    }
}
