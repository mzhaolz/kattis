package cs4150;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;


class Node {
	private Node left; 
	private Node right;
	private int value;
	
	// Initialize
	public Node(int value) {
		this.value = value;
	}
	
	void add(Node current) {
		if (current.value >= value) {
			if (right != null)
				right.add(current);
			else
				right = current;
		} else {
			if (left != null)
				left.add(current);
			else
				left = current;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		Node current = (Node) other;
		
		if (nullMismatch(this.left, current.left) || nullMismatch(this.right, current.right)) {
			return false;
		}
		
		if (!checkSubtree(this.left, current.left) || !checkSubtree(this.right, current.right)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		int base = value;
		if (left != null)
			base ^= left.hashCode();
		if (right != null)
			base ^= right.hashCode();
		return base;
	}
	
	boolean checkSubtree(Node firstSub, Node secondSub) {
		if (firstSub != null && secondSub != null) {
			if (!firstSub.equals(secondSub)) {
				return false;
			}
		}
		return true;
	}
	
	boolean nullMismatch(Node first, Node other) {
		return (first == null && other != null) || (first != null && other == null);
	}
	
	static Node createTree(Scanner sc, int r) {
		Node rt = new Node(sc.nextInt());
		for (int j = 1; j < r; j++) {
			rt.add(new Node(sc.nextInt()));
		}
		return rt;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int column = 0; 
		int row = 0;
		column = sc.nextInt();
		row = sc.nextInt();
		Node[] v = new Node[column];
		for (int i = 0; i < column; i++) {
			v[i] = createTree(sc, row);
		}
		
		int distinct = 0;
		for (int i = 0; i < column; i++) {
			distinct++;
			for (int j = i + 1; j < column; j++) {
				if (v[i].equals(v[j])) {
					distinct--;
					break;
				}
			}
		}

		System.out.println(distinct);
	}


	class Kattio extends PrintWriter {
	    public Kattio(InputStream i) {
	        super(new BufferedOutputStream(System.out));
	        r = new BufferedReader(new InputStreamReader(i));
	    }
	    public Kattio(InputStream i, OutputStream o) {
	        super(new BufferedOutputStream(o));
	        r = new BufferedReader(new InputStreamReader(i));
	    }

	    public boolean hasMoreTokens() {
	        return peekToken() != null;
	    }

	    public int getInt() {
	        return Integer.parseInt(nextToken());
	    }

	    public String getWord() {
	        return nextToken();
	    }



	    private BufferedReader r;
	    private String line;
	    private StringTokenizer st;
	    private String token;

	    private String peekToken() {
	        if (token == null)
	            try {
	                while (st == null || !st.hasMoreTokens()) {
	                    line = r.readLine();
	                    if (line == null) return null;
	                    st = new StringTokenizer(line);
	                }
	                token = st.nextToken();
	            } catch (IOException e) { }
	        return token;
	    }

	    private String nextToken() {
	        String ans = peekToken();
	        token = null;
	        return ans;
	    }
	}

}
