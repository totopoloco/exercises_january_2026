package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Evaluates polynomials and their derivatives using Horner's method.
 *
 * <p>
 * This component provides numerically stable and efficient evaluation for
 * Newton-Raphson iteration without computing explicit powers.
 * </p>
 *
 * @since 2026-03-29
 */
@Component
public class PolynomialEvaluator {

  /**
   * Evaluates a polynomial and its derivative at a given point.
   *
   * <p>
   * Uses a reverse Horner pass over coefficients in ascending order
   * {@code [a0, a1, ..., an]} to compute both values in O(n) time.
   * </p>
   *
   * @param coefficients polynomial coefficients from constant to highest order
   * @param x            evaluation point
   * @return evaluated polynomial and derivative at {@code x}
   */
  public EvaluationResult evaluatePolynomialAndDerivative(final List<BigDecimal> coefficients, final BigDecimal x) {
    BigDecimal polynomial = coefficients.getLast();
    BigDecimal derivative = BigDecimal.ZERO;

    for (int i = coefficients.size() - 2; i >= 0; i--) {
      derivative = derivative.multiply(x).add(polynomial);
      polynomial = polynomial.multiply(x).add(coefficients.get(i));
    }

    return new EvaluationResult(polynomial, derivative);
  }

  /**
   * Evaluates a polynomial at a given point using Horner's method.
   *
   * @param coefficients polynomial coefficients from constant to highest order
   * @param x            evaluation point
   * @return polynomial value at {@code x}
   */
  public BigDecimal evaluatePolynomial(final List<BigDecimal> coefficients, final BigDecimal x) {
    BigDecimal value = coefficients.getLast();

    for (int i = coefficients.size() - 2; i >= 0; i--) {
      value = value.multiply(x).add(coefficients.get(i));
    }

    return value;
  }

  /**
   * Immutable pair containing evaluated polynomial and derivative values.
   *
   * @param polynomial value of {@code f(x)}
   * @param derivative value of {@code f'(x)}
   */
  public record EvaluationResult(BigDecimal polynomial, BigDecimal derivative) {
  }
}
