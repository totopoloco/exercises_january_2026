package at.mavila.exercises_january_2026.components;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Given an array of integers, find the one that appears an odd number of times.
 *
 * There will always be only one integer that appears an odd number of times.
 *
 * @author mavila
 * @since 11.01.2026
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OddFinder {

    private final IntBinaryOperator oddFinderOperator;

    /**
     * Finds the integer that appears an odd number of times in the given array.
     *
     * @param a the array of integers to search e.g. [1,2,2,3,3,3,4,3,3,3,2,2,1]
     * @return the integer that appears an odd number of times e.g. 4 because it
     *         appears 1 time (which is odd)
     */
    public int findIt(final int[] a) {
        // Let's use the Stream API to reduce the array to the odd occurring element
        return Arrays.stream(a).reduce(0, this.oddFinderOperator);
    }

}
