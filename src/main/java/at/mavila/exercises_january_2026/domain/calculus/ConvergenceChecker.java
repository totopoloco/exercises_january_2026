package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/**
 * Determines Newton-Raphson convergence conditions.
 *
 * <p>
 * This component centralizes convergence predicates so the root finder can
 * focus on high-level orchestration.
 * </p>
 *
 * @since 2026-03-29
 */
@Component
public class ConvergenceChecker {

    /**
     * Checks convergence using residual magnitude.
     *
     * @param value   current polynomial value
     * @param epsilon positive convergence tolerance
     * @return {@code true} when {@code |value| < epsilon}
     */
    public boolean hasConvergedByResidual(final BigDecimal value, final BigDecimal epsilon) {
        return value.abs().compareTo(epsilon) < 0;
    }

    /**
     * Checks convergence using the iteration step size.
     *
     * @param currentX current Newton-Raphson value
     * @param nextX    next Newton-Raphson value
     * @param epsilon  positive convergence tolerance
     * @return {@code true} when {@code |nextX - currentX| < epsilon}
     */
    public boolean hasConvergedByDelta(final BigDecimal currentX, final BigDecimal nextX, final BigDecimal epsilon) {
        return nextX.subtract(currentX).abs().compareTo(epsilon) < 0;
    }
}
