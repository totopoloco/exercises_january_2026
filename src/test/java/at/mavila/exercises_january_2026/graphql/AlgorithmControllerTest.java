package at.mavila.exercises_january_2026.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@DisplayName("GraphQL Algorithm Controller Tests")
class AlgorithmControllerTest {

  @Autowired
  private ExecutionGraphQlService graphQlService;

  private GraphQlTester graphQlTester;

  @BeforeEach
  void setUp() {
    graphQlTester = ExecutionGraphQlServiceTester.create(graphQlService);
  }

  @Test
  @DisplayName("Should calculate median via GraphQL")
  void shouldCalculateMedian() {
    graphQlTester.document("""
        query {
            calculateMedian(array1: [1, 3], array2: [2])
        }
        """)
        .execute()
        .path("calculateMedian")
        .entity(Double.class)
        .isEqualTo(2.0);
  }

  @Test
  @DisplayName("Should calculate max water area via GraphQL")
  void shouldCalculateMaxWaterArea() {
    graphQlTester.document("""
        query {
            calculateMaxWaterArea(heights: [1, 8, 6, 2, 5, 4, 8, 3, 7])
        }
        """)
        .execute()
        .path("calculateMaxWaterArea")
        .entity(Integer.class)
        .isEqualTo(49);
  }

  @Test
  @DisplayName("Should count negatives via GraphQL")
  void shouldCountNegatives() {
    graphQlTester.document("""
        query {
            countNegatives(grid: [[4, 3, 2, -1], [3, 2, 1, -1], [1, 1, -1, -2], [-1, -1, -2, -3]])
        }
        """)
        .execute()
        .path("countNegatives")
        .entity(Integer.class)
        .isEqualTo(8);
  }

  @Test
  @DisplayName("Should get letter combinations via GraphQL")
  void shouldGetLetterCombinations() {
    graphQlTester.document("""
        query {
            getLetterCombinations(digits: "23")
        }
        """)
        .execute()
        .path("getLetterCombinations")
        .entityList(String.class)
        .hasSize(9);
  }

  @Test
  @DisplayName("Should merge k sorted lists via GraphQL")
  void shouldMergeKSortedLists() {
    graphQlTester.document("""
        query {
            mergeKSortedLists(lists: [[1, 4, 5], [1, 3, 4], [2, 6]])
        }
        """)
        .execute()
        .path("mergeKSortedLists")
        .entityList(Integer.class)
        .containsExactly(1, 1, 2, 3, 4, 4, 5, 6);
  }

  @Test
  @DisplayName("Should find longest palindrome via GraphQL")
  void shouldFindLongestPalindrome() {
    graphQlTester.document("""
        query {
            findLongestPalindrome(input: "cbbd")
        }
        """)
        .execute()
        .path("findLongestPalindrome")
        .entity(String.class)
        .isEqualTo("bb");
  }

  @Test
  @DisplayName("Should find longest unique substring length via GraphQL")
  void shouldFindLongestUniqueSubstringLength() {
    graphQlTester.document("""
        query {
            findLongestUniqueSubstringLength(input: "abcabcbb")
        }
        """)
        .execute()
        .path("findLongestUniqueSubstringLength")
        .entity(Integer.class)
        .isEqualTo(3);
  }

  @Test
  @DisplayName("Should rearrange fruits via GraphQL")
  void shouldRearrangeFruits() {
    graphQlTester.document("""
        query {
            rearrangeFruits(basket1: [4, 2, 2, 2], basket2: [1, 4, 1, 2])
        }
        """)
        .execute()
        .path("rearrangeFruits")
        .entity(Long.class)
        .isEqualTo(1L);
  }
}
