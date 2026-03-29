package at.mavila.exercises_january_2026.domain.calculus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validation constraint ensuring a list does not contain null elements.
 *
 * @since 2026-03-29
 */
@Documented
@Constraint(validatedBy = NoNullElementsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNullElements {

  /**
   * Validation error message.
   *
   * @return message template
   */
  String message() default "All coefficients must be non-null";

  /**
   * Validation groups.
   *
   * @return groups
   */
  Class<?>[] groups() default {};

  /**
   * Validation payload.
   *
   * @return payload metadata
   */
  Class<? extends Payload>[] payload() default {};
}
