package at.mavila.exercises_january_2026.domain.number;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Domain service for finding the element that appears an odd number of times.
 *
 * <p>
 * Given an array of integers, this service finds the one that appears an
 * odd number of times. There will always be only one such integer.
 * </p>
 *
 * <h2>Algorithm</h2>
 * <p>
 * Uses XOR reduction: XORing all elements together cancels out pairs
 * (even occurrences), leaving only the element that appears an odd number
 * of times.
 * </p>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(n) - single pass through the array</li>
 * <li><b>Space:</b> O(1) - constant extra space</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OddFinder {

  private final IntBinaryOperator oddFinderOperator;

  /**
   * Finds the integer that appears an odd number of times in the given array.
   *
   * @param a the array of integers to search (e.g., [1,2,2,3,3,3,4,3,3,3,2,2,1])
   * @return the integer that appears an odd number of times (e.g., 4)
   */
  public int findIt(final int[] a) {
    // Use the Stream API to reduce the array to the odd occurring element
    return Arrays.stream(a).reduce(0, this.oddFinderOperator);
  }

}
