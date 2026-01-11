package at.mavila.exercises_january_2026.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * Given an array of integers, find the one that appears an odd number of times.
 *
 * There will always be only one integer that appears an odd number of times.
 *
 * @author mavila
 * @since 11.01.2026
 */
@Component
public class OddFinder {

    /**
     * Finds the integer that appears an odd number of times in the given array.
     *
     * @param a the array of integers to search e.g. [1,2,2,3,3,3,4,3,3,3,2,2,1]
     * @return the integer that appears an odd number of times e.g. 4 because it
     *         appears 1 time (which is odd)
     */
    public int findIt(final int[] a) {

        if (Objects.isNull(a)) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        // Convert the array to a map of counts
        // Then find the entry with an odd count
        final List<Integer> aList = new ArrayList<>();

        for (int n = 0; n < a.length; n++) {
            aList.add(a[n]);
        }

        // Transform to a map of counts where the key is the number and the value is the
        // count
        for (Integer number : aList) {
            long count = aList.stream().filter(n -> n.equals(number)).count();
            if (count % 2 != 0) {
                return number;
            }
        }

        return 0;

    }

}
