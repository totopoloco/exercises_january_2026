# Newton-Raphson Root Finder for Polynomials

## Problem Statement

Implement the **Newton-Raphson method** to find a root of a polynomial $f(x)$ of order $n$.

Given a polynomial defined by its coefficients and an initial guess $x_0$, iteratively refine the approximation using:

$$x_{k+1} = x_k - \frac{f(x_k)}{f'(x_k)}$$

until the value converges within a specified tolerance.

A polynomial of order $n$ is represented by its coefficients $[a_0, a_1, a_2, \ldots, a_n]$ such that:

$$f(x) = a_0 + a_1 x + a_2 x^2 + \cdots + a_n x^n$$

Its derivative is:

$$f'(x) = a_1 + 2a_2 x + 3a_3 x^2 + \cdots + n \cdot a_n x^{n-1}$$

---

## Input / Output

### Input

| Parameter       | Type               | Constraints                                                                                                                                                     | Default  |
| --------------- | ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| `coefficients`  | `List<BigDecimal>` | Must not be null or empty. No null elements. Represents $[a_0, a_1, \ldots, a_n]$. At least 2 elements (minimum linear polynomial). Last element must not be 0. | required |
| `initialGuess`  | `BigDecimal`       | Must not be null.                                                                                                                                               | required |
| `epsilon`       | `BigDecimal`       | Must be > 0 when provided. Nullable.                                                                                                                            | `1e-10`  |
| `maxIterations` | `Integer`          | Must be > 0 when provided. Nullable.                                                                                                                            | `1000`   |
| `scale`         | `Integer`          | Must be > 0 when provided. Nullable. Controls decimal places for internal division rounding (`RoundingMode.HALF_UP`).                                           | `10`     |

### Output

| Type         | Description                                                                                                                                                         |
| ------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `BigDecimal` | An approximate root $r$ of $f(x)$ such that $\lvert f(r) \rvert < \epsilon$ or $\lvert x_{k+1} - x_k \rvert < \epsilon$. The result is rounded to `scale` decimals. |

---

## Domain Exceptions

All exceptions are `RuntimeException` subclasses in the `domain.calculus.exception` package. Each carries contextual fields accessible via getters.

| Exception                       | Extends            | Contextual Fields                       | Scenario                             |
| ------------------------------- | ------------------ | --------------------------------------- | ------------------------------------ |
| `InvalidPolynomialException`    | `RuntimeException` | `reason` (`String`)                     | Rules 1–4: invalid coefficient list  |
| `InvalidInitialGuessException`  | `RuntimeException` | —                                       | Rule 5: null starting value          |
| `InvalidToleranceException`     | `RuntimeException` | `epsilon` (`BigDecimal`)                | Rule 6: non-positive epsilon         |
| `InvalidMaxIterationsException` | `RuntimeException` | `maxIterations` (`int`)                 | Rule 7: non-positive iteration limit |
| `InvalidScaleException`         | `RuntimeException` | `scale` (`int`)                         | Rule 8: non-positive scale           |
| `ZeroDerivativeException`       | `RuntimeException` | `x` (`BigDecimal`), `iteration` (`int`) | Rule 9: derivative exactly zero      |
| `ConvergenceFailedException`    | `RuntimeException` | `maxIterations` (`int`)                 | Rule 11: iteration limit exhausted   |

---

## Rules & Edge Cases

1. **Null or empty coefficients**: Throw `InvalidPolynomialException` with message `"Coefficients must not be null or empty"`.
2. **Single coefficient**: A constant polynomial (e.g., `[5]`) has no root. Throw `InvalidPolynomialException` with message `"Polynomial must be at least linear (2 or more coefficients)"`.
3. **Leading coefficient is zero**: The last element in the list (highest-order term) must not be 0. Throw `InvalidPolynomialException` with message `"Leading coefficient must not be zero"`.
4. **Null element in coefficients**: If any element in the list is `null`, throw `InvalidPolynomialException` with message `"All coefficients must be non-null"`.
5. **Null initial guess**: If `initialGuess` is `null`, throw `InvalidInitialGuessException` with message `"Initial guess must not be null"`.
6. **Invalid epsilon**: If provided and is ≤ 0, throw `InvalidToleranceException` with message `"Epsilon must be a positive number, got: <epsilon>"`.
7. **Invalid maxIterations**: If provided and is ≤ 0, throw `InvalidMaxIterationsException` with message `"Max iterations must be a positive integer, got: <maxIterations>"`.
8. **Invalid scale**: If provided and is ≤ 0, throw `InvalidScaleException` with message `"Scale must be a positive integer, got: <scale>"`.
9. **Derivative is zero — guard before every division**: The only division in the algorithm is $f(x_k) / f'(x_k)$. **Before every such division**, the implementation must check that $f'(x_k) \neq 0$ (using `BigDecimal.compareTo(BigDecimal.ZERO) == 0`). If $f'(x_k) = 0$, throw `ZeroDerivativeException` with message `"Derivative is zero at x = <x_k>. Newton-Raphson cannot continue."`. This applies equally whether it is the first iteration or any subsequent iteration.
10. **Convergence criterion**: Stop iterating when $|x_{k+1} - x_k| < \epsilon$ (using `BigDecimal.subtract`, `BigDecimal.abs()`, and `compareTo`).
11. **Non-convergence**: If the method does not converge within `maxIterations`, throw `ConvergenceFailedException` with message `"Newton-Raphson did not converge within <maxIterations> iterations"`.
12. **Linear polynomial**: For $f(x) = a_0 + a_1 x$, the method should converge in exactly one iteration to $x = -a_0 / a_1$.
13. **Multiple roots**: The method finds whichever root the initial guess converges to. No guarantee on which root is returned.
14. **Polynomial evaluation**: Use **Horner's method** for efficient and numerically stable evaluation of $f(x)$ and $f'(x)$.
15. **BigDecimal arithmetic**: All internal arithmetic must use `BigDecimal` methods (`add`, `subtract`, `multiply`, `divide`). Division must specify `scale` and `RoundingMode.HALF_UP` to avoid `ArithmeticException` from non-terminating decimals.

---

## Examples

### Example 1 — Linear polynomial

```
Input:  coefficients = [-6.0, 3.0], initialGuess = 0.0
        f(x) = -6 + 3x
Output: 2.0

Explanation: The root of 3x - 6 = 0 is x = 2. Converges in one iteration.
```

### Example 2 — Quadratic with two roots

```
Input:  coefficients = [-6.0, 1.0, 1.0], initialGuess = 3.0
        f(x) = -6 + x + x²
Output: 2.0

Explanation: x² + x - 6 = (x + 3)(x - 2) = 0 has roots at x = 2 and x = -3.
             Starting from x₀ = 3.0, the method converges to the nearby root x = 2.
```

### Example 3 — Quadratic converging to negative root

```
Input:  coefficients = [-6.0, 1.0, 1.0], initialGuess = -4.0
        f(x) = -6 + x + x²
Output: -3.0

Explanation: Same polynomial as Example 2, but starting from x₀ = -4.0
             converges to the other root x = -3.
```

### Example 4 — Cubic polynomial

```
Input:  coefficients = [0.0, 0.0, 0.0, 1.0], initialGuess = 1.0
        f(x) = x³
Output: 0.0

Explanation: x³ = 0 has a triple root at x = 0.
             Starting from x₀ = 1.0, the method converges to 0.
```

### Example 5 — Higher-order polynomial

```
Input:  coefficients = [-1.0, 0.0, 1.0], initialGuess = 0.5
        f(x) = -1 + x²
Output: 1.0

Explanation: x² - 1 = 0 has roots at x = 1 and x = -1.
             Starting from x₀ = 0.5, the method converges to x = 1.
```

### Example 6 — Custom tolerance

```
Input:  coefficients = [-2.0, 0.0, 1.0], initialGuess = 1.0, epsilon = 0.001
        f(x) = x² - 2
Output: ≈ 1.4142 (any value where |result - √2| < 0.001)

Explanation: Finding √2 via Newton-Raphson on x² - 2 = 0.
             With coarser tolerance, fewer iterations are needed.
```

### Example 7 — Derivative zero (error)

```
Input:  coefficients = [1.0, 0.0, 1.0], initialGuess = 0.0
        f(x) = 1 + x²
Output: ZeroDerivativeException("Derivative is zero at x = 0.0. Newton-Raphson cannot continue.")

Explanation: f'(x) = 2x, so f'(0) = 0. The method cannot proceed.
```

### Example 8 — Negative input validation

```
Input:  coefficients = null, initialGuess = 1.0
Output: InvalidPolynomialException("Coefficients must not be null or empty")
```

### Example 9 — Constant polynomial (no root)

```
Input:  coefficients = [5.0], initialGuess = 1.0
Output: InvalidPolynomialException("Polynomial must be at least linear (2 or more coefficients)")
```

### Example 10 — Fourth-degree polynomial

```
Input:  coefficients = [-16.0, 0.0, 0.0, 0.0, 1.0], initialGuess = 3.0
        f(x) = x⁴ - 16
Output: 2.0

Explanation: x⁴ - 16 = 0 → x = ±2, ±2i. Starting from x₀ = 3.0, converges to x = 2.
```

### Example 11 — Derivative becomes zero mid-iteration

```
Input:  coefficients = [0.0, 0.0, 0.0, 1.0], initialGuess = -1.0
        f(x) = x³
Output: (the method converges toward 0, but if an intermediate x_k
         lands exactly at 0 while f(x_k) is also 0, it converges;
         if f'(x_k) = 0 while f(x_k) ≠ 0, throws ZeroDerivativeException)

Explanation: f'(x) = 3x². Iteration from x₀ = -1 yields x₁ = -2/3, x₂ = -4/9, ...
             The derivative approaches zero as x → 0. For a triple root the method
             still converges, but the guard must be evaluated at every step.
```

### Example 12 — High-precision root with custom scale

```
Input:  coefficients = [-2.0, 0.0, 1.0], initialGuess = 1.0, scale = 20
        f(x) = -2 + x²
Output: 1.41421356237309504880 (√2 to 20 decimal places)

Explanation: With scale = 20, the result preserves 20 decimal places,
             providing much higher precision than IEEE 754 double (≈15 digits).
```

### Example 13 — Large coefficients without overflow

```
Input:  coefficients = [-1.0E+1000, 0.0, 1.0], initialGuess = 1.0E+500
        f(x) = -10^1000 + x²
Output: ≈ 1.0E+500 (the square root of 10^1000)

Explanation: BigDecimal handles arbitrarily large values without overflow.
             With IEEE 754 double, these coefficients would produce Infinity.
```

---

## Complexity Targets

| Metric    | Target                                                                                                                                                                                                                                  |
| --------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Time**  | O(k × n × d²) per call, where k = number of iterations, n = polynomial order, and d = number of digits (`scale`). Newton-Raphson converges quadratically, so k is typically O(log(1/ε)). The d² factor is from `BigDecimal` arithmetic. |
| **Space** | O(n × d) — storing the `BigDecimal` coefficients and intermediate values.                                                                                                                                                               |

---

## Implementation Notes

- Use **Horner's method** for evaluating $f(x)$ and $f'(x)$ to avoid computing powers explicitly:
    - $f(x) = a_0 + x(a_1 + x(a_2 + \cdots + x \cdot a_n))$
    - Horner's method translates directly to `BigDecimal.multiply(x).add(a_i)` in a reverse loop.
- The derivative coefficients can be computed on the fly: the derivative of term $a_i x^i$ is $i \cdot a_i x^{i-1}$.
- All division must use `BigDecimal.divide(divisor, scale, RoundingMode.HALF_UP)` to avoid `ArithmeticException` from non-terminating decimal expansions (e.g., $1 / 3$).
- The `scale` parameter controls precision throughout the algorithm. Higher values yield more accurate results at the cost of performance.

---

## GraphQL API

### Query signature

```graphql
"""
Finds a root of a polynomial using the Newton-Raphson method.
The polynomial is defined by its coefficients [a₀, a₁, ..., aₙ] where f(x) = a₀ + a₁x + a₂x² + ... + aₙxⁿ.
All numeric values use BigDecimal (serialised as strings in JSON) for arbitrary-precision arithmetic.
"""
findPolynomialRoot(
  "Polynomial coefficients from lowest to highest order: [a₀, a₁, ..., aₙ]."
  coefficients: [BigDecimal!]!
  "The starting value for iteration."
  initialGuess: BigDecimal!
  "Optional convergence tolerance. Defaults to 1e-10."
  epsilon: BigDecimal
  "Optional maximum number of iterations. Defaults to 1000."
  maxIterations: Int
  "Optional number of decimal places for division rounding (RoundingMode.HALF_UP). Defaults to 10."
  scale: Int
): BigDecimal!
```

---

## Domain Placement

- **Subdomain**: `calculus`
- **Class name**: `NewtonRaphsonRootFinder`
- **Package**: `at.mavila.exercises_january_2026.domain.calculus`
- **Exception package**: `at.mavila.exercises_january_2026.domain.calculus.exception`
- **Exception classes**:
    - `InvalidPolynomialException`
    - `InvalidInitialGuessException`
    - `InvalidToleranceException`
    - `InvalidMaxIterationsException`
    - `InvalidScaleException`
    - `ZeroDerivativeException`
    - `ConvergenceFailedException`
