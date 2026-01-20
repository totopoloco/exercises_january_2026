package at.mavila.exercises_january_2026.domain.security;

import static org.assertj.core.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("PinCracker Tests")
class PinCrackerTest {

    @Autowired
    private PinCracker pinCracker;

    /**
     * Helper to compute MD5 hash for test setup.
     */
    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    @DisplayName("Basic PIN Cracking")
    class BasicCracking {

        @Test
        @DisplayName("Should crack simple 5-digit PIN '12345'")
        void simpleCrack() {
            final String hashed = "827ccb0eea8a706c4c34a16891f84e7b";
            final String expected = "12345";
            final String cracked = pinCracker.crack(hashed);
            assertThat(cracked).isEqualTo(expected);
        }

        @Test
        @DisplayName("Should crack PIN with leading zeros '00078'")
        void complexCrack() {
            final String hashed = "86aa400b65433b608a9db30070ec60cd";
            final String expected = "00078";
            final String cracked = pinCracker.crack(hashed);
            assertThat(cracked).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Single Digit PINs")
    class SingleDigitPins {

        @ParameterizedTest(name = "Should crack single digit PIN ''{0}''")
        @ValueSource(strings = { "0", "1", "5", "9" })
        void shouldCrackSingleDigitPins(String pin) {
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash);
            assertThat(cracked).isEqualTo(pin);
        }
    }

    @Nested
    @DisplayName("Leading Zeros")
    class LeadingZeros {

        @ParameterizedTest(name = "Should preserve leading zeros for PIN ''{0}''")
        @CsvSource({
                "00, 2",
                "000, 3",
                "0000, 4",
                "00000, 5",
                "00001, 5",
                "00010, 5",
                "00100, 5",
                "01000, 5"
        })
        void shouldPreserveLeadingZeros(String pin, int maxLen) {
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash, maxLen);
            assertThat(cracked).isEqualTo(pin);
        }

        @Test
        @DisplayName("Should crack all zeros PIN '00000'")
        void shouldCrackAllZeros() {
            String pin = "00000";
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash);
            assertThat(cracked).isEqualTo(pin);
        }
    }

    @Nested
    @DisplayName("Boundary Values")
    class BoundaryValues {

        @ParameterizedTest(name = "Should crack maximum value PIN ''{0}'' for length {1}")
        @CsvSource({
                "9, 1",
                "99, 2",
                "999, 3",
                "9999, 4",
                "99999, 5"
        })
        void shouldCrackMaximumValuePins(String pin, int maxLen) {
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash, maxLen);
            assertThat(cracked).isEqualTo(pin);
        }

        @ParameterizedTest(name = "Should crack minimum value PIN ''{0}'' for length {1}")
        @CsvSource({
                "0, 1",
                "00, 2",
                "000, 3",
                "0000, 4",
                "00000, 5"
        })
        void shouldCrackMinimumValuePins(String pin, int maxLen) {
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash, maxLen);
            assertThat(cracked).isEqualTo(pin);
        }
    }

    @Nested
    @DisplayName("Custom MaxLen Parameter")
    class CustomMaxLen {

        @Test
        @DisplayName("Should find PIN when maxLen is exact match")
        void shouldFindPinWithExactMaxLen() {
            String pin = "123";
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash, 3);
            assertThat(cracked).isEqualTo(pin);
        }

        @Test
        @DisplayName("Should find PIN when maxLen is larger than needed")
        void shouldFindPinWithLargerMaxLen() {
            String pin = "12";
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash, 5);
            assertThat(cracked).isEqualTo(pin);
        }

        @Test
        @DisplayName("Should return null when PIN exceeds maxLen")
        void shouldReturnNullWhenPinExceedsMaxLen() {
            String pin = "12345";
            String hash = md5(pin);
            // Search only up to 4 digits, so 5-digit PIN won't be found
            String cracked = pinCracker.crack(hash, 4);
            assertThat(cracked).isNull();
        }

        @Test
        @DisplayName("Should crack 6-digit PIN with maxLen=6")
        void shouldCrackSixDigitPin() {
            String pin = "123456";
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash, 6);
            assertThat(cracked).isEqualTo(pin);
        }
    }

    @Nested
    @DisplayName("Hash Input Handling")
    class HashInputHandling {

        @Test
        @DisplayName("Should handle uppercase hash")
        void shouldHandleUppercaseHash() {
            String hash = "827CCB0EEA8A706C4C34A16891F84E7B"; // uppercase
            String cracked = pinCracker.crack(hash);
            assertThat(cracked).isEqualTo("12345");
        }

        @Test
        @DisplayName("Should handle mixed case hash")
        void shouldHandleMixedCaseHash() {
            String hash = "827ccb0EEA8a706C4c34a16891F84e7B";
            String cracked = pinCracker.crack(hash);
            assertThat(cracked).isEqualTo("12345");
        }

        @Test
        @DisplayName("Should handle hash with leading/trailing whitespace")
        void shouldHandleWhitespaceInHash() {
            String hash = "  827ccb0eea8a706c4c34a16891f84e7b  ";
            String cracked = pinCracker.crack(hash);
            assertThat(cracked).isEqualTo("12345");
        }
    }

    @Nested
    @DisplayName("Not Found Cases")
    class NotFoundCases {

        @Test
        @DisplayName("Should return null for non-numeric PIN hash")
        void shouldReturnNullForNonNumericPinHash() {
            // MD5 of "abcde" - not a numeric PIN
            String hash = md5("abcde");
            String cracked = pinCracker.crack(hash);
            assertThat(cracked).isNull();
        }

        @Test
        @DisplayName("Should return null for invalid hash")
        void shouldReturnNullForInvalidHash() {
            String hash = "ffffffffffffffffffffffffffffffff";
            String cracked = pinCracker.crack(hash);
            assertThat(cracked).isNull();
        }

        @Test
        @DisplayName("Should return null when PIN is longer than maxLen")
        void shouldReturnNullForTooLongPin() {
            String pin = "999999"; // 6 digits
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash, 5); // only search up to 5
            assertThat(cracked).isNull();
        }
    }

    @Nested
    @DisplayName("Invalid Input Handling")
    class InvalidInputHandling {

        @Test
        @DisplayName("Should throw exception for maxLen < 1")
        void shouldThrowForZeroMaxLen() {
            assertThatThrownBy(() -> pinCracker.crack("somehash", 0))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("maxLen must be at least 1");
        }

        @Test
        @DisplayName("Should throw exception for negative maxLen")
        void shouldThrowForNegativeMaxLen() {
            assertThatThrownBy(() -> pinCracker.crack("somehash", -1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("maxLen must be at least 1");
        }
    }

    @Nested
    @DisplayName("Progress Listener")
    class ProgressListenerTests {

        @Test
        @DisplayName("Should call progress listener for each candidate")
        void shouldCallProgressListener() {
            AtomicLong callCount = new AtomicLong(0);
            String hash = md5("5"); // Single digit, should be found quickly

            pinCracker.crack(hash, 1, (candidate, triedSoFar) -> {
                callCount.incrementAndGet();
                return true;
            });

            // Should have tried candidates 0-5 (6 candidates)
            assertThat(callCount.get()).isEqualTo(6);
        }

        @Test
        @DisplayName("Should abort when listener returns false")
        void shouldAbortWhenListenerReturnsFalse() {
            AtomicLong callCount = new AtomicLong(0);
            String hash = md5("99999"); // Will take many iterations

            String result = pinCracker.crack(hash, 5, (candidate, triedSoFar) -> {
                callCount.incrementAndGet();
                return triedSoFar < 10; // Abort after 10 tries
            });

            assertThat(result).isNull(); // Should be null because we aborted
            assertThat(callCount.get()).isEqualTo(10);
        }

        @Test
        @DisplayName("Should pass correct trial count to listener")
        void shouldPassCorrectTrialCount() {
            AtomicLong lastTrialCount = new AtomicLong(0);
            String hash = md5("15");

            pinCracker.crack(hash, 2, (candidate, triedSoFar) -> {
                lastTrialCount.set(triedSoFar);
                return true;
            });

            // PIN "15" is at position: 10 (for length 1: 0-9) + 16 (for length 2: 00-15) =
            // 26
            assertThat(lastTrialCount.get()).isEqualTo(26);
        }

        @Test
        @DisplayName("Should work with null listener")
        void shouldWorkWithNullListener() {
            String hash = md5("123");
            String cracked = pinCracker.crack(hash, 3, null);
            assertThat(cracked).isEqualTo("123");
        }
    }

    @Nested
    @DisplayName("Various PIN Patterns")
    class VariousPinPatterns {

        static Stream<Arguments> pinPatterns() {
            return Stream.of(
                    Arguments.of("11111", "Repeated digits"),
                    Arguments.of("12321", "Palindrome"),
                    Arguments.of("13579", "Odd digits only"),
                    Arguments.of("02468", "Even digits with leading zero"),
                    Arguments.of("10101", "Alternating pattern"),
                    Arguments.of("54321", "Descending order"),
                    Arguments.of("00001", "Leading zeros with trailing 1"),
                    Arguments.of("10000", "One followed by zeros"),
                    Arguments.of("50000", "Middle digit with zeros"));
        }

        @ParameterizedTest(name = "Should crack {1} PIN ''{0}''")
        @MethodSource("pinPatterns")
        void shouldCrackVariousPatterns(String pin, String description) {
            String hash = md5(pin);
            String cracked = pinCracker.crack(hash);
            assertThat(cracked)
                    .as("Cracking %s PIN", description)
                    .isEqualTo(pin);
        }
    }

    @Nested
    @DisplayName("Search Space Verification")
    class SearchSpaceVerification {

        @Test
        @DisplayName("Should search correct number of candidates for maxLen=1")
        void shouldSearchCorrectCandidatesForLen1() {
            AtomicLong totalCandidates = new AtomicLong(0);
            // Use a hash that won't match any 1-digit PIN
            String hash = "ffffffffffffffffffffffffffffffff";

            pinCracker.crack(hash, 1, (candidate, triedSoFar) -> {
                totalCandidates.set(triedSoFar);
                return true;
            });

            // For maxLen=1: (10^2 - 10) / 9 = 10 candidates
            assertThat(totalCandidates.get()).isEqualTo(10);
        }

        @Test
        @DisplayName("Should search correct number of candidates for maxLen=2")
        void shouldSearchCorrectCandidatesForLen2() {
            AtomicLong totalCandidates = new AtomicLong(0);
            String hash = "ffffffffffffffffffffffffffffffff";

            pinCracker.crack(hash, 2, (candidate, triedSoFar) -> {
                totalCandidates.set(triedSoFar);
                return true;
            });

            // For maxLen=2: (10^3 - 10) / 9 = 110 candidates
            assertThat(totalCandidates.get()).isEqualTo(110);
        }

        @Test
        @DisplayName("Should search correct number of candidates for maxLen=3")
        void shouldSearchCorrectCandidatesForLen3() {
            AtomicLong totalCandidates = new AtomicLong(0);
            String hash = "ffffffffffffffffffffffffffffffff";

            pinCracker.crack(hash, 3, (candidate, triedSoFar) -> {
                totalCandidates.set(triedSoFar);
                return true;
            });

            // For maxLen=3: (10^4 - 10) / 9 = 1110 candidates
            assertThat(totalCandidates.get()).isEqualTo(1110);
        }
    }
}
