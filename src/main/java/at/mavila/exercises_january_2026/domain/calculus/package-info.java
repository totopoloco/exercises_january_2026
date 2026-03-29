/**
 * Domain package for calculus-related numerical methods.
 *
 * <p>
 * This package contains domain services for root-finding and other calculus
 * operations implemented with precise {@code BigDecimal} arithmetic.
 * </p>
 *
 * <h2>Components</h2>
 * <ul>
 * <li>{@link at.mavila.exercises_january_2026.domain.calculus.NewtonRaphsonRootFinder}
 * - Orchestrates polynomial root finding using Newton-Raphson iteration</li>
 * <li>{@link at.mavila.exercises_january_2026.domain.calculus.PolynomialEvaluator}
 * - Evaluates polynomial and derivative values via Horner's method</li>
 * <li>{@link at.mavila.exercises_january_2026.domain.calculus.ConvergenceChecker}
 * - Evaluates residual and step-size convergence conditions</li>
 * </ul>
 *
 * <h2>Subpackages</h2>
 * <ul>
 * <li>{@code exception} - Domain-specific calculus exceptions</li>
 * </ul>
 *
 * @since 2026-03-22
 */
package at.mavila.exercises_january_2026.domain.calculus;
