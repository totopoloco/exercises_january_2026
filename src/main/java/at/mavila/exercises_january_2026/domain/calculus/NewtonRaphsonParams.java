package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;

import at.mavila.exercises_january_2026.domain.calculus.validation.LeadingCoefficientNonZero;
import at.mavila.exercises_january_2026.domain.calculus.validation.NoNullElements;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Immutable parameter carrier for the Newton-Raphson root finder.
 *
 * <p>
 * All input constraints are expressed declaratively via Jakarta Bean Validation annotations. The domain service
 * validates this record through an injected {@link jakarta.validation.Validator} and maps any violations to
 * domain-specific exceptions.
 * </p>
 *
 * @param coefficients
 *                          polynomial coefficients {@code [a₀, a₁, …, aₙ]} from lowest to highest order
 * @param initialGuess
 *                          starting value for Newton-Raphson iteration
 * @param epsilon
 *                          convergence tolerance (must be strictly positive)
 * @param maxIterations
 *                          maximum number of Newton steps (must be strictly positive)
 * @param scale
 *                          decimal places for internal division rounding (must be strictly positive)
 * @author mavila
 * @since 2026-03-30
 */
public record NewtonRaphsonParams(

        @NotEmpty(message = "Coefficients must not be null or empty") @Size(min = 2, message = "Polynomial must be at least linear (2 or more coefficients)") @NoNullElements(message = "All coefficients must be non-null") @LeadingCoefficientNonZero(message = "Leading coefficient must not be zero")
        List<BigDecimal> coefficients,

        @NotNull(message = "Initial guess must not be null")
        BigDecimal initialGuess,

        @NotNull(message = "Epsilon must be a positive number, got: null") @DecimalMin(value = "0", inclusive = false, message = "Epsilon must be a positive number")
        BigDecimal epsilon,

        @NotNull(message = "Max iterations must be a positive integer, got: null") @Positive(message = "Max iterations must be a positive integer")
        Integer maxIterations,

        @NotNull(message = "Scale must be a positive integer, got: null") @Positive(message = "Scale must be a positive integer")
        Integer scale) {
}
