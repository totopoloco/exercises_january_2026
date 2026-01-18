package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RomanNumberProcessorTest {

    @Autowired
    private RomanNumberProcessor romanNumberProcessor;

    // ==================== Basic Conversion Tests ====================

    @Test
    void testRomanIIIToInt3() {
        int result = this.romanNumberProcessor.romanToInt("III");
        assertThat(result).isEqualTo(3);
    }

    @Test
    void testRomanLVIIIToInt58() {
        int result = this.romanNumberProcessor.romanToInt("LVIII");
        assertThat(result).isEqualTo(58);
    }

    @Test
    void testRomanMCMXCIVToInt1994() {
        int result = this.romanNumberProcessor.romanToInt("MCMXCIV");
        assertThat(result).isEqualTo(1994);
    }

    @Test
    void testRomanIVToInt4() {
        int result = this.romanNumberProcessor.romanToInt("IV");
        assertThat(result).isEqualTo(4);
    }

    // ==================== Single Character Tests ====================

    @ParameterizedTest(name = "Roman {0} should equal {1}")
    @CsvSource({
            "I, 1",
            "V, 5",
            "X, 10",
            "L, 50",
            "C, 100",
            "D, 500",
            "M, 1000"
    })
    void testSingleRomanCharacters(String roman, int expected) {
        assertThat(this.romanNumberProcessor.romanToInt(roman)).isEqualTo(expected);
    }

    // ==================== Subtraction Cases ====================

    @ParameterizedTest(name = "Subtraction case {0} should equal {1}")
    @CsvSource({
            "IV, 4",
            "IX, 9",
            "XL, 40",
            "XC, 90",
            "CD, 400",
            "CM, 900"
    })
    void testSubtractionCases(String roman, int expected) {
        assertThat(this.romanNumberProcessor.romanToInt(roman)).isEqualTo(expected);
    }

    // ==================== Repeated Characters ====================

    @ParameterizedTest(name = "Repeated characters {0} should equal {1}")
    @CsvSource({
            "II, 2",
            "III, 3",
            "XX, 20",
            "XXX, 30",
            "CC, 200",
            "CCC, 300",
            "MM, 2000",
            "MMM, 3000"
    })
    void testRepeatedCharacters(String roman, int expected) {
        assertThat(this.romanNumberProcessor.romanToInt(roman)).isEqualTo(expected);
    }

    // ==================== Large Numbers ====================

    @ParameterizedTest(name = "Large number {0} should equal {1}")
    @CsvSource({
            "MMMCMXCIX, 3999", // Maximum standard Roman numeral
            "MMMDCCCLXXXVIII, 3888",
            "MMMCCCXXXIII, 3333",
            "MMCMXCIX, 2999",
            "MDCLXVI, 1666",
            "MMXXVI, 2026" // Current year
    })
    void testLargeNumbers(String roman, int expected) {
        assertThat(this.romanNumberProcessor.romanToInt(roman)).isEqualTo(expected);
    }

    // ==================== Complex Mixed Cases ====================

    @ParameterizedTest(name = "Complex case {0} should equal {1}")
    @CsvSource({
            "XLIV, 44",
            "XCIX, 99",
            "CDXLIV, 444",
            "CMXCIX, 999",
            "MCMLXXXIV, 1984",
            "MMCDXXI, 2421",
            "DCCCXC, 890",
            "CDLVI, 456"
    })
    void testComplexMixedCases(String roman, int expected) {
        assertThat(this.romanNumberProcessor.romanToInt(roman)).isEqualTo(expected);
    }

    // ==================== Null and Empty Input Tests ====================

    @ParameterizedTest
    @NullAndEmptySource
    void testNullAndEmptyInputThrowsException(String input) {
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input Roman numeral string is null or empty");
    }

    @ParameterizedTest
    @ValueSource(strings = { " ", "  ", "\t", "\n", "   " })
    void testBlankInputThrowsException(String input) {
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input Roman numeral string is null or empty");
    }

    // ==================== Invalid Characters Tests ====================

    @Test
    void testInvalidCharactersAreIgnored() {
        // Current implementation ignores invalid characters
        // "XYZQ" - only X is valid = 10 (Y, Z, Q are invalid)
        int result = this.romanNumberProcessor.romanToInt("XYZQ");
        assertThat(result).isEqualTo(10);
    }

    @Test
    void testMixedValidAndInvalidCharacters() {
        // "X1V2I3" should be treated as "XVI" = 16
        int result = this.romanNumberProcessor.romanToInt("X1V2I3");
        assertThat(result).isEqualTo(16);
    }

    @Test
    void testOnlyInvalidCharactersReturnsZero() {
        // All invalid characters result in 0 (no valid Roman numerals in string)
        int result = this.romanNumberProcessor.romanToInt("ABEFGHJKNOPQRSTUWYZ");
        assertThat(result).isZero();
    }

    @Test
    void testSpecialCharactersAreIgnored() {
        // "X!@#V" should be treated as "XV" = 15
        int result = this.romanNumberProcessor.romanToInt("X!@#V");
        assertThat(result).isEqualTo(15);
    }

    // ==================== Case Sensitivity Tests ====================

    @Test
    void testLowercaseCharactersAreIgnored() {
        // Current implementation is case-sensitive, lowercase letters are ignored
        // "iii" should return 0 (all ignored)
        int result = this.romanNumberProcessor.romanToInt("iii");
        assertThat(result).isZero();
    }

    @Test
    void testMixedCaseOnlyUppercaseProcessed() {
        // "XiVi" should be treated as "XV" = 15 (lowercase ignored)
        int result = this.romanNumberProcessor.romanToInt("XiVi");
        assertThat(result).isEqualTo(15);
    }

    // ==================== Boundary and Edge Cases ====================

    @Test
    void testMinimumValidRomanNumeral() {
        // "I" = 1 is the smallest valid Roman numeral
        int result = this.romanNumberProcessor.romanToInt("I");
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testMaximumStandardRomanNumeral() {
        // "MMMCMXCIX" = 3999 is the largest standard Roman numeral
        int result = this.romanNumberProcessor.romanToInt("MMMCMXCIX");
        assertThat(result).isEqualTo(3999);
    }

    @Test
    void testConsecutiveSubtractions() {
        // "IXIV" = 9 + 4 = 13
        int result = this.romanNumberProcessor.romanToInt("IXIV");
        assertThat(result).isEqualTo(13);
    }

    @Test
    void testAllSubtractionPatternsInOne() {
        // "MCMXCIV" = 1000 + 900 + 90 + 4 = 1994
        int result = this.romanNumberProcessor.romanToInt("MCMXCIV");
        assertThat(result).isEqualTo(1994);
    }

    // ==================== Round Number Tests ====================

    @ParameterizedTest(name = "Round number {0} should equal {1}")
    @CsvSource({
            "X, 10",
            "XX, 20",
            "L, 50",
            "C, 100",
            "D, 500",
            "M, 1000",
            "MM, 2000",
            "MMM, 3000"
    })
    void testRoundNumbers(String roman, int expected) {
        assertThat(this.romanNumberProcessor.romanToInt(roman)).isEqualTo(expected);
    }

    // ==================== Numbers 1-10 Complete Coverage ====================

    @ParameterizedTest(name = "Number 1-10: {0} should equal {1}")
    @CsvSource({
            "I, 1",
            "II, 2",
            "III, 3",
            "IV, 4",
            "V, 5",
            "VI, 6",
            "VII, 7",
            "VIII, 8",
            "IX, 9",
            "X, 10"
    })
    void testNumbersOneToTen(String roman, int expected) {
        assertThat(this.romanNumberProcessor.romanToInt(roman)).isEqualTo(expected);
    }

    //////// ==================== intToRoman Tests ====================//////
    @Test
    void testIntToRoman1() {
        String result = this.romanNumberProcessor.intToRoman(58);
        assertThat(result).isEqualTo("LVIII");
    }

    @Test
    void testIntToRoman2() {
        String result = this.romanNumberProcessor.intToRoman(94);
        assertThat(result).isEqualTo("XCIV");
    }

    @Test
    void testIntToRoman3() {
        String result = this.romanNumberProcessor.intToRoman(1994);
        assertThat(result).isEqualTo("MCMXCIV");
    }

    @Test
    void testIntToRoman4() {
        String result = this.romanNumberProcessor.intToRoman(4);
        assertThat(result).isEqualTo("IV");
    }

}
