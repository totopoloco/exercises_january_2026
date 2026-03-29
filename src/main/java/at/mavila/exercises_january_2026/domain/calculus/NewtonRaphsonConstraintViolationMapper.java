package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;

import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidInitialGuessException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidMaxIterationsException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidPolynomialException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidScaleException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidToleranceException;
import jakarta.validation.ConstraintViolation;

/**
 * Maps Jakarta Bean Validation violations to domain-specific calculus exceptions.
 *
 * @since 2026-03-29
 */
@Component
public class NewtonRaphsonConstraintViolationMapper {

    private static final String COEFFICIENTS_PROPERTY = "coefficients";
    private static final String INITIAL_GUESS_PROPERTY = "initialGuess";
    private static final String EPSILON_PROPERTY = "epsilon";
    private static final String MAX_ITERATIONS_PROPERTY = "maxIterations";
    private static final String SCALE_PROPERTY = "scale";

    private static final List<String> COEFFICIENT_VIOLATION_PRIORITY = List.of("Coefficients must not be null or empty",
            "Polynomial must be at least linear (2 or more coefficients)", "All coefficients must be non-null",
            "Leading coefficient must not be zero");

    /**
     * Converts a set of violations into the corresponding domain exception.
     *
     * @param violations
     *                       constraint violations raised while validating {@link NewtonRaphsonParams}
     * @return domain-specific runtime exception
     */
    public RuntimeException toDomainException(final Set<ConstraintViolation<NewtonRaphsonParams>> violations) {
        final ConstraintViolation<NewtonRaphsonParams> violation = selectHighestPriorityViolation(violations);

        final String propertyPath = violation.getPropertyPath().toString();

        if (propertyPath.startsWith(COEFFICIENTS_PROPERTY)) {
            return new InvalidPolynomialException(violation.getMessage());
        }

        if (INITIAL_GUESS_PROPERTY.equals(propertyPath)) {
            return new InvalidInitialGuessException(violation.getMessage());
        }

        if (EPSILON_PROPERTY.equals(propertyPath)) {
            final BigDecimal epsilon = (BigDecimal) violation.getInvalidValue();
            if (Objects.isNull(epsilon)) {
                return new InvalidToleranceException(null, violation.getMessage());
            }

            return new InvalidToleranceException(epsilon,
                    "Epsilon must be a positive number, got: %s".formatted(epsilon));
        }

        if (MAX_ITERATIONS_PROPERTY.equals(propertyPath)) {
            final Integer maxIterations = (Integer) violation.getInvalidValue();
            if (Objects.isNull(maxIterations)) {
                return new InvalidMaxIterationsException(0, violation.getMessage());
            }

            final int value = maxIterations.intValue();
            return new InvalidMaxIterationsException(value,
                    "Max iterations must be a positive integer, got: %d".formatted(value));
        }

        if (SCALE_PROPERTY.equals(propertyPath)) {
            final Integer scale = (Integer) violation.getInvalidValue();
            if (Objects.isNull(scale)) {
                return new InvalidScaleException(0, violation.getMessage());
            }

            final int value = scale.intValue();
            return new InvalidScaleException(value, "Scale must be a positive integer, got: %d".formatted(value));
        }

        return new IllegalArgumentException(violation.getMessage());
    }

    private ConstraintViolation<NewtonRaphsonParams> selectHighestPriorityViolation(
            final Set<ConstraintViolation<NewtonRaphsonParams>> violations) {
        if (violations.isEmpty()) {
            throw new IllegalArgumentException("Constraint violations must not be empty");
        }

        for (String coefficientMessage : COEFFICIENT_VIOLATION_PRIORITY) {
            final ConstraintViolation<NewtonRaphsonParams> matchingCoefficientViolation = violations.stream()
                    .filter(violation -> violation.getPropertyPath().toString().startsWith(COEFFICIENTS_PROPERTY))
                    .filter(violation -> coefficientMessage.equals(violation.getMessage())).findFirst().orElse(null);

            if (Objects.nonNull(matchingCoefficientViolation)) {
                return matchingCoefficientViolation;
            }
        }

        return violations.stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Constraint violations must not be empty"));
    }
}
