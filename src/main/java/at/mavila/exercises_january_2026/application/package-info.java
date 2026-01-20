/**
 * Application Layer - Use Case Orchestration.
 * 
 * <p>
 * This layer coordinates domain services and implements application-specific
 * business logic. It serves as the entry point for external adapters (GraphQL,
 * REST, CLI, etc.) to access domain functionality.
 * </p>
 * 
 * <h2>Responsibilities</h2>
 * <ul>
 * <li>Orchestrate domain service calls for complex use cases</li>
 * <li>Handle transaction management</li>
 * <li>Convert between external DTOs and domain models</li>
 * <li>Enforce application-level authorization</li>
 * </ul>
 * 
 * <h2>Components</h2>
 * <ul>
 * <li>{@link at.mavila.exercises_january_2026.application.AlgorithmService} -
 * Main application service that provides access to all algorithms</li>
 * </ul>
 * 
 * <h2>Design Principles</h2>
 * <ul>
 * <li>Services in this layer are stateless</li>
 * <li>They depend on domain layer but not infrastructure</li>
 * <li>They define the application's use cases</li>
 * </ul>
 * 
 * @since January 2026
 */
package at.mavila.exercises_january_2026.application;
