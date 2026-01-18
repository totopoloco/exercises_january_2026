package at.mavila.exercises_january_2026.components;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class RomanNumberProcessor {

    private static final Map<Character, Integer> ROMAN_TO_INT_MAP = Map.of(
            'I', 1,
            'V', 5,
            'X', 10,
            'L', 50,
            'C', 100,
            'D', 500,
            'M', 1000);

    public int romanToInt(final String roman) {

        if (isNull(roman) || roman.isBlank()) {
            throw new IllegalArgumentException("Input Roman numeral string is null or empty");
        }

        int result = 0;

        for (int i = 0; i < roman.length(); i++) {
            Integer currentValue = ROMAN_TO_INT_MAP.get(roman.charAt(i));
            // Ignore invalid characters
            if (isNull(currentValue)) {
                continue;
            }

            // Look ahead to check if this is a subtraction case (e.g., IV, IX, XL, XC, CD,
            // CM)
            Integer nextValue = ((i + 1) < roman.length()) ? ROMAN_TO_INT_MAP.get(roman.charAt(i + 1)) : null;

            if (nonNull(nextValue) && (currentValue < nextValue)) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
        }

        return result;

    }

}
