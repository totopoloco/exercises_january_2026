package at.mavila.exercises_january_2026.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

/**
 * GraphQL configuration class.
 * Registers custom scalars and other GraphQL-related beans.
 */
@Configuration
public class GraphQLConfig {

  /**
   * Configures custom scalars for GraphQL.
   * Adds support for Long scalar type.
   */
  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder -> wiringBuilder
        .scalar(ExtendedScalars.GraphQLLong);
  }

}
