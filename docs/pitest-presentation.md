---
marp: true
theme: default
paginate: true
backgroundColor: #ffffff
---

<style>
section {
  font-family: 'Segoe UI', Arial, sans-serif;
}
section.lead h1 {
  font-size: 2.4rem;
  color: #1a1a2e;
}
section.lead p {
  color: #555;
}
h1 { color: #1a1a2e; border-bottom: 3px solid #e63946; padding-bottom: 8px; }
h2 { color: #457b9d; }
code { background: #f1faee; padding: 2px 6px; border-radius: 4px; color: #e63946; }
pre { background: #f4f4f4; color: #1d3557; border-radius: 8px; border: 1px solid #ddd; }
pre code { background: none; color: inherit; padding: 0; }
blockquote { border-left: 4px solid #e63946; background: #f1faee; padding: 10px 20px; }
table { width: 100%; border-collapse: collapse; }
th { background: #1d3557; color: white; padding: 8px 12px; }
td { border: 1px solid #ddd; padding: 8px 12px; }
tr:nth-child(even) { background: #f1faee; }
.columns { display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; }
</style>

<!-- _class: lead -->

# Mutation Testing with PIT

## Knowing _when_ your tests actually test

**Team Tech Talk · May 2026**
`./gradlew pitest`

---

# Agenda

1. **The Problem** — What code coverage doesn't tell you
2. **What is Mutation Testing?** — The core idea
3. **How PIT Works** — Under the hood
4. **Live Demo** — Our project results
5. **Reading the Report** — Surviving vs. killed mutants
6. **Practical Setup** — Gradle config walkthrough
7. **When to Use It** — Thresholds & CI integration
8. **Hands-On Exercise** — Kill a mutant together

---

# The Problem with Line Coverage

```java
public int max(int a, int b) {
    if (a > b) {        // ← covered ✓
        return a;       // ← covered ✓
    }
    return b;           // ← covered ✓
}
```

Test:

```java
@Test
void testMax() {
    assertEquals(5, max(5, 3));  // line coverage: 100%
}
```

> **100% line coverage — but we never tested `max(3, 5)`!**
> The `>` could be changed to `>=` and the test still passes.

---

# What is Mutation Testing?

The idea is simple:

1. **Introduce a small bug** (a _mutation_) into the production code
2. **Run the tests** against the mutated code
3. If a test **fails** → the mutation was **killed** ✅  
   If all tests **pass** → the mutation **survived** ❌

> A survived mutation reveals a gap in your test suite — a bug your tests would **not catch**.

---

# Mutation Operators (What PIT Changes)

| Operator                  | Original       | Mutated     |
| ------------------------- | -------------- | ----------- |
| **Conditionals Boundary** | `a > b`        | `a >= b`    |
| **Negate Conditionals**   | `a == b`       | `a != b`    |
| **Remove Conditionals**   | `if (cond)`    | `if (true)` |
| **Math**                  | `a + b`        | `a - b`     |
| **Increment**             | `i++`          | `i--`       |
| **Return Values**         | `return x`     | `return 0`  |
| **Void Method Calls**     | `list.clear()` | _(removed)_ |
| **Invert Negatives**      | `return -x`    | `return x`  |

PIT's `DEFAULTS` mutator set applies all of these.

---

# How PIT Works — The Process

```
Source Code          Test Suite
    │                    │
    ▼                    ▼
[Compile] ──────► [Run Tests] ──► Coverage Map
    │                                   │
    ▼                                   ▼
[Generate Mutants]◄────────────[Only mutate covered code]
    │
    ▼
[For each mutant: run covering tests]
    │
    ├── Test FAILS  → Mutant KILLED   ✅
    └── Tests PASS  → Mutant SURVIVED ❌
         │
         ▼
    [HTML Report]
```

**PIT is smart**: it only runs tests that _cover_ the mutated line, making it fast.

---

# Our Project Setup

**`build.gradle`** — key configuration:

```groovy
pitest {
    targetClasses  = ['at.mavila.exercises_january_2026.domain.*',
                      'at.mavila.exercises_january_2026.application.*']
    targetTests    = ['at.mavila.exercises_january_2026.domain.*',
                      'at.mavila.exercises_january_2026.application.*']
    pitestVersion         = '1.25.1'   // Java 25 support via ASM 9.9
    junit5PluginVersion   = '1.2.1'
    mutators              = ['DEFAULTS']
    outputFormats         = ['HTML', 'XML']
    threads               = 4
    mutationThreshold     = 80         // build fails below 80%
    coverageThreshold     = 80
    timestampedReports    = false
}
```

Run with: **`./gradlew pitest`**
Report at: **`build/reports/pitest/index.html`**

---

# Java 25 Support — What We Fixed

Previously Pitest didn't support Java 25. Here's what changed:

| What                  | Before                | After          |
| --------------------- | --------------------- | -------------- |
| PIT core version      | `1.17.3`              | `1.25.1`       |
| Gradle plugin         | `1.19.0-rc.2`         | `1.19.0`       |
| Bytecode target       | Forced `--release 21` | Native Java 25 |
| `sourceCompatibility` | `VERSION_21`          | Removed        |

**Root cause fixed in PIT 1.22.1 (Feb 2026):** ASM bumped to 9.9 which understands Java 25 bytecode.

> No more workarounds. We compile and mutate with Java 25 natively.

---

# LIVE DEMO — Running Pitest

```bash
./gradlew pitest
```

What you'll see:

- PIT scans classes and builds a coverage map
- Generates mutations (one per bytecode change)
- Runs each mutation against its covering tests
- Outputs an HTML report

```
> Task :pitest
PIT >> INFO : Created N mutation test units in pre scan
PIT >> INFO : Completed in Xs.
               Mutation score: XX%
               Line coverage:  XX%
```

**Report:** `build/reports/pitest/index.html`

---

# Reading the HTML Report — Overview Page

The index shows per-package stats:

| Package             | Classes | Line Cov. | Mutation Score |
| ------------------- | ------- | --------- | -------------- |
| `domain.array`      | 1       | 95%       | 88%            |
| `domain.calculus`   | 6       | 82%       | 79%            |
| `domain.collection` | 3       | 91%       | 85%            |
| `domain.number`     | 4       | 88%       | 83%            |
| `domain.string`     | 3       | 94%       | 90%            |

Click any class to drill into the **line-level mutation detail**.

---

# Reading the Report — Line Detail

Each mutated line is colour-coded:

- 🟢 **Green** — all mutations on this line were **killed** (good!)
- 🔴 **Red** — at least one mutation **survived** (needs a test!)
- ⬜ **Grey** — not covered by any test at all

Hovering over a red line shows exactly what mutation survived:

```
Line 23: Replaced integer addition with subtraction → SURVIVED
```

That tells you: _write a test that breaks when `+` becomes `-`._

---

# A Concrete Example from Our Codebase

### `OddFinder.java`

```java
public boolean isOdd(int n) {
    return n % 2 != 0;   // ← what if PIT changes != to ==?
}
```

**Surviving mutation:** `n % 2 != 0` → `n % 2 == 0`

This means our tests **never assert on an even number returning false**.

**Fix:** Add the missing test case:

```java
@Test void evenNumber_isNotOdd() {
    assertFalse(finder.isOdd(4));
}
```

After the fix: mutation **killed** ✅

---

# Understanding Mutation Score

$$\text{Mutation Score} = \frac{\text{Killed Mutants}}{\text{Total Mutants} - \text{Equivalent Mutants}} \times 100$$

**Equivalent mutants** — mutations that don't change observable behaviour (PIT filters most of these automatically).

### What's a good score?

| Score      | Interpretation                    |
| ---------- | --------------------------------- |
| < 60%      | Weak test suite, high risk        |
| 60–79%     | Acceptable for non-critical code  |
| **80–89%** | **Our threshold — good baseline** |
| 90%+       | Strong suite, diminishing returns |

---

# Mutation Testing vs. Other Metrics

| Metric              | What it measures        | Blind spot                            |
| ------------------- | ----------------------- | ------------------------------------- |
| **Line Coverage**   | Was this line executed? | Tests can execute without asserting   |
| **Branch Coverage** | Were both paths taken?  | Same blind spot — no assertion needed |
| **Mutation Score**  | Would a bug be caught?  | Equivalent mutants (rare)             |

> **Mutation score is the only metric that directly measures test effectiveness.**

Code coverage tells you where tests _ran_. Mutation score tells you where tests _work_.

---

# CI Integration

Add to your pipeline to enforce quality:

```groovy
// build.gradle — enable as part of every build:
build.dependsOn 'pitest'
```

Or run separately in CI (recommended for large projects):

```yaml
# GitHub Actions example
- name: Mutation Tests
  run: ./gradlew pitest
- name: Upload Pitest Report
  uses: actions/upload-artifact@v4
  with:
      name: pitest-report
      path: build/reports/pitest/
```

Build fails automatically if score drops below `mutationThreshold = 80`.

---

# Performance Tips

Pitest can be slow on large codebases. Strategies:

**1. Target only business logic:**

```groovy
targetClasses = ['com.example.domain.*', 'com.example.service.*']
// Exclude DTOs, config, infrastructure
```

**2. Use more threads:**

```groovy
threads = Runtime.getRuntime().availableProcessors()
```

**3. Use incremental analysis (history):**

```groovy
historyInputLocation  = file('build/pitest-history')
historyOutputLocation = file('build/pitest-history')
```

Only re-mutates classes that changed since last run.

---

# When NOT to chase 100%

Some code is legitimately hard to mutate-test:

- **Exception constructors** — rarely worth testing
- **Lombok-generated code** — PIT has a `+lombok` filter (enabled by default)
- **Framework glue code** — Spring configs, JPA entities
- **Logging statements** — filtered by `+flogcall`

Exclude noisy classes:

```groovy
excludedClasses = ['*.config.*', '*.dto.*', '*.entity.*']
```

> Focus mutation energy on **business rules**, not plumbing.

---

# Hands-On Exercise

**Goal:** Find and kill a surviving mutant in `NewtonRaphsonRootFinder`

1. Run Pitest: `./gradlew pitest`
2. Open `build/reports/pitest/index.html`
3. Navigate to `domain.calculus` → `NewtonRaphsonRootFinder`
4. Find a **red line** (surviving mutant)
5. Read the mutation description
6. Write a test that **kills** that mutant
7. Re-run `./gradlew pitest` — confirm it turns green!

**Tip:** Surviving mutants on boundary checks (`>` vs `>=`) are the easiest to kill with a targeted edge-case test.

---

# Summary

- **Code coverage lies** — 100% coverage ≠ good tests
- **Mutation testing is the gold standard** for test quality
- **PIT** is fast, practical, and integrates into Gradle in minutes
- **Our project** runs PIT 1.25.1 on Java 25 natively (no workarounds)
- **Threshold 80%** enforced — build fails below that
- **Report** at `build/reports/pitest/index.html`

> The goal isn't to kill every mutant.  
> The goal is to make every test **mean something**.

---

<!-- _class: lead -->

# Questions?

```bash
./gradlew pitest
# Report: build/reports/pitest/index.html
```

**Resources:**

- pitest.org
- github.com/hcoles/pitest
- github.com/szpak/gradle-pitest-plugin
