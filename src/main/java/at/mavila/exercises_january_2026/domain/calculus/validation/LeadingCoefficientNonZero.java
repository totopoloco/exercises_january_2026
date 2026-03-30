package at.mavila.exercises_january_2026.domain.calculus.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates that the last element (leading coefficient of the highest-order term) in a {@link java.util.List} of
 * {@link java.math.BigDecimal} is not zero.
 *
 * <p>
 * A {@code null} or empty list, or a list whose last element is {@code null}, is considered valid — other annotations
 * ({@code @NotEmpty}, {@code @NoNullElements}) handle those cases.
 * </p>
 *
 * @author mavila
 * @since 2026-03-30
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LeadingCoefficientNonZeroValidator.class)
public @interface LeadingCoefficientNonZero {

  /**
   * @return the error message template
   */
  String message() default "Leading coefficient must not be zero";

  /**
   * @return the validation groups
   */
  Class<?>[] groups() default {};

  /**
   * @return the payload
   */
  Class<? extends Payload>[] payload() default {};
}
