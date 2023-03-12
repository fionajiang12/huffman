import java.util.*;


import org.junit.Test;



import static org.junit.Assert.*;

public class HuffmanTest {

    @Test (expected = IllegalArgumentException.class)
    public void testConstructNullSeed() {
        String seed = null;
        Huffman huff = new Huffman(seed);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructEmptySeed() {
        String seed = "";
        Huffman huff = new Huffman(seed);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructOneChar() {
        String seed = "aaaa";
        Huffman huff = new Huffman(seed);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructNullAlphabet() {
        Map<Character, Integer> alphabet = null;
        Huffman huff = new Huffman(alphabet);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructEmptyAlphabet() {
        Map<Character, Integer> alphabet = new HashMap<>();
        Huffman huff = new Huffman(alphabet);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructFewerThanTwo() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('c', 1);
        Huffman huff = new Huffman(alphabet);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructNonPosFreq() {
        Map<Character, Integer> alphabet = new HashMap<>();
        char c = 'c';
        alphabet.put(c, -1);
        Huffman huff = new Huffman(alphabet);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCompressNullInput() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('c', 1);
        alphabet.put('b', 2);
        Huffman huff = new Huffman(alphabet);
        huff.compress(null);
    }


    @Test (expected = IllegalArgumentException.class)
    public void testCompressNoncompressibleInput() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('c', 1);
        alphabet.put('b', 2);
        Huffman huff = new Huffman(alphabet);
        huff.compress("a");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDecompressNullInput() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('c', 1);
        alphabet.put('b', 2);
        Huffman huff = new Huffman(alphabet);
        huff.decompress(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDecompressNoOneInput() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('c', 1);
        alphabet.put('b', 2);
        Huffman huff = new Huffman(alphabet);
        huff.decompress("02");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDecompressNondecodableInput() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('a', 4);
        alphabet.put('b', 3);
        alphabet.put('c', 2);
        alphabet.put('d', 1);
        Huffman huff = new Huffman(alphabet);
        huff.decompress("1");
    }



    @Test
    public void testEncodeDecode() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('a', 5);
        alphabet.put('b', 9);
        alphabet.put('c', 12);
        alphabet.put('d', 13);
        alphabet.put('e', 16);
        alphabet.put('f', 45);
        Huffman huff = new Huffman(alphabet);
        String encode = huff.compress("a");
        assertEquals(encode, "1100");
        String decode = huff.decompress(encode);
        assertEquals(decode, "a");
        String decode2 = huff.decompress("11011100");
        assertEquals(decode2, "ba");

    }

    @Test
    public void testConstructSeed() {
        String seed = "aaaabbbccd";
        Huffman huffman = new Huffman(seed);
        String compressed = huffman.compress("bcda");
        String decompressed = huffman.decompress(compressed);
        assertEquals("bcda", decompressed);
    }

    @Test
    public void testCompressEmpty() {
        String seed = "aaaabbbccd";
        Huffman huffman = new Huffman(seed);
        String compressed = huffman.compress("");
        String decompressed = huffman.decompress(compressed);
        assertEquals("", decompressed);
    }




    @Test (expected = IllegalStateException.class)
    public void testNotCallCompress() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('a', 5);
        alphabet.put('b', 9);
        alphabet.put('c', 12);
        alphabet.put('d', 13);
        alphabet.put('e', 16);
        alphabet.put('f', 45);
        Huffman huff = new Huffman(alphabet);
        huff.compressionRatio();
    }

    @Test
    public void testCompressionRatio() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('a', 4);
        alphabet.put('b', 3);
        alphabet.put('c', 2);
        alphabet.put('d', 1);
        Huffman huff = new Huffman(alphabet);
        huff.compress("c");
        assertEquals((3.0 / 16.0), huff.compressionRatio(), 0.0);
        huff.compress("d");
        assertEquals((6.0 / 32.0), huff.compressionRatio(), 0.0);
    }

    @Test
    public void testExpectedEncodingLength() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put('a', 5);
        alphabet.put('b', 4);
        alphabet.put('c', 3);
        alphabet.put('d', 2);
        alphabet.put('e', 1);
        Huffman huff = new Huffman(alphabet);
        assertEquals(huff.expectedEncodingLength(),2.2, 0.0);
    }

    @Test
    public void testWeirdCharacters() {
        Map<Character, Integer> alphabet = new HashMap<>();
        alphabet.put(' ', 5);
        alphabet.put('!', 9);
        alphabet.put('@', 12);
        alphabet.put('#', 13);
        alphabet.put('a', 16);
        alphabet.put('.', 45);
        Huffman huff = new Huffman(alphabet);
        String encode = huff.compress("!a @");
        String decode = huff.decompress(encode);
        assertEquals(decode, "!a @");
    }

    @Test
    public void testSentence() {
        Huffman huff = new Huffman(" abcccdfsdsjkfaknjweoijoiscdefghijklmnopqrstuvwxyz,.");
        assertEquals("this is a sentence.",
                huff.decompress(huff.compress("this is a sentence.")));
    }









}
