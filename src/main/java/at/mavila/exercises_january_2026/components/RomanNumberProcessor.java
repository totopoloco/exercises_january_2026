package at.mavila.exercises_january_2026.components;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * Converts Roman numerals to their integer representation.
 *
 * <p>
 * Roman numerals are represented by seven different symbols:
 * <ul>
 * <li>I = 1</li>
 * <li>V = 5</li>
 * <li>X = 10</li>
 * <li>L = 50</li>
 * <li>C = 100</li>
 * <li>D = 500</li>
 * <li>M = 1000</li>
 * </ul>
 *
 * <p>
 * The algorithm handles subtraction cases where a smaller numeral appears
 * before a larger one (e.g., IV = 4, IX = 9, XL = 40, XC = 90, CD = 400, CM =
 * 900).
 *
 * <p>
 * <b>Algorithm:</b> Single-pass iteration with look-ahead comparison.
 * For each character, if its value is less than the next character's value,
 * subtract it; otherwise, add it.
 *
 * <p>
 * <b>Complexity:</b>
 * <ul>
 * <li>Time: O(n) where n is the length of the input string</li>
 * <li>Space: O(1) constant space</li>
 * </ul>
 *
 * @author exercises_january_2026
 * @see <a href="https://en.wikipedia.org/wiki/Roman_numerals">Roman Numerals -
 *      Wikipedia</a>
 */
@Component
public class RomanNumberProcessor {

    /**
     * Mapping of Roman numeral characters to their integer values.
     */
    private static final Map<Character, Integer> ROMAN_TO_INT_MAP = Map.of(
            'I', 1,
            'V', 5,
            'X', 10,
            'L', 50,
            'C', 100,
            'D', 500,
            'M', 1000);

    private static final Map<String, Integer> SUBTRACTION_CASES = Map.of(
            "IV", 4,
            "IX", 9,
            "XL", 40,
            "XC", 90,
            "CD", 400,
            "CM", 900);

    /**
     * Valid subtraction pairs: maps a character to the set of characters it can
     * precede in subtraction.
     * I can only precede V and X; X can only precede L and C; C can only precede D
     * and M.
     */
    private static final Map<Character, Set<Character>> VALID_SUBTRACTION_PAIRS = Map.of(
            'I', Set.of('V', 'X'),
            'X', Set.of('L', 'C'),
            'C', Set.of('D', 'M'));

    /**
     * Characters that cannot repeat (V, L, D).
     */
    private static final Set<Character> NON_REPEATABLE = Set.of('V', 'L', 'D');

    /**
     * Maximum consecutive repetitions allowed for repeatable characters (I, X, C,
     * M).
     */
    private static final int MAX_REPETITIONS = 3;

    /**
     * Combined map ordered by value descending, used for integer to Roman
     * conversion.
     */
    private static final Map<String, Integer> COMBINED_MAP_DESC;

    static {
        // Create a temporary map with all entries
        Map<String, Integer> tempMap = new LinkedHashMap<>();

        // Add single character mappings
        ROMAN_TO_INT_MAP.forEach((key, value) -> tempMap.put(String.valueOf(key), value));

        // Add subtraction cases
        tempMap.putAll(SUBTRACTION_CASES);

        // Sort by value descending for intToRoman conversion
        COMBINED_MAP_DESC = tempMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    /**
     * Converts a Roman numeral string to its integer value.
     *
     * <p>
     * Examples:
     * <ul>
     * <li>"III" → 3</li>
     * <li>"LVIII" → 58</li>
     * <li>"MCMXCIV" → 1994</li>
     * <li>"IV" → 4</li>
     * </ul>
     *
     * <p>
     * This method validates the input against standard Roman numeral rules:
     * <ul>
     * <li>Only valid subtraction pairs: I before V/X, X before L/C, C before
     * D/M</li>
     * <li>V, L, D cannot repeat</li>
     * <li>I, X, C, M cannot repeat more than 3 times consecutively</li>
     * </ul>
     *
     * @param roman the Roman numeral string to convert (must not be null or blank)
     * @return the integer value of the Roman numeral
     * @throws IllegalArgumentException     if the input is null or blank
     * @throws InvalidRomanNumeralException if the input violates Roman numeral
     *                                      rules
     */
    public int romanToInt(final String roman) {

        if (isNull(roman) || roman.isBlank()) {
            throw new IllegalArgumentException("Input Roman numeral string is null or empty");
        }

        // Validate the Roman numeral before conversion
        validateRomanNumeral(roman);

        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            Integer currentValue = ROMAN_TO_INT_MAP.get(roman.charAt(i));
            // Skip invalid characters (already validated, but keeping for safety)
            if (isNull(currentValue)) {
                continue;
            }

            // Look ahead to check if this is a subtraction case (e.g., IV, IX, XL, XC, CD,
            // CM)
            Integer nextValue = ((i + 1) < roman.length()) ? ROMAN_TO_INT_MAP.get(roman.charAt(i + 1)) : null;

            if (nonNull(nextValue) && (currentValue < nextValue)) {
                result -= currentValue;
                continue;
            }

            result += currentValue;
        }

        return result;

    }

    /**
     * Validates a Roman numeral string against standard rules.
     *
     * @param roman the Roman numeral string to validate
     * @throws InvalidRomanNumeralException if validation fails
     */
    private void validateRomanNumeral(final String roman) {
        int consecutiveCount = 1;
        Character previousChar = null;
        Character charBeforePrevious = null;

        for (int i = 0; i < roman.length(); i++) {
            char currentChar = roman.charAt(i);
            Integer currentValue = ROMAN_TO_INT_MAP.get(currentChar);

            // Check for invalid characters
            if (isNull(currentValue)) {
                throw new InvalidRomanNumeralException(roman,
                        String.format("Invalid character '%c' at position %d", currentChar, i));
            }

            // Check for consecutive repetitions
            if (previousChar != null && currentChar == previousChar) {
                consecutiveCount++;

                // V, L, D cannot repeat at all
                if (NON_REPEATABLE.contains(currentChar)) {
                    throw new InvalidRomanNumeralException(roman,
                            String.format("Character '%c' cannot repeat", currentChar));
                }

                // I, X, C, M cannot repeat more than 3 times
                if (consecutiveCount > MAX_REPETITIONS) {
                    throw new InvalidRomanNumeralException(roman,
                            String.format("Character '%c' cannot repeat more than %d times consecutively",
                                    currentChar, MAX_REPETITIONS));
                }
            } else {
                consecutiveCount = 1;
            }

            // Check for valid subtraction pairs
            if (previousChar != null) {
                Integer previousValue = ROMAN_TO_INT_MAP.get(previousChar);

                if (previousValue < currentValue) {
                    // This is a subtraction case - validate it
                    Set<Character> allowedFollowers = VALID_SUBTRACTION_PAIRS.get(previousChar);

                    if (isNull(allowedFollowers) || !allowedFollowers.contains(currentChar)) {
                        throw new InvalidRomanNumeralException(roman,
                                String.format("Invalid subtraction pair '%c%c' - '%c' cannot precede '%c'",
                                        previousChar, currentChar, previousChar, currentChar));
                    }

                    // Check for multiple subtraction (e.g., IIV, XXC) - the char before the
                    // subtraction
                    // must not be the same as the subtracting char
                    if (charBeforePrevious != null && charBeforePrevious.equals(previousChar)) {
                        throw new InvalidRomanNumeralException(roman,
                                String.format("Invalid pattern '%c%c%c' - multiple '%c' before subtraction",
                                        charBeforePrevious, previousChar, currentChar, previousChar));
                    }
                }
            }

            charBeforePrevious = previousChar;
            previousChar = currentChar;
        }
    }

    /**
     * Converts an integer to its Roman numeral representation.
     *
     * <p>
     * Uses a greedy algorithm that iterates through Roman numeral values
     * from largest to smallest, subtracting and appending symbols as needed.
     *
     * <p>
     * Examples:
     * <ul>
     * <li>3 → "III"</li>
     * <li>58 → "LVIII"</li>
     * <li>1994 → "MCMXCIV"</li>
     * <li>94 → "XCIV"</li>
     * </ul>
     *
     * <p>
     * <b>Complexity:</b>
     * <ul>
     * <li>Time: O(1) - fixed number of symbols (13)</li>
     * <li>Space: O(1) - maximum Roman numeral length is bounded</li>
     * </ul>
     *
     * @param number the integer to convert (must be between 1 and 3999 inclusive)
     * @return the Roman numeral string representation
     * @throws IllegalArgumentException if number is less than 1 or greater than
     *                                  3999
     */
    public String intToRoman(final int number) {

        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999, got: " + number);
        }

        int remaining = number;
        final StringBuilder result = new StringBuilder();

        // Iterate through symbols from largest to smallest value
        for (Map.Entry<String, Integer> entry : COMBINED_MAP_DESC.entrySet()) {
            final String symbol = entry.getKey();
            final int value = entry.getValue();

            // While we can still use this symbol
            while (remaining >= value) {
                result.append(symbol);
                remaining -= value;
            }

            // Early exit if nothing left to convert
            if (remaining == 0) {
                break;
            }
        }

        return result.toString();
    }

}
