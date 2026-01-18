package at.mavila.exercises_january_2026.graphql;

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
 * </ul>
 *
 * <h2>Example Error Response</h2>
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
            return GraphqlErrorBuilder.newError(env)
                    .message("Invalid Roman numeral: %s", invalidRomanEx.getViolationReason())
                    .errorType(ErrorType.BAD_REQUEST)
                    .extensions(java.util.Map.of(
                            "invalidInput", invalidRomanEx.getInvalidInput(),
                            "violationReason", invalidRomanEx.getViolationReason(),
                            "errorCode", "INVALID_ROMAN_NUMERAL"))
                    .build();
        }

        // Let other exceptions be handled by the default resolver
        return null;
    }
}
