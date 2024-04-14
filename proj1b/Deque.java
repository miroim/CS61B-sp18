public interface Deque<T> {
    void addFirst(T item);
    void addLast(T item);
    T removeFirst();
    T removeLast();
    T get(int index);
    int size();
    default boolean isEmpty() {
        return size() == 0;
    };
    void printDeque();

    public class ArrayDeque<T> implements Deque<T> {
        private int size;
        private T[] items;
        private int nextFirst;
        private int nextLast;

        public ArrayDeque() {
            size = 0;
            nextFirst = 7;
            nextLast = 0;
            items = (T[]) new Object[8];
        }

        // decrease nextLast when addFirst
        private void decreaseNextFirst() {
            if (nextFirst == 0) {
                nextFirst = items.length - 1;
            } else {
                nextFirst -= 1;
            }
        }

        // decrease nextFirst when removeLast
        private void decreaseNextLast() {
            if (nextLast == 0) {
                nextLast = items.length - 1;
            } else {
                nextLast -= 1;
            }
        }

        // increase nextLast when addLast
        private void increaseNextLast() {
            if (nextLast == items.length - 1) {
                nextLast = 0;
            } else {
                nextLast += 1;
            }
        }

        // increase nextFirst when removeFirst
        private void increaseNextFirst() {
            if (nextFirst == items.length - 1) {
                nextFirst = 0;
            } else {
                nextFirst += 1;
            }
        }

        // get the index of first ( Items[0] )
        private int indexFirst() {
            if (nextFirst == items.length - 1) {
                return 0;
            } else {
                return nextFirst + 1;
            }
        }

        // get the index of last ( Items[size - 1] )
        private int indexLast() {
            if (nextLast == 0) {
                return items.length - 1;
            } else {
                return nextLast - 1;
            }
        }

        // resize the memory of deque
        private void resize(int capacity) {
            T[] a = (T[]) new Object[capacity];
            int index = indexFirst();
            if (index + size > items.length) {
                System.arraycopy(items, index, a, 0, items.length - index);
                System.arraycopy(items, 0, a, items.length - index, nextLast);
            } else {
                System.arraycopy(items, index, a, 0, size);
            }
            items = a;
            nextFirst = a.length - 1;
            nextLast = size;
        }

        @Override
        public void addFirst(T i) {
            if (size == items.length) {
                resize(items.length * 2);
            }
            size += 1;
            items[nextFirst] = i;
            decreaseNextFirst();
        }

        @Override
        public void addLast(T i) {
            if (size == items.length) {
                resize(items.length * 2);
            }
            size += 1;
            items[nextLast] = i;
            increaseNextLast();
        }

        @Override
        public T removeFirst() {
            if (isEmpty()) {
                return null;
            }
            T item = items[indexFirst()];
            float ratio = (float) size / items.length; // the ratio of usage
            if (ratio < 0.25 && items.length >= 16) { // the usage factor should always be at least 25%
                resize(items.length / 2);
            }
            size -= 1;
            items[indexFirst()] = null;
            increaseNextFirst();
            return item;
        }

        @Override
        public T removeLast() {
            if (isEmpty()) {
                return null;
            }
            T item = items[indexLast()];
            float ratio = (float) size / items.length;
            if (ratio < 0.25 && items.length >= 16) {
                resize(items.length / 2);
            }
            size -= 1;
            items[indexLast()] = null;
            decreaseNextLast();
            return item;
        }

        @Override
        public T get(int index) {
            if (index > size - 1) {
                return null;
            }
            index = indexFirst() + index;
            if (index > items.length - 1) {
                return items[index - items.length];
            } else {
                return items[index];
            }
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public void printDeque() {
            for (int i = 0; i < size; i++) {
                System.out.print(get(i) + " ");
            }
        }
    }

}
