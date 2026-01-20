package at.mavila.exercises_january_2026.domain.string;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MaskServiceTest {

    @Autowired
    private MaskService maskService;

    @Test
    void testMaskifyCreditCard() {
        final String input = "4556364607935616";
        final String expected = "############5616";
        assertThat(this.maskService.maskify(input)).isEqualTo(expected);
    }

    @Test
    void testMaskifyShortString() {
        final String input = "64607935616";
        final String expected = "#######5616";
        assertThat(this.maskService.maskify(input)).isEqualTo(expected);
    }

    @Test
    void testMaskifyVeryShortString() {
        final String input = "1";
        final String expected = "1";
        assertThat(this.maskService.maskify(input)).isEqualTo(expected);
    }

    @Test
    void testMaskifyEmptyString() {
        final String input = "";
        final String expected = "";
        assertThat(this.maskService.maskify(input)).isEqualTo(expected);
    }

    @Test
    void testMaskifySkippy() {
        final String input = "Skippy";
        final String expected = "##ippy";
        assertThat(this.maskService.maskify(input)).isEqualTo(expected);
    }

    @Test
    void testMaskifyBatman() {
        final String input = "Nananananananananananananananana Batman!";
        final String expected = "####################################man!";
        assertThat(this.maskService.maskify(input)).isEqualTo(expected);
    }

}
