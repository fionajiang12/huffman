import java.util.Map;
import java.util.HashMap;

/**
 * Implements construction, encoding, and decoding logic of the Huffman coding algorithm. Characters
 * not in the given seed or alphabet should not be compressible, and attempts to use those
 * characters should result in the throwing of an {@link IllegalArgumentException} if used in {@link
 * #compress(String)}.
 */
public class Huffman {

    Node root;
    HashMap<String, String> encodingMap;
    int inputNum;
    int outputNum;
    int sum;
    double expected;

    /**
     * Constructs a {@code Huffman} instance from a seed string, from which to deduce the alphabet
     * and corresponding frequencies.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param seed the String from which to build the encoding
     * @throws IllegalArgumentException seed is null, seed is empty, or resulting alphabet only has
     *                                  1 character
     */
    public Huffman(String seed) {
        if (seed == null) {
            throw new IllegalArgumentException("seed is null");
        }
        if (seed.equals("")) {
            throw new IllegalArgumentException("seed is empty");
        }

        BinaryMinHeap<Integer, Node> minHeap = new BinaryMinHeapImpl<>();
        HashMap<String, Integer> freqList = new HashMap<>();
        for (int i = 0; i < seed.length(); i++) {
            String s = Character.toString(seed.charAt(i));
            if (freqList.containsKey(s)) {
                int freq = freqList.get(s);
                freqList.put(s, freq + 1);
            } else {
                freqList.put(s, 1);
            }
        }
        for (String s : freqList.keySet()) {
            minHeap.add(freqList.get(s), new Node(s, freqList.get(s)));
        }

        if (freqList.size() == 1) {
            throw new IllegalArgumentException("resulting alphabet only has 1 character");
        }
        while (minHeap.size() != 1) {
            BinaryMinHeap.Entry<Integer, Node> min1 = minHeap.extractMin();
            Node left = new Node(min1.value.s, min1.key);
            left.left = min1.value.left;
            left.right = min1.value.right;
            BinaryMinHeap.Entry<Integer, Node> min2 = minHeap.extractMin();
            Node right = new Node(min2.value.s, min2.key);
            right.left = min2.value.left;
            right.right = min2.value.right;
            Node combine = new Node(null, min1.key + min2.key);
            combine.left = left;
            combine.right = right;
            minHeap.add(combine.freq, combine);
        }
        this.root = minHeap.peek().value;
        this.inputNum = 0;
        this.outputNum = 0;
        this.encodingMap = new HashMap<>();
        for (int freq: freqList.values()) {
            this.sum += freq;
        }
        createEncodingMap(encodingMap, root, "");
        for (String letter: encodingMap.keySet()) {
            this.expected += freqList.get(letter) * (double) encodingMap.get(letter).length();
        }
    }

    /**
     * Constructs a {@code Huffman} instance from a frequency map of the input alphabet.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param alphabet a frequency map for characters in the alphabet
     * @throws IllegalArgumentException if the alphabet is null, empty, has fewer than 2 characters,
     *                                  or has any non-positive frequencies
     */
    public Huffman(Map<Character, Integer> alphabet) {
        if (alphabet == null) {
            throw new IllegalArgumentException("alphabet is null");
        }
        if (alphabet.size() == 0) {
            throw new IllegalArgumentException("alphabet is empty");
        }
        if (alphabet.keySet().size() < 2) {
            throw new IllegalArgumentException("alphabet has fewer than 2 characters");
        }

        BinaryMinHeap<Integer, Node> minHeap = new BinaryMinHeapImpl<>();
        HashMap<String, Integer> freqList = new HashMap<>();
        for (Character c: alphabet.keySet()) {
            String s = Character.toString(c);
            int freq = alphabet.get(c);
            if (freq <= 0) {
                throw new IllegalArgumentException("non-positive frequency");
            }
            freqList.put(s, freq);
            minHeap.add(freq, new Node(s, freq));
        }
        if (minHeap.size() == 1) {
            throw new IllegalArgumentException("resulting alphabet only has 1 character");
        }
        while (minHeap.size() != 1) {
            BinaryMinHeap.Entry<Integer, Node> min1 = minHeap.extractMin();
            Node left = new Node(min1.value.s, min1.key);
            left.left = min1.value.left;
            left.right = min1.value.right;
            BinaryMinHeap.Entry<Integer, Node> min2 = minHeap.extractMin();
            Node right = new Node(min2.value.s, min2.key);
            right.left = min2.value.left;
            right.right = min2.value.right;
            Node combine = new Node(null, min1.key + min2.key);
            combine.left = left;
            combine.right = right;
            minHeap.add(combine.freq, combine);
        }
        this.root = minHeap.peek().value;
        this.inputNum = 0;
        this.outputNum = 0;
        this.encodingMap = new HashMap<>();
        for (int freq: freqList.values()) {
            this.sum += freq;
        }
        createEncodingMap(encodingMap, root, "");
        for (String letter: encodingMap.keySet()) {
            this.expected += freqList.get(letter) * (double) encodingMap.get(letter).length();
        }
    }

    public void createEncodingMap(HashMap<String, String> map, Node node, String s) {
        if (node.s == null) {
            createEncodingMap(map, node.left, s + "0");
            createEncodingMap(map, node.right, s + "1");
        } else {
            map.put(node.s, s);
        }
    }

    /**
     * Compresses the input string.
     *
     * @param input the string to compress, can be the empty string
     * @return a string of ones and zeroes, representing the binary encoding of the inputted String.
     * @throws IllegalArgumentException if the input is null or if the input contains characters
     *                                  that are not compressible
     */
    public String compress(String input) {
        if (input == null) {
            throw new IllegalArgumentException("null input");
        }
        StringBuilder encode = new StringBuilder();
        if (input.equals("")) {
            return "";
        }
        inputNum += input.length();
        for (int i = 0; i < input.length(); i++) {
            String s = Character.toString(input.charAt(i));
            if (!this.encodingMap.containsKey(s)) {
                throw new IllegalArgumentException("Not compressible characters");
            }
            String code = encodingMap.get(s);
            encode.append(code);

        }
        this.outputNum += encode.length();
        return encode.toString();
    }

    /**
     * Decompresses the input string.
     *
     * @param input the String of binary digits to decompress, given that it was generated by a
     *              matching instance of the same compression strategy
     * @return the decoded version of the compressed input string
     * @throws IllegalArgumentException if the input is null, or if the input contains characters
     *                                  that are NOT 0 or 1, or input contains a sequence of bits
     *                                  that is not decodable
     */
    public String decompress(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }
        StringBuilder decode = new StringBuilder();
        if (input.equals("")) {
            return "";
        }
        Node curr = root;
        for (int i = 0; i < input.length(); i++) {
            String s = Character.toString(input.charAt(i));
            if (!s.equals("0") && !s.equals("1")) {
                throw new IllegalArgumentException("contains characters not 0 or 1");
            }
            if (curr.s == null) {
                if (s.equals("0")) {
                    if (curr.left != null) {
                        curr = curr.left;
                    } else {
                        throw new IllegalArgumentException("input contains sequence " +
                                "that is not decodable");
                    }
                } else {
                    if (curr.right != null) {
                        curr = curr.right;
                    } else {
                        throw new IllegalArgumentException("input contains sequence " +
                                "that is not decodable");
                    }
                }
            } else {
                decode.append(curr.s);
                curr = root;
                i--;
            }
        }
        if (curr.s == null) {
            throw new IllegalArgumentException("input contains sequence " +
                    "that is not decodable");
        } else {
            decode.append(curr.s);
        }
        return decode.toString();
    }

    /**
     * Computes the compression ratio so far. This is the length of all output strings from {@link
     * #compress(String)} divided by the length of all input strings to {@link #compress(String)}.
     * Assume that each char in the input string is a 16 bit int.
     *
     * @return the ratio of the total output length to the total input length in bits
     * @throws IllegalStateException if no calls have been made to {@link #compress(String)} before
     *                               calling this method
     */
    public double compressionRatio() {
        if (this.outputNum == 0) {
            throw new IllegalStateException("no calls to compress");
        }
        return (double) outputNum / ((double) inputNum * 16.0);
    }

    /**
     * Computes the expected encoding length of an arbitrary character in the alphabet based on the
     * objective function of the compression.
     * <p>
     * The expected encoding length is simply the sum of the length of the encoding of each
     * character multiplied by the probability that character occurs.
     *
     * @return the expected encoding length of an arbitrary character in the alphabet
     */
    public double expectedEncodingLength() {
        return this.expected / this.sum;
    }

    class Node {
        String s;
        int freq;
        Node left;
        Node right;

        public Node(String s, int freq) {
            this.s = s;
            this.freq = freq;
        }
    }
}
