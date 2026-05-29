# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
./gradlew build            # compile + test
./gradlew build -x test    # compile only
./gradlew test             # run all tests
./gradlew bootRun          # start application (port 8080)
./gradlew pitest           # mutation testing (report: build/reports/pitest/index.html)
```

Run a single test class:

```bash
./gradlew test --tests "at.mavila.exercises_january_2026.domain.array.MedianCalculatorTest"
```

## Architecture

Three-layer DDD. Base package: `at.mavila.exercises_january_2026`.

```
domain/          # Core algorithms — @Component beans, no outer-layer deps
  <category>/    # array, calculus, collection, container, number, phonetic, security, string
application/     # AlgorithmService — delegates to domain, applies defaults, no business logic
infrastructure/  # GraphQL controller + exception handler — no business logic
```

**The only API is GraphQL** (`/graphql`). There are no REST controllers. The schema lives at `src/main/resources/graphql/schema.graphqls`. The GraphiQL IDE is enabled in dev at `/graphiql`.

When adding a new algorithm the full touch list is:

1. `src/specs/<category>/<Name>.md` — spec
2. `domain/<category>/<Name>.java` — `@Component` domain service
3. `application/AlgorithmService.java` — delegate method
4. `resources/graphql/schema.graphqls` — new query
5. `infrastructure/web/graphql/AlgorithmController.java` — `@QueryMapping`
6. `infrastructure/web/graphql/GraphQLExceptionHandler.java` — if new domain exception
7. Tests mirroring the main package structure (`@SpringBootTest`, AssertJ for domain; `ExecutionGraphQlServiceTester` for GraphQL integration tests)
8. Verify `./gradlew pitest` still passes (≥ 79% mutation and line coverage)
9. `README.md` — exercise entry

## Coding conventions

**Null handling** — always `Objects.isNull()` / `Objects.nonNull()`, never `== null` or `!= null`.

**Input validation** — domain services must use **Jakarta Bean Validation** on parameter `records`. No manual null-guard `if` statements in domain service bodies. Use custom `@Constraint` annotations when standard ones don't suffice (see `domain/calculus/validation/`). The application layer (`AlgorithmService`) is the only place where null defaulting via `Objects.isNull()` is permitted.

**Iteration** — prefer Stream API. Use enhanced for-each when index-dependent or carrying mutable state. Never use C-style indexed `for` loops when a stream or for-each suffices.

**Immutability** — all method parameters `final`, local variables `final` when not reassigned, `List.of()`/`Map.of()` for literals, `private static final` for constants.

**Method design**:

- Public methods ≤ 3 parameters; group extras into a `record` parameter object.
- Cyclomatic complexity ≤ 10 per method; extract private helpers.
- Guard-clause pattern: check the **positive** (happy-path) condition and return early, then handle the exceptional path below — not the other way around.
- Domain services must delegate sub-concerns (evaluation, convergence, validation) to injected `@Component` collaborators. No monolithic methods.

**String formatting** — `String.format(...)` or `"...".formatted(...)` for messages with dynamic values. No string concatenation (`+`) for error messages.

**Javadoc** — required on every public class and public method across all layers, including `@param`, `@return`, `@throws`, algorithm description, and time/space complexity where applicable. `package-info.java` required for every package.

**Lombok** — `@RequiredArgsConstructor` for constructor injection; `@Getter`/`@Setter`/`@Builder` on models.

## Testing conventions

- Domain tests: `@SpringBootTest` (full context, no mocks), AssertJ assertions, exhaustive coverage of happy paths, edge cases, and constraint violations.
- Application tests: `@Nested` inner classes inside `AlgorithmServiceTest` using JUnit 5 assertions.
- GraphQL integration tests: `ExecutionGraphQlServiceTester` with raw GraphQL query documents, covering happy-path and error responses.
- Mutation testing: every new domain class must maintain the project-wide ≥ 79% mutation and line coverage threshold (`./gradlew pitest`). Tests must kill mutations — avoid trivial assertions that survive mutants.

## Key configuration

- Active profile: `dev` (H2 in-memory DB, GraphiQL enabled, `logging.pattern.console=%msg%n` for clean algorithm output)
- Java 25 toolchain, Spring Boot 4.x
- Pitest mutation threshold: 79% (both mutation and line coverage)
- Pitest targets only `domain.*` and `application.*` packages
