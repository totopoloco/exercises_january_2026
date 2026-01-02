package at.mavila.exercises_january_2026.components;

import org.springframework.stereotype.Component;

@Component
public class ContainerWater {

    /**
     * Calculates the maximum amount of water that can be contained between two
     * lines
     * represented by the input array.
     *
     * @param height an array of integers representing the heights of the lines
     * @return the maximum area of water that can be contained
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
