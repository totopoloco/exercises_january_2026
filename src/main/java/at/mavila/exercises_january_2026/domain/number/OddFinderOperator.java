package at.mavila.exercises_january_2026.domain.number;

import java.util.function.IntBinaryOperator;

import org.springframework.stereotype.Component;

/**
 * Domain operator for XOR-based odd number detection.
 *
 * <p>
 * This operator implements XOR operation used to find the element
 * that appears an odd number of times in an array.
 * </p>
 *
 * <h2>Mathematical Principle</h2>
 * <p>
 * XOR has these properties:
 * </p>
 * <ul>
 * <li>a ^ a = 0 (any number XOR with itself is 0)</li>
 * <li>a ^ 0 = a (any number XOR with 0 is itself)</li>
 * <li>XOR is commutative and associative</li>
 * </ul>
 * <p>
 * Therefore, XORing all elements cancels out pairs, leaving only the odd
 * occurrence.
 * </p>
 *
 * @author mavila
 * @since January 2026
 */
@Component
public class OddFinderOperator implements IntBinaryOperator {

  @Override
  public int applyAsInt(int left, int right) {
    return left ^ right;
  }

}
