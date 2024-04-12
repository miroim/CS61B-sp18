import java.util.Arrays;

public class ArrayDeque<Item> {
    private int size;
    private Item[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        nextFirst = 7;
        nextLast = 0;
        items = (Item[]) new Object[8];
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
        Item[] a = (Item[]) new Object[capacity];
        int index = indexFirst();
        if (nextFirst + size > items.length) {
            System.arraycopy(items, index, a, 0, size - index);
            System.arraycopy(items, 0, a, size - index, nextLast);
        } else {
            System.arraycopy(items, index, a, 0, size);
        }
        items = a;
        nextFirst = a.length - 1;
        nextLast = size;
    }

    public void addFirst(Item i) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        size += 1;
        items[nextFirst] = i;
        decreaseNextFirst();
    }

    public void addLast(Item i) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        size += 1;
        items[nextLast] = i;
        increaseNextLast();
    }

    public Item removeFirst() {
        size -= 1;
        float ratio = (float) size / items.length; // the ratio of usage
        if (ratio < 0.25 && items.length >= 16) { // the usage factor should always be at least 25%
            resize(items.length / 2);
        }
        Item item = items[indexFirst()];
        items[indexFirst()] = null;
        increaseNextFirst();
        return item;
    }

    public Item removeLast() {
        size -= 1;
        float ratio = (float) size / items.length;
        if (ratio < 0.25 && items.length >= 16) {
            resize(items.length / 2);
        }
        Item item = items[indexLast()];
        items[indexLast()] = null;
        decreaseNextLast();
        return item;
    }

    public Item get(int index) {
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

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
