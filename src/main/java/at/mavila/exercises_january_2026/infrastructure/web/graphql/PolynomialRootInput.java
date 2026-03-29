package at.mavila.exercises_january_2026.infrastructure.web.graphql;

import java.math.BigDecimal;
import java.util.List;

/**
 * GraphQL input model for the {@code findPolynomialRoot} query.
 *
 * <p>
 * This input type mirrors the GraphQL schema and keeps the controller method
 * signature concise while delegating default handling to the application layer.
 * </p>
 *
 * @param coefficients  polynomial coefficients in ascending power order
 * @param initialGuess  starting value for Newton-Raphson iteration
 * @param epsilon       optional convergence tolerance
 * @param maxIterations optional maximum number of iterations
 * @param scale         optional decimal scale for division rounding
 * @since 2026-03-24
 */
public record PolynomialRootInput(
    List<BigDecimal> coefficients,
    BigDecimal initialGuess,
    BigDecimal epsilon,
    Integer maxIterations,
    Integer scale) {
}
