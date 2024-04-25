package byog.Core;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRectangle {
    @Test
    public void testIsPointOverlapRectangle() {
        Position p = new Position(10, 10);
        Rectangle rect = new Rectangle(p, 4, 4);

        Position left = new Position(5, 10);
        Position right = new Position(15, 10);
        Position top = new Position(10, 15);
        Position bottom = new Position(10, 5);
        Position onBoard = new Position(10, 13);
        Position inside = new Position(12, 12);

        assertFalse(Rectangle.isPointOverlapRectangle(left, rect));
        assertFalse(Rectangle.isPointOverlapRectangle(right, rect));
        assertFalse(Rectangle.isPointOverlapRectangle(top, rect));
        assertFalse(Rectangle.isPointOverlapRectangle(bottom, rect));
        assertTrue(Rectangle.isPointOverlapRectangle(onBoard, rect));
        assertTrue(Rectangle.isPointOverlapRectangle(inside, rect));
    }

    @Test
    public void testIsOverlap() {
        Position p1 = new Position(10, 10);
        Position p2 = new Position(15, 15);
        Position p3 = new Position(20, 20);
        Position p4 = new Position(21, 21);

        Rectangle r1 = new Rectangle(p1, 5, 5);
        Rectangle r2 = new Rectangle(p2, 3, 3);
        Rectangle r3 = new Rectangle(p3, 7, 7);
        Rectangle r4 = new Rectangle(p4, 2, 3);

        assertTrue(r1.isOverlap(r2));
        assertTrue(r2.isOverlap(r1));
        assertTrue(r3.isOverlap(r4));
        assertFalse(r2.isOverlap(r3));
        assertFalse(r1.isOverlap(r4));
    }
}
