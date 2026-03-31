package at.mavila.exercises_january_2026.domain.calculus;

import java.math.BigDecimal;

/**
 * A single Newton-Raphson iteration recording the step number and the x value computed.
 *
 * @param number
 *                   the 1-based iteration number
 * @param value
 *                   the x value computed at this iteration, rounded to the requested scale
 * @author mavila
 * @since 2026-03-31
 */
public record NewtonRaphsonIteration(int number, BigDecimal value) {
}
