package assignment3;

import java.util.ArrayList;

public class Node {
	ArrayList<Node> children = new ArrayList<Node>();
	Node parent;
	String word;
	
	Node(String word) {
		this.word = word;
	}
	
	Node(Node parent) {
		this.parent = parent;
	}
}
