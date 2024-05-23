package lab9;

import edu.princeton.cs.algs4.Queue;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author miro
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
//            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (key == null) {
            return null;
        }
        if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = putHelper(key, value, root);
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        for (K k : this) {
            set.add(k);
        }
        return set;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        return remove(root, key).value;
    }

    private Node remove(Node p, K key) {
        if (p == null || key == null) {
            return null;
//            throw new IllegalArgumentException("calls remove() with a null key");
        }
        if (key.compareTo(p.key) < 0) {
            p.left = remove(p.left, key);
        } else if (key.compareTo(p.key) > 0) {
            p.right = remove(p.right, key);
        } else {
            if (p.right == null) {
                return p.left;
            }
            if (p.left == null) {
                return p.right;
            }
            Node temp = p;
            p = min(p.right);
            p.right = removeMin(p.right);
            p.left = temp.left;
        }
        size -= 1;
        return p;
    }

    private Node removeMin(Node p) {
        if (p.left == null) {
            return p.right;
        }
        p.left = removeMin(p.left);
        return p;
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    // return true if this map is empty
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<K> iterator() {
        if (isEmpty()) {
            return new Queue<K>().iterator();
        }
        return keys(min(), max());
    }

    public Iterator<K> keys(K lo, K hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to iterator() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to iterator() is null");
        }
        Queue<K> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue.iterator();
    }

    private void keys(Node p, Queue<K> queue, K lo, K hi) {
        if (p == null) {
            return;
        }
        if (lo.compareTo(p.key) < 0) {
            keys(p.left, queue, lo, hi);
        }
        if (lo.compareTo(p.key) <= 0 && hi.compareTo(p.key) >= 0){
            queue.enqueue(p.key);
        }
        if (hi.compareTo(p.key) > 0) {
            keys(p.right, queue, lo, hi);
        }
    }

    // return the greatest key
    private Node max(Node p) {
        if (p.right == null) {
            return p;
        }
        return max(p.right);
    }

    public K max() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls max() with empty map");
        }
        return max(root).key;
    }

    // return the smallest key
    private Node min(Node p) {
        if (p.left == null) {
            return p;
        }
        return min(p.left);
    }

    public K min() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty map");
        }
        return min(root).key;
    }

}
