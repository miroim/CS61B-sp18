public class LinkedListDeque<T> {
    private static class IntNode<T> {
        public T item;
        public IntNode<T> prev;
        public IntNode<T> next;

        public IntNode () {
            prev = this;
            next = this;
        }

        public IntNode (T i, IntNode<T> p, IntNode<T> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size;
    private IntNode<T> sentinel;

    // create an empty deque
    public LinkedListDeque() {
        size = 0;
        sentinel = new IntNode<> ();
    };

    public void addFirst(T item) {
        size += 1;
        IntNode<T> ptr = sentinel.next;
        sentinel.next = new IntNode<> (item, ptr.prev, sentinel.next);
        ptr.prev = sentinel.next;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        IntNode<T> ptr = sentinel.next;
        sentinel.next = ptr.next;
        ptr.next.prev = ptr.prev;
        return ptr.item;
    }

    public void addLast(T item) {
        size += 1;
        IntNode<T> ptr = sentinel.prev;
        sentinel.prev = new IntNode<> (item, sentinel.prev, ptr.next);
        ptr.next = sentinel.prev;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        size -= 1;
        IntNode<T> ptr = sentinel.prev;
        sentinel.prev = ptr.prev;
        ptr.prev.next = ptr.next;
        return ptr.item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int n = 0;
        IntNode<T> ptr = sentinel.next;
        while (n < size) {
            System.out.print(ptr.item + " ");
            n++;
            ptr = ptr.next;
        }
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        }

        IntNode<T> ptr = sentinel.next;
        int n = 0;
        while (n != index) {
            ptr = ptr.next;
            n++;
        }
        return ptr.item;
    }
    private T getRecursive(IntNode<T> p, int index) {
        IntNode<T> ptr = p.next;
        while (index == 0) {
            return p.item;
        }
        return getRecursive(ptr, index - 1);
    }

    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        return this.getRecursive(sentinel.next, index);
    }

}
