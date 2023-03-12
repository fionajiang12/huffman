import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.Test;

import static org.junit.Assert.*;


public class BinaryMinHeapImplTest {

    @Test
    public void testIsEmpty() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        assertTrue(minHeap.isEmpty());
    }

    @Test
    public void testContainsValue() {
        ArrayList<BinaryMinHeap.Entry<Integer, String>> input = new ArrayList<>();
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.heap = input;
        minHeap.valueIndex = new HashMap<String, Integer>();
        minHeap.add(1, "a");
        assertTrue(minHeap.containsValue("a"));
        assertFalse(minHeap.containsValue("b"));
    }

    @Test
    public void testConstructHeapInOrder() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(1, "a");
        minHeap.add(2, "b");
        minHeap.add(3, "c");
        minHeap.add(4, "d");
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
    }

    @Test
    public void testConstructHeapNotInOrder() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(2, "b");
        minHeap.add(4, "d");
        minHeap.add(1, "a");
        minHeap.add(3, "c");
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(3, minHeap.size());
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertTrue(minHeap.isEmpty());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullKey() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(null, "b");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddExistedValue() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(2, "b");
        minHeap.add(3, "b");
    }

    @Test (expected = NoSuchElementException.class)
    public void testDecreaseValueNotExist() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(2, "b");
        minHeap.decreaseKey("a", 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDecreaseNullNewKey() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(2, "b");
        minHeap.decreaseKey("b", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDecreaseBiggerNewKey() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(2, "b");
        minHeap.decreaseKey("b", 3);
    }

    @Test (expected = NoSuchElementException.class)
    public void testPeakEmpty() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.peek();
    }

    @Test (expected = NoSuchElementException.class)
    public void testExtractEmpty() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.extractMin();
    }

    @Test
    public void testDecreaseKeyStructureUnchanged() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(2, "b");
        minHeap.add(4, "d");
        minHeap.add(1, "a");
        minHeap.add(3, "c");
        minHeap.decreaseKey("d", 4);
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
    }

    @Test
    public void testDecreaseKeyStructureChanged() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(3, "b");
        minHeap.add(2, "a");
        minHeap.add(4, "c");
        minHeap.add(5, "d");
        minHeap.decreaseKey("d", 1);
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
        assertEquals(minHeap.peek(), minHeap.extractMin());
    }

    @Test
    public void testValues() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(3, "b");
        minHeap.add(2, "a");
        minHeap.add(4, "c");
        minHeap.add(5, "d");
        Set<String> values = minHeap.values();
        Set<String> expected = new HashSet<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("d");
        assertEquals(expected, values);
        minHeap.heap.remove(0);
        values = minHeap.values();
        expected.remove("a");
        assertEquals(expected, values);
    }

    @Test
    public void testValuesAddNull() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(3, "b");
        minHeap.add(2, "a");
        minHeap.add(4, "c");
        minHeap.add(5, "d");
        minHeap.add(1, null);
        Set<String> values = minHeap.values();
        Set<String> expected = new HashSet<>();
        expected.add(null);
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("d");
        assertEquals(expected, values);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullTwice() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(3, "b");
        minHeap.add(2, "a");
        minHeap.add(1, null);
        minHeap.add(4, null);
    }

    @Test
    public void testExtractMin() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(5, "a");
        minHeap.add(9, "b");
        minHeap.add(12, "c");
        minHeap.add(13, "d");
        minHeap.add(16, "e");
        minHeap.add(45, "f");
        BinaryMinHeap.Entry<Integer, String> min = minHeap.extractMin();
        assertEquals(min.value, "a");

    }

    @Test
    public void testNonUniquePriorities() {
        BinaryMinHeapImpl<Integer, String> minHeap = new BinaryMinHeapImpl<>();
        minHeap.add(1, "a");
        minHeap.add(1, "b");
        minHeap.add(5, "c");
        minHeap.add(3, "d");
        minHeap.add(5, "e");
        BinaryMinHeap.Entry<Integer, String> min = minHeap.extractMin();
        assertEquals(min.value, "a");
        min = minHeap.extractMin();
        assertEquals(min.value, "b");
        min = minHeap.extractMin();
        assertEquals(min.value, "d");
        min = minHeap.extractMin();
        assertEquals(min.value, "c");
        min = minHeap.extractMin();
        assertEquals(min.value, "e");
    }












}
