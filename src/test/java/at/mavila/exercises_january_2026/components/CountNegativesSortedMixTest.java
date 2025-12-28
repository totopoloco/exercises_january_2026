package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CountNegativesSortedMixTest {

    @Autowired
    private CountNegativesSortedMix countNegativesSortedMix;

    @Test
    void countNegatives() {
        final int count = this.countNegativesSortedMix.count(new int[][] {
                { 4, 3, 2, -1 },
                { 3, 2, 1, -1 },
                { 1, 1, -1, -2 },
                { -1, -1, -2, -3 }
        });
        assertThat(count).isEqualTo(8);
    }

    @Test
    void countNoNegatives() {
        final int count = this.countNegativesSortedMix.count(new int[][] {
                { 3, 2 },
                { 1, 0 }
        });
        assertThat(count).isEqualTo(0);
    }

    @Test
    void validateEmptyGrid() {
        final int count = this.countNegativesSortedMix.count(new int[][] {});
        assertThat(count).isEqualTo(0);
    }

    @Test
    void validateNullGrid() {
        assertThatThrownBy(() -> this.countNegativesSortedMix.count(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Grid must not be null");
    }

    @Test
    void countNegatives_Example1() {
        final int count = this.countNegativesSortedMix.count(new int[][] {
                { 4, 3, 2, -1 },
                { 3, 2, 1, -1 },
                { 1, 1, -1, -2 },
                { -1, -1, -2, -3 }
        });
        assertThat(count).isEqualTo(8); // There are 8 negatives number in the matrix.
    }

    @Test
    void countNegatives_Example2() {
        final int count = this.countNegativesSortedMix.count(new int[][] {
                { 3, 2 },
                { 1, 0 }
        });
        assertThat(count).isEqualTo(0);
    }
}
