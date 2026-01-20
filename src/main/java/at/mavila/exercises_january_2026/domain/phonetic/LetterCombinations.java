package at.mavila.exercises_january_2026.domain.phonetic;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Domain service for generating letter combinations from phone digits.
 *
 * <p>
 * Given a string of digits (2-9), this service returns all possible letter
 * combinations that the digits could represent on a phone keypad.
 * </p>
 *
 * <h2>Phone Keypad Mapping</h2>
 * <ul>
 * <li>2 → abc</li>
 * <li>3 → def</li>
 * <li>4 → ghi</li>
 * <li>5 → jkl</li>
 * <li>6 → mno</li>
 * <li>7 → pqrs</li>
 * <li>8 → tuv</li>
 * <li>9 → wxyz</li>
 * </ul>
 *
 * <h2>Algorithm</h2>
 * <p>
 * Uses Stream reduction to build combinations incrementally by extending
 * each existing combination with each letter for the current digit.
 * </p>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(4^n) where n is the number of digits</li>
 * <li><b>Space:</b> O(4^n) for storing all combinations</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
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

  /**
   * Generates all letter combinations for the given phone digits.
   *
   * @param digits a string containing digits 2-9 (length 1-4)
   * @return list of all possible letter combinations
   * @throws IllegalArgumentException if digits is null, empty, or invalid length
   */
  public List<String> combine(final String digits) {

    if (Objects.isNull(digits) || digits.isEmpty() || digits.length() < 1 || digits.length() > 4) {
      throw new IllegalArgumentException("Invalid input. Length of digits must be between 1 and 4.");
    }

    log.debug("Processing the following digits: {}", digits);

    final Stream<String> mapToObj = digits.chars().mapToObj(c -> PHONE_MAP.get((char) c));

    List<String> reduced = mapToObj.reduce(
        List.of(""),
        getAccumulatorFunction(),
        getCombinerFunction());

    return reduced;
  }

  private BinaryOperator<List<String>> getCombinerFunction() {
    return (left, right) -> {
      log.debug("Combining two lists: {} and {}", left, right);
      return left;
    };
  }

  private BiFunction<List<String>, ? super String, List<String>> getAccumulatorFunction() {
    return (listOfStrings, str) -> {
      final List<String> extendedCombinationsList = getExtendedCombinationsList(listOfStrings, str);
      log.debug("List so far: {}", extendedCombinationsList);
      return extendedCombinationsList;
    };
  }

  private List<String> getExtendedCombinationsList(List<String> combinations, String letters) {
    return combinations
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
  }

}
