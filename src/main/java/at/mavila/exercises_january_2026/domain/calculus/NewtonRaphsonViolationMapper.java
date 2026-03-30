package at.mavila.exercises_january_2026.domain.calculus;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidInitialGuessException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidMaxIterationsException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidPolynomialException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidScaleException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidToleranceException;
import jakarta.validation.ConstraintViolation;

/**
 * Maps Jakarta Bean Validation {@link ConstraintViolation}s from a {@link NewtonRaphsonParams} instance to the
 * appropriate domain exception.
 *
 * <p>
 * When multiple fields are violated simultaneously, exceptions are thrown in field-priority order: {@code coefficients}
 * → {@code initialGuess} → {@code epsilon} → {@code maxIterations} → {@code scale}. When a single field has multiple
 * violations (e.g., coefficients), the message with the highest priority is selected to ensure deterministic behaviour.
 * </p>
 *
 * @author mavila
 * @since 2026-03-30
 */
@Component
public class NewtonRaphsonViolationMapper {

  private static final List<String> FIELD_PRIORITY = List.of("coefficients", "initialGuess", "epsilon", "maxIterations",
      "scale");

  private static final List<String> COEFFICIENT_MESSAGE_PRIORITY = List.of("Coefficients must not be null or empty",
      "Polynomial must be at least linear (2 or more coefficients)", "All coefficients must be non-null",
      "Leading coefficient must not be zero");

  /**
   * Inspects the violation set and throws the highest-priority domain exception if any violations exist. Does nothing
   * when the set is empty.
   *
   * @param violations
   *                     the constraint violations produced by the validator
   * @param params
   *                     the original parameter record, used to extract actual field values for exception messages
   * @throws InvalidPolynomialException
   *                                         on coefficient violations
   * @throws InvalidInitialGuessException
   *                                         when initialGuess is null
   * @throws InvalidToleranceException
   *                                         when epsilon is null or non-positive
   * @throws InvalidMaxIterationsException
   *                                         when maxIterations is null or non-positive
   * @throws InvalidScaleException
   *                                         when scale is null or non-positive
   */
  public void throwOnViolation(final Set<ConstraintViolation<NewtonRaphsonParams>> violations,
      final NewtonRaphsonParams params) {
    if (violations.isEmpty()) {
      return;
    }

    final Map<String, List<ConstraintViolation<NewtonRaphsonParams>>> byField = violations.stream()
        .collect(Collectors.groupingBy(v -> v.getPropertyPath().toString()));

    for (final String field : FIELD_PRIORITY) {
      final List<ConstraintViolation<NewtonRaphsonParams>> fieldViolations = byField.get(field);
      if (Objects.nonNull(fieldViolations) && !fieldViolations.isEmpty()) {
        throwForField(field, fieldViolations, params);
      }
    }
  }

  private void throwForField(final String field, final List<ConstraintViolation<NewtonRaphsonParams>> violations,
      final NewtonRaphsonParams params) {
    switch (field) {
    case "coefficients" -> throw new InvalidPolynomialException(selectCoefficientMessage(violations));
    case "initialGuess" -> throw new InvalidInitialGuessException();
    case "epsilon" -> throw new InvalidToleranceException(params.epsilon());
    case "maxIterations" -> throw new InvalidMaxIterationsException(params.maxIterations());
    case "scale" -> throw new InvalidScaleException(params.scale());
    default -> throw new IllegalStateException("Unexpected validation field: %s".formatted(field));
    }
  }

  private String selectCoefficientMessage(final List<ConstraintViolation<NewtonRaphsonParams>> violations) {
    if (violations.size() == 1) {
      return violations.getFirst().getMessage();
    }
    return violations.stream().map(ConstraintViolation::getMessage)
        .min(Comparator.comparingInt(this::coefficientMessagePriority)).orElse(violations.getFirst().getMessage());
  }

  private int coefficientMessagePriority(final String message) {
    final int index = COEFFICIENT_MESSAGE_PRIORITY.indexOf(message);
    return index >= 0 ? index : Integer.MAX_VALUE;
  }
}
