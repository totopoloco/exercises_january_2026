package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.Comparator;
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
 * Maps Jakarta Bean Validation violations to domain-specific calculus
 * exceptions.
 *
 * @since 2026-03-29
 */
@Component
public class NewtonRaphsonConstraintViolationMapper {

  /**
   * Converts a set of violations into the corresponding domain exception.
   *
   * @param violations constraint violations raised while validating
   *                   {@link NewtonRaphsonParams}
   * @return domain-specific runtime exception
   */
  public RuntimeException toDomainException(final Set<ConstraintViolation<NewtonRaphsonParams>> violations) {
    final ConstraintViolation<NewtonRaphsonParams> violation = violations.stream()
        .sorted(Comparator.comparing(item -> item.getPropertyPath().toString()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Constraint violations must not be empty"));

    final String propertyPath = violation.getPropertyPath().toString();

    if (propertyPath.startsWith("coefficients")) {
      return new InvalidPolynomialException(violation.getMessage());
    }

    if ("initialGuess".equals(propertyPath)) {
      return new InvalidInitialGuessException(violation.getMessage());
    }

    if ("epsilon".equals(propertyPath)) {
      final BigDecimal epsilon = (BigDecimal) violation.getInvalidValue();
      return new InvalidToleranceException(epsilon,
          "Epsilon must be a positive number, got: %s".formatted(epsilon));
    }

    if ("maxIterations".equals(propertyPath)) {
      final Integer maxIterations = (Integer) violation.getInvalidValue();
      final int value = Objects.isNull(maxIterations) ? 0 : maxIterations.intValue();
      return new InvalidMaxIterationsException(value,
          "Max iterations must be a positive integer, got: %d".formatted(value));
    }

    if ("scale".equals(propertyPath)) {
      final Integer scale = (Integer) violation.getInvalidValue();
      final int value = Objects.isNull(scale) ? 0 : scale.intValue();
      return new InvalidScaleException(value,
          "Scale must be a positive integer, got: %d".formatted(value));
    }

    return new IllegalArgumentException(violation.getMessage());
  }
}
