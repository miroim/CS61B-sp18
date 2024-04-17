import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void randomTest() {
        StudentArrayDeque<Integer> s = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> a = new ArrayDequeSolution<>();

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                s.addLast(i);
                a.addLast(i);
                Integer x = s.get(s.size() - 1);
                Integer y = a.get(a.size() - 1);
                System.out.println("addLast(" + i + ")");
                assertEquals("addLast(" + i + ")", y, x);
            } else {
                s.addFirst(i);
                a.addFirst(i);
                Integer x = s.get(0);
                Integer y = a.get(0);
                System.out.println("addFirst(" + i + ")");
                assertEquals("addFirst(" + i + ")", y, x);
            }
        }
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                Integer x = s.removeLast();
                Integer y = a.removeLast();
                System.out.println("removeLast()");
                assertEquals("removeLast()", y, x);
            } else {
                Integer x = s.removeFirst();
                Integer y = a.removeFirst();
                System.out.println("removeFirst()");
                assertEquals("removeFirst()", y, x);
            }
        }
        assertTrue(s.isEmpty());

    }
}
