package at.mavila.exercises_january_2026.domain.container;

import org.springframework.stereotype.Component;

/**
 * Domain service for calculating maximum water container area.
 * 
 * <p>
 * Given an array of heights representing vertical lines, this service
 * finds two lines that together with the x-axis form a container that
 * holds the most water.
 * </p>
 * 
 * <h2>Algorithm: Two-Pointer Technique</h2>
 * <p>
 * Start from both ends and move inward, always moving the pointer
 * with the smaller height because the area is limited by the shorter line.
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
public class WaterCalculator {

  /**
   * Calculates the maximum amount of water that can be contained between two
   * lines represented by the input array.
   *
   * @param height an array of integers representing the heights of the lines
   * @return the maximum area of water that can be contained
   * @throws IllegalArgumentException if the array is null, has fewer than 2
   *                                  elements,
   *                                  or exceeds 10^5 elements
   */
  public int maxArea(int[] height) {

    validations(height);

    int left = 0;
    int right = height.length - 1;
    int maxArea = 0;

    // Two-pointer technique: O(n) time complexity
    // Start from both ends and move inward
    while (left < right) {
      int leftHeight = height[left];
      int rightHeight = height[right];

      // Calculate area with current pointers
      int currentArea = Math.min(leftHeight, rightHeight) * (right - left);
      maxArea = Math.max(maxArea, currentArea);

      // Move the pointer with the smaller height inward
      // Why? Because the area is limited by the shorter line.
      // Moving the taller line can only decrease the area (width shrinks, min stays
      // same or shrinks)
      // Moving the shorter line might find a taller line that increases the area
      if (leftHeight <= rightHeight) {
        left++;
      } else {
        right--;
      }
    }

    return maxArea;
  }

  private void validations(int[] height) {
    if (height == null || height.length < 2) {
      throw new IllegalArgumentException("Input array must contain at least two heights.");
    }

    // Validate 10^5 constraint for array length
    if (height.length > 100000) {
      throw new IllegalArgumentException("Input array length exceeds the maximum allowed size of 10^5.");
    }
  }

}
