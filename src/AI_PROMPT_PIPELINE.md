# AI Spec → Code Pipeline

> Spec-Driven Development guide for the **exercises_january_2026** project.
> When given a spec file, the AI agent follows these steps to generate production-ready code
> that conforms to the project's DDD architecture and conventions.

---

## Project Context

| Aspect               | Value                                                        |
| -------------------- | ------------------------------------------------------------ |
| **Language**         | Java 25 (toolchain), compiled to Java 21 bytecode            |
| **Framework**        | Spring Boot 4.0.3                                            |
| **API style**        | GraphQL (Spring for GraphQL) — no REST controllers           |
| **Build tool**       | Gradle (Groovy DSL)                                          |
| **Testing**          | JUnit 5 + AssertJ + `ExecutionGraphQlServiceTester`          |
| **Libraries**        | Lombok, Apache Commons Lang 3, graphql-java-extended-scalars |
| **Mutation testing** | Pitest                                                       |
| **Base package**     | `at.mavila.exercises_january_2026`                           |

---

## Step 1 — Write the Spec

Create a Markdown spec file under `src/specs/` describing the exercise or algorithm.

A spec **must** include:

| Section                | Purpose                                                          |
| ---------------------- | ---------------------------------------------------------------- |
| **Problem statement**  | What the algorithm/exercise solves                               |
| **Input / Output**     | Parameter types, return type, constraints (min/max, nullability) |
| **Rules & edge cases** | Boundary conditions, error handling, special cases               |
| **Examples**           | At least 3 input → output examples with explanations             |
| **Complexity targets** | Expected time and space complexity (if applicable)               |

### Spec naming convention

```
src/specs/<category>/<PascalCaseName>.md
```

`<category>` must match one of the existing domain subdomains (`array`, `collection`, `container`, `number`, `phonetic`, `security`, `string`) or define a new one.

---

## Step 2 — Determine the Domain Subdomain

Map the spec to a **domain subdomain package**:

```
at.mavila.exercises_january_2026.domain.<category>
```

If the exercise introduces a new category, create a new subpackage with its own `package-info.java`.

---

## Step 3 — Generate Domain Layer

### 3.1 Domain Service (`@Component`)

Create a stateless `@Component` class in `domain/<category>/`:

```
src/main/java/at/mavila/exercises_january_2026/domain/<category>/<ClassName>.java
```

**Conventions:**

- Annotate with `@Component` and `@RequiredArgsConstructor` (Lombok).
- Class name: descriptive `NounVerb` or `ActionNoun` pattern (e.g., `MedianCalculator`, `PinCracker`, `PalindromeExtractor`).
- Comprehensive **Javadoc** on class and all public methods including `@param`, `@return`, `@throws`, algorithm description, and time/space complexity.
- Add `@since` tag with the current date.
- Validate inputs — throw `IllegalArgumentException` or a custom domain exception with a descriptive message.
- Define constraint boundaries as `private static final` fields (e.g., `MAX_LENGTH`, `MIN_VALUE`).
- Prefer Java Streams and functional style where appropriate.
- **No dependencies on application or infrastructure layers.**

### 3.2 Domain Model (if needed)

If the algorithm requires a dedicated data structure, create a plain model class (e.g., `ListNode`):

- Minimal, no Spring annotations.
- Use Lombok for boilerplate (`@Getter`, `@Setter`, `@Builder`, etc.) when it improves clarity.

### 3.3 Domain Exception (if needed)

If a domain-specific error condition exists beyond `IllegalArgumentException`:

- Create a custom exception in the same subdomain package.
- Naming: `Descriptive` + `Exception` (e.g., `InvalidRomanNumeralException`).

### 3.4 `package-info.java`

If this is a **new subdomain**, create `package-info.java` with Javadoc describing:

- The subdomain's purpose
- Key components
- Design principles

---

## Step 4 — Wire the Application Layer

Update `AlgorithmService` (`application/AlgorithmService.java`):

1. Add the new domain service as a `private final` field (Lombok auto-generates the constructor).
2. Add a **delegate method** that calls the domain service.
3. Handle parameter defaulting here (e.g., null checks, default values), not in the domain.
4. Add Javadoc on the new method.

---

## Step 5 — Expose via Infrastructure Layer

### 5.1 GraphQL Schema

Add a new query (or extend parameters of an existing one) in:

```
src/main/resources/graphql/schema.graphqls
```

- Include GraphQL doc comments (`"Description"`) for the query and its arguments.
- Use appropriate GraphQL scalars (`Int`, `Float`, `String`, `Boolean`, `[Int]`, `Long`, etc.).

### 5.2 GraphQL Controller

Add a `@QueryMapping` method in `AlgorithmController` (`infrastructure/web/graphql/`):

- Delegate to `AlgorithmService`.
- Convert between GraphQL input types and domain types if needed.
- Do **not** contain business logic.

### 5.3 Exception Handler (if needed)

If a new domain exception was created, add a handler in `GraphQLExceptionHandler`:

- Map the domain exception to a GraphQL error with an appropriate classification.

---

## Step 6 — Generate Tests

Tests mirror the main source package structure exactly.

### 6.1 Domain Test

```
src/test/java/at/mavila/exercises_january_2026/domain/<category>/<ClassName>Test.java
```

- `@SpringBootTest` — full application context, no mocks.
- `@Autowired` the domain component under test.
- Use **AssertJ** assertions (`assertThat(...).isEqualTo(...)`, `assertThatThrownBy(...)`, etc.).
- Cover **exhaustively**: happy paths, edge cases, boundary values, constraint violations, null/empty inputs.
- Each test method gets a `@Test` annotation and a descriptive method name (`test<Scenario>_<Condition>`).
- Include inline comments explaining inputs and expected outputs.

### 6.2 Application Test

Add a `@Nested` inner class inside `AlgorithmServiceTest`:

- Use `@DisplayName` for readability.
- Smoke-test the delegate method through `AlgorithmService`.
- Use **JUnit 5 assertions** (`assertEquals`, `assertTrue`, `assertThrows`).

### 6.3 GraphQL Integration Test

Add test cases in `AlgorithmControllerTest`:

- Use `ExecutionGraphQlServiceTester` (already configured via `@AutoConfigureGraphQlTester`).
- Send raw GraphQL query documents and assert on response paths.
- Cover happy-path and error responses.

---

## Step 7 — Validate

Run the full pipeline to verify correctness:

1. **Compile**: `./gradlew build -x test` — must pass with zero errors.
2. **Tests**: `./gradlew test` — all tests green.
3. **Mutation testing** (optional): `./gradlew pitest` — verify mutation coverage.
4. **Review**: Ensure Javadoc, naming, and package placement match project conventions.

---

## Checklist

Use this checklist for every spec implementation:

- [ ] Spec file written under `src/specs/<category>/`
- [ ] Domain service created (`@Component`, Javadoc, input validation)
- [ ] Domain model created (if applicable)
- [ ] Domain exception created (if applicable)
- [ ] `package-info.java` created (if new subdomain)
- [ ] `AlgorithmService` updated with delegate method
- [ ] GraphQL schema updated
- [ ] `AlgorithmController` updated with `@QueryMapping`
- [ ] `GraphQLExceptionHandler` updated (if new exception)
- [ ] Domain test created (AssertJ, exhaustive)
- [ ] Application test added (`@Nested` in `AlgorithmServiceTest`)
- [ ] GraphQL integration test added
- [ ] `./gradlew build` passes
- [ ] `./gradlew test` passes

---

## Example: Adding a "FibonacciCalculator" exercise

```
Spec:           src/specs/number/FibonacciCalculator.md
Domain:         domain/number/FibonacciCalculator.java          (@Component)
App layer:      application/AlgorithmService.java               (add delegate method)
Schema:         resources/graphql/schema.graphqls                (add query)
Controller:     infrastructure/web/graphql/AlgorithmController.java (add @QueryMapping)
Domain test:    test/domain/number/FibonacciCalculatorTest.java  (@SpringBootTest, AssertJ)
App test:       test/application/AlgorithmServiceTest.java       (add @Nested class)
GraphQL test:   test/infrastructure/web/graphql/AlgorithmControllerTest.java (add query test)
```
