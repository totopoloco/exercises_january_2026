package at.mavila.exercises_january_2026.domain.phonetic;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LetterCombinationsTest {

    @Autowired
    private LetterCombinations letterCombinations;

    @Test
    void testCombineSimplestCase() {
        /*
         * Input: digits = "23"
         * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
         */
        List<String> result = this.letterCombinations.combine("23");

        assertThat(result).containsExactlyInAnyOrder("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf");
    }
}
