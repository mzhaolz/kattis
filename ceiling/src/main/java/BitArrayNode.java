package cs4150;

import static java.lang.Math.floorMod;

import java.io.InputStream;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BitArrayNode {

  public BitSet tree = new BitSet(20);
  //public HashMap<Integer, Integer> vals = new HashMap<>(20);
  public SparseArray vals = new SparseArray();

  public BitArrayNode(int val) {
    vals.put(0, val);
  }

  public void add(int newVal) {
    int rootIdx = 0;
    do {
      boolean left = newVal < vals.get(rootIdx);
      rootIdx <<= 1;
      rootIdx = left ? rootIdx + 1 : rootIdx + 2;
    } while (tree.get(rootIdx));
    
    vals.put(rootIdx, newVal);
    if (!tree.get(rootIdx))
      tree.set(rootIdx);
  }
  
  @Override
  public boolean equals(Object other) {
    return tree.equals(((BitArrayNode) other).tree);
  }

  @Override
  public int hashCode() {
    return tree.hashCode();
  }

  public static BitArrayNode createTree(InputReader ir, int r) {
    BitArrayNode rt = new BitArrayNode(ir.readInt());
    for (int j = 1; j < r; ++j) {
      rt.add(ir.readInt());
    }
    return rt;
  }
  
  public static int countUnique(InputReader reader) {
  	int c = reader.readInt(), r = reader.readInt(); 
    Set<BitArrayNode> nodes = new HashSet<>();
    for (int i = 0; i < c; ++i) {
      nodes.add(createTree(reader, r));
    }
    return nodes.size();
  }

  public static void main(String[] args) {
    System.out.println(countUnique(new InputReader(System.in)));
  }

  public static class SparseArray {

    private static final int START = 13;
    private int[] keys = new int[START];
    private int[] vals = new int[START];
    private int size = 0;

    public SparseArray() {
      Arrays.fill(keys, -1);
    }


    public int get(int key) {
      int i = 0;
      int len = keys.length;
      int idx = floorMod(key, keys.length);
      while (keys[idx] != idx) {
        idx += (i << 1) + 1;
        idx = floorMod(idx, len);
        i++;
      }
      return vals[idx];
    }

    public void put(int key, int val) {
      put(keys, vals, key, val);
    }

    public void put(int[] keys, int[] vals, int key, int val) {
      if (size == (keys.length - 1)/2)
        resize();

      int i = 0;
      int len = keys.length;
      int idx = floorMod(key, len);
      while (vals[floorMod(idx, len)] != 0) {
        if (vals[floorMod(idx, len)] == val) {
          return;
        }
        idx += (i << 1) + 1;
        i++;
      }

      size++;
      keys[idx] = key;
      vals[idx] = val;
    }

    private void resize() {
      int n = nextPrime();
      int[] newKeys = new int[n], newVals = new int[n];
      for (int i = 0; i < keys.length; ++i) {
        int key = keys[i], val = vals[i];

        put(newKeys, newVals, key, val);
      }

      keys = newKeys; vals = newVals;
    }

    private int nextPrime() {
      int curr = keys.length;
      while (!isPrime(++curr)) {
      }
      return curr;
    }

    private boolean isPrime(int n) {
      // n >= 13
      if (n%2==0 || n%3==0) return false;
      long sqrtN = (long) Math.sqrt(n) + 1;
      for (long i = 13L; i <= sqrtN; i += 6) {
        if (n%(i - 1) == 0 || n % (i + 1) == 0) return false;
      }
      return true;
    }

  }

  public static class InputReader {
  
    private InputStream stream;
    private static final int BUFFER_SIZE = 1 << 6;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
  
    public InputReader() {
      this(System.in);
    }
    
    public InputReader(InputStream is) {
      stream = is;
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
    }
  
    public int readInt() {
      int ret = 0;
      byte c = read();
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) != ' ' && c != '\n');
      
      return ret;
    }
  
    private void fillBuffer() {
      try {
        bytesRead = stream.read(buffer, bufferPointer = 0, BUFFER_SIZE);
      } catch (Exception e) { throw new RuntimeException(e); }
      if (bytesRead == -1)
        buffer[0] = -1;
    }
  
    private byte read() {
      if (bufferPointer == bytesRead)
        fillBuffer();
      return buffer[bufferPointer++];
    }
  }

}
