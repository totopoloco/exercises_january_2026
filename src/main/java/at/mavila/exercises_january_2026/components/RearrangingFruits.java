package at.mavila.exercises_january_2026.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class RearrangingFruits {

    private static final int MAX_BASKET_LENGTH = 100_000;
    private static final int MIN_FRUIT_VALUE = 1;
    private static final int MAX_FRUIT_VALUE = 1_000_000_000;

    /**
     * Rearrange costs of fruits between two baskets to make them identical.
     * Example: Given:
     * basket 1: [4,2,2,2]
     * basket 2: [1,4,1,2]
     *
     * Step 1: Let's create a frequency map for both baskets:
     * v fA(v) fB(v) fU(v)=fA+fB
     * 1 0 2 2
     * 2 3 1 4
     * 4 1 1 2
     *
     * @param basket1
     * @param basket2
     * @return
     */
    long rearrange(int[] basket1, int[] basket2) {
        validations(basket1, basket2);

        // Step 1: Create frequency map
        /*
         * freq1 ← empty map // key = fruit cost, value = count in basket1
         * freq2 ← empty map // key = fruit cost, value = count in basket2
         */
        final Map<Integer, Integer> freqMap1 = new HashMap<>();
        final Map<Integer, Integer> freqMap2 = new HashMap<>();

        /*
         * FOR each value v IN basket1
         * freq1[v] ← freq1.get(v, 0) + 1
         * END FOR
         */
        for (int n = 0; n < basket1.length; n++) {
            int currentValueBasketOne = basket1[n];
            freqMap1.put(currentValueBasketOne, freqMap1.getOrDefault(currentValueBasketOne, 0) + 1);
        }
        /*
         * FOR each value v IN basket2
         * freq2[v] ← freq2.get(v, 0) + 1
         * END FOR
         */
        for (int n = 0; n < basket2.length; n++) {
            int currentValueBasketTwo = basket2[n];
            freqMap2.put(currentValueBasketTwo, freqMap2.getOrDefault(currentValueBasketTwo, 0) + 1);
        }

        // ------------------------------------------------------------
        // 2. Feasibility test - any odd total count → impossible
        // ------------------------------------------------------------
        /*
         * FOR each key k IN union of keys(freq1) U keys(freq2)
         * total ← freq1.get(k, 0) + freq2.get(k, 0)
         * IF total MOD 2 ≠ 0
         * RETURN -1 // cannot split evenly
         * END IF
         * END FOR
         */
        // Union of keys
        final Map<Integer, Integer> unionKeys = new HashMap<>(freqMap1);
        freqMap2.forEach((key, value) -> unionKeys.merge(key, value, Integer::sum));
        for (Map.Entry<Integer, Integer> entry : unionKeys.entrySet()) {
            int total = entry.getValue();
            if (total % 2 != 0) {
                return -1; // cannot split evenly
            }
        }

        // ------------------------------------------------------------
        // 3. Determine the global minimum value (the cheapest fruit)
        // ------------------------------------------------------------
        /*
         * globalMin ← +∞
         * FOR each key k IN keys(freq1) U keys(freq2)
         * IF k < globalMin
         * globalMin ← k
         * END IF
         * END FOR
         */
        int globalMin = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : unionKeys.entrySet()) {
            int key = entry.getKey();
            if (key < globalMin) {
                globalMin = key;
            }
        }

        // ------------------------------------------------------------
        // 4. Build the “excess” lists.
        // excess1 holds items that are too many in basket1,
        // excess2 holds items that are too many in basket2.
        // ------------------------------------------------------------
        /*
         * excess1 ← empty list
         * excess2 ← empty list
         *
         * FOR each key k IN union of keys(freq1) U keys(freq2)
         * total ← freq1.get(k, 0) + freq2.get(k, 0) // even by step 2
         * targetEach ← total / 2 // how many k each basket should finally have
         *
         * // surplus in basket1 ?
         * surplus1 ← freq1.get(k, 0) - targetEach
         * IF surplus1 > 0
         * REPEAT surplus1 TIMES
         * excess1.APPEND(k)
         * END REPEAT
         * END IF
         *
         * // surplus in basket2 ?
         * surplus2 ← freq2.get(k, 0) - targetEach
         * IF surplus2 > 0
         * REPEAT surplus2 TIMES
         * excess2.APPEND(k)
         * END REPEAT
         * END IF
         * END FOR
         */
        final List<Integer> excess1 = new java.util.ArrayList<>();
        final List<Integer> excess2 = new java.util.ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : unionKeys.entrySet()) {
            int key = entry.getKey();
            int total = freqMap1.getOrDefault(key, 0) + freqMap2.getOrDefault(key, 0);
            int targetEach = total / 2;
            // surplus1 ← freq1.get(k, 0) - targetEach
            Integer currentValueAtKeyFreqMap1 = freqMap1.getOrDefault(key, 0);
            int surplus1 = currentValueAtKeyFreqMap1 - targetEach;
            // IF surplus1 > 0
            // REPEAT surplus1 TIMES
            // excess1.APPEND(k)
            // END REPEAT
            // END IF
            // for (int i = 0; i < surplus1; i++) {
            // excess1.add(key);
            // }
            while (surplus1 > 0) {
                excess1.add(key);
                surplus1--;
            }

            Integer currentValueAtKeyFreqMap2 = freqMap2.getOrDefault(key, 0);
            int surplus2 = currentValueAtKeyFreqMap2 - targetEach;
            while (surplus2 > 0) {
                excess2.add(key);
                surplus2--;
            }

        } // END FOR

        /*
         * / At this point excess1.length == excess2.length == number of swaps needed
         * // ------------------------------------------------------------
         * // 5. Sort the two excess lists.
         * // Pair smallest from excess1 with largest from excess2
         * // ------------------------------------------------------------
         * SORT(excess1) // ascending
         * SORT(excess2) // ascending
         * // we will walk from the start of excess1 and from the end of excess2
         * i ← 0
         * j ← excess2.LENGTH - 1
         * totalCost ← 0
         *
         * WHILE i ≤ excess1.LENGTH - 1
         * a ← excess1[i] // element that must leave basket1
         * b ← excess2[j] // element that must leave basket2
         *
         * // cost of swapping a ↔ b directly
         * directCost ← MIN(a, b)
         *
         * // cost of swapping via the globally cheapest fruit
         * viaMinCost ← 2 * globalMin
         *
         * // choose the cheaper option for this pair
         * totalCost ← totalCost + MIN(directCost, viaMinCost)
         *
         * i ← i + 1
         * j ← j - 1
         * END WHILE
         *
         * RETURN totalCost
         */
        // Sorting both excess lists
        excess1.sort(Integer::compareTo);
        excess2.sort(Integer::compareTo);

        int i = 0;
        int j = excess2.size() - 1;
        long totalCost = 0;

        while (i <= (excess1.size() - 1)) {
            int a = excess1.get(i); // element that must leave basket1
            int b = excess2.get(j); // element that must leave basket2

            // cost of swapping a ↔ b directly
            int directCost = Math.min(a, b);

            // cost of swapping via the globally cheapest fruit
            int viaMinCost = 2 * globalMin;

            // choose the cheaper option for this pair
            totalCost += Math.min(directCost, viaMinCost);

            i++;
            j--;

        }

        return totalCost;
    }

    private void validations(int[] basket1, int[] basket2) {
        if (Objects.isNull(basket1)) {
            throw new IllegalArgumentException("Basket 1 must not be null");
        }

        if (Objects.isNull(basket2)) {
            throw new IllegalArgumentException("Basket 2 must not be null");
        }

        // Validation for empty baskets
        if (basket1.length == 0 || basket2.length == 0) {
            throw new IllegalArgumentException("Baskets must not be empty");
        }

        // Validate same length
        if (basket1.length != basket2.length) {
            throw new IllegalArgumentException("Baskets must have the same length");
        }

        // Validate maximum length (1 <= length <= 10^5)
        if (basket1.length > MAX_BASKET_LENGTH) {
            throw new IllegalArgumentException("Basket length must not exceed " + MAX_BASKET_LENGTH);
        }

        // Validate fruit values (1 <= value <= 10^9)
        for (int i = 0; i < basket1.length; i++) {
            if (basket1[i] < MIN_FRUIT_VALUE || basket1[i] > MAX_FRUIT_VALUE) {
                throw new IllegalArgumentException(
                        "Basket 1 contains invalid fruit value at index " + i + ": " + basket1[i] +
                                ". Values must be between " + MIN_FRUIT_VALUE + " and " + MAX_FRUIT_VALUE);
            }
            if (basket2[i] < MIN_FRUIT_VALUE || basket2[i] > MAX_FRUIT_VALUE) {
                throw new IllegalArgumentException(
                        "Basket 2 contains invalid fruit value at index " + i + ": " + basket2[i] +
                                ". Values must be between " + MIN_FRUIT_VALUE + " and " + MAX_FRUIT_VALUE);
            }
        }
    }

}
