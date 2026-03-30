package at.mavila.exercises_january_2026.domain.calculus.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates that a {@link java.util.List} contains no {@code null} elements.
 *
 * <p>
 * A {@code null} list is considered valid — use {@code @NotEmpty} to guard against null lists separately.
 * </p>
 *
 * @author mavila
 * @since 2026-03-30
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NoNullElementsValidator.class)
public @interface NoNullElements {

  /**
   * @return the error message template
   */
  String message() default "All elements must be non-null";

  /**
   * @return the validation groups
   */
  Class<?>[] groups() default {};

  /**
   * @return the payload
   */
  Class<? extends Payload>[] payload() default {};
}
