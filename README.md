# Exercises January 2026

A collection of algorithm exercises implemented in Java with Spring Boot.

## Table of Contents

1. [Array Median Calculator](#1-array-median-calculator)
2. [Container With Most Water](#2-container-with-most-water)
3. [Count Negatives in Sorted Matrix](#3-count-negatives-in-sorted-matrix)
4. [Letter Combinations of a Phone Number](#4-letter-combinations-of-a-phone-number)
5. [Merge K Sorted Linked Lists](#5-merge-k-sorted-linked-lists)
6. [Longest Palindromic Substring](#6-longest-palindromic-substring)
7. [Longest Substring Without Repeating Characters](#7-longest-substring-without-repeating-characters)
8. [Rearranging Fruits](#8-rearranging-fruits)

---

## 1. Array Median Calculator

**File:** `ArrayMediaCalculator.java`

### Problem

Given two integer arrays, merge them and calculate the median of the combined sorted array.

### Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: array1, array2                     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              1. Validate inputs (non-null)                   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│         2. Convert arrays to Lists and merge                 │
│            list1.addAll(list2)                               │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              3. Sort the merged list                         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              4. Calculate median                             │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Is size odd?                                        │    │
│  │  YES: median = list[size/2]                          │    │
│  │  NO:  median = (list[size/2-1] + list[size/2]) / 2   │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: median value                      │
└─────────────────────────────────────────────────────────────┘
```

### Complexity

-   **Time:** O((n+m) log(n+m)) due to sorting
-   **Space:** O(n+m) for the merged list

### Example

```
array1 = [1, 3]
array2 = [2]
merged = [1, 2, 3]
median = 2 (odd size, middle element)
```

---

## 2. Container With Most Water

**File:** `ContainerWater.java`

### Problem

Given an array of heights representing vertical lines, find two lines that together with the x-axis form a container that holds the most water.

### Algorithm: Two-Pointer Technique

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: height[]                           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│      Initialize: left = 0, right = length-1, maxArea = 0     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
         ┌────────────────────┴────────────────────┐
         │            WHILE left < right            │
         └────────────────────┬────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │  Calculate area = min(height[left], height[right]) │
    │                   × (right - left)                 │
    └─────────────────────────┬─────────────────────────┘
                              │
    ┌─────────────────────────┴─────────────────────────┐
    │         Update maxArea = max(maxArea, area)        │
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

-   **Time:** O(n) - single pass with two pointers
-   **Space:** O(1) - constant extra space

---

## 3. Count Negatives in Sorted Matrix

**File:** `CountNegativesSortedMix.java`

### Problem

Given a 2D grid, count the number of negative numbers.

### Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│                    INPUT: grid[][]                           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│               Initialize: negatives = []                     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              FOR each row m in grid                          │
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
│                OUTPUT: negatives.size()                      │
└─────────────────────────────────────────────────────────────┘
```

### Complexity

-   **Time:** O(m × n) where m = rows, n = columns
-   **Space:** O(k) where k = number of negatives

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
│                    INPUT: digits "23"                        │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│        Map digits to letters: 2→"abc", 3→"def"               │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   Stream.reduce with initial value [""]                      │
│                                                              │
│   Accumulator function (flatMap expansion):                  │
│   ┌─────────────────────────────────────────────────────┐   │
│   │  For each combination in current list:               │   │
│   │    For each letter in current digit's mapping:       │   │
│   │      Create: combination + letter                    │   │
│   └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│     OUTPUT: ["ad","ae","af","bd","be","bf","cd","ce","cf"]   │
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

-   **Time:** O(4^n × n) where n = number of digits (worst case for 7 and 9 which have 4 letters)
-   **Space:** O(4^n × n) for storing all combinations

---

## 5. Merge K Sorted Linked Lists

**File:** `LinkedListMerger.java`

### Problem

Merge k sorted linked lists into one sorted linked list.

### Algorithm: Sequential Merge with Dummy Head

```
┌─────────────────────────────────────────────────────────────┐
│                INPUT: ListNode[] lists                       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│           Initialize: merged = null                          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│              FOR each list in lists[]                        │
│              ┌─────────────────────────────────────────┐    │
│              │  IF list is not null                    │    │
│              │    merged = mergeTwoLists(merged, list) │    │
│              └─────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: merged                            │
└─────────────────────────────────────────────────────────────┘
```

### mergeTwoLists Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│             INPUT: source1, source2                          │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│    Determine head = node with smaller value                  │
│    Initialize tail = head                                    │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
         ┌────────────────────┴────────────────────┐
         │     WHILE both lists have nodes          │
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
│     Append remaining nodes from non-empty list               │
│     tail.next = (source1 != null) ? source1 : source2        │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: head                              │
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

-   **Time:** O(N × k) where N = total nodes, k = number of lists
-   **Space:** O(1) - we reuse existing nodes

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

| Variable                        | Description                                                                                                                        |
| ------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| `palindrome_radii[]` (or `p[]`) | Array storing the radius of the palindrome centered at each index. The radius equals the palindrome length in the original string. |
| `center`                        | The index of the center of the rightmost palindrome found so far.                                                                  |
| `right_boundary` (or `right`)   | The rightmost index reached by any palindrome found so far: `right = center + palindrome_radii[center]`                            |

### Step 3: The Three Scenarios

The algorithm can be broken down into **three scenarios**:

#### Scenario 1: Expansion Only

```java
while (s[i + 1 + p[i]] == s[i - 1 - p[i]]) {
    p[i]++;
}
```

This is the base case. We take the character at the current index, compare it to its neighbors symmetrically, and expand as long as they match.

```
Example: i=1 in "^#b#a#..."

Index:  0  1  2  3  4  5
Char:   ^  #  b  #  a  #
            ↑
            i=1

Try to expand: s[0]='^' vs s[2]='b' → DON'T MATCH
Result: p[1] = 0
```

#### Scenario 2: Expansion + Update center/right_boundary

```java
// After expansion...
if (i + p[i] > right_boundary) {
    center = i;
    right_boundary = i + p[i];
}
```

When the palindrome centered at `i` extends past the current `right_boundary`, we update both `center` and `right_boundary`. This ensures future iterations can reuse this information.

```
Example: i=4 in "^#b#a#b#a#d#$"

Index:  0  1  2  3  4  5  6  7  8  9  10 11 12
Char:   ^  #  b  #  a  #  b  #  a  #  d  #  $
                    ↑
                    i=4 (center of "bab")

Expansion finds: "#b#a#b#" is a palindrome
p[4] = 3

Since i + p[i] = 4 + 3 = 7 > right_boundary:
  → center = 4
  → right_boundary = 7
```

#### Scenario 3: Mirroring + Expansion + Update (The Magic!)

```java
mirror = 2 * center - i;

if (i < right_boundary) {
    p[i] = min(right_boundary - i, p[mirror]);
}
// Then expand and update as before...
```

**This is the genius of Manacher's Algorithm!** It exploits the symmetric property of palindromes.

```
The Mirror Concept:
═══════════════════════════════════════════════════════════════

         center
            │
    ◄───────┼───────►
            │
  mirror    │    i
     │      │    │
     ▼      ▼    ▼
─────────────────────────────
     [   palindrome      ]
                     ◄───► right_boundary

Since everything within the palindrome centered at 'center' is symmetric,
the palindrome at position 'i' mirrors the one at position 'mirror'.
```

**But why use `min(right_boundary - i, p[mirror])`?**

Because the mirror's palindrome might extend **beyond** the left boundary of the current palindrome. We can only trust information **within** the known palindrome:

```
Case where mirror's palindrome exceeds the boundary:
════════════════════════════════════════════════════════════════

                        center
                           │
              ◄────────────┼────────────►
                           │
    mirror                 │           i
       │                   │           │
       ▼                   ▼           ▼
───────────────────────────────────────────────────────
  [===palindrome at mirror===]    [===?===]
  ▲                            ▲           ▲
  │                            │           │
extends beyond             left_boundary   right_boundary
left_boundary!

We can only guarantee p[i] >= min(p[mirror], right_boundary - i)
The part beyond right_boundary is UNVERIFIED — we must expand to check!
```

### Why This Achieves O(n)

Each position is visited at most twice:

1. Once when `i` reaches it during iteration
2. Once during expansion (but this expansion contributes to moving `right_boundary` forward)

Since `right_boundary` only moves forward and never backward, the total work is linear.

### Complexity

-   **Time:** O(n) - each position is visited at most twice
-   **Space:** O(n) - for the preprocessed string and p[] array

### Detailed Execution Trace

Below is the actual algorithm execution output for input `"babad"`, organized by the three scenarios:

```
╔══════════════════════════════════════════════════════════════════════════════╗
║           MANACHER'S ALGORITHM - LONGEST PALINDROMIC SUBSTRING               ║
╚══════════════════════════════════════════════════════════════════════════════╝
INPUT: "babad"
Preprocessed string: ^#b#a#b#a#d#$

═══════════════════════════════════════════════════════════════════════════════
STEP 1: PREPROCESSING
═══════════════════════════════════════════════════════════════════════════════
  Original:     "babad"
  Preprocessed: "^#b#a#b#a#d#$"
  Length:       5 → 13

═══════════════════════════════════════════════════════════════════════════════
STEP 2: INITIALIZE VARIABLES
═══════════════════════════════════════════════════════════════════════════════
  n (length)        = 13
  palindrome_radii  = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
  center            = 0
  right_boundary    = 0
  maxLen            = 0
  maxCenter         = 0

═══════════════════════════════════════════════════════════════════════════════
STEP 3: MAIN LOOP
═══════════════════════════════════════════════════════════════════════════════
```

#### Scenario 1: Expansion Only (i=1)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATION 1: i = 1 (char = '#')  →  SCENARIO 1: Expansion Only
└─────────────────────────────────────────────────────────────────────────────┘

  State: center=0, right_boundary=0
  Check: i (1) >= right_boundary (0)  →  OUTSIDE known palindrome

  ┌─ EXPANSION ─────────────────────────────────────────────────────────────┐
  │  Try:  s[0]='^' vs s[2]='b'  →  DON'T MATCH                             │
  │  Result: p[1] = 0                                                        │
  └─────────────────────────────────────────────────────────────────────────┘

  Update: i + p[i] = 1 > right_boundary = 0
    → center = 1, right_boundary = 1

  palindrome_radii = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
```

#### Scenario 2: Expansion + Update (i=2, i=4)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATION 2: i = 2 (char = 'b')  →  SCENARIO 2: Expansion + Update
└─────────────────────────────────────────────────────────────────────────────┘

  State: center=1, right_boundary=1
  Check: i (2) >= right_boundary (1)  →  OUTSIDE known palindrome

  ┌─ EXPANSION ─────────────────────────────────────────────────────────────┐
  │  Try:  s[1]='#' vs s[3]='#'  →  MATCH! p[2]++ = 1                        │
  │  Try:  s[0]='^' vs s[4]='a'  →  DON'T MATCH                              │
  │  Result: p[2] = 1                                                        │
  └─────────────────────────────────────────────────────────────────────────┘

  Update: i + p[i] = 3 > right_boundary = 1
    → center = 2, right_boundary = 3

  ★★ NEW LONGEST: maxLen = 1, palindrome = "b"

  palindrome_radii = [0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
```

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATION 4: i = 4 (char = 'a')  →  SCENARIO 2: Expansion + Update
└─────────────────────────────────────────────────────────────────────────────┘

  State: center=2, right_boundary=3
  Check: i (4) >= right_boundary (3)  →  OUTSIDE known palindrome

  ┌─ EXPANSION ─────────────────────────────────────────────────────────────┐
  │  Try:  s[3]='#' vs s[5]='#'  →  MATCH! p[4]++ = 1                        │
  │  Try:  s[2]='b' vs s[6]='b'  →  MATCH! p[4]++ = 2                        │
  │  Try:  s[1]='#' vs s[7]='#'  →  MATCH! p[4]++ = 3                        │
  │  Try:  s[0]='^' vs s[8]='a'  →  DON'T MATCH                              │
  │  Result: p[4] = 3                                                        │
  └─────────────────────────────────────────────────────────────────────────┘

  Update: i + p[i] = 7 > right_boundary = 3
    → center = 4, right_boundary = 7

  ★★ NEW LONGEST: maxLen = 3, palindrome = "bab"

  palindrome_radii = [0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0]
```

#### Scenario 3: Mirroring + Expansion + Update (i=5, i=6)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATION 5: i = 5 (char = '#')  →  SCENARIO 3: Mirroring (no expansion)
└─────────────────────────────────────────────────────────────────────────────┘

  State: center=4, right_boundary=7
  Check: i (5) < right_boundary (7)  →  INSIDE known palindrome!

  ┌─ MIRRORING ─────────────────────────────────────────────────────────────┐
  │  mirror = 2 * center - i = 2 * 4 - 5 = 3                                 │
  │                                                                          │
  │  Calculating initial p[5]:                                               │
  │    right_boundary - i = 7 - 5 = 2                                        │
  │    p[mirror] = p[3] = 0                                                  │
  │    p[5] = min(2, 0) = 0                                                  │
  │                                                                          │
  │  Visual:                                                                 │
  │    Index:  0  1  2  3  4  5  6  7  8                                     │
  │    Char:   ^  #  b  #  a  #  b  #  a                                     │
  │                     ↑  ↑  ↑                                              │
  │                  mirror center i                                         │
  │                     └──┼──┘                                              │
  │                        └── p[3]=0, so p[5] starts at 0                   │
  └─────────────────────────────────────────────────────────────────────────┘

  ┌─ EXPANSION ─────────────────────────────────────────────────────────────┐
  │  Try:  s[4]='a' vs s[6]='b'  →  DON'T MATCH                              │
  │  Result: p[5] = 0                                                        │
  └─────────────────────────────────────────────────────────────────────────┘

  No update: i + p[i] = 5 <= right_boundary = 7

  palindrome_radii = [0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0]
```

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATION 6: i = 6 (char = 'b')  →  SCENARIO 3: Mirroring + Expansion
└─────────────────────────────────────────────────────────────────────────────┘

  State: center=4, right_boundary=7
  Check: i (6) < right_boundary (7)  →  INSIDE known palindrome!

  ┌─ MIRRORING ─────────────────────────────────────────────────────────────┐
  │  mirror = 2 * center - i = 2 * 4 - 6 = 2                                 │
  │                                                                          │
  │  Calculating initial p[6]:                                               │
  │    right_boundary - i = 7 - 6 = 1                                        │
  │    p[mirror] = p[2] = 1                                                  │
  │    p[6] = min(1, 1) = 1    ← Start with radius 1!                        │
  │                                                                          │
  │  Visual:                                                                 │
  │    Index:  0  1  2  3  4  5  6  7  8                                     │
  │    Char:   ^  #  b  #  a  #  b  #  a                                     │
  │               ↑     ↑     ↑                                              │
  │            mirror center  i                                              │
  │            p[2]=1        p[6]=1 (inherited!)                             │
  └─────────────────────────────────────────────────────────────────────────┘

  ┌─ EXPANSION (starting from p[6]=1) ──────────────────────────────────────┐
  │  Try:  s[4]='a' vs s[8]='a'  →  MATCH! p[6]++ = 2                        │
  │  Try:  s[3]='#' vs s[9]='#'  →  MATCH! p[6]++ = 3                        │
  │  Try:  s[2]='b' vs s[10]='d' →  DON'T MATCH                              │
  │  Result: p[6] = 3                                                        │
  └─────────────────────────────────────────────────────────────────────────┘

  Update: i + p[i] = 9 > right_boundary = 7
    → center = 6, right_boundary = 9

  Note: maxLen stays 3 (same as "bab"), palindrome "aba" also valid

  palindrome_radii = [0, 0, 1, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0]
```

#### Final State

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ ITERATIONS 7-11: Remaining positions...
└─────────────────────────────────────────────────────────────────────────────┘

  Final palindrome_radii = [0, 0, 1, 0, 3, 0, 3, 0, 1, 0, 1, 0, 0]

  Index:  0  1  2  3  4  5  6  7  8  9  10 11 12
  Char:   ^  #  b  #  a  #  b  #  a  #  d  #  $
  p[]:    0  0  1  0  3  0  3  0  1  0  1  0  0
                    ↑     ↑
                   "bab" "aba"  (both have radius 3)

═══════════════════════════════════════════════════════════════════════════════
STEP 4: EXTRACT RESULT
═══════════════════════════════════════════════════════════════════════════════
  maxCenter = 4, maxLen = 3

  Formula: start = (maxCenter - maxLen) / 2
                 = (4 - 3) / 2 = 0

  Result: s.substring(0, 0 + 3) = "bab"

╔══════════════════════════════════════════════════════════════════════════════╗
║ RESULT: "bab"
║ Length: 3
╚══════════════════════════════════════════════════════════════════════════════╝
```

### Even-Length Palindrome Example: "cbbd"

The preprocessing trick shines when handling **even-length** palindromes. In "cbbd", the palindrome "bb" has no center character — but after preprocessing, it becomes "#b#b#" with '#' as the center!

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ Input: "cbbd"  →  Preprocessed: "^#c#b#b#d#$"
└─────────────────────────────────────────────────────────────────────────────┘

Index:  0  1  2  3  4  5  6  7  8  9  10
Char:   ^  #  c  #  b  #  b  #  d  #  $
                       ↑
                    i=5 (the '#' BETWEEN the two 'b's)

┌─ EXPANSION at i=5 ────────────────────────────────────────────────────────┐
│  Try:  s[4]='b' vs s[6]='b'  →  MATCH! p[5]++ = 1                          │
│  Try:  s[3]='#' vs s[7]='#'  →  MATCH! p[5]++ = 2                          │
│  Try:  s[2]='c' vs s[8]='d'  →  DON'T MATCH                                │
│  Result: p[5] = 2                                                          │
└────────────────────────────────────────────────────────────────────────────┘

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
│                    INPUT: string s                           │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   Initialize:                                                │
│     lastSeen[256] = {-1, -1, ...}  // last index of char    │
│     left = 0                       // window start           │
│     maxLen = 0                     // result                 │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
         ┌────────────────────┴────────────────────┐
         │       FOR right = 0 to s.length-1        │
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
│                    OUTPUT: maxLen                            │
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

-   **Time:** O(n) - single pass through the string
-   **Space:** O(1) or O(256) - fixed-size map for ASCII characters

---

## 8. Rearranging Fruits

**File:** `RearrangingFruits.java`

### Problem

Given two baskets of fruits (represented as arrays of costs), rearrange fruits between them so both baskets contain identical sets. The cost of swapping fruit `a` from basket1 with fruit `b` from basket2 is `min(a, b)`. Find the minimum total cost.

### Algorithm

```
┌─────────────────────────────────────────────────────────────┐
│                INPUT: basket1[], basket2[]                   │
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
│   STEP 2: Feasibility check                                  │
│   FOR each unique fruit v:                                   │
│     total = freq1[v] + freq2[v]                              │
│     IF total is ODD → RETURN -1 (impossible)                 │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 3: Find globalMin (cheapest fruit across both)        │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 4: Build excess lists                                 │
│   FOR each unique fruit v:                                   │
│     target = (freq1[v] + freq2[v]) / 2                       │
│     IF freq1[v] > target:                                    │
│       Add (freq1[v] - target) copies of v to excess1         │
│     IF freq2[v] > target:                                    │
│       Add (freq2[v] - target) copies of v to excess2         │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 5: Sort and pair optimally                            │
│   SORT excess1 ascending                                     │
│   SORT excess2 ascending                                     │
│                                                              │
│   Pair: smallest from excess1 with largest from excess2      │
│         (i starts at 0, j starts at end)                     │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│   STEP 6: Calculate minimum cost for each pair               │
│   FOR each pair (a from excess1, b from excess2):            │
│     directCost = min(a, b)                                   │
│     viaMinCost = 2 × globalMin                               │
│     totalCost += min(directCost, viaMinCost)                 │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    OUTPUT: totalCost                         │
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

-   **Time:** O(n log n) due to sorting
-   **Space:** O(n) for frequency maps and excess lists

---

## Running the Project

### Prerequisites

-   Java 17+
-   Gradle

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
