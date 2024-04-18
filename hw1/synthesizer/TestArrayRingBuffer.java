package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
        assertEquals(0, arb.peek());
        assertFalse(arb.isEmpty());
        assertTrue(arb.isFull());

        for (int i = 0; i < 10; i += 1) {
            assertEquals(i, arb.dequeue());
        }
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());

    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(10);
        arb.enqueue(11);
        int n = 2;
        for (int num : arb) {
            assertEquals(n, num);
            n += 1;
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
