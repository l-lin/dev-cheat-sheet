package lin.louis.data_structures.exercises.palindrome;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;


class PalindromeTest {
    private static final List<String> PALINDROMES = Arrays.asList("strrts", "a", "aBccBa", "");

    private static final List<String> NOT_PALINDROMES = Arrays.asList("az", "azerttrez", "pAap", "mlkjkl");

    private static final List<Integer> NB_PALINDROMES = Arrays.asList(313, 1, 489984);

    private static final List<Integer> NB_NOT_PALINDROMES = Arrays.asList(123, 1223, 877);

    @Test
    void testIsPalindromeString() {
        for (String str : PALINDROMES) {
            assertThat(Palindrome.isPalindrome(str)).isTrue();
        }
        for (String str : NOT_PALINDROMES) {
            assertThat(Palindrome.isPalindrome(str)).isFalse();
        }
    }

    @Test
    void testIsPalindromeInteger() {
        for (Integer nb : NB_PALINDROMES) {
            assertThat(Palindrome.isPalindrome(nb)).isTrue();
        }
        for (Integer nb : NB_NOT_PALINDROMES) {
            assertThat(Palindrome.isPalindrome(nb)).isFalse();
        }
    }
}
