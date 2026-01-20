package at.mavila.exercises_january_2026.domain.collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * Domain service for calculating minimum cost to rearrange fruits between
 * baskets.
 *
 * <p>
 * Given two baskets of fruits (represented by their costs), this service
 * calculates
 * the minimum cost to make both baskets identical through swapping.
 * </p>
 *
 * <h2>Algorithm Overview</h2>
 * <ol>
 * <li>Build frequency maps for both baskets</li>
 * <li>Check feasibility (all fruit types must have even total count)</li>
 * <li>Find the global minimum fruit value</li>
 * <li>Build excess lists (fruits that need to be swapped)</li>
 * <li>Match excess items optimally using direct swap or via minimum fruit</li>
 * </ol>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(n log n) due to sorting</li>
 * <li><b>Space:</b> O(n) for frequency maps and excess lists</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
public class RearrangingFruits {

  private static final int MAX_BASKET_LENGTH = 100_000;
  private static final int MIN_FRUIT_VALUE = 1;
  private static final int MAX_FRUIT_VALUE = 1_000_000_000;

  /**
   * Calculates the minimum cost to rearrange fruits to make both baskets
   * identical.
   *
   * @param basket1 the first basket of fruit costs
   * @param basket2 the second basket of fruit costs
   * @return the minimum cost, or -1 if impossible
   * @throws IllegalArgumentException if inputs are invalid
   */
  public long rearrange(int[] basket1, int[] basket2) {
    validations(basket1, basket2);

    // Step 1: Create frequency maps
    final Map<Integer, Integer> freqMap1 = new HashMap<>();
    final Map<Integer, Integer> freqMap2 = new HashMap<>();

    for (int n = 0; n < basket1.length; n++) {
      int currentValueBasketOne = basket1[n];
      freqMap1.put(currentValueBasketOne, freqMap1.getOrDefault(currentValueBasketOne, 0) + 1);
    }

    for (int n = 0; n < basket2.length; n++) {
      int currentValueBasketTwo = basket2[n];
      freqMap2.put(currentValueBasketTwo, freqMap2.getOrDefault(currentValueBasketTwo, 0) + 1);
    }

    // Step 2: Feasibility test - any odd total count → impossible
    final Map<Integer, Integer> unionKeys = new HashMap<>(freqMap1);
    freqMap2.forEach((key, value) -> unionKeys.merge(key, value, Integer::sum));
    for (Map.Entry<Integer, Integer> entry : unionKeys.entrySet()) {
      int total = entry.getValue();
      if (total % 2 != 0) {
        return -1; // cannot split evenly
      }
    }

    // Step 3: Determine the global minimum value
    int globalMin = Integer.MAX_VALUE;
    for (Map.Entry<Integer, Integer> entry : unionKeys.entrySet()) {
      int key = entry.getKey();
      if (key < globalMin) {
        globalMin = key;
      }
    }

    // Step 4: Build the excess lists
    final List<Integer> excess1 = new java.util.ArrayList<>();
    final List<Integer> excess2 = new java.util.ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : unionKeys.entrySet()) {
      int key = entry.getKey();
      int total = freqMap1.getOrDefault(key, 0) + freqMap2.getOrDefault(key, 0);
      int targetEach = total / 2;

      Integer currentValueAtKeyFreqMap1 = freqMap1.getOrDefault(key, 0);
      int surplus1 = currentValueAtKeyFreqMap1 - targetEach;
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
    }

    // Step 5: Sort and match optimally
    excess1.sort(Integer::compareTo);
    excess2.sort(Integer::compareTo);

    int i = 0;
    int j = excess2.size() - 1;
    long totalCost = 0;

    while (i <= (excess1.size() - 1)) {
      int a = excess1.get(i);
      int b = excess2.get(j);

      // Cost of swapping directly
      int directCost = Math.min(a, b);

      // Cost of swapping via the globally cheapest fruit
      int viaMinCost = 2 * globalMin;

      // Choose the cheaper option
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

    if (basket1.length == 0 || basket2.length == 0) {
      throw new IllegalArgumentException("Baskets must not be empty");
    }

    if (basket1.length != basket2.length) {
      throw new IllegalArgumentException("Baskets must have the same length");
    }

    if (basket1.length > MAX_BASKET_LENGTH) {
      throw new IllegalArgumentException("Basket length must not exceed " + MAX_BASKET_LENGTH);
    }

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
