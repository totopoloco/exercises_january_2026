/**
 * Infrastructure Layer - External Concerns and Adapters.
 * 
 * <p>
 * This is the outermost layer of the DDD architecture containing all
 * infrastructure concerns such as web controllers, persistence, messaging,
 * and external service integrations.
 * </p>
 * 
 * <h2>Subpackages</h2>
 * <ul>
 * <li>{@code config} - Configuration classes (GraphQL, security, etc.)</li>
 * <li>{@code web} - Web adapters (controllers, interceptors)</li>
 * <li>{@code web.graphql} - GraphQL-specific components</li>
 * </ul>
 * 
 * <h2>Design Principles</h2>
 * <ul>
 * <li>Infrastructure depends on application and domain layers</li>
 * <li>Contains all framework-specific code</li>
 * <li>Implements ports defined in inner layers</li>
 * <li>Handles external communication (HTTP, GraphQL, databases, etc.)</li>
 * </ul>
 * 
 * @since January 2026
 */
package at.mavila.exercises_january_2026.infrastructure;
