# Exercises January 2026

A collection of algorithm exercises implemented in Java with Spring Boot, following **Domain-Driven Design (DDD)** architecture.

## Architecture

This project follows Domain-Driven Design principles with a clear separation of concerns:

```
src/main/java/at/mavila/exercises_january_2026/
├── domain/                          # Domain Layer - Core Business Logic
│   ├── array/                       # Array operations
│   │   └── MedianCalculator         # Merge arrays and calculate median
│   ├── container/                   # Container optimization
│   │   └── WaterCalculator          # Maximum water container problem
│   ├── calculus/                    # Calculus and numerical methods
│   │   ├── NewtonRaphsonRootFinder  # Polynomial root finding
│   │   ├── NewtonRaphsonParams      # Root finder parameter object
│   │   ├── PolynomialEvaluator      # Horner evaluation for f(x) and f'(x)
│   │   ├── ConvergenceChecker       # Residual and delta convergence checks
│   │   └── exception/               # Calculus-specific domain exceptions
│   ├── string/                      # String processing
│   │   ├── LongestSubstringFinder   # Longest substring without repeats
│   │   ├── MaskService              # String masking
│   │   └── PalindromeExtractor      # Longest palindromic substring
│   ├── number/                      # Number operations
│   │   ├── MinimumPercentageCalculator
│   │   ├── OddFinder                # Find odd-occurring element
│   │   ├── OddFinderOperator        # XOR operator for odd finder
│   │   └── roman/                   # Roman numeral subdomain
│   │       ├── RomanNumberProcessor # Roman ↔ Integer conversion
│   │       └── InvalidRomanNumeralException
│   ├── security/                    # Security operations
│   │   ├── PinCracker               # MD5 PIN cracking
│   │   └── MessageDigestInstanceException
│   ├── collection/                  # Collection operations
│   │   ├── ListNode                 # Linked list node model
│   │   ├── LinkedListMerger         # Merge k sorted lists
│   │   ├── CountNegativesSortedMix  # Count negatives in matrix
│   │   └── RearrangingFruits        # Basket optimization
│   └── phonetic/                    # Phone-related operations
│       └── LetterCombinations       # Phone digit combinations
├── application/                     # Application Layer - Use Cases
│   └── AlgorithmService             # Orchestrates domain services
└── infrastructure/                  # Infrastructure Layer - External Concerns
    ├── config/
    │   └── GraphQLConfig            # GraphQL configuration
    └── web/graphql/
        ├── AlgorithmController      # GraphQL query resolver
        └── GraphQLExceptionHandler  # Error handling
```

### Layer Responsibilities

| Layer              | Responsibility                                                                                   |
| ------------------ | ------------------------------------------------------------------------------------------------ |
| **Domain**         | Core business logic, algorithms, domain models, and exceptions. No dependencies on outer layers. |
| **Application**    | Use case orchestration, coordinates domain services, handles transaction boundaries.             |
| **Infrastructure** | External concerns: web adapters (GraphQL), configuration, persistence, messaging.                |

## Table of Contents

1. [Array Median Calculator](#1-array-median-calculator)
2. [Container With Most Water](#2-container-with-most-water)
3. [Count Negatives in Sorted Matrix](#3-count-negatives-in-sorted-matrix)
4. [Letter Combinations of a Phone Number](#4-letter-combinations-of-a-phone-number)
5. [Merge K Sorted Linked Lists](#5-merge-k-sorted-linked-lists)
6. [Longest Palindromic Substring](#6-longest-palindromic-substring)
7. [Longest Substring Without Repeating Characters](#7-longest-substring-without-repeating-characters)
8. [Rearranging Fruits](#8-rearranging-fruits)
9. [PIN Cracker](#9-pin-cracker)
10. [Minimum Percentage](#10-minimum-percentage)
11. [Roman Numeral to Integer](#11-roman-numeral-to-integer)
    - [11b. Integer to Roman Numeral](#11b-integer-to-roman-numeral)
    - [11c. Roman Numeral Validation](#11c-roman-numeral-validation)
12. [Newton-Raphson Polynomial Root Finder](#12-newton-raphson-polynomial-root-finder)
13. [GraphQL API](#13-graphql-api)

---

## 1. Array Median Calculator

**Location:** `domain/array/MedianCalculator.java`

### Problem

Given two integer arrays, merge them and calculate the median of the combined sorted array.

### Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: array1, array2                    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              1. Validate inputs (non-null)                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│         2. Convert arrays to Lists and merge                │
│            list1.addAll(list2)                              │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              3. Sort the merged list                        │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              4. Calculate median                            │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Is size odd?                                       │    │
│  │  YES: median = list[size/2]                         │    │
│  │  NO:  median = (list[size/2-1] + list[size/2]) / 2  │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: median value                     │
└─────────────────────────────────────────────────────────────┘
```

### Complexity

- **Time:** O((n+m) log(n+m)) due to sorting
- **Space:** O(n+m) for the merged list

### Example

```
array1 = [1, 3]
array2 = [2]
merged = [1, 2, 3]
median = 2 (odd size, middle element)
```

---

## 2. Container With Most Water

**Location:** `domain/container/WaterCalculator.java`

### Problem

Given an array of heights representing vertical lines, find two lines that together with the x-axis form a container that holds the most water.

### Algorithm: Two-Pointer Technique

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: height[]                          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│      Initialize: left = 0, right = length-1, maxArea = 0    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
         ┌────────────────────┴────────────────────┐
         │            WHILE left < right           │
         └────────────────────┬────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │  Calculate area = min(height[left], height[right])│
    │                   × (right - left)                │
    └─────────────────────────┬─────────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │         Update maxArea = max(maxArea, area)       │
    └─────────────────────────┬─────────────────────────┘
                              │
                              ▼
    ┌─────────────────────────────────────────────────────┐
    │         height[left] <= height[right] ?             │
    │         ┌──────────┐         ┌──────────┐           │
    │    YES: │ left++   │    NO:  │ right--  │           │
    │         └──────────┘         └──────────┘           │
    └─────────────────────────┬───────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: maxArea                           │
└─────────────────────────────────────────────────────────────┘
```

### Visual Example

```
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]

     8           8
     █     6     █     7
     █     █  5  █     █
     █     █  █  █     █
     █     █  █  4     █
     █     █  █  █  3  █
     █     █  2  █  █  █
  1  █     █  █  █  █  █
  █  █     █  █  █  █  █
  ─────────────────────────
  0  1  2  3  4  5  6  7  8

Best container: between index 1 and 8
Area = min(8, 7) × (8 - 1) = 7 × 7 = 49
```

### Why Move the Shorter Line?

The area is limited by the shorter line. Moving the taller line can only **decrease** or maintain the area (width shrinks, height stays same or shrinks). Moving the shorter line **might** find a taller line that increases the area.

### Complexity

- **Time:** O(n) - single pass with two pointers
- **Space:** O(1) - constant extra space

---

## 3. Count Negatives in Sorted Matrix

**Location:** `domain/collection/CountNegativesSortedMix.java`

### Problem

Given a 2D grid, count the number of negative numbers.

### Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: grid[][]                          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│               Initialize: negatives = []                    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              FOR each row m in grid                         │
│              ┌─────────────────────────────────────────┐    │
│              │    FOR each element n in row            │    │
│              │    ┌───────────────────────────────┐    │    │
│              │    │  IF n < 0                     │    │    │
│              │    │    negatives.add(n)           │    │    │
│              │    └───────────────────────────────┘    │    │
│              └─────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                OUTPUT: negatives.size()                     │
└─────────────────────────────────────────────────────────────┘
```

### Complexity

- **Time:** O(m × n) where m = rows, n = columns
- **Space:** O(k) where k = number of negatives

---

## 4. Letter Combinations of a Phone Number

**File:** `LetterCombinations.java`

### Problem

Given a string containing digits 2-9, return all possible letter combinations that the number could represent (like on a telephone keypad).

### Phone Mapping

```
┌─────┬─────┬─────┐
│  1  │  2  │  3  │
│     │ abc │ def │
├─────┼─────┼─────┤
│  4  │  5  │  6  │
│ ghi │ jkl │ mno │
├─────┼─────┼─────┤
│  7  │  8  │  9  │
│pqrs │ tuv │wxyz │
└─────┴─────┴─────┘
```

### Algorithm: Stream Reduction with FlatMap

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: digits "23"                       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│        Map digits to letters: 2→"abc", 3→"def"              │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   Stream.reduce with initial value [""]                     │
│                                                             │
│   Accumulator function (flatMap expansion):                 │
│   ┌─────────────────────────────────────────────────────┐   │
│   │  For each combination in current list:              │   │
│   │    For each letter in current digit's mapping:      │   │
│   │      Create: combination + letter                   │   │
│   └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│     OUTPUT: ["ad","ae","af","bd","be","bf","cd","ce","cf"]  │
└─────────────────────────────────────────────────────────────┘
```

### Step-by-Step Example for "23"

```
Initial: [""]

After processing '2' (abc):
  "" + 'a' = "a"
  "" + 'b' = "b"
  "" + 'c' = "c"
  Result: ["a", "b", "c"]

After processing '3' (def):
  "a" + 'd' = "ad"    "b" + 'd' = "bd"    "c" + 'd' = "cd"
  "a" + 'e' = "ae"    "b" + 'e' = "be"    "c" + 'e' = "ce"
  "a" + 'f' = "af"    "b" + 'f' = "bf"    "c" + 'f' = "cf"

  Result: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

### Complexity

- **Time:** O(4^n × n) where n = number of digits (worst case for 7 and 9 which have 4 letters)
- **Space:** O(4^n × n) for storing all combinations

---

## 5. Merge K Sorted Linked Lists

**File:** `LinkedListMerger.java`

### Problem

Merge k sorted linked lists into one sorted linked list.

### Algorithm: Sequential Merge with Dummy Head

```
┌─────────────────────────────────────────────────────────────┐
│                INPUT: ListNode[] lists                      │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│           Initialize: merged = null                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              FOR each list in lists[]                       │
│              ┌─────────────────────────────────────────┐    │
│              │  IF list is not null                    │    │
│              │    merged = mergeTwoLists(merged, list) │    │
│              └─────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: merged                           │
└─────────────────────────────────────────────────────────────┘
```

### mergeTwoLists Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│             INPUT: source1, source2                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│    Determine head = node with smaller value                 │
│    Initialize tail = head                                   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
         ┌────────────────────┴────────────────────┐
         │     WHILE both lists have nodes         │
         └────────────────────┬────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │         source1.val <= source2.val ?              │
    │    ┌──────────────────┐    ┌──────────────────┐   │
    │YES:│tail.next=source1 │ NO:│tail.next=source2 │   │
    │    │tail = tail.next  │    │tail = tail.next  │   │
    │    │source1=src1.next │    │source2=src2.next │   │
    │    └──────────────────┘    └──────────────────┘   │
    └─────────────────────────┬─────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│     Append remaining nodes from non-empty list              │
│     tail.next = (source1 != null) ? source1 : source2       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: head                             │
└─────────────────────────────────────────────────────────────┘
```

### Visual Example

```
List 1:  1 → 4 → 5
List 2:  1 → 3 → 4
List 3:  2 → 6

Step 1: Merge List1 and List2
        1 → 1 → 3 → 4 → 4 → 5

Step 2: Merge result with List3
        1 → 1 → 2 → 3 → 4 → 4 → 5 → 6
```

### Complexity

- **Time:** O(N × k) where N = total nodes, k = number of lists
- **Space:** O(1) - we reuse existing nodes

---

## 6. Longest Palindromic Substring

**File:** `LongestPalindromeSubstringExtractor.java`

### Problem

Given a string, find the longest palindromic substring.

### Algorithm: Manacher's Algorithm

Introduced by Glenn Manacher in 1975, this algorithm finds the longest palindromic substring in **O(n)** time. Compared to brute-force O(n³) and dynamic programming O(n²), Manacher's Algorithm is incredibly efficient by avoiding redundant work through clever use of symmetry.

### Step 1: Modifying the Input String

The algorithm begins by inserting a special character (like `#`) between every character, plus sentinels at the boundaries:

```
Input:  "babad"
After:  "^#b#a#b#a#d#$"

The ^ and $ are sentinels to avoid boundary checks.
```

**Why do this?** It ensures all palindromes have **odd length** in the transformed string. Without this, you'd need separate logic for odd-length ("aba") and even-length ("bb") palindromes. After transformation, even "bb" becomes "#b#b#" — an odd-length palindrome centered on the middle `#`.

### Step 2: Key Variables

| Variable       | Description                                                                                                                        |
| -------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| `pradii[]`     | Array storing the radius of the palindrome centered at each index. The radius equals the palindrome length in the original string. |
| `center`       | The index of the center of the rightmost palindrome found so far.                                                                  |
| `right`        | The rightmost index reached by any palindrome found so far: `right = center + pradii[center]`                                      |
| `currentIndex` | The current position being processed in the main loop.                                                                             |
| `mirror`       | The mirror position of `currentIndex` relative to `center`: `mirror = 2 * center - currentIndex`                                   |
| `maxLen`       | The longest palindrome radius found so far.                                                                                        |
| `maxCenter`    | The center index of the longest palindrome.                                                                                        |

### Step 3: The Three Scenarios

The algorithm can be broken down into **three scenarios**:

#### Scenario 1: Mirroring (currentIndex < right)

When we're inside a known palindrome, we can exploit symmetry:

```java
final int mirror = 2 * center - currentIndex;

if (currentIndex < right) {
    int distanceToRight = right - currentIndex;
    int mirrorRadius = pradii[mirror];
    pradii[currentIndex] = Math.min(distanceToRight, mirrorRadius);
}
// Then expand...
```

#### Scenario 2: No Mirroring (currentIndex >= right)

When we're outside any known palindrome, we start fresh with `pradii[currentIndex] = 0` (already initialized).

#### Scenario 3: Expansion (Always Performed)

After mirroring (or starting fresh), expand outward using the `palindromeDetector` method:

```java
private void palindromeDetector(char[] preprocessed, int[] pradii, int currentIndex) {
    while (true) {
        int offset = pradii[currentIndex] + 1;
        char charRight = preprocessed[currentIndex + offset];
        char charLeft = preprocessed[currentIndex - offset];

        if (charRight == charLeft) {
            pradii[currentIndex]++;
        } else {
            break;
        }
    }
}
```

After expansion, update rightmost palindrome if needed:

```java
final int currentPalindromeRadius = pradii[currentIndex];
final int expandedIndex = currentIndex + currentPalindromeRadius;

if (expandedIndex > right) {
    center = currentIndex;
    right = expandedIndex;
}
```

**This is the genius of Manacher's Algorithm!**

```
The Mirror Concept:
═══════════════════════════════════════════════════════════════

         center
            │
    ◄───────┼───────►
            │
  mirror    │    currentIndex
     │      │         │
     ▼      ▼         ▼
─────────────────────────────────────
     [   palindrome         ]
                         ◄───► right

Since everything within the palindrome centered at 'center' is symmetric,
the palindrome at 'currentIndex' mirrors the one at 'mirror'.
```

**Why use `min(distanceToRight, mirrorRadius)`?**

Because the mirror's palindrome might extend beyond the left boundary:

```
Case where mirror's palindrome exceeds the boundary:
════════════════════════════════════════════════════════════════

                        center
                           │
              ◄────────────┼────────────►
                           │
    mirror                 │           currentIndex
       │                   │               │
       ▼                   ▼               ▼
───────────────────────────────────────────────────────
  [===palindrome at mirror===]        [===?===]
  ▲                                ▲           ▲
  │                                │           │
extends beyond              left_boundary    right
left_boundary!

We can only guarantee pradii[currentIndex] >= min(pradii[mirror], right - currentIndex)
The part beyond right is UNVERIFIED — we must expand to check!
```

### Visual State Animation

The implementation includes detailed logging that shows a visual "animation" of the algorithm's state at each iteration:

```
  ═══════════════════════════════════════════════════════════════════════════
  📊 VISUAL STATE
  ═══════════════════════════════════════════════════════════════════════════
  idx:    0  1  2  3  4  5  6  7  8  9 10 11 12
  char:   ^  #  b  #  a  #  b  #  a  #  d  #  $
  P[i]:   0  0  1  0  3  0  3  0  1  0  1  0  0
                ▲
               CM  R
  ───────────────────────────────────────────────────────────────────────────
  LEGEND: ▲ = currentIndex, C = center, R = right, M = maxCenter
  ───────────────────────────────────────────────────────────────────────────
  VARIABLES:
    currentIndex = 2  │  center    = 2  │  right      = 3
    maxLen       = 1  │  maxCenter = 2  │  pradii[2]  = 1
  ───────────────────────────────────────────────────────────────────────────
  CURRENT PALINDROME RANGE: [1..3] centered at 2
         ─  ●  ─
  ───────────────────────────────────────────────────────────────────────────
  RIGHTMOST PALINDROME: [1..3] centered at 2
         ═  ◆  ═
  ═══════════════════════════════════════════════════════════════════════════
```

### Why This Achieves O(n)

Each position is visited at most twice:

1. Once when `currentIndex` reaches it during iteration
2. Once during expansion (but this expansion contributes to moving `right` forward)

Since `right` only moves forward and never backward, the total work is linear.

### Complexity

- **Time:** O(n) - each position is visited at most twice
- **Space:** O(n) - for the preprocessed string and pradii[] array

### Execution Example: "babad"

```
╔══════════════════════════════════════════════════════════════════════════════╗
║           MANACHER'S ALGORITHM - LONGEST PALINDROMIC SUBSTRING               ║
╚══════════════════════════════════════════════════════════════════════════════╝
INPUT: "babad"

═══════════════════════════════════════════════════════════════════════════════
STEP 1: PREPROCESSING
═══════════════════════════════════════════════════════════════════════════════
  Original:     "babad" (length 5)
  Preprocessed: "^#b#a#b#a#d#$" (length 13)

═══════════════════════════════════════════════════════════════════════════════
STEP 2: INITIALIZE VARIABLES
═══════════════════════════════════════════════════════════════════════════════
  pradii[]  = array of 13 zeros
  center    = 0
  right     = 0
  maxLen    = 0
  maxCenter = 0
```

#### Iteration at currentIndex=4 (No Mirroring + Expansion)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATION: currentIndex = 4 (char = 'a')
└─────────────────────────────────────────────────────────────────────────────┘
  State: center=2, right=3

  ┌─ MIRROR CALCULATION ───────────────────────────────────────────────────┐
  │  mirror = 2 * center - currentIndex
  │        = 2 * 2 - 4 = 0
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ NO MIRRORING (currentIndex >= right) ─────────────────────────────────┐
  │  4 >= 3 → We're OUTSIDE any known palindrome
  │  Starting fresh with pradii[4] = 0
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ EXPANSION ────────────────────────────────────────────────────────────┐
  │  Try to expand palindrome centered at currentIndex=4
  │  Expansion #1: offset=1
  │    charLeft  = preprocessed[4 - 1] = preprocessed[3] = '#'
  │    charRight = preprocessed[4 + 1] = preprocessed[5] = '#'
  │    '#' == '#' → MATCH! pradii[4] incremented to 1
  │  Expansion #2: offset=2
  │    charLeft  = preprocessed[4 - 2] = preprocessed[2] = 'b'
  │    charRight = preprocessed[4 + 2] = preprocessed[6] = 'b'
  │    'b' == 'b' → MATCH! pradii[4] incremented to 2
  │  Expansion #3: offset=3
  │    charLeft  = preprocessed[4 - 3] = preprocessed[1] = '#'
  │    charRight = preprocessed[4 + 3] = preprocessed[7] = '#'
  │    '#' == '#' → MATCH! pradii[4] incremented to 3
  │  Expansion #4: offset=4
  │    charLeft  = preprocessed[4 - 4] = preprocessed[0] = '^'
  │    charRight = preprocessed[4 + 4] = preprocessed[8] = 'a'
  │    '^' != 'a' → MISMATCH. Expansion stops.
  │  Final radius: pradii[4] = 3
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ CHECK: Update rightmost palindrome? ──────────────────────────────────┐
  │  expandedIndex = currentIndex + pradii[currentIndex]
  │               = 4 + 3 = 7
  │  Compare: expandedIndex (7) > right (3) ?
  │  → YES! This palindrome extends further right.
  │    center: 2 → 4
  │    right:  3 → 7
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ CHECK: New longest palindrome? ───────────────────────────────────────┐
  │  Compare: pradii[4] (3) > maxLen (1) ?
  │  → YES! New longest found.
  │    maxLen:    1 → 3
  │    maxCenter: 4
  │    palindrome: "bab"
  └────────────────────────────────────────────────────────────────────────┘
```

#### Iteration at currentIndex=6 (Mirroring + Expansion)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATION: currentIndex = 6 (char = 'b')
└─────────────────────────────────────────────────────────────────────────────┘
  State: center=4, right=7

  ┌─ MIRROR CALCULATION ───────────────────────────────────────────────────┐
  │  mirror = 2 * center - currentIndex
  │        = 2 * 4 - 6 = 2
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ MIRRORING (currentIndex < right) ─────────────────────────────────────┐
  │  6 < 7 → We're INSIDE the rightmost palindrome!
  │
  │  Key insight: Due to symmetry around 'center', the palindrome at
  │  'currentIndex' mirrors the one at 'mirror'. But we can only trust
  │  information up to 'right' boundary.
  │
  │  distanceToRight = right - currentIndex = 7 - 6 = 1
  │  mirrorRadius    = pradii[mirror] = pradii[2] = 1
  │
  │  pradii[6] = min(1, 1) = 1
  │  (We take minimum because mirror's palindrome might extend beyond 'right')
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ EXPANSION ────────────────────────────────────────────────────────────┐
  │  Try to expand palindrome centered at currentIndex=6
  │  Expansion #1: offset=2
  │    charLeft  = preprocessed[6 - 2] = preprocessed[4] = 'a'
  │    charRight = preprocessed[6 + 2] = preprocessed[8] = 'a'
  │    'a' == 'a' → MATCH! pradii[6] incremented to 2
  │  Expansion #2: offset=3
  │    charLeft  = preprocessed[6 - 3] = preprocessed[3] = '#'
  │    charRight = preprocessed[6 + 3] = preprocessed[9] = '#'
  │    '#' == '#' → MATCH! pradii[6] incremented to 3
  │  Expansion #3: offset=4
  │    charLeft  = preprocessed[6 - 4] = preprocessed[2] = 'b'
  │    charRight = preprocessed[6 + 4] = preprocessed[10] = 'd'
  │    'b' != 'd' → MISMATCH. Expansion stops.
  │  Expanded 2 time(s).
  │  Final radius: pradii[6] = 3
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ CHECK: Update rightmost palindrome? ──────────────────────────────────┐
  │  expandedIndex = currentIndex + pradii[currentIndex]
  │               = 6 + 3 = 9
  │  Compare: expandedIndex (9) > right (7) ?
  │  → YES! This palindrome extends further right.
  │    center: 4 → 6
  │    right:  7 → 9
  └────────────────────────────────────────────────────────────────────────┘

  ┌─ CHECK: New longest palindrome? ───────────────────────────────────────┐
  │  Compare: pradii[6] (3) > maxLen (3) ?
  │  → NO, keeping maxLen=3, maxCenter=4
  └────────────────────────────────────────────────────────────────────────┘
```

#### Final Result

```
═══════════════════════════════════════════════════════════════════════════════
STEP 4: EXTRACT RESULT
═══════════════════════════════════════════════════════════════════════════════
  maxCenter = 4, maxLen = 3

  Formula: start = (maxCenter - maxLen) / 2
                 = (4 - 3) / 2 = 0

  Why divide by 2? Because preprocessed string has '#' between each char,
  so indices in preprocessed are ~2x the indices in original string.

╔══════════════════════════════════════════════════════════════════════════════╗
║ RESULT: "bab"
║ Length: 3
╚══════════════════════════════════════════════════════════════════════════════╝
```

### Even-Length Palindrome Example: "cbbd"

The preprocessing trick shines when handling **even-length** palindromes:

```
Input: "cbbd"  →  Preprocessed: "^#c#b#b#d#$"

Index:  0  1  2  3  4  5  6  7  8  9  10
Char:   ^  #  c  #  b  #  b  #  d  #  $
                       ↑
                    currentIndex=5 (the '#' BETWEEN the two 'b's)

Expansion at currentIndex=5:
  s[4]='b' vs s[6]='b'  →  MATCH! pradii[5]++ = 1
  s[3]='#' vs s[7]='#'  →  MATCH! pradii[5]++ = 2
  s[2]='c' vs s[8]='d'  →  DON'T MATCH

Result: pradii[5] = 2

Extract: start = (5 - 2) / 2 = 1
         Result = "cbbd".substring(1, 1+2) = "bb"

╔══════════════════════════════════════════════════════════════════════════════╗
║ RESULT: "bb"
║ Length: 2
╚══════════════════════════════════════════════════════════════════════════════╝
```

---

## 7. Longest Substring Without Repeating Characters

**File:** `LongestSubstring.java`

### Problem

Given a string, find the length of the longest substring without repeating characters.

### Algorithm: Sliding Window

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: string s                          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   Initialize:                                               │
│     lastSeen[256] = {-1, -1, ...}  // last index of char    │
│     left = 0                       // window start          │
│     maxLen = 0                     // result                │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
         ┌────────────────────┴────────────────────┐
         │       FOR right = 0 to s.length-1       │
         └────────────────────┬────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │   currentChar = s[right]                          │
    │   IF lastSeen[currentChar] >= left:               │
    │      left = lastSeen[currentChar] + 1             │
    └─────────────────────────┬─────────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │   lastSeen[currentChar] = right                   │
    └─────────────────────────┬─────────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │   windowLen = right - left + 1                    │
    │   maxLen = max(maxLen, windowLen)                 │
    └─────────────────────────┬─────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: maxLen                           │
└─────────────────────────────────────────────────────────────┘
```

### Visual Example: "abcabcbb"

```
Step-by-step window expansion and contraction:

String: a b c a b c b b
Index:  0 1 2 3 4 5 6 7

right=0: 'a' → lastSeen['a']=-1 < left=0, no shrink
         window=[a], maxLen=1
         lastSeen['a']=0

right=1: 'b' → lastSeen['b']=-1 < left=0, no shrink
         window=[a,b], maxLen=2
         lastSeen['b']=1

right=2: 'c' → lastSeen['c']=-1 < left=0, no shrink
         window=[a,b,c], maxLen=3
         lastSeen['c']=2

right=3: 'a' → lastSeen['a']=0 >= left=0, shrink!
         left = 0 + 1 = 1
         window=[b,c,a], maxLen=3
         lastSeen['a']=3

right=4: 'b' → lastSeen['b']=1 >= left=1, shrink!
         left = 1 + 1 = 2
         window=[c,a,b], maxLen=3
         lastSeen['b']=4

right=5: 'c' → lastSeen['c']=2 >= left=2, shrink!
         left = 2 + 1 = 3
         window=[a,b,c], maxLen=3
         lastSeen['c']=5

right=6: 'b' → lastSeen['b']=4 >= left=3, shrink!
         left = 4 + 1 = 5
         window=[c,b], maxLen=3
         lastSeen['b']=6

right=7: 'b' → lastSeen['b']=6 >= left=5, shrink!
         left = 6 + 1 = 7
         window=[b], maxLen=3
         lastSeen['b']=7

RESULT: 3 (substring "abc")
```

### Window Visualization

```
"abcabcbb"
 ├─────────► time

 [a]           maxLen = 1
 [a b]         maxLen = 2
 [a b c]       maxLen = 3
   [b c a]     maxLen = 3 (shrink: 'a' repeated)
     [c a b]   maxLen = 3 (shrink: 'b' repeated)
       [a b c] maxLen = 3 (shrink: 'c' repeated)
         [c b] maxLen = 3 (shrink: 'b' repeated)
           [b] maxLen = 3 (shrink: 'b' repeated)
```

### Complexity

- **Time:** O(n) - single pass through the string
- **Space:** O(1) or O(256) - fixed-size map for ASCII characters

---

## 8. Rearranging Fruits

**File:** `RearrangingFruits.java`

### Problem

Given two baskets of fruits (represented as arrays of costs), rearrange fruits between them so both baskets contain identical sets. The cost of swapping fruit `a` from basket1 with fruit `b` from basket2 is `min(a, b)`. Find the minimum total cost.

### Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│                INPUT: basket1[], basket2[]                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 1: Create frequency maps for both baskets            │
│   freq1[v] = count of v in basket1                          │
│   freq2[v] = count of v in basket2                          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 2: Feasibility check                                 │
│   FOR each unique fruit v:                                  │
│     total = freq1[v] + freq2[v]                             │
│     IF total is ODD → RETURN -1 (impossible)                │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 3: Find globalMin (cheapest fruit across both)       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 4: Build excess lists                                │
│   FOR each unique fruit v:                                  │
│     target = (freq1[v] + freq2[v]) / 2                      │
│     IF freq1[v] > target:                                   │
│       Add (freq1[v] - target) copies of v to excess1        │
│     IF freq2[v] > target:                                   │
│       Add (freq2[v] - target) copies of v to excess2        │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 5: Sort and pair optimally                           │
│   SORT excess1 ascending                                    │
│   SORT excess2 ascending                                    │
│                                                             │
│   Pair: smallest from excess1 with largest from excess2     │
│         (i starts at 0, j starts at end)                    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 6: Calculate minimum cost for each pair              │
│   FOR each pair (a from excess1, b from excess2):           │
│     directCost = min(a, b)                                  │
│     viaMinCost = 2 × globalMin                              │
│     totalCost += min(directCost, viaMinCost)                │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: totalCost                        │
└─────────────────────────────────────────────────────────────┘
```

### Why Two Swap Options?

**Direct Swap:** Exchange fruit `a` and fruit `b` directly. Cost = `min(a, b)`.

**Via Minimum Swap:** Use the globally cheapest fruit as an intermediary:

1. Swap `a` with `globalMin` (cost = `globalMin`)
2. Swap `globalMin` with `b` (cost = `globalMin`)
3. Total cost = `2 × globalMin`

Choose whichever is cheaper!

### Example

```
basket1 = [4, 2, 2, 2]
basket2 = [1, 4, 1, 2]

Frequency Table:
┌───────┬────────┬────────┬───────┬────────┐
│ Fruit │ freq1  │ freq2  │ Total │ Target │
├───────┼────────┼────────┼───────┼────────┤
│   1   │   0    │   2    │   2   │   1    │
│   2   │   3    │   1    │   4   │   2    │
│   4   │   1    │   1    │   2   │   1    │
└───────┴────────┴────────┴───────┴────────┘

Excess Lists:
  excess1 = [2]     (basket1 has 3 twos but needs only 2)
  excess2 = [1]     (basket2 has 2 ones but needs only 1)

globalMin = 1

Pair: a=2 from excess1, b=1 from excess2
  directCost = min(2, 1) = 1
  viaMinCost = 2 × 1 = 2
  Best = min(1, 2) = 1

Total Cost = 1
```

### Visual: Swap Options

```
Direct Swap (cost = min(a,b)):
  Basket1: [..., a, ...]  →  Basket1: [..., b, ...]
  Basket2: [..., b, ...]  →  Basket2: [..., a, ...]

Via-Minimum Swap (cost = 2 × globalMin):
  Step 1: Swap 'a' with 'globalMin'
  Basket1: [..., a, ...]     Basket1: [..., globalMin, ...]
  Basket2: [..., globalMin]  Basket2: [..., a, ...]

  Step 2: Swap 'globalMin' with 'b'
  Basket1: [..., globalMin, ...] Basket1: [..., b, ...]
  Basket2: [..., b, ...]         Basket2: [..., globalMin, ...]
```

### Complexity

- **Time:** O(n log n) due to sorting
- **Space:** O(n) for frequency maps and excess lists

---

## 9. PIN Cracker

**File:** `PinCracker.java`

### Problem

Given an MD5 hash of a numeric PIN, recover the original PIN by brute-force search.

### Algorithm: Exhaustive Search

```
┌─────────────────────────────────────────────────────────────┐
│              INPUT: hash (MD5 hex string), maxLen           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│            Normalize hash to lowercase                      │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│           For each length L from 1 to maxLen:               │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  For each candidate from "0...0" to "9...9" (L digits)│  │
│  │    1. Compute MD5(candidate)                          │  │
│  │    2. If MD5 matches target hash → return candidate   │  │
│  └───────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│     OUTPUT: recovered PIN string, or null if not found      │
└─────────────────────────────────────────────────────────────┘
```

### Mathematical Background

#### Search Space Size

For a maximum PIN length **M**, the algorithm searches all numeric strings from length 1 to M. The total number of candidates is:

$$
\text{Total} = \sum_{k=1}^{M} 10^k = 10^1 + 10^2 + 10^3 + \ldots + 10^M
$$

This is a geometric series with first term $a = 10$, common ratio $r = 10$, and $M$ terms.

Using the geometric series formula:

$$
\sum_{k=1}^{M} 10^k = 10 \cdot \frac{10^M - 1}{10 - 1} = \frac{10^{M+1} - 10}{9}
$$

#### Candidate Count by Maximum Length

| Max Length (M) | Formula Result | Total Candidates |
| -------------- | -------------- | ---------------- |
| 1              | (10² - 10) / 9 | **10**           |
| 2              | (10³ - 10) / 9 | **110**          |
| 3              | (10⁴ - 10) / 9 | **1,110**        |
| 4              | (10⁵ - 10) / 9 | **11,110**       |
| 5              | (10⁶ - 10) / 9 | **111,110**      |
| 6              | (10⁷ - 10) / 9 | **1,111,110**    |
| 7              | (10⁸ - 10) / 9 | **11,111,110**   |
| 8              | (10⁹ - 10) / 9 | **111,111,110**  |

#### Derivation

The formula can be derived as follows:

1. **Per-length count:** For length $k$, candidates range from $\underbrace{00\ldots0}_{k}$ to $\underbrace{99\ldots9}_{k}$, giving exactly $10^k$ candidates.

2. **Summation:** Total = $10^1 + 10^2 + \ldots + 10^M$

3. **Geometric Series:** Let $S = 10 + 10^2 + \ldots + 10^M$

    Multiply by 10: $10S = 10^2 + 10^3 + \ldots + 10^{M+1}$

    Subtract: $10S - S = 10^{M+1} - 10$

    Therefore: $S = \frac{10^{M+1} - 10}{9}$

### Complexity

- **Time:** $O\left(\frac{10^{M+1} - 10}{9}\right) \approx O(10^M)$ — exponential in PIN length
- **Space:** $O(M)$ for storing the candidate string

### Example

```
Input:  hash = "827ccb0eea8a706c4c34a16891f84e7b" (MD5 of "12345")
        maxLen = 5

Search: "0" → MD5 ≠ target
        "1" → MD5 ≠ target
        ...
        "12345" → MD5 = "827ccb0eea8a706c4c34a16891f84e7b" ✓

Output: "12345"
```

### Leading Zeros Example

```
Input:  hash = "86aa400b65433b608a9db30070ec60cd" (MD5 of "00078")
        maxLen = 5

Search: ...
        "00078" → MD5 = "86aa400b65433b608a9db30070ec60cd" ✓

Output: "00078"  (leading zeros preserved)
```

### Progress Listener

The cracker supports a callback interface for monitoring progress:

```java
pinCracker.crack(hash, 6, (candidate, triedSoFar) -> {
    if (triedSoFar % 100000 == 0) {
        System.out.println("Progress: " + triedSoFar + " candidates tried");
    }
    return true; // return false to abort search
});
```

---

## 10. Minimum Percentage

**File:** `MinimumPercentage.java`

### Problem

Given an array of n percentages (representing completion rates, scores, etc.), calculate the **minimum percentage** that must be achieved by ALL items simultaneously for the overall goal to be met.

This is useful in scenarios like:

- Determining the minimum score needed across multiple subjects to pass
- Finding the required completion rate for parallel tasks
- Calculating threshold values for multi-criteria systems

### Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│                INPUT: percentages[] (n values)              │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              1. Calculate sum of all percentages            │
│                     Σp = p₁ + p₂ + ... + pₙ                 │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              2. Calculate threshold                         │
│                 threshold = 100 × (n - 1)                   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              3. Calculate excess                            │
│                 excess = Σp - threshold                     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              4. Return result                               │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  excess < 0 ? return 0 : return excess              │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: minimum percentage               │
└─────────────────────────────────────────────────────────────┘
```

### Mathematical Background

#### The Formula

$$
\text{Result} = \max\left(0, \sum_{i=1}^{n} p_i - 100 \cdot (n-1)\right)
$$

Where:

- $p_i$ = each percentage value in the input
- $n$ = number of elements
- The result is clamped to 0 if negative

#### Intuition

The formula calculates how much "excess" percentage exists beyond the baseline. If you have n items and each could contribute up to 100%, then:

- The maximum total is 100n
- If n-1 items each contribute exactly 100%, that's 100(n-1)
- The minimum required for the nth item is: Total - 100(n-1)

#### Step-by-Step Example

```
Input: [65, 80, 80, 90]

Step 1: Calculate sum
        Σp = 65 + 80 + 80 + 90 = 315

Step 2: Calculate threshold
        n = 4
        threshold = 100 × (4 - 1) = 300

Step 3: Calculate excess
        excess = 315 - 300 = 15

Step 4: Return result
        15 ≥ 0, so return 15

Output: 15
```

#### More Examples

| Input            | Sum (Σp) | n   | Threshold | Excess | Result  |
| ---------------- | -------- | --- | --------- | ------ | ------- |
| [76]             | 76       | 1   | 0         | 76     | **76**  |
| [50, 50]         | 100      | 2   | 100       | 0      | **0**   |
| [30, 40]         | 70       | 2   | 100       | -30    | **0**   |
| [100, 100, 100]  | 300      | 3   | 200       | 100    | **100** |
| [80, 80, 80, 80] | 320      | 4   | 300       | 20     | **20**  |

### Complexity

- **Time:** O(n) — single pass to sum all elements
- **Space:** O(1) — only stores sum and intermediate values

### Edge Cases

| Case                 | Input           | Result | Explanation                       |
| -------------------- | --------------- | ------ | --------------------------------- |
| Single element       | [76]            | 76     | threshold=0, result=76-0=76       |
| All zeros            | [0, 0, 0]       | 0      | Sum below threshold, clamped to 0 |
| All 100%             | [100, 100, 100] | 100    | 300-200=100                       |
| Below threshold      | [30, 40]        | 0      | 70-100=-30 → clamped to 0         |
| Exactly at threshold | [50, 50]        | 0      | 100-100=0                         |
| Values > 100%        | [150, 120]      | 170    | 270-100=170                       |

---

## 11. Roman Numeral to Integer

**File:** `RomanNumberProcessor.java`

### Problem

Convert a Roman numeral string to its integer value. Roman numerals are represented by seven different symbols with the following values:

| Symbol | Value |
| ------ | ----- |
| I      | 1     |
| V      | 5     |
| X      | 10    |
| L      | 50    |
| C      | 100   |
| D      | 500   |
| M      | 1000  |

Roman numerals use **subtraction notation** for certain combinations where a smaller numeral appears before a larger one:

| Pattern | Value | Meaning    |
| ------- | ----- | ---------- |
| IV      | 4     | 5 - 1      |
| IX      | 9     | 10 - 1     |
| XL      | 40    | 50 - 10    |
| XC      | 90    | 100 - 10   |
| CD      | 400   | 500 - 100  |
| CM      | 900   | 1000 - 100 |

### Algorithm: Single-Pass with Look-Ahead

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: roman string                      │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│       Validate: Is input null or blank?                     │
│       YES → throw IllegalArgumentException                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│       Validate Roman numeral format:                        │
│       • Only valid characters (I, V, X, L, C, D, M)         │
│       • Valid subtraction pairs (IV, IX, XL, XC, CD, CM)    │
│       • No invalid repetitions (V, L, D cannot repeat)      │
│       • Max 3 consecutive same symbols (I, X, C, M)         │
│       INVALID → throw InvalidRomanNumeralException          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│       Initialize: result = 0, i = 0                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│       FOR each character at index i in roman string         │
│       ┌───────────────────────────────────────────────────┐ │
│       │  currentValue = map.get(roman[i])                 │ │
│       │  nextValue = map.get(roman[i+1]) if exists        │ │
│       │                                                   │ │
│       │  IF isAdditionCase(currentValue, nextValue)       │ │
│       │      result += currentValue  (addition case)      │ │
│       │  ELSE                                             │ │
│       │      result -= currentValue  (subtraction case)   │ │
│       │                                                   │ │
│       │  isAdditionCase: no next OR current >= next       │ │
│       └───────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: result                           │
└─────────────────────────────────────────────────────────────┘
```

### Step-by-Step Example

```
Input: "MCMXCIV"

Processing each character:
┌───────┬───────────────┬───────────┬───────────┬─────────────────────┬────────┐
│ Index │   Character   │  Current  │   Next    │       Action        │ Result │
├───────┼───────────────┼───────────┼───────────┼─────────────────────┼────────┤
│   0   │      M        │   1000    │   100 (C) │ 1000 > 100 → ADD    │  1000  │
│   1   │      C        │    100    │  1000 (M) │ 100 < 1000 → SUB    │   900  │
│   2   │      M        │   1000    │    10 (X) │ 1000 > 10 → ADD     │  1900  │
│   3   │      X        │     10    │   100 (C) │ 10 < 100 → SUB      │  1890  │
│   4   │      C        │    100    │     1 (I) │ 100 > 1 → ADD       │  1990  │
│   5   │      I        │      1    │     5 (V) │ 1 < 5 → SUB         │  1989  │
│   6   │      V        │      5    │   (none)  │ No next → ADD       │  1994  │
└───────┴───────────────┴───────────┴───────────┴─────────────────────┴────────┘

Output: 1994
```

### Visual Representation

```
        MCMXCIV = 1994

        M    CM    XC    IV
        │     │     │     │
        │     │     │     └─→ 5-1 = 4
        │     │     └───────→ 100-10 = 90
        │     └─────────────→ 1000-100 = 900
        └───────────────────→ 1000

        1000 + 900 + 90 + 4 = 1994
```

### Key Insight

The algorithm uses a simple rule: **if the current value is less than the next value, subtract it; otherwise, add it.**

This works because subtraction notation in Roman numerals always involves exactly one smaller numeral immediately before a larger one.

### Examples

| Input     | Output | Explanation                          |
| --------- | ------ | ------------------------------------ |
| III       | 3      | 1 + 1 + 1                            |
| IV        | 4      | 5 - 1                                |
| IX        | 9      | 10 - 1                               |
| LVIII     | 58     | 50 + 5 + 1 + 1 + 1                   |
| MCMXCIV   | 1994   | 1000 + (1000-100) + (100-10) + (5-1) |
| MMMCMXCIX | 3999   | Maximum standard Roman numeral       |
| MMXXVI    | 2026   | 1000 + 1000 + 10 + 10 + 5 + 1        |

### Edge Cases

| Case                  | Input     | Output | Notes                                         |
| --------------------- | --------- | ------ | --------------------------------------------- |
| Single character      | I         | 1      | Minimum valid input                           |
| Maximum standard      | MMMCMXCIX | 3999   | Largest representable with M, C, X, I         |
| All same digits       | III       | 3      | Simple addition                               |
| All subtraction pairs | MCMXCIV   | 1994   | Multiple subtraction patterns                 |
| Null input            | null      | Error  | IllegalArgumentException                      |
| Empty string          | ""        | Error  | IllegalArgumentException                      |
| Blank string          | " "       | Error  | IllegalArgumentException                      |
| Invalid characters    | "ABC"     | Error  | InvalidRomanNumeralException                  |
| Lowercase letters     | "iii"     | Error  | InvalidRomanNumeralException (case-sensitive) |
| Invalid subtraction   | "IC"      | Error  | InvalidRomanNumeralException (I→C invalid)    |
| Too many repetitions  | "IIII"    | Error  | InvalidRomanNumeralException (max 3)          |
| Invalid repetition    | "VV"      | Error  | InvalidRomanNumeralException (V can't repeat) |

### Complexity

- **Time:** O(n) where n is the length of the input string — single pass
- **Space:** O(1) — constant extra space (only integer variables)

---

## 11b. Integer to Roman Numeral

**File:** `RomanNumberProcessor.java`

### Problem

Convert an integer (1-3999) to its Roman numeral representation.

### Algorithm: Greedy Approach

Iterate through Roman numeral values from largest to smallest. For each value, repeatedly subtract it from the number and append the corresponding symbol until the number is less than that value.

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: number (integer)                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│       Validate: Is number between 1 and 3999?               │
│       NO → throw IllegalArgumentException                   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│       Initialize: result = "", remaining = number           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│       FOR each (symbol, value) in descending order:         │
│       M(1000), CM(900), D(500), CD(400), C(100), XC(90),    │
│       L(50), XL(40), X(10), IX(9), V(5), IV(4), I(1)        │
│       ┌─────────────────────────────────────────────────┐   │
│       │  WHILE remaining >= value:                      │   │
│       │      result += symbol                           │   │
│       │      remaining -= value                         │   │
│       └─────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: result                           │
└─────────────────────────────────────────────────────────────┘
```

### Step-by-Step Example

**Converting 94 to Roman numerals:**

```
Start: num = 94, result = ""

┌────────┬───────┬─────────────┬────────────────┬────────────┐
│ Symbol │ Value │ num >= val? │     Action     │   Result   │
├────────┼───────┼─────────────┼────────────────┼────────────┤
│   M    │ 1000  │  94 < 1000  │     skip       │     ""     │
│   CM   │  900  │  94 < 900   │     skip       │     ""     │
│   D    │  500  │  94 < 500   │     skip       │     ""     │
│   CD   │  400  │  94 < 400   │     skip       │     ""     │
│   C    │  100  │  94 < 100   │     skip       │     ""     │
│   XC   │   90  │  94 >= 90 ✓ │ append, 94-90  │    "XC"    │
│   L    │   50  │   4 < 50    │     skip       │    "XC"    │
│   XL   │   40  │   4 < 40    │     skip       │    "XC"    │
│   X    │   10  │   4 < 10    │     skip       │    "XC"    │
│   IX   │    9  │   4 < 9     │     skip       │    "XC"    │
│   V    │    5  │   4 < 5     │     skip       │    "XC"    │
│   IV   │    4  │   4 >= 4 ✓  │ append, 4-4    │   "XCIV"   │
└────────┴───────┴─────────────┴────────────────┴────────────┘

num = 0, conversion complete
Output: "XCIV"
```

**Converting 58 to Roman numerals:**

```
Start: num = 58, result = ""

L(50):  58 >= 50 → append "L", num = 8
V(5):    8 >= 5  → append "V", num = 3
I(1):    3 >= 1  → append "I", num = 2
I(1):    2 >= 1  → append "I", num = 1
I(1):    1 >= 1  → append "I", num = 0

Output: "LVIII"
```

### Examples

| Input | Output    | Explanation                          |
| ----- | --------- | ------------------------------------ |
| 3     | III       | 1 + 1 + 1                            |
| 4     | IV        | 5 - 1 (subtraction notation)         |
| 9     | IX        | 10 - 1 (subtraction notation)        |
| 58    | LVIII     | 50 + 5 + 1 + 1 + 1                   |
| 94    | XCIV      | (100-10) + (5-1)                     |
| 1994  | MCMXCIV   | 1000 + (1000-100) + (100-10) + (5-1) |
| 2026  | MMXXVI    | 1000 + 1000 + 10 + 10 + 5 + 1        |
| 3999  | MMMCMXCIX | Maximum standard Roman numeral       |

### Why the Limit of 3999?

Standard Roman numerals have a maximum of **3999** because:

1. **M (1000)** is the largest symbol
2. The rule "no symbol repeats more than 3 times" limits us to **MMM = 3000**
3. The maximum is therefore: MMM + CM + XC + IX = 3000 + 900 + 90 + 9 = **3999**

Historically, Romans used additional notations for larger numbers:

- **Vinculum (overline):** V̄ = 5,000, X̅ = 10,000
- **Apostrophus:** Special curved symbols for 5,000, 10,000, etc.

### Edge Cases

| Case        | Input | Output    | Notes                    |
| ----------- | ----- | --------- | ------------------------ |
| Minimum     | 1     | I         | Smallest valid input     |
| Maximum     | 3999  | MMMCMXCIX | Largest standard numeral |
| Zero        | 0     | Error     | IllegalArgumentException |
| Negative    | -1    | Error     | IllegalArgumentException |
| Too large   | 4000  | Error     | IllegalArgumentException |
| Recent year | 2026  | MMXXVI    | 1000+1000+10+10+5+1      |

### Complexity

- **Time:** O(1) — fixed number of symbols (13), bounded iterations
- **Space:** O(1) — output length is bounded (max ~15 characters)

---

## 11c. Roman Numeral Validation

**File:** `RomanNumberProcessor.java`, `InvalidRomanNumeralException.java`

### Overview

The Roman numeral processor enforces strict validation rules to ensure only properly formed Roman numerals are accepted. Invalid inputs throw an `InvalidRomanNumeralException` with detailed information about the violation.

### Validation Rules

```
┌─────────────────────────────────────────────────────────────┐
│              ROMAN NUMERAL VALIDATION RULES                 │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. VALID CHARACTERS ONLY                                   │
│     ├── Must be uppercase: I, V, X, L, C, D, M              │
│     └── Lowercase letters are INVALID                       │
│                                                             │
│  2. VALID SUBTRACTION PAIRS                                 │
│     ├── I can precede: V (IV=4), X (IX=9)                   │
│     ├── X can precede: L (XL=40), C (XC=90)                 │
│     ├── C can precede: D (CD=400), M (CM=900)               │
│     └── V, L, D CANNOT be used for subtraction              │
│                                                             │
│  3. REPETITION LIMITS                                       │
│     ├── V, L, D: Cannot repeat (max 1)                      │
│     └── I, X, C, M: Maximum 3 consecutive                   │
│                                                             │
│  4. NO DOUBLE SUBTRACTION                                   │
│     └── Cannot have multiple chars before subtraction       │
│         (e.g., IIV, XXC are INVALID)                        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### InvalidRomanNumeralException

A custom `RuntimeException` that provides detailed error information:

```java
public class InvalidRomanNumeralException extends RuntimeException {
    private final String invalidInput;      // The input that failed validation
    private final String violationReason;   // Why it's invalid
}
```

### Examples of Invalid Roman Numerals

| Input  | Violation                                       | Error Message                                          |
| ------ | ----------------------------------------------- | ------------------------------------------------------ |
| `IIII` | Too many repetitions                            | Character 'I' cannot repeat more than 3 times          |
| `VV`   | V cannot repeat                                 | Character 'V' cannot repeat                            |
| `LL`   | L cannot repeat                                 | Character 'L' cannot repeat                            |
| `DD`   | D cannot repeat                                 | Character 'D' cannot repeat                            |
| `IC`   | Invalid subtraction (I can only precede V or X) | Invalid subtraction pair 'IC' - 'I' cannot precede 'C' |
| `IM`   | Invalid subtraction (I can only precede V or X) | Invalid subtraction pair 'IM' - 'I' cannot precede 'M' |
| `XD`   | Invalid subtraction (X can only precede L or C) | Invalid subtraction pair 'XD' - 'X' cannot precede 'D' |
| `VX`   | V cannot be used for subtraction                | Invalid subtraction pair 'VX' - 'V' cannot precede 'X' |
| `IIV`  | Double subtraction (II before V)                | Cannot have multiple 'I' before subtraction            |
| `XXC`  | Double subtraction (XX before C)                | Cannot have multiple 'X' before subtraction            |
| `ABC`  | Invalid character                               | Invalid character 'A' at position 0                    |
| `iii`  | Lowercase not allowed                           | Invalid character 'i' at position 0                    |
| `X1V`  | Invalid character                               | Invalid character '1' at position 1                    |

### GraphQL Error Response

When an invalid Roman numeral is passed via the GraphQL API, a structured error is returned:

```json
{
    "errors": [
        {
            "message": "Invalid Roman numeral: Character 'I' cannot repeat more than 3 times consecutively",
            "locations": [{ "line": 1, "column": 3 }],
            "path": ["romanToInt"],
            "extensions": {
                "errorCode": "INVALID_ROMAN_NUMERAL",
                "invalidInput": "IIII",
                "violationReason": "Character 'I' cannot repeat more than 3 times consecutively",
                "classification": "BAD_REQUEST"
            }
        }
    ]
}
```

### Example API Calls with Invalid Input

**Invalid Repetition:**

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ romanToInt(roman: \"IIII\") }"}'
```

**Invalid Subtraction:**

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ romanToInt(roman: \"IC\") }"}'
```

**Invalid Character:**

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ romanToInt(roman: \"XYZ\") }"}'
```

---

## 12. Newton-Raphson Polynomial Root Finder

**Location:** `domain/calculus/NewtonRaphsonRootFinder.java`

**GraphQL Query:** `findPolynomialRoot`

### Problem

Find an approximate root of a polynomial using the Newton-Raphson method with arbitrary-precision `BigDecimal` arithmetic.

### Algorithm

The implementation evaluates both $f(x)$ and $f'(x)$ with Horner's method and iteratively applies:

$$
x_{k+1} = x_k - \frac{f(x_k)}{f'(x_k)}
$$

Iteration stops when either $|f(x_k)| < \epsilon$ or $|x_{k+1} - x_k| < \epsilon$.

### Complexity

- **Time:** O($k \times n \times d^2$), where $k$ is the iteration count, $n$ is the polynomial degree, and $d$ is the precision scale
- **Space:** O($n \times d$) for coefficients and intermediate `BigDecimal` values

### Example GraphQL Query

```graphql
query {
    findPolynomialRoot(
        coefficients: [-2.0, 0.0, 1.0]
        initialGuess: 1.0
        epsilon: 0.001
        scale: 4
    )
}
```

**Response:**

```json
{
    "data": {
        "findPolynomialRoot": 1.4142
    }
}
```

---

## 13. GraphQL API

All algorithms are exposed via a GraphQL API, allowing you to interact with them through HTTP requests or the built-in GraphiQL interface.

### Endpoints

| Endpoint    | Description             |
| ----------- | ----------------------- |
| `/graphql`  | GraphQL API endpoint    |
| `/graphiql` | Interactive GraphQL IDE |

### Available Queries

```graphql
type Query {
    # Merges two arrays and calculates the median
    calculateMedian(array1: [Int!]!, array2: [Int!]!): Float!

    # Calculates max water area (two-pointer technique)
    calculateMaxWaterArea(heights: [Int!]!): Int!

    # Counts negative numbers in a 2D grid
    countNegatives(grid: [[Int!]!]!): Int!

    # Returns letter combinations for phone digits
    getLetterCombinations(digits: String!): [String!]!

    # Merges k sorted linked lists
    mergeKSortedLists(lists: [[Int!]!]!): [Int!]!

    # Finds longest palindromic substring
    findLongestPalindrome(input: String!): String!

    # Finds length of longest unique substring
    findLongestUniqueSubstringLength(input: String!): Int!

    # Calculates min cost to rearrange fruits (-1 if impossible)
    rearrangeFruits(basket1: [Int!]!, basket2: [Int!]!): Long!

    # Cracks a numeric PIN from its MD5 hash (maxLen defaults to 5)
    crackPin(hash: String!, maxLen: Int): String

    # Converts Roman numeral to integer (e.g., "MCMXCIV" → 1994)
    romanToInt(roman: String!): Int!

    # Converts integer to Roman numeral (e.g., 1994 → "MCMXCIV")
    intToRoman(number: Int!): String!

    # Finds a polynomial root using Newton-Raphson
    findPolynomialRoot(
        coefficients: [BigDecimal!]!
        initialGuess: BigDecimal!
        epsilon: BigDecimal
        maxIterations: Int
        scale: Int
    ): BigDecimal!
}
```

### Example Queries

#### Calculate Median

```graphql
query {
    calculateMedian(array1: [1, 3], array2: [2])
}
```

**Response:**

```json
{
    "data": {
        "calculateMedian": 2.0
    }
}
```

#### Container With Most Water

```graphql
query {
    calculateMaxWaterArea(heights: [1, 8, 6, 2, 5, 4, 8, 3, 7])
}
```

**Response:**

```json
{
    "data": {
        "calculateMaxWaterArea": 49
    }
}
```

#### Letter Combinations

```graphql
query {
    getLetterCombinations(digits: "23")
}
```

**Response:**

```json
{
    "data": {
        "getLetterCombinations": [
            "ad",
            "ae",
            "af",
            "bd",
            "be",
            "bf",
            "cd",
            "ce",
            "cf"
        ]
    }
}
```

#### Longest Palindrome

```graphql
query {
    findLongestPalindrome(input: "babad")
}
```

**Response:**

```json
{
    "data": {
        "findLongestPalindrome": "bab"
    }
}
```

#### Merge K Sorted Lists

```graphql
query {
    mergeKSortedLists(lists: [[1, 4, 5], [1, 3, 4], [2, 6]])
}
```

**Response:**

```json
{
    "data": {
        "mergeKSortedLists": [1, 1, 2, 3, 4, 4, 5, 6]
    }
}
```

#### Count Negatives

```graphql
query {
    countNegatives(
        grid: [[4, 3, 2, -1], [3, 2, 1, -1], [1, 1, -1, -2], [-1, -1, -2, -3]]
    )
}
```

**Response:**

```json
{
    "data": {
        "countNegatives": 8
    }
}
```

#### Longest Unique Substring Length

```graphql
query {
    findLongestUniqueSubstringLength(input: "abcabcbb")
}
```

**Response:**

```json
{
    "data": {
        "findLongestUniqueSubstringLength": 3
    }
}
```

#### Rearrange Fruits

```graphql
query {
    rearrangeFruits(basket1: [4, 2, 2, 2], basket2: [1, 4, 1, 2])
}
```

**Response:**

```json
{
    "data": {
        "rearrangeFruits": 1
    }
}
```

#### Crack PIN

```graphql
query {
    crackPin(hash: "827ccb0eea8a706c4c34a16891f84e7b")
}
```

**Response:**

```json
{
    "data": {
        "crackPin": "12345"
    }
}
```

#### Crack PIN with Leading Zeros

```graphql
query {
    crackPin(hash: "86aa400b65433b608a9db30070ec60cd", maxLen: 5)
}
```

**Response:**

```json
{
    "data": {
        "crackPin": "00078"
    }
}
```

#### Roman Numeral to Integer

```graphql
query {
    romanToInt(roman: "MCMXCIV")
}
```

**Response:**

```json
{
    "data": {
        "romanToInt": 1994
    }
}
```

#### Roman Numeral - Recent Year

```graphql
query {
    romanToInt(roman: "MMXXVI")
}
```

**Response:**

```json
{
    "data": {
        "romanToInt": 2026
    }
}
```

#### Integer to Roman Numeral

```graphql
query {
    intToRoman(number: 1994)
}
```

**Response:**

```json
{
    "data": {
        "intToRoman": "MCMXCIV"
    }
}
```

#### Integer to Roman - Recent Year

```graphql
query {
    intToRoman(number: 2026)
}
```

**Response:**

```json
{
    "data": {
        "intToRoman": "MMXXVI"
    }
}
```

### Error Handling

The GraphQL API provides structured error responses for invalid inputs:

#### Invalid Roman Numeral (validation error)

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ romanToInt(roman: \"IIII\") }"}'
```

**Response:**

```json
{
    "errors": [
        {
            "message": "Invalid Roman numeral: Character 'I' cannot repeat more than 3 times consecutively",
            "extensions": {
                "errorCode": "INVALID_ROMAN_NUMERAL",
                "invalidInput": "IIII",
                "violationReason": "Character 'I' cannot repeat more than 3 times consecutively",
                "classification": "BAD_REQUEST"
            }
        }
    ]
}
```

#### Invalid Argument (null/blank input or out-of-range)

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ intToRoman(number: 5000) }"}'
```

**Response:**

```json
{
    "errors": [
        {
            "message": "Invalid input: Number must be between 1 and 3999",
            "extensions": {
                "errorCode": "INVALID_ARGUMENT",
                "reason": "Number must be between 1 and 3999",
                "classification": "BAD_REQUEST"
            }
        }
    ]
}
```

### Using cURL

You can also call the GraphQL API using cURL:

```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ calculateMedian(array1: [1, 3], array2: [2]) }"}'
```

---

## Running the Project

### Prerequisites

- Java 17+
- Gradle

### Build

```bash
./gradlew build
```

### Run Tests

```bash
./gradlew test
```

### Run Application

```bash
./gradlew bootRun
```

---

## License

This project is for educational purposes.
