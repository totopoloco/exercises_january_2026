package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Immutable parameter object for Newton-Raphson polynomial root finding.
 *
 * <p>
 * This record centralizes all Newton-Raphson inputs and declares validation
 * constraints using Jakarta Bean Validation so domain-service logic can focus
 * strictly on algorithm orchestration.
 * </p>
 *
 * @param coefficients  polynomial coefficients in ascending power order
 *                      {@code [a0, a1, ..., an]}
 * @param initialGuess  starting value for Newton-Raphson iteration
 * @param epsilon       convergence tolerance that must be strictly positive
 * @param maxIterations maximum iteration count that must be strictly positive
 * @param scale         decimal scale used for division rounding, must be
 *                      strictly
 *                      positive
 * @since 2026-03-29
 */
public record NewtonRaphsonParams(
        @NotEmpty(message = "Coefficients must not be null or empty") @Size(min = 2, message = "Polynomial must be at least linear (2 or more coefficients)") @NoNullElements(message = "All coefficients must be non-null") @LeadingCoefficientNonZero(message = "Leading coefficient must not be zero") List<BigDecimal> coefficients,

        @NotNull(message = "Initial guess must not be null") BigDecimal initialGuess,

        @NotNull(message = "Epsilon must be a positive number, got: null") @DecimalMin(value = "0", inclusive = false, message = "Epsilon must be a positive number") BigDecimal epsilon,

        @NotNull(message = "Max iterations must be a positive integer, got: null") @Positive(message = "Max iterations must be a positive integer") Integer maxIterations,

        @NotNull(message = "Scale must be a positive integer, got: null") @Positive(message = "Scale must be a positive integer") Integer scale) {
}
