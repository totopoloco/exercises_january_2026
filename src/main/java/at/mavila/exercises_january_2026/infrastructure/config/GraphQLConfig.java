package at.mavila.exercises_january_2026.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

/**
 * Infrastructure configuration for GraphQL.
 *
 * <p>
 * This configuration class registers custom scalars and other GraphQL-related
 * beans for the GraphQL infrastructure.
 * </p>
 *
 * <h2>Custom Scalars</h2>
 * <ul>
 * <li>Long - Support for 64-bit integers</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Configuration
public class GraphQLConfig {

  /**
   * Configures custom scalars for GraphQL.
   * Adds support for Long scalar type.
   *
   * @return the runtime wiring configurer with custom scalars
   */
  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder -> wiringBuilder
        .scalar(ExtendedScalars.GraphQLLong);
  }

}
