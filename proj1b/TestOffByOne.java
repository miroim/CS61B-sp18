import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        OffByOne obo = new OffByOne();
        assertTrue(obo.equalChars('a', 'b'));
        assertTrue(obo.equalChars('r', 'q'));
        assertFalse(obo.equalChars('a', 'e'));
        assertFalse(obo.equalChars('a', 'z'));
        assertFalse(obo.equalChars('a', 'a'));

    }

    @Test
    public void testNonChar() {
        OffByOne obo = new OffByOne();
        assertTrue(obo.equalChars('&', '%'));
        assertTrue(obo.equalChars('%', '&'));
        assertFalse(obo.equalChars('%', '?'));
    }

    @Test
    public void testUpperLower() {
        OffByOne obo = new OffByOne();
        assertTrue(obo.equalChars('A', 'B'));
        assertTrue(obo.equalChars('C', 'B'));
        assertFalse(obo.equalChars('A', 'b'));
    }
}
