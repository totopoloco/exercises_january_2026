package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for {@link LeadingCoefficientNonZero}.
 *
 * @since 2026-03-29
 */
public class LeadingCoefficientNonZeroValidator
    implements ConstraintValidator<LeadingCoefficientNonZero, List<BigDecimal>> {

  /**
   * Returns whether the leading coefficient is non-zero.
   *
   * @param value   candidate list of coefficients
   * @param context validation context
   * @return {@code true} when validation passes or cannot yet be evaluated
   */
  @Override
  public boolean isValid(final List<BigDecimal> value, final ConstraintValidatorContext context) {
    if (Objects.isNull(value) || value.isEmpty()) {
      return true;
    }

    final BigDecimal leadingCoefficient = value.getLast();
    return Objects.nonNull(leadingCoefficient) && leadingCoefficient.compareTo(BigDecimal.ZERO) != 0;
  }
}
