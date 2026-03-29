package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;

import at.mavila.exercises_january_2026.domain.calculus.exception.ConvergenceFailedException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidInitialGuessException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidMaxIterationsException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidPolynomialException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidScaleException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidToleranceException;
import at.mavila.exercises_january_2026.domain.calculus.exception.ZeroDerivativeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

/**
 * Domain service that finds a polynomial root using the Newton-Raphson method.
 *
 * <p>
 * Given polynomial coefficients {@code [a0, a1, ..., an]} where
 * {@code f(x) = a0 + a1*x + ... + an*x^n}, this component iteratively computes:
 * </p>
 *
 * <pre>
 * x(k+1) = x(k) - f(x(k)) / f'(x(k))
 * </pre>
 *
 * <p>
 * Polynomial and derivative evaluation are implemented with Horner's method.
 * </p>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(k × n), where {@code k} is the number of iterations and
 * {@code n} is the polynomial degree</li>
 * <li><b>Space:</b> O(1) excluding input storage</li>
 * </ul>
 *
 * @author mavila
 * @since 2026-03-24
 */
@Component
@RequiredArgsConstructor
public class NewtonRaphsonRootFinder {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private final Validator validator;
    private final NewtonRaphsonConstraintViolationMapper violationMapper;
    private final PolynomialEvaluator polynomialEvaluator;
    private final ConvergenceChecker convergenceChecker;

    /**
     * Finds a polynomial root using Newton-Raphson iteration.
     *
     * <p>
     * The polynomial and its derivative are evaluated with Horner's method to
     * avoid explicit power computation. On each iteration the method applies
     * $x_{k+1} = x_k - f(x_k) / f'(x_k)$ until either the residual or the step
     * size falls below {@code epsilon}.
     * </p>
     *
     * <p>
     * Time complexity is O(k × n × d²), where {@code k} is the number of
     * iterations, {@code n} is the polynomial degree, and {@code d} is the
     * effective decimal precision. Space complexity is O(1) excluding the input
     * coefficient list.
     * </p>
     *
     * @param params immutable root-finding parameters containing polynomial
     *               coefficients, iteration settings, and numeric precision
     * @return approximate root rounded to the configured {@code scale}
     * @throws IllegalArgumentException      when the parameter object itself is
     *                                       null
     * @throws InvalidPolynomialException    when polynomial coefficients are
     *                                       invalid
     * @throws InvalidInitialGuessException  when the initial guess is null
     * @throws InvalidToleranceException     when epsilon is null or non-positive
     * @throws InvalidMaxIterationsException when maxIterations is non-positive
     * @throws InvalidScaleException         when scale is non-positive
     * @throws ZeroDerivativeException       when the derivative is zero before a
     *                                       required division step
     * @throws ConvergenceFailedException    when convergence is not reached within
     *                                       the configured iteration limit
     */
    public BigDecimal findRoot(final NewtonRaphsonParams params) {
        validateParams(params);

        final List<BigDecimal> coefficients = params.coefficients();
        final BigDecimal epsilon = params.epsilon();
        final int scale = params.scale();
        final int maxIterations = params.maxIterations();

        BigDecimal currentX = params.initialGuess();

        for (int iteration = 1; iteration <= maxIterations; iteration++) {
            final PolynomialEvaluator.EvaluationResult evaluation = polynomialEvaluator
                    .evaluatePolynomialAndDerivative(coefficients, currentX);
            final BigDecimal fx = evaluation.polynomial();
            final BigDecimal derivative = evaluation.derivative();

            if (convergenceChecker.hasConvergedByResidual(fx, epsilon)) {
                return currentX.setScale(scale, RoundingMode.HALF_UP);
            }

            if (derivative.compareTo(ZERO) == 0) {
                throw new ZeroDerivativeException(currentX, iteration,
                        "Derivative is zero at x = %s. Newton-Raphson cannot continue.".formatted(currentX));
            }

            final BigDecimal delta = fx.divide(derivative, scale, RoundingMode.HALF_UP);
            final BigDecimal nextX = currentX.subtract(delta);

            if (convergenceChecker.hasConvergedByDelta(currentX, nextX, epsilon)) {
                return nextX.setScale(scale, RoundingMode.HALF_UP);
            }

            final BigDecimal nextFx = polynomialEvaluator.evaluatePolynomial(coefficients, nextX);
            if (convergenceChecker.hasConvergedByResidual(nextFx, epsilon)) {
                return nextX.setScale(scale, RoundingMode.HALF_UP);
            }

            currentX = nextX;
        }

        throw new ConvergenceFailedException(maxIterations,
                "Newton-Raphson did not converge within %d iterations".formatted(maxIterations));
    }

    private void validateParams(final NewtonRaphsonParams params) {
        if (Objects.isNull(params)) {
            throw new IllegalArgumentException("Root finder parameters must not be null");
        }

        final Set<ConstraintViolation<NewtonRaphsonParams>> violations = validator.validate(params);
        if (!violations.isEmpty()) {
            throw violationMapper.toDomainException(violations);
        }
    }
}
