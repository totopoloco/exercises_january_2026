package at.mavila.exercises_january_2026.infrastructure.web.graphql;

import java.util.Map;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import at.mavila.exercises_january_2026.domain.calculus.exception.ConvergenceFailedException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidInitialGuessException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidMaxIterationsException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidPolynomialException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidScaleException;
import at.mavila.exercises_january_2026.domain.calculus.exception.InvalidToleranceException;
import at.mavila.exercises_january_2026.domain.calculus.exception.ZeroDerivativeException;
import at.mavila.exercises_january_2026.domain.number.roman.InvalidRomanNumeralException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

/**
 * GraphQL exception handler that converts application exceptions into
 * properly formatted GraphQL errors with meaningful messages.
 *
 * <p>
 * This handler intercepts exceptions thrown during GraphQL query execution
 * and transforms them into structured {@link GraphQLError} responses that
 * include:
 * </p>
 * <ul>
 * <li>A human-readable error message</li>
 * <li>An appropriate error type classification (e.g., BAD_REQUEST)</li>
 * <li>Extension fields with detailed error information</li>
 * </ul>
 *
 * <h2>Handled Exceptions</h2>
 * <ul>
 * <li>{@link InvalidRomanNumeralException} - Returns BAD_REQUEST with
 * errorCode "INVALID_ROMAN_NUMERAL"</li>
 * <li>{@link IllegalArgumentException} - Returns BAD_REQUEST with
 * errorCode "INVALID_ARGUMENT"</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 * @see InvalidRomanNumeralException
 * @see DataFetcherExceptionResolverAdapter
 */
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

  /**
   * Resolves an exception to a single GraphQL error.
   *
   * @param ex  the exception that was thrown
   * @param env the data fetching environment containing query context
   * @return a {@link GraphQLError} for handled exceptions, or {@code null}
   *         to delegate to the default resolver
   */
  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    if (ex instanceof InvalidRomanNumeralException invalidRomanEx) {
      return buildBadRequestError(env, "Invalid Roman numeral: %s".formatted(invalidRomanEx.getViolationReason()),
          Map.of(
              "errorCode", "INVALID_ROMAN_NUMERAL",
              "invalidInput", invalidRomanEx.getInvalidInput(),
              "violationReason", invalidRomanEx.getViolationReason()));
    }

    if (ex instanceof IllegalArgumentException illegalArgEx) {
      return buildBadRequestError(env, "Invalid input: %s".formatted(illegalArgEx.getMessage()),
          Map.of(
              "errorCode", "INVALID_ARGUMENT",
              "reason", illegalArgEx.getMessage()));
    }

    if (ex instanceof InvalidPolynomialException invalidPolynomialException) {
      return buildBadRequestError(env, invalidPolynomialException.getMessage(),
          Map.of(
              "errorCode", "INVALID_POLYNOMIAL",
              "reason", invalidPolynomialException.getReason()));
    }

    if (ex instanceof InvalidInitialGuessException invalidInitialGuessException) {
      return buildBadRequestError(env, invalidInitialGuessException.getMessage(),
          Map.of(
              "errorCode", "INVALID_INITIAL_GUESS"));
    }

    if (ex instanceof InvalidToleranceException invalidToleranceException) {
      return buildBadRequestError(env, invalidToleranceException.getMessage(),
          Map.of(
              "errorCode", "INVALID_TOLERANCE",
              "epsilon", String.valueOf(invalidToleranceException.getEpsilon())));
    }

    if (ex instanceof InvalidMaxIterationsException invalidMaxIterationsException) {
      return buildBadRequestError(env, invalidMaxIterationsException.getMessage(),
          Map.of(
              "errorCode", "INVALID_MAX_ITERATIONS",
              "maxIterations", invalidMaxIterationsException.getMaxIterations()));
    }

    if (ex instanceof InvalidScaleException invalidScaleException) {
      return buildBadRequestError(env, invalidScaleException.getMessage(),
          Map.of(
              "errorCode", "INVALID_SCALE",
              "scale", invalidScaleException.getScale()));
    }

    if (ex instanceof ZeroDerivativeException zeroDerivativeException) {
      return buildBadRequestError(env, zeroDerivativeException.getMessage(),
          Map.of(
              "errorCode", "ZERO_DERIVATIVE",
              "x", zeroDerivativeException.getX().toPlainString(),
              "iteration", zeroDerivativeException.getIteration()));
    }

    if (ex instanceof ConvergenceFailedException convergenceFailedException) {
      return buildBadRequestError(env, convergenceFailedException.getMessage(),
          Map.of(
              "errorCode", "CONVERGENCE_FAILED",
              "maxIterations", convergenceFailedException.getMaxIterations()));
    }

    // Let other exceptions be handled by the default resolver
    return null;
  }

  /**
   * Builds a GraphQL error with BAD_REQUEST type and the given extensions.
   *
   * @param env        the data fetching environment
   * @param message    the error message
   * @param extensions the extension fields to include in the error
   * @return the constructed GraphQL error
   */
  private GraphQLError buildBadRequestError(final DataFetchingEnvironment env, final String message,
      final Map<String, Object> extensions) {
    return GraphqlErrorBuilder.newError(env)
        .message(message)
        .errorType(ErrorType.BAD_REQUEST)
        .extensions(extensions)
        .build();
  }
}
