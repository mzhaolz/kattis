package cs4150;

import java.util.HashSet;
import java.util.Set;

public class Node {

  public Node left;
  public Node right;
  public int value;
  public int hash;

  public Node(int val) {
    value = val;
  }

  public void add(Node other) {
    add(other, 0);
  }
  
  public void add(Node other, int index) {
    if (other.value < value) {
      if (right == null) {
        right = other;
        hash ^= 1 << (32 - index);
      } else
        right.add(other, 2*index + 2);
    } else {
      if (left == null) {
        left = other;
        hash ^= 1 << (32 - index);
      } else
        left.add(other, 2*index + 1);
    }

    // update the hash of the current node,
    // in case the elses are called
    if (left != null)
      hash |= left.hash;
    if (right != null)
      hash |= right.hash;
  }

  @Override
  public boolean equals(Object other) {
    Node current = (Node) other;
    return !nullMismatch(left, current.left) &&
        !nullMismatch(right, current.right) &&
        checkSubtree(left, current.left) &&
        checkSubtree(right, current.right);
  }

  @Override
  public int hashCode() {
    return hash;
  }

  public boolean checkSubtree(Node first, Node second) {
    return first == null || second == null || first.equals(second);
  }

  public boolean nullMismatch(Node first, Node other) {
    return (first == null && other != null) ||
      (first != null && other == null);
  }

  public static Node createTree(BitArrayNode.InputReader ir, int r) {
    Node rt = new Node(ir.readInt());
    for (int j = 1; j < r; ++j) {
      rt.add(new Node(ir.readInt()));
    }
    return rt;
  }
  
  public static int countUnique(BitArrayNode.InputReader reader) {
  	int c = reader.readInt(), r = reader.readInt(); 
    Set<Node> nodes = new HashSet<>();
    for (int i = 0; i < c; ++i)
      nodes.add(createTree(reader, r));
    return nodes.size();
  }

  public static void main(String[] args) {
    System.out.println(countUnique(new BitArrayNode.InputReader(System.in)));
  }
}
