import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;



/**
 * @param <V>   {@inheritDoc}
 * @param <Key> {@inheritDoc}
 */
public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {
    /**
     * {@inheritDoc}
     */

    ArrayList<Entry<Key, V>> heap;
    HashMap<V, Integer> valueIndex;
    private int nullAddCounter = 0;

    public BinaryMinHeapImpl() {
        this.heap = new ArrayList<>();
        Entry<Key, V> first = null;
        this.heap.add(first);
        this.valueIndex = new HashMap<>();
        this.valueIndex.put(null, 0);
    }

    @Override
    public int size() {
        return heap.size() - 1;
    }

    @Override
    public boolean isEmpty() {
        return (heap == null || heap.size() == 1);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return valueIndex.containsKey(value);
    }



    private void minHeapify(ArrayList<Entry<Key, V>> heap,
                            HashMap<V, Integer> valueIndex, int index) {
        int l = 2 * index;
        int r = (2 * index) + 1;
        int smallest;
        if (l < heap.size() && l > 0 &&
                (heap.get(l).key.compareTo(heap.get(index).key) < 0)) {
            smallest = l;
        } else {
            smallest = index;
        }
        if (r < heap.size() && r > 0 &&
                (heap.get(r).key.compareTo(heap.get(smallest).key) < 0)) {
            smallest = r;
        }
        if (smallest != index) {
            valueIndex.put(heap.get(index).value, smallest);
            valueIndex.put(heap.get(smallest).value, index);
            Collections.swap(heap, smallest, index);
            minHeapify(heap, valueIndex, smallest);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        Entry<Key, V> e = new Entry<>(key, value);
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (containsValue(value)) {
            if (value == null && nullAddCounter == 0) {
                nullAddCounter++;
            } else {
                throw new IllegalArgumentException("value is already in the heap");
            }
        }
        valueIndex.put(value, heap.size());
        heap.add(e);
        int index = valueIndex.get(value);
        int parent = Math.floorDiv(index, 2);
        while (index > 1 && (heap.get(parent).key.compareTo(heap.get(index).key)) > 0) {
            valueIndex.put(heap.get(index).value, parent);
            valueIndex.put(heap.get(parent).value, index);
            Collections.swap(heap, index, parent);
            index = parent;
            parent = Math.floorDiv(index, 2);
        }


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!containsValue(value)) {
            throw new NoSuchElementException("value not in heap");
        }
        if (newKey == null) {
            throw new IllegalArgumentException("newKey is null");
        }
        int index = valueIndex.get(value);
        if ((Integer) newKey > (Integer) heap.get(index).key) {
            throw new IllegalArgumentException("newKey is greater than old key");
        }
        Entry<Key, V> newEntry = new Entry<>(newKey, value);
        heap.set(index, newEntry);
        int parent = Math.floorDiv(index, 2);
        while (index > 1 && (heap.get(parent).key.compareTo(heap.get(index).key)) > 0) {
            valueIndex.put(heap.get(index).value, parent);
            valueIndex.put(heap.get(parent).value, index);
            Collections.swap(heap, index, parent);
            index = parent;
            parent = Math.floorDiv(index, 2);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> peek() {
        if (heap.isEmpty() || heap.size() == 1) {
            throw new NoSuchElementException("heap is empty");
        }
        return heap.get(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> extractMin() {
        if (heap.isEmpty() || heap.size() == 1) {
            throw new NoSuchElementException("heap is empty");
        }
        Entry<Key, V> min = heap.get(1);
        Collections.swap(heap, 1, heap.size() - 1);
        valueIndex.put(heap.get(1).value, 1);
        valueIndex.put(heap.get(heap.size() - 1).value, heap.size() - 1);
        heap.remove(heap.size() - 1);
        valueIndex.remove(min.value);
        minHeapify(this.heap, this.valueIndex, 1);
        return min;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        HashSet<V> values = new HashSet<>();
        int nullCounter = 0;
        for (int i = 1; i < heap.size(); i++) {
            if (heap.get(i).value == null) {
                if (nullCounter != 0) {
                    values.add(heap.get(i).value);
                }
                nullCounter++;
            }
            V value = heap.get(i).value;
            values.add(value);
        }
        return values;
    }
}