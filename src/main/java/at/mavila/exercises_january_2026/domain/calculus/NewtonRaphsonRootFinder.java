package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import at.mavila.exercises_january_2026.domain.calculus.exception.ConvergenceFailedException;
import at.mavila.exercises_january_2026.domain.calculus.exception.ZeroDerivativeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

/**
 * Finds a root of a polynomial using the <strong>Newton-Raphson method</strong>.
 *
 * <p>
 * Given a polynomial {@code f(x) = a₀ + a₁x + a₂x² + … + aₙxⁿ} and an initial guess {@code x₀}, the method iteratively
 * refines the approximation via
 * </p>
 *
 * <pre>
 *   x_{k+1} = x_k − f(x_k) / f′(x_k)
 * </pre>
 * <p>
 * until one of the convergence criteria is met or the iteration limit is exhausted.
 * </p>
 *
 * <h2>Architecture</h2>
 * <p>
 * This service orchestrates four injected collaborators:
 * </p>
 * <ul>
 * <li>{@link Validator} — validates the {@link NewtonRaphsonParams} record</li>
 * <li>{@link NewtonRaphsonViolationMapper} — converts constraint violations to domain exceptions</li>
 * <li>{@link PolynomialEvaluator} — evaluates f(x) and f′(x) via Horner's method</li>
 * <li>{@link ConvergenceChecker} — checks function-value and step-size convergence</li>
 * </ul>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li>Time: O(k × n × d²) where k = iterations, n = polynomial degree, d = digit count (scale)</li>
 * <li>Space: O(n × d)</li>
 * </ul>
 *
 * @author mavila
 * @since 2026-03-30
 */
@Component
@RequiredArgsConstructor
public class NewtonRaphsonRootFinder {

    private final PolynomialEvaluator polynomialEvaluator;
    private final ConvergenceChecker convergenceChecker;
    private final Validator validator;
    private final NewtonRaphsonViolationMapper violationMapper;

    /**
     * Finds an approximate root of the polynomial defined by the given parameters.
     *
     * <p>
     * The method first validates the parameter record via Jakarta Bean Validation. It then iterates the Newton-Raphson
     * formula, checking for convergence by function value ({@code |f(x)| < ε}) and by step size
     * ({@code |x_{k+1} − x_k| < ε}).
     * </p>
     *
     * @param params
     *                   the validated parameter set including coefficients, initial guess, epsilon, max iterations, and
     *                   scale
     * @return a {@link NewtonRaphsonResult} containing the approximate root, iteration count, and iteration history
     * @throws at.mavila.exercises_january_2026.domain.calculus.exception.InvalidPolynomialException
     *                                                                                                      if the
     *                                                                                                      coefficient
     *                                                                                                      list
     *                                                                                                      violates any
     *                                                                                                      constraint
     * @throws at.mavila.exercises_january_2026.domain.calculus.exception.InvalidInitialGuessException
     *                                                                                                      if the
     *                                                                                                      initial
     *                                                                                                      guess is
     *                                                                                                      null
     * @throws at.mavila.exercises_january_2026.domain.calculus.exception.InvalidToleranceException
     *                                                                                                      if epsilon
     *                                                                                                      is null or
     *                                                                                                      non-positive
     * @throws at.mavila.exercises_january_2026.domain.calculus.exception.InvalidMaxIterationsException
     *                                                                                                      if
     *                                                                                                      maxIterations
     *                                                                                                      is null or
     *                                                                                                      non-positive
     * @throws at.mavila.exercises_january_2026.domain.calculus.exception.InvalidScaleException
     *                                                                                                      if scale is
     *                                                                                                      null or
     *                                                                                                      non-positive
     * @throws ZeroDerivativeException
     *                                                                                                      if f′(x) = 0
     *                                                                                                      at any
     *                                                                                                      iterate
     * @throws ConvergenceFailedException
     *                                                                                                      if the
     *                                                                                                      iteration
     *                                                                                                      limit is
     *                                                                                                      exhausted
     */
    public NewtonRaphsonResult findRoot(final NewtonRaphsonParams params) {
        final Set<ConstraintViolation<NewtonRaphsonParams>> violations = validator.validate(params);
        violationMapper.throwOnViolation(violations, params);

        final List<BigDecimal> coefficients = params.coefficients();
        final BigDecimal epsilon = params.epsilon();
        final int maxIterations = params.maxIterations();
        final int scale = params.scale();

        BigDecimal x = params.initialGuess();
        final List<NewtonRaphsonIteration> iterations = new ArrayList<>();

        BigDecimal fx = polynomialEvaluator.evaluate(coefficients, x);

        if (convergenceChecker.hasConvergedByFunctionValue(fx, epsilon)) {
            return new NewtonRaphsonResult(x.setScale(scale, RoundingMode.HALF_UP), 0, iterations);
        }

        for (int iteration = 1; iteration <= maxIterations; iteration++) {
            final BigDecimal fpx = polynomialEvaluator.evaluateDerivative(coefficients, x);

            if (fpx.compareTo(BigDecimal.ZERO) == 0) {
                throw new ZeroDerivativeException(x, iteration);
            }

            final BigDecimal xNew = x.subtract(fx.divide(fpx, scale, RoundingMode.HALF_UP));
            iterations.add(new NewtonRaphsonIteration(iteration, xNew.setScale(scale, RoundingMode.HALF_UP)));

            if (convergenceChecker.hasConvergedByStepSize(xNew, x, epsilon)) {
                return new NewtonRaphsonResult(xNew.setScale(scale, RoundingMode.HALF_UP), iteration, iterations);
            }

            fx = polynomialEvaluator.evaluate(coefficients, xNew);

            if (convergenceChecker.hasConvergedByFunctionValue(fx, epsilon)) {
                return new NewtonRaphsonResult(xNew.setScale(scale, RoundingMode.HALF_UP), iteration, iterations);
            }

            x = xNew;
        }

        throw new ConvergenceFailedException(maxIterations);
    }
}
