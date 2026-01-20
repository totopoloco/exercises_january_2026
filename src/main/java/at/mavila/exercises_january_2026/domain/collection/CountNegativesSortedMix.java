package at.mavila.exercises_january_2026.domain.collection;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Domain service for counting negative numbers in a sorted matrix.
 *
 * <p>
 * Given a 2D grid of integers (not necessarily sorted), this service
 * counts the total number of negative numbers.
 * </p>
 *
 * <h2>Algorithm</h2>
 * <p>
 * Simple iteration through all elements, counting negatives.
 * </p>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(m*n) where m is rows and n is columns</li>
 * <li><b>Space:</b> O(k) where k is the count of negatives</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
public class CountNegativesSortedMix {

  /**
   * Counts the number of negative integers in the grid.
   *
   * @param grid the 2D grid of integers
   * @return the count of negative numbers
   * @throws IllegalArgumentException if grid is null
   */
  public int count(final int[][] grid) {
    if (isNull(grid)) {
      throw new IllegalArgumentException("Grid must not be null");
    }

    if (grid.length == 0) {
      return 0;
    }

    final List<Integer> negatives = new ArrayList<>();
    for (int m = 0; m < grid.length; m++) {
      final int[] n = grid[m];
      for (int o = 0; o < n.length; o++) {
        int currentValue = n[o];
        if (currentValue < 0) {
          negatives.add(currentValue);
        }
      }
    }

    return negatives.size();
  }

}
