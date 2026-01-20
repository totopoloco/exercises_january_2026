/**
 * Domain Layer - Core Business Logic.
 *
 * <p>
 * This is the innermost layer of the DDD architecture containing all
 * business logic and domain models. This layer has no dependencies on
 * outer layers (application, infrastructure).
 * </p>
 *
 * <h2>Subpackages</h2>
 * <ul>
 * <li>{@code array} - Array manipulation operations (median calculation)</li>
 * <li>{@code container} - Container optimization (water calculation)</li>
 * <li>{@code string} - String processing (palindrome, substring, masking)</li>
 * <li>{@code number} - Number operations (percentage, odd finder, roman
 * numerals)</li>
 * <li>{@code security} - Security operations (PIN cracking)</li>
 * <li>{@code collection} - Collection operations (linked lists, matrices,
 * baskets)</li>
 * <li>{@code phonetic} - Phone-related operations (letter combinations)</li>
 * </ul>
 *
 * <h2>Design Principles</h2>
 * <ul>
 * <li>Each subdomain is self-contained with its own services and
 * exceptions</li>
 * <li>Domain services are stateless and focus on single responsibilities</li>
 * <li>Business rules are enforced at this layer</li>
 * <li>No framework dependencies except Spring annotations for DI</li>
 * </ul>
 *
 * @since January 2026
 */
package at.mavila.exercises_january_2026.domain;
