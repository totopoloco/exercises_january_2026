package at.mavila.exercises_january_2026.domain.array;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * Domain service for calculating the median of merged arrays.
 *
 * <p>
 * This component provides functionality to merge two integer arrays
 * and calculate the median of the combined sorted result.
 * </p>
 *
 * <h2>Algorithm</h2>
 * <ol>
 * <li>Validate inputs (non-null)</li>
 * <li>Convert arrays to Lists and merge</li>
 * <li>Sort the merged list</li>
 * <li>Calculate median based on size (odd/even)</li>
 * </ol>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O((n+m) log(n+m)) due to sorting</li>
 * <li><b>Space:</b> O(n+m) for the merged list</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
public class MedianCalculator {

  /**
   * Merges two integer arrays and calculates the median of the combined sorted
   * array.
   *
   * @param array1 the first array
   * @param array2 the second array
   * @return the median value
   * @throws IllegalArgumentException if either array is null
   */
  public double mergeAndCalculate(int[] array1, int[] array2) {
    validations(array1, array2);

    // From long[] to List<Long>
    final List<Integer> list1 = Arrays.stream(array1).boxed().collect(Collectors.toList());
    final List<Integer> list2 = Arrays.stream(array2).boxed().collect(Collectors.toList());

    // Merge both sorted lists by appending list2 to the tail of list1.
    list1.addAll(list2);

    // Sort both lists
    list1.sort(Integer::compareTo);

    // list 1 now contains all elements from both arrays in sorted order.
    // Calculate the media
    final int size = list1.size();
    if (size == 0) {
      return 0D;
    }
    final int isOdd = size % 2; // impar in Spanish
    if (isOdd == 1) {
      final int indexOfMedianValue = size / 2;
      return BigDecimal.valueOf(list1.get(indexOfMedianValue)).doubleValue();
    }

    final int indexOfFirstMedianValue = (size / 2) - 1;
    final int indexOfSecondMedianValue = size / 2;
    final long firstMedianValue = list1.get(indexOfFirstMedianValue);
    final long secondMedianValue = list1.get(indexOfSecondMedianValue);
    final BigDecimal median = BigDecimal.valueOf(firstMedianValue + secondMedianValue)
        .setScale(10, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(2));
    return median.doubleValue();
  }

  private void validations(int[] array1, int[] array2) {
    if (Objects.isNull(array1) || Objects.isNull(array2)) {
      throw new IllegalArgumentException("Input arrays cannot be null");
    }
  }

}
