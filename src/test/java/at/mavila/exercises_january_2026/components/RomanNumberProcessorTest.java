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
            "MMXXVI, 2026" // Recent year example
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
    void testInvalidCharactersThrowException() {
        // "XYZQ" contains invalid characters Y, Z, Q - should throw exception
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("XYZQ"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid character");
    }

    @Test
    void testMixedValidAndInvalidCharactersThrowException() {
        // "X1V2I3" contains invalid characters - should throw exception
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("X1V2I3"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid character");
    }

    @Test
    void testOnlyInvalidCharactersThrowException() {
        // All invalid characters - should throw exception
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("ABEFGHJKNOPQRSTUWYZ"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid character");
    }

    @Test
    void testSpecialCharactersThrowException() {
        // "X!@#V" contains special characters - should throw exception
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("X!@#V"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid character");
    }

    // ==================== Case Sensitivity Tests ====================

    @Test
    void testLowercaseCharactersThrowException() {
        // "iii" - lowercase letters are not valid Roman numerals
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("iii"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid character");
    }

    @Test
    void testMixedCaseThrowsException() {
        // "XiVi" contains lowercase - should throw exception
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("XiVi"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid character");
    }

    // ==================== Invalid Subtraction Pair Tests ====================

    @Test
    void testInvalidSubtractionPairIMThrowsException() {
        // "IM" - I can only precede V or X
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("IM"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid subtraction pair 'IM'");
    }

    @Test
    void testInvalidSubtractionPairICThrowsException() {
        // "IC" - I can only precede V or X
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("IC"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid subtraction pair 'IC'");
    }

    @Test
    void testInvalidSubtractionPairXDThrowsException() {
        // "XD" - X can only precede L or C
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("XD"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid subtraction pair 'XD'");
    }

    @Test
    void testInvalidSubtractionPairVXThrowsException() {
        // "VX" - V cannot be used for subtraction
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("VX"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("Invalid subtraction pair 'VX'");
    }

    // ==================== Invalid Repetition Tests ====================

    @Test
    void testFourConsecutiveIThrowsException() {
        // "IIII" - I cannot repeat more than 3 times
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("IIII"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("cannot repeat more than 3 times");
    }

    @Test
    void testRepeatingVThrowsException() {
        // "VV" - V cannot repeat
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("VV"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("'V' cannot repeat");
    }

    @Test
    void testRepeatingLThrowsException() {
        // "LL" - L cannot repeat
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("LL"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("'L' cannot repeat");
    }

    @Test
    void testRepeatingDThrowsException() {
        // "DD" - D cannot repeat
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("DD"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("'D' cannot repeat");
    }

    // ==================== Double Subtraction Tests ====================

    @Test
    void testDoubleSubtractionIIVThrowsException() {
        // "IIV" - cannot have II before subtraction
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("IIV"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("multiple 'I' before subtraction");
    }

    @Test
    void testDoubleSubtractionXXCThrowsException() {
        // "XXC" - cannot have XX before subtraction
        assertThatThrownBy(() -> this.romanNumberProcessor.romanToInt("XXC"))
                .isInstanceOf(InvalidRomanNumeralException.class)
                .hasMessageContaining("multiple 'X' before subtraction");
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

    // ==================== intToRoman Boundary Tests ====================

    @Test
    void testIntToRomanMinimumValid() {
        // 1 is the smallest valid input
        String result = this.romanNumberProcessor.intToRoman(1);
        assertThat(result).isEqualTo("I");
    }

    @Test
    void testIntToRomanMaximumValid() {
        // 3999 is the largest valid input
        String result = this.romanNumberProcessor.intToRoman(3999);
        assertThat(result).isEqualTo("MMMCMXCIX");
    }

    // ==================== intToRoman Invalid Input Tests ====================

    @Test
    void testIntToRomanZeroThrowsException() {
        // Zero is not representable in Roman numerals
        assertThatThrownBy(() -> this.romanNumberProcessor.intToRoman(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be between 1 and 3999");
    }

    @Test
    void testIntToRomanNegativeThrowsException() {
        // Negative numbers are not valid
        assertThatThrownBy(() -> this.romanNumberProcessor.intToRoman(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be between 1 and 3999");
    }

    @Test
    void testIntToRomanTooLargeThrowsException() {
        // 4000 exceeds the maximum valid Roman numeral
        assertThatThrownBy(() -> this.romanNumberProcessor.intToRoman(4000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be between 1 and 3999");
    }

    @Test
    void testIntToRomanLargeNegativeThrowsException() {
        // Large negative numbers are not valid
        assertThatThrownBy(() -> this.romanNumberProcessor.intToRoman(-1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be between 1 and 3999");
    }

    @Test
    void testIntToRomanVeryLargeThrowsException() {
        // Very large numbers are not valid
        assertThatThrownBy(() -> this.romanNumberProcessor.intToRoman(10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be between 1 and 3999");
    }

}
