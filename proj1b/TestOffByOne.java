import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertFalse(offByOne.equalChars('a', 'e'));
        assertFalse(offByOne.equalChars('a', 'z'));
        assertFalse(offByOne.equalChars('a', 'a'));

    }

    @Test
    public void testNonChar() {
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertFalse(offByOne.equalChars('%', '?'));
    }

    @Test
    public void testUpperLower() {
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('C', 'B'));
        assertFalse(offByOne.equalChars('A', 'b'));
    }
}
