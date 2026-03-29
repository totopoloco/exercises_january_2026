package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for {@link NoNullElements}.
 *
 * @since 2026-03-29
 */
public class NoNullElementsValidator implements ConstraintValidator<NoNullElements, List<BigDecimal>> {

  /**
   * Returns whether the list contains only non-null elements.
   *
   * @param value   candidate list of coefficients
   * @param context validation context
   * @return {@code true} when all elements are non-null or the list itself is
   *         null
   */
  @Override
  public boolean isValid(final List<BigDecimal> value, final ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return true;
    }

    return value.stream().allMatch(Objects::nonNull);
  }
}
