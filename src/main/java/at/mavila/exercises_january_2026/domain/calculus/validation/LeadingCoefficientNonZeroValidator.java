package at.mavila.exercises_january_2026.domain.calculus.validation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validates that the last element of a {@link BigDecimal} coefficient list (the leading coefficient of the
 * highest-order polynomial term) is not zero.
 *
 * <p>
 * Returns {@code true} (valid) when the list is {@code null}, empty, or its last element is {@code null}, deferring
 * those checks to {@code @NotEmpty}, {@code @Size}, and {@code @NoNullElements} respectively.
 * </p>
 *
 * @author mavila
 * @since 2026-03-30
 */
public class LeadingCoefficientNonZeroValidator
    implements ConstraintValidator<LeadingCoefficientNonZero, List<BigDecimal>> {

  /**
   * Returns {@code false} only when the list is non-empty, its last element is non-null, and that element compares
   * equal to zero.
   *
   * @param value
   *                  the coefficient list to validate
   * @param context
   *                  the constraint validator context
   * @return {@code true} if the leading coefficient is non-zero or the precondition is not met
   */
  @Override
  public boolean isValid(final List<BigDecimal> value, final ConstraintValidatorContext context) {
    if (Objects.isNull(value) || value.isEmpty()) {
      return true;
    }
    final BigDecimal leading = value.getLast();
    if (Objects.isNull(leading)) {
      return true;
    }
    return leading.compareTo(BigDecimal.ZERO) != 0;
  }
}
