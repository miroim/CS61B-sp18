package byog.Core;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRectangle {

    @Test
    public void testIsOverlap() {
        Position p1 = new Position(10, 10);
        Position p2 = new Position(15, 15);
        Position p3 = new Position(20, 20);
        Position p4 = new Position(21, 21);
        Position p5 = new Position(64, 15);
        Position p6 = new Position(59, 13);

        Rectangle r1 = new Rectangle(p1, 5, 5);
        Rectangle r2 = new Rectangle(p2, 3, 3);
        Rectangle r3 = new Rectangle(p3, 7, 7);
        Rectangle r4 = new Rectangle(p4, 2, 3);
        Rectangle r5 = new Rectangle(p5, 9, 6);
        Rectangle r6 = new Rectangle(p6, 6, 9);

        assertFalse(r1.isOverlap(r2));
        assertFalse(r2.isOverlap(r1));
        assertTrue(r3.isOverlap(r4));
        assertFalse(r2.isOverlap(r3));
        assertFalse(r1.isOverlap(r4));
        assertTrue(r5.isOverlap(r6));
        assertTrue(r6.isOverlap(r5));
    }

    @Test
    public void testIsOutOfBound() {
        Rectangle r1 = new Rectangle(new Position(43, -5), 7, 6);
        Rectangle r2 = new Rectangle(new Position(43, 5), 7, 6);
        assertTrue(r1.isOutOfBound());
        assertFalse(r2.isOutOfBound());
    }

    @Test
    public void testEquals() {
        Rectangle r1 = new Rectangle(new Position(43, 5), 7, 6);
        Rectangle r2 = new Rectangle(new Position(43, 5), 7, 6);
        Rectangle r3 = new Rectangle(new Position(40, 5), 7, 6);
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(r3));
    }
}
