package lin.louis.exercice.palindrome;

import exercice.palindrome.Palindrome;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14.
 */
public class PalindromeTest {
    private static final List<String> PALINDROMES = newArrayList("strrts", "a", "aBccBa", "");

    private static final List<String> NOT_PALINDROMES = newArrayList("az", "azerttrez", "pAap", "mlkjkl");

    private static final List<Integer> NB_PALINDROMES = newArrayList(313, 1, 489984);

    private static final List<Integer> NB_NOT_PALINDROMES = newArrayList(123, 1223, 877);

    @Test
    public void testIsPalindromeString() {
        for (String str : PALINDROMES) {
            assertThat(Palindrome.isPalindrome(str)).isTrue();
        }
        for (String str : NOT_PALINDROMES) {
            assertThat(Palindrome.isPalindrome(str)).isFalse();
        }
    }

    @Test
    public void testIsPalindromeInteger() {
        for (Integer nb : NB_PALINDROMES) {
            assertThat(Palindrome.isPalindrome(nb)).isTrue();
        }
        for (Integer nb : NB_NOT_PALINDROMES) {
            assertThat(Palindrome.isPalindrome(nb)).isFalse();
        }
    }
}
