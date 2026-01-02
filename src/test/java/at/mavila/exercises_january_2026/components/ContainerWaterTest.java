package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContainerWaterTest {

    @Autowired
    private ContainerWater containerWater;

    @Test
    void testMaxArea() {
        int[] height = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        int result = containerWater.maxArea(height);
        assertThat(result).isEqualTo(49);

    }

    @Test
    void testMaxAreaWithMinimumInput() {
        int[] height = { 8, 7, 2, 1 };
        int result = containerWater.maxArea(height);
        assertThat(result).isEqualTo(7);
    }

    @Test
    void testMaxAreaWithLongArray() {

    }
}
