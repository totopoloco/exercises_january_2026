/**
 * Calculus subdomain — numerical methods for polynomial root finding.
 *
 * <h2>Purpose</h2>
 * <p>
 * Provides implementations of iterative numerical algorithms operating on polynomials represented as
 * {@link java.math.BigDecimal} coefficient lists. All arithmetic uses {@code BigDecimal} to avoid floating-point
 * rounding and to support arbitrary-precision results.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 * <li>{@link at.mavila.exercises_january_2026.domain.calculus.NewtonRaphsonRootFinder} — orchestrates Newton-Raphson
 * iteration</li>
 * <li>{@link at.mavila.exercises_january_2026.domain.calculus.PolynomialEvaluator} — evaluates f(x) and f′(x) via
 * Horner's method</li>
 * <li>{@link at.mavila.exercises_january_2026.domain.calculus.ConvergenceChecker} — checks function-value and step-size
 * convergence criteria</li>
 * <li>{@link at.mavila.exercises_january_2026.domain.calculus.NewtonRaphsonParams} — validated parameter record with
 * Jakarta Bean Validation constraints</li>
 * </ul>
 *
 * <h2>Design Principles</h2>
 * <ul>
 * <li>Input validation is fully declarative via Jakarta Bean Validation annotations on the parameter record.</li>
 * <li>Domain exceptions in the {@code exception} sub-package carry contextual fields for downstream error
 * reporting.</li>
 * <li>Each collaborator is a Spring {@code @Component} injected via constructor, keeping the root-finder's public
 * method a high-level orchestration recipe.</li>
 * </ul>
 *
 * @since 2026-03-30
 */
package at.mavila.exercises_january_2026.domain.calculus;
