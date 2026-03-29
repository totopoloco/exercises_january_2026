package at.mavila.exercises_january_2026.application;

import java.math.BigDecimal;
import java.util.List;

/**
 * Application-layer request object for polynomial root finding.
 *
 * <p>
 * This record captures the raw user-facing inputs before application-level
 * default values are applied and before the request is translated into the
 * domain-specific
 * {@link at.mavila.exercises_january_2026.domain.calculus.NewtonRaphsonParams}.
 * </p>
 *
 * @param coefficients  polynomial coefficients in ascending power order
 * @param initialGuess  starting value for iteration
 * @param epsilon       optional convergence tolerance; defaults to
 *                      {@code 1e-10}
 *                      when absent
 * @param maxIterations optional iteration limit; defaults to {@code 1000} when
 *                      absent
 * @param scale         optional decimal scale; defaults to {@code 10} when
 *                      absent
 * @since 2026-03-24
 */
public record PolynomialRootRequest(
    List<BigDecimal> coefficients,
    BigDecimal initialGuess,
    BigDecimal epsilon,
    Integer maxIterations,
    Integer scale) {
}
