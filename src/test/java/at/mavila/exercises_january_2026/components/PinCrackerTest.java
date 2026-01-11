package at.mavila.exercises_january_2026.components;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PinCrackerTest {

    @Autowired
    private PinCracker pinCracker;

    @Test
    void simpleCrack() {

        final String hashed = "827ccb0eea8a706c4c34a16891f84e7b";
        final String expected = "12345";
        final String cracked = this.pinCracker.crack(hashed);
        assertThat(cracked).isEqualTo(expected);

    }

    @Test
    void complexCrack() {
        final String hashed = "86aa400b65433b608a9db30070ec60cd";
        final String expected = "00078";
        final String cracked = this.pinCracker.crack(hashed);
        assertThat(cracked).isEqualTo(expected);
    }
}
