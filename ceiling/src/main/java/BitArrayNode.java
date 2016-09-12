package cs4150;

import java.io.InputStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BitArrayNode {

  public BitSet tree = new BitSet(20);
  public HashMap<Integer, Integer> vals = new HashMap<>(20);

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
      rt.add(ir.readInt(), 0);
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
      byte c = read() - '0';
      do {
        ret = ret * 10 + c;
      } while ((c = read() - '0') >= '0' && c <= '9');
      
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
