package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/**
 * Checks whether Newton-Raphson iteration has converged by evaluating two independent criteria:
 *
 * <ul>
 * <li><strong>Function-value convergence</strong>: {@code |f(x)| < ε} — the current iterate is close enough to a
 * root.</li>
 * <li><strong>Step-size convergence</strong>: {@code |x_{k+1} − x_k| < ε} — successive iterates are close enough.</li>
 * </ul>
 *
 * @author mavila
 * @since 2026-03-30
 */
@Component
public class ConvergenceChecker {

  /**
   * Returns {@code true} when the absolute function value at the current iterate is smaller than the tolerance.
   *
   * @param fx
   *                  the polynomial value {@code f(x)}
   * @param epsilon
   *                  the convergence tolerance
   * @return {@code true} if {@code |f(x)| < ε}
   */
  public boolean hasConvergedByFunctionValue(final BigDecimal fx, final BigDecimal epsilon) {
    return fx.abs().compareTo(epsilon) < 0;
  }

  /**
   * Returns {@code true} when the absolute difference between two successive iterates is smaller than the tolerance.
   *
   * @param xNew
   *                  the new iterate {@code x_{k+1}}
   * @param xOld
   *                  the previous iterate {@code x_k}
   * @param epsilon
   *                  the convergence tolerance
   * @return {@code true} if {@code |x_{k+1} − x_k| < ε}
   */
  public boolean hasConvergedByStepSize(final BigDecimal xNew, final BigDecimal xOld, final BigDecimal epsilon) {
    return xNew.subtract(xOld).abs().compareTo(epsilon) < 0;
  }
}
