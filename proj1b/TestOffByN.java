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
}
