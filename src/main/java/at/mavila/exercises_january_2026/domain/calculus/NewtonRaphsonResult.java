package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.util.List;

/**
 * The result of a Newton-Raphson root-finding computation.
 *
 * @param root
 *                           an approximate root of the polynomial, rounded to the requested scale
 * @param iterationCount
 *                           the number of iterations performed before convergence (1-based)
 * @param iterations
 *                           ordered list of every iteration performed, each recording the step number and computed x
 *                           value
 * @author mavila
 * @since 2026-03-31
 */
public record NewtonRaphsonResult(BigDecimal root, int iterationCount, List<NewtonRaphsonIteration> iterations) {
}
