package at.mavila.exercises_january_2026.components;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LetterCombinations {

    private static final Map<Character, String> PHONE_MAP = Map.of(
            '2', "abc",
            '3', "def",
            '4', "ghi",
            '5', "jkl",
            '6', "mno",
            '7', "pqrs",
            '8', "tuv",
            '9', "wxyz");

    public List<String> combine(final String digits) {

        if (Objects.isNull(digits) || digits.isEmpty() || digits.length() < 1 || digits.length() > 4) {
            throw new IllegalArgumentException("Invalid input. Length of digits must be between 1 and 4.");
        }

        log.debug("Processing the following digits: {}", digits);

        final Stream<String> mapToObj = digits.chars().mapToObj(c -> PHONE_MAP.get((char) c));

        List<String> reduced = mapToObj.reduce(
                List.of(""),
                getAccumulatorFunction(),
                getCombinerFunction() // combiner (not used in sequential streams)
        );

        return reduced;

    }

    private BinaryOperator<List<String>> getCombinerFunction() {

        final BinaryOperator<List<String>> combiner = new BinaryOperator<List<String>>() {
            @Override
            public List<String> apply(List<String> left, List<String> right) {
                // In a parallel stream, this would combine two partial results.
                // Since our stream is sequential, this method won't be called.
                log.debug("Combining two lists: {} and {}", left, right);
                return left; // or some combination logic if needed
            }
        };

        return combiner;

    }

    private BiFunction<List<String>, ? super String, List<String>> getAccumulatorFunction() {

        final BiFunction<List<String>, String, List<String>> accumulator = new BiFunction<List<String>, String, List<String>>() {
            @Override
            public List<String> apply(List<String> listOfStrings, String str) {
                final List<String> extendedCombinationsList = getExtendedCombinationsList(listOfStrings, str);
                log.debug("List so far: {}", extendedCombinationsList);
                return extendedCombinationsList;
            }

        };

        return accumulator;

    }

    private List<String> getExtendedCombinationsList(List<String> combinations, String letters) {
        List<String> extendedCombinationsList = combinations
                .stream()
                .flatMap(combo -> {
                    final IntStream chars = letters.chars();
                    return chars.mapToObj(ch -> {
                        char convertedChar = (char) ch;
                        log.debug("Combining '{}' with '{}'", combo, convertedChar);
                        return combo + convertedChar;
                    });
                })
                .toList();
        return extendedCombinationsList;
    }

}
