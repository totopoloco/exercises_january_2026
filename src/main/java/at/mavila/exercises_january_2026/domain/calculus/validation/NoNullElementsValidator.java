package at.mavila.exercises_january_2026.domain.calculus.validation;

import java.util.List;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validates that every element in a {@link List} is non-null.
 *
 * <p>
 * A {@code null} list is considered valid so that {@code @NotEmpty} can handle the null-list case independently.
 * </p>
 *
 * @author mavila
 * @since 2026-03-30
 */
public class NoNullElementsValidator implements ConstraintValidator<NoNullElements, List<?>> {

  /**
   * Returns {@code true} when the list is {@code null} or contains no {@code null} elements.
   *
   * @param value
   *                  the list to validate
   * @param context
   *                  the constraint validator context
   * @return {@code false} only if the list is non-null and contains a {@code null} element
   */
  @Override
  public boolean isValid(final List<?> value, final ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return true;
    }
    return value.stream().noneMatch(Objects::isNull);
  }
}
