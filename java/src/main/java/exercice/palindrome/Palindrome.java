package exercice.palindrome;

/**
 * @author llin
 * @created 14/05/14.
 */
public class Palindrome {
    public static boolean isPalindrome(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }

        for (int i = 0, j = str.length() - 1; i < j; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome(int nb) {
        return isPalindrome(Integer.toString(nb));
    }
}
