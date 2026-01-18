package at.mavila.exercises_january_2026.graphql;

import java.util.Map;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import at.mavila.exercises_january_2026.components.InvalidRomanNumeralException;
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
 * <ul>
 * <li>A human-readable error message</li>
 * <li>An appropriate error type classification (e.g., BAD_REQUEST)</li>
 * <li>Extension fields with detailed error information</li>
 * </ul>
 *
 * <h2>Handled Exceptions</h2>
 * <ul>
 * <li>{@link InvalidRomanNumeralException} - Returns BAD_REQUEST with
 * errorCode "INVALID_ROMAN_NUMERAL", invalidInput, and violationReason</li>
 * <li>{@link IllegalArgumentException} - Returns BAD_REQUEST with
 * errorCode "INVALID_ARGUMENT" for null/blank input or out-of-range
 * numbers</li>
 * </ul>
 *
 * <h2>Example Error Responses</h2>
 *
 * <h3>InvalidRomanNumeralException</h3>
 *
 * <pre>{@code
 * {
 *   "errors": [{
 *     "message": "Invalid Roman numeral: Character 'I' cannot repeat more than 3 times",
 *     "extensions": {
 *       "errorCode": "INVALID_ROMAN_NUMERAL",
 *       "invalidInput": "IIII",
 *       "violationReason": "Character 'I' cannot repeat more than 3 times consecutively",
 *       "classification": "BAD_REQUEST"
 *     }
 *   }]
 * }
 * }</pre>
 *
 * <h3>IllegalArgumentException</h3>
 *
 * <pre>{@code
 * {
 *   "errors": [{
 *     "message": "Invalid input: Number must be between 1 and 3999",
 *     "extensions": {
 *       "errorCode": "INVALID_ARGUMENT",
 *       "reason": "Number must be between 1 and 3999",
 *       "classification": "BAD_REQUEST"
 *     }
 *   }]
 * }
 * }</pre>
 *
 * @author exercises_january_2026
 * @see InvalidRomanNumeralException
 * @see DataFetcherExceptionResolverAdapter
 */
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    /**
     * Resolves an exception to a single GraphQL error.
     *
     * <p>
     * This method is called when an exception occurs during data fetching.
     * It examines the exception type and creates an appropriate GraphQL error
     * response.
     *
     * @param ex  the exception that was thrown
     * @param env the data fetching environment containing query context
     * @return a {@link GraphQLError} for handled exceptions, or {@code null}
     *         to delegate to the default resolver for unhandled exceptions
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
