package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Evaluates a polynomial and its first derivative at a given point using <strong>Horner's method</strong> for numerical
 * stability and efficiency.
 *
 * <p>
 * A polynomial {@code f(x) = a₀ + a₁x + a₂x² + … + aₙxⁿ} is represented by its coefficient list {@code [a₀, a₁, …, aₙ]}
 * (lowest order first). Horner's scheme rewrites the polynomial as {@code a₀ + x(a₁ + x(a₂ + … + x·aₙ))}, avoiding
 * explicit power computation and reducing the number of multiplications to O(n).
 * </p>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li>Time: O(n) per evaluation, where n is the polynomial degree</li>
 * <li>Space: O(1) beyond the input list</li>
 * </ul>
 *
 * @author mavila
 * @since 2026-03-30
 */
@Component
public class PolynomialEvaluator {

  /**
   * Evaluates {@code f(x)} using Horner's method.
   *
   * @param coefficients
   *                       the polynomial coefficients {@code [a₀, a₁, …, aₙ]}
   * @param x
   *                       the point at which to evaluate
   * @return the value {@code f(x)}
   */
  public BigDecimal evaluate(final List<BigDecimal> coefficients, final BigDecimal x) {
    BigDecimal result = coefficients.getLast();
    for (int i = coefficients.size() - 2; i >= 0; i--) {
      result = result.multiply(x).add(coefficients.get(i));
    }
    return result;
  }

  /**
   * Evaluates the first derivative {@code f′(x)} using Horner's method.
   *
   * <p>
   * The derivative coefficients are computed on the fly: {@code f′(x) = a₁ + 2a₂x + 3a₃x² + … + n·aₙxⁿ⁻¹}.
   * </p>
   *
   * @param coefficients
   *                       the polynomial coefficients {@code [a₀, a₁, …, aₙ]}
   * @param x
   *                       the point at which to evaluate the derivative
   * @return the value {@code f′(x)}
   */
  public BigDecimal evaluateDerivative(final List<BigDecimal> coefficients, final BigDecimal x) {
    final int n = coefficients.size() - 1;
    BigDecimal result = coefficients.get(n).multiply(BigDecimal.valueOf(n));
    for (int i = n - 1; i >= 1; i--) {
      result = result.multiply(x).add(coefficients.get(i).multiply(BigDecimal.valueOf(i)));
    }
    return result;
  }
}
