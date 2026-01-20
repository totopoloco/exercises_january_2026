package at.mavila.exercises_january_2026.domain.number;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OddFinderTest {

    @Autowired
    private OddFinder oddFinder;

    @Test
    void testFindIt() {
        final int[] input = { 1, 2, 2, 3, 3, 3, 4, 3, 3, 3, 2, 2, 1 };
        final int expected = 4;
        assertThat(this.oddFinder.findIt(input)).isEqualTo(expected);
    }

    @Test
    void testFindItZerosAndOnes() {
        final int[] input = { 0, 1, 0, 1, 0 };
        final int expected = 0;
        assertThat(this.oddFinder.findIt(input)).isEqualTo(expected);
    }

    @Test
    void testOneDigit() {
        final int[] input = { 7 };
        final int expected = 7;
        assertThat(this.oddFinder.findIt(input)).isEqualTo(expected);
    }

    @Test
    void testZero() {
        final int[] input = { 0 };
        final int expected = 0;
        assertThat(this.oddFinder.findIt(input)).isEqualTo(expected);
    }

}
