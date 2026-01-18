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
 */
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

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
