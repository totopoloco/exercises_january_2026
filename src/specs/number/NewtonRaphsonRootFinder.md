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

| Parameter       | Type           | Constraints                                                                                                                                   | Default  |
| --------------- | -------------- | --------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| `coefficients`  | `List<Double>` | Must not be null or empty. Represents $[a_0, a_1, \ldots, a_n]$. At least 2 elements (minimum linear polynomial). Last element must not be 0. | required |
| `initialGuess`  | `double`       | Must be a finite number (not `NaN` or `Infinity`).                                                                                            | required |
| `epsilon`       | `Double`       | Must be > 0 when provided. Nullable.                                                                                                          | `1e-10`  |
| `maxIterations` | `Integer`      | Must be > 0 when provided. Nullable.                                                                                                          | `1000`   |

### Output

| Type     | Description                                                                                                              |
| -------- | ------------------------------------------------------------------------------------------------------------------------ |
| `double` | An approximate root $r$ of $f(x)$ such that $\lvert f(r) \rvert < \epsilon$ or $\lvert x_{k+1} - x_k \rvert < \epsilon$. |

---

## Rules & Edge Cases

1. **Null or empty coefficients**: Throw `IllegalArgumentException` with message `"Coefficients must not be null or empty"`.
2. **Single coefficient**: A constant polynomial (e.g., `[5]`) has no root. Throw `IllegalArgumentException` with message `"Polynomial must be at least linear (2 or more coefficients)"`.
3. **Leading coefficient is zero**: The last element in the list (highest-order term) must not be 0. Throw `IllegalArgumentException` with message `"Leading coefficient must not be zero"`.
4. **NaN/Infinity in coefficients**: If any coefficient is `NaN` or `Infinity`, throw `IllegalArgumentException` with message `"All coefficients must be finite numbers"`.
5. **NaN/Infinity initial guess**: Throw `IllegalArgumentException` with message `"Initial guess must be a finite number"`.
6. **Invalid epsilon**: If provided and is ≤ 0, `NaN`, or `Infinity`, throw `IllegalArgumentException` with message `"Epsilon must be a positive finite number, got: <epsilon>"`.
7. **Invalid maxIterations**: If provided and is ≤ 0, throw `IllegalArgumentException` with message `"Max iterations must be a positive integer, got: <maxIterations>"`.
8. **Derivative is zero**: If $f'(x_k) = 0$ at any iteration, throw `ArithmeticException` with message `"Derivative is zero at x = <x_k>. Newton-Raphson cannot continue."`. This means the method hit a stationary point.
9. **Convergence criterion**: Stop iterating when $|x_{k+1} - x_k| < \epsilon$.
10. **Non-convergence**: If the method does not converge within `maxIterations`, throw `ArithmeticException` with message `"Newton-Raphson did not converge within <maxIterations> iterations"`.
11. **Linear polynomial**: For $f(x) = a_0 + a_1 x$, the method should converge in exactly one iteration to $x = -a_0 / a_1$.
12. **Multiple roots**: The method finds whichever root the initial guess converges to. No guarantee on which root is returned.
13. **Polynomial evaluation**: Use **Horner's method** for efficient and numerically stable evaluation of $f(x)$ and $f'(x)$.

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
Output: ArithmeticException("Derivative is zero at x = 0.0. Newton-Raphson cannot continue.")

Explanation: f'(x) = 2x, so f'(0) = 0. The method cannot proceed.
```

### Example 8 — Negative input validation

```
Input:  coefficients = null, initialGuess = 1.0
Output: IllegalArgumentException("Coefficients must not be null or empty")
```

### Example 9 — Constant polynomial (no root)

```
Input:  coefficients = [5.0], initialGuess = 1.0
Output: IllegalArgumentException("Polynomial must be at least linear (2 or more coefficients)")
```

### Example 10 — Fourth-degree polynomial

```
Input:  coefficients = [-16.0, 0.0, 0.0, 0.0, 1.0], initialGuess = 3.0
        f(x) = x⁴ - 16
Output: 2.0

Explanation: x⁴ - 16 = 0 → x = ±2, ±2i. Starting from x₀ = 3.0, converges to x = 2.
```

---

## Complexity Targets

| Metric    | Target                                                                                                                                                   |
| --------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Time**  | O(k × n) per call, where k = number of iterations and n = polynomial order. Newton-Raphson converges quadratically, so k is typically O(log(1/epsilon)). |
| **Space** | O(n) — storing the coefficients list.                                                                                                                    |

---

## Implementation Notes

- Use **Horner's method** for evaluating $f(x)$ and $f'(x)$ to avoid computing powers explicitly:
    - $f(x) = a_0 + x(a_1 + x(a_2 + \cdots + x \cdot a_n))$
    - This evaluates in O(n) with superior numerical stability.
- The derivative coefficients can be computed on the fly: the derivative of term $a_i x^i$ is $i \cdot a_i x^{i-1}$.

---

## GraphQL API

### Query signature

```graphql
"""
Finds a root of a polynomial using the Newton-Raphson method.
The polynomial is defined by its coefficients [a₀, a₁, ..., aₙ] where f(x) = a₀ + a₁x + a₂x² + ... + aₙxⁿ.
"""
findPolynomialRoot(
  "Polynomial coefficients from lowest to highest order: [a₀, a₁, ..., aₙ]."
  coefficients: [Float!]!
  "The starting value for iteration."
  initialGuess: Float!
  "Optional convergence tolerance. Defaults to 1e-10."
  epsilon: Float
  "Optional maximum number of iterations. Defaults to 1000."
  maxIterations: Int
): Float!
```

---

## Domain Placement

- **Subdomain**: `number`
- **Class name**: `NewtonRaphsonRootFinder`
- **Package**: `at.mavila.exercises_january_2026.domain.number`
