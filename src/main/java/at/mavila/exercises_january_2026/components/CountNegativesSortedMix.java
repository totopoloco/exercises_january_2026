package at.mavila.exercises_january_2026.components;

import static java.util.Objects.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CountNegativesSortedMix {

    public int count(final int[][] grid) {
        if (isNull(grid)) {
            throw new IllegalArgumentException("Grid must not be null");
        }

        if (grid.length == 0) {
            return 0;
        }

        final List<Integer> negatives = new ArrayList<>();
        for (int m = 0; m < grid.length; m++) {
            // Get the element for the first column
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
