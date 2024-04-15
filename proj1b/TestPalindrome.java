import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String s1 = "cat";
        assertFalse(palindrome.isPalindrome(s1));

        String s2 = "a";
        assertTrue(palindrome.isPalindrome(s2));

        String s3 = "noon";
        assertTrue(palindrome.isPalindrome(s3));

        String s4 = "madam";
        assertTrue(palindrome.isPalindrome(s4));

        String s5 = "";
        assertTrue(palindrome.isPalindrome(s5));
    }

    @Test
    public void testIsPalindromeOBO() {
        OffByOne obo = new OffByOne();
        String s1 = "cat";
        assertFalse(palindrome.isPalindrome(s1, obo));

        String s2 = "flake";
        assertTrue(palindrome.isPalindrome(s2, obo));
    }
}
