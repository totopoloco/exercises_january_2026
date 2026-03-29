package at.mavila.exercises_january_2026.domain.calculus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import at.mavila.exercises_january_2026.domain.calculus.exception.ConvergenceFailedException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidInitialGuessException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidMaxIterationsException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidPolynomialException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidScaleException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidToleranceException;
import at.mavila.exercises_january_2026.domain.calculus.exception.ZeroDerivativeException;

@SpringBootTest
class NewtonRaphsonRootFinderTest {

    private static final BigDecimal DEFAULT_EPSILON = new BigDecimal("1e-10");
    private static final int DEFAULT_MAX_ITERATIONS = 1000;
    private static final int DEFAULT_SCALE = 10;

    @Autowired
    private NewtonRaphsonRootFinder newtonRaphsonRootFinder;

    @Test
    void testFindRoot_LinearPolynomial_ConvergesInOneIteration() {
        // f(x) = -6 + 3x has the exact root x = 2.
        BigDecimal result = newtonRaphsonRootFinder
                .findRoot(createParams(List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), new BigDecimal("0.0")));

        assertThat(result).isEqualByComparingTo(new BigDecimal("2.0000000000"));
    }

    @Test
    void testFindRoot_QuadraticConvergesToPositiveRoot() {
        // Starting near 3 should converge to the positive root x = 2.
        BigDecimal result = newtonRaphsonRootFinder.findRoot(createParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("1.0"), new BigDecimal("1.0")), new BigDecimal("3.0")));

        assertThat(result).isEqualByComparingTo(new BigDecimal("2.0000000000"));
    }

    @Test
    void testFindRoot_QuadraticConvergesToNegativeRoot() {
        // Starting near -4 should converge to the negative root x = -3.
        BigDecimal result = newtonRaphsonRootFinder.findRoot(createParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("1.0"), new BigDecimal("1.0")), new BigDecimal("-4.0")));

        assertThat(result).isEqualByComparingTo(new BigDecimal("-3.0000000000"));
    }

    @Test
    void testFindRoot_HighPrecisionSqrt2() {
        // A larger scale should preserve twenty decimal places for sqrt(2).
        BigDecimal result = newtonRaphsonRootFinder.findRoot(
                new NewtonRaphsonParams(List.of(new BigDecimal("-2.0"), new BigDecimal("0.0"), new BigDecimal("1.0")),
                        new BigDecimal("1.0"), new BigDecimal("1e-20"), 2000, 20));

        assertThat(result).isEqualByComparingTo(new BigDecimal("1.41421356237309504880"));
    }

    @Test
    void testFindRoot_ExactRootWithZeroDerivative_ReturnsRoot() {
        // The exact root x = 0 should be returned before any derivative-based division.
        BigDecimal result = newtonRaphsonRootFinder.findRoot(createParams(
                List.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE), BigDecimal.ZERO));

        assertThat(result).isEqualByComparingTo(new BigDecimal("0.0000000000"));
    }

    @Test
    void testFindRoot_NullCoefficients_ThrowsException() {
        // A null coefficient list is never a valid polynomial input.
        final NewtonRaphsonParams params = createParams(null, BigDecimal.ONE);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(InvalidPolynomialException.class).hasMessage("Coefficients must not be null or empty");
    }

    @Test
    void testFindRoot_ConstantPolynomial_ThrowsException() {
        // A constant polynomial has no root to iterate toward.
        final NewtonRaphsonParams params = createParams(List.of(new BigDecimal("5.0")), BigDecimal.ONE);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(InvalidPolynomialException.class)
                .hasMessage("Polynomial must be at least linear (2 or more coefficients)");
    }

    @Test
    void testFindRoot_LeadingCoefficientZero_ThrowsException() {
        // The highest-order coefficient must be non-zero.
        final NewtonRaphsonParams params = createParams(List.of(new BigDecimal("1.0"), BigDecimal.ZERO),
                BigDecimal.ONE);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(InvalidPolynomialException.class).hasMessage("Leading coefficient must not be zero");
    }

    @Test
    void testFindRoot_NullCoefficientElement_ThrowsException() {
        // Every coefficient position must contain a concrete BigDecimal value.
        final NewtonRaphsonParams params = createParams(java.util.Arrays.asList(BigDecimal.ONE, null), BigDecimal.ONE);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(InvalidPolynomialException.class).hasMessage("All coefficients must be non-null");
    }

    @Test
    void testFindRoot_NullInitialGuess_ThrowsException() {
        // Newton-Raphson requires an explicit starting value.
        final NewtonRaphsonParams params = createParams(List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), null);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(InvalidInitialGuessException.class).hasMessage("Initial guess must not be null");
    }

    @Test
    void testFindRoot_NonPositiveEpsilon_ThrowsException() {
        // Epsilon must stay strictly positive to define convergence.
        final NewtonRaphsonParams params = new NewtonRaphsonParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), BigDecimal.ZERO, BigDecimal.ZERO, 1000, 10);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params)).isInstanceOf(InvalidToleranceException.class)
                .hasMessage("Epsilon must be a positive number, got: 0");
    }

    @Test
    void testFindRoot_NullEpsilon_ThrowsException() {
        // Epsilon must always be present after application-level defaulting.
        final NewtonRaphsonParams params = new NewtonRaphsonParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), BigDecimal.ZERO, null, 1000, 10);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params)).isInstanceOf(InvalidToleranceException.class)
                .hasMessage("Epsilon must be a positive number, got: null");
    }

    @Test
    void testFindRoot_NonPositiveMaxIterations_ThrowsException() {
        // Iteration limit must allow at least one Newton step.
        final NewtonRaphsonParams params = new NewtonRaphsonParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), BigDecimal.ZERO, DEFAULT_EPSILON, 0, 10);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(InvalidMaxIterationsException.class)
                .hasMessage("Max iterations must be a positive integer, got: 0");
    }

    @Test
    void testFindRoot_NullMaxIterations_ThrowsException() {
        // Max iterations must always be present after application-level defaulting.
        final NewtonRaphsonParams params = new NewtonRaphsonParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), BigDecimal.ZERO, DEFAULT_EPSILON, null, 10);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(InvalidMaxIterationsException.class)
                .hasMessage("Max iterations must be a positive integer, got: null");
    }

    @Test
    void testFindRoot_NonPositiveScale_ThrowsException() {
        // Scale must stay positive for deterministic BigDecimal division.
        final NewtonRaphsonParams params = new NewtonRaphsonParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), BigDecimal.ZERO, DEFAULT_EPSILON, 1000, 0);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params)).isInstanceOf(InvalidScaleException.class)
                .hasMessage("Scale must be a positive integer, got: 0");
    }

    @Test
    void testFindRoot_NullScale_ThrowsException() {
        // Scale must always be present after application-level defaulting.
        final NewtonRaphsonParams params = new NewtonRaphsonParams(
                List.of(new BigDecimal("-6.0"), new BigDecimal("3.0")), BigDecimal.ZERO, DEFAULT_EPSILON, 1000, null);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params)).isInstanceOf(InvalidScaleException.class)
                .hasMessage("Scale must be a positive integer, got: null");
    }

    @Test
    void testFindRoot_ZeroDerivativeAtInitialGuess_ThrowsException() {
        // f(x) = 1 + x² has derivative 0 at x = 0 while not being a root.
        final NewtonRaphsonParams params = createParams(List.of(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE),
                new BigDecimal("0.0"));

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params)).isInstanceOf(ZeroDerivativeException.class)
                .hasMessage("Derivative is zero at x = 0.0. Newton-Raphson cannot continue.");
    }

    @Test
    void testFindRoot_NonConvergence_ThrowsException() {
        // A very small tolerance with too few iterations should exhaust the limit.
        final NewtonRaphsonParams params = new NewtonRaphsonParams(
                List.of(new BigDecimal("2.0"), new BigDecimal("-2.0"), BigDecimal.ZERO, BigDecimal.ONE),
                BigDecimal.ZERO, new BigDecimal("1e-20"), 4, 20);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(ConvergenceFailedException.class)
                .hasMessage("Newton-Raphson did not converge within 4 iterations");
    }

    @Test
    void testFindRoot_CubicCyclesFromZero_ThrowsConvergenceFailedException() {
        // f(x) = x^3 - 2x + 2 with x0 = 0 alternates between 0 and 1 and does not
        // satisfy convergence.
        final List<BigDecimal> coefficients = List.of(new BigDecimal("2.0"), new BigDecimal("-2.0"), BigDecimal.ZERO,
                BigDecimal.ONE);

        final BigDecimal initialGuess = BigDecimal.ZERO;

        final NewtonRaphsonParams params = createParams(coefficients, initialGuess);

        assertThatThrownBy(() -> newtonRaphsonRootFinder.findRoot(params))
                .isInstanceOf(ConvergenceFailedException.class)
                .hasMessage("Newton-Raphson did not converge within 1000 iterations");
    }

    private NewtonRaphsonParams createParams(final List<BigDecimal> coefficients, final BigDecimal initialGuess) {
        return new NewtonRaphsonParams(coefficients, initialGuess, DEFAULT_EPSILON, DEFAULT_MAX_ITERATIONS,
                DEFAULT_SCALE);
    }
}
