package at.mavila.exercises_january_2026.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ArrayMediaCalculator {

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
