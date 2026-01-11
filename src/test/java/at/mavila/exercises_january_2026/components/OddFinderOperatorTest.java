package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link OddFinderOperator} demonstrating the XOR (^) bitwise
 * operator.
 *
 * <h2>XOR Operator Properties</h2>
 * <p>
 * The XOR (exclusive or) operator has special properties that make it perfect
 * for finding the element that appears an odd number of times:
 * </p>
 *
 * <h3>Key Properties:</h3>
 * <ul>
 * <li><b>Self-inverse:</b> {@code a ^ a = 0} (any number XOR itself equals
 * zero)</li>
 * <li><b>Identity:</b> {@code a ^ 0 = a} (any number XOR zero equals
 * itself)</li>
 * <li><b>Commutative:</b> {@code a ^ b = b ^ a} (order doesn't matter)</li>
 * <li><b>Associative:</b> {@code (a ^ b) ^ c = a ^ (b ^ c)} (grouping doesn't
 * matter)</li>
 * </ul>
 *
 * <h3>How It Works (Binary Level):</h3>
 *
 * <pre>
 *   5 in binary: 101
 *   5 in binary: 101
 *   5 ^ 5      : 000  (same bits cancel out → 0)
 *
 *   4 in binary: 100
 *   5 in binary: 101
 *   4 ^ 5      : 001  (different bits become 1 → 1)
 *
 *   7 in binary: 111
 *   3 in binary: 011
 *   7 ^ 3      : 100  (→ 4)
 * </pre>
 *
 * <h3>Why It Finds Odd-Occurring Elements:</h3>
 * <p>
 * When XORing all elements in an array:
 * </p>
 * <ul>
 * <li>Elements appearing an <b>even</b> number of times cancel out (a ^ a =
 * 0)</li>
 * <li>Elements appearing an <b>odd</b> number of times remain</li>
 * </ul>
 *
 * <h3>Example:</h3>
 *
 * <pre>
 *   Array: [1, 2, 2, 3, 3, 3, 4, 3, 3, 3, 2, 2, 1]
 *
 *   Step by step XOR:
 *   0 ^ 1 = 1
 *   1 ^ 2 = 3
 *   3 ^ 2 = 1      (2 appears twice, cancels)
 *   1 ^ 3 = 2
 *   2 ^ 3 = 1      (3 appears twice so far)
 *   1 ^ 3 = 2
 *   2 ^ 4 = 6      (4 appears once - odd!)
 *   6 ^ 3 = 5
 *   5 ^ 3 = 6      (3 appears 4 times now)
 *   6 ^ 3 = 5
 *   5 ^ 2 = 7
 *   7 ^ 2 = 5      (2 appears 4 times - even, cancels)
 *   5 ^ 1 = 4      (1 appears twice - even, cancels)
 *
 *   Result: 4 (the only element appearing an odd number of times)
 * </pre>
 *
 * <h3>Time &amp; Space Complexity:</h3>
 * <ul>
 * <li><b>Time:</b> O(n) - single pass through the array</li>
 * <li><b>Space:</b> O(1) - only one variable needed</li>
 * </ul>
 *
 * @author mavila
 * @see OddFinderOperator
 * @see OddFinder
 */
@SpringBootTest
@DisplayName("OddFinderOperator XOR Tests")
class OddFinderOperatorTest {
    @Autowired
    private OddFinderOperator oddFinderOperator;

    @Test
    @DisplayName("XOR: a ^ a = 0 (self-inverse property)")
    void testSelfInverse() {
        // 5 ^ 5 = 0: Any number XOR itself equals zero
        // Binary: 101 ^ 101 = 000
        assertThat(this.oddFinderOperator.applyAsInt(5, 5)).isEqualTo(0);
    }

    @Test
    @DisplayName("XOR: a ^ 0 = a (identity property)")
    void testIdentity() {
        // 0 ^ 9 = 9: Any number XOR zero equals itself
        // Binary: 0000 ^ 1001 = 1001
        assertThat(this.oddFinderOperator.applyAsInt(0, 9)).isEqualTo(9);
    }

    @Test
    @DisplayName("XOR: 4 ^ 5 = 1 (different bits)")
    void testDifferentNumbers() {
        // 4 ^ 5 = 1
        // Binary: 100 ^ 101 = 001
        assertThat(this.oddFinderOperator.applyAsInt(4, 5)).isEqualTo(1);
    }

    @Test
    @DisplayName("XOR: 7 ^ 3 = 4 (bit manipulation)")
    void testBitManipulation() {
        // 7 ^ 3 = 4
        // Binary: 111 ^ 011 = 100
        assertThat(this.oddFinderOperator.applyAsInt(7, 3)).isEqualTo(4);
    }
}
