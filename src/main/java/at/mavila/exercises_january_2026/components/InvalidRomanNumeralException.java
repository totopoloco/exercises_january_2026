package at.mavila.exercises_january_2026.components;

/**
 * Exception thrown when an invalid Roman numeral string is encountered.
 *
 * <p>
 * A Roman numeral is considered invalid if it violates any of these rules:
 * <ul>
 * <li>Invalid subtraction pairs (only I before V/X, X before L/C, C before
 * D/M)</li>
 * <li>V, L, D cannot be used for subtraction</li>
 * <li>V, L, D cannot repeat</li>
 * <li>I, X, C, M cannot repeat more than 3 times consecutively</li>
 * <li>Double subtraction patterns (e.g., IIV, XXC)</li>
 * </ul>
 *
 * @author exercises_january_2026
 */
public class InvalidRomanNumeralException extends RuntimeException {

    private final String invalidInput;
    private final String violationReason;

    /**
     * Constructs a new InvalidRomanNumeralException with the specified input and
     * reason.
     *
     * @param invalidInput    the invalid Roman numeral string
     * @param violationReason a description of why the input is invalid
     */
    public InvalidRomanNumeralException(String invalidInput, String violationReason) {
        super(String.format("Invalid Roman numeral '%s': %s", invalidInput, violationReason));
        this.invalidInput = invalidInput;
        this.violationReason = violationReason;
    }

    /**
     * Constructs a new InvalidRomanNumeralException with the specified input,
     * reason, and cause.
     *
     * @param invalidInput    the invalid Roman numeral string
     * @param violationReason a description of why the input is invalid
     * @param cause           the cause of this exception
     */
    public InvalidRomanNumeralException(String invalidInput, String violationReason, Throwable cause) {
        super(String.format("Invalid Roman numeral '%s': %s", invalidInput, violationReason), cause);
        this.invalidInput = invalidInput;
        this.violationReason = violationReason;
    }

    /**
     * Returns the invalid input that caused this exception.
     *
     * @return the invalid Roman numeral string
     */
    public String getInvalidInput() {
        return invalidInput;
    }

    /**
     * Returns the reason why the input is invalid.
     *
     * @return the violation reason
     */
    public String getViolationReason() {
        return violationReason;
    }

}
