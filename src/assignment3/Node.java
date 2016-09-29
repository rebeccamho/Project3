/* WORD LADDER Node.java
 * EE422C Project 3 submission by
 * Ben Zimmerman
 * bdz223
 * 16465
 * Rebecca Ho
 * rh29645
 * 16480
 * Slip days used: 0
 * Git URL: https://github.com/rebeccamho/Project3
 * Fall 2016
 */

package assignment3;

import java.util.ArrayList;

public class Node {
	ArrayList<Node> children = new ArrayList<Node>();
	Node parent;
	String word;
	
	/**
	 * Constructor for Node that sets its word attribute to an input String.
	 * @param word is the String to set word to.
	 */
	Node(String word) {
		this.word = word;
	}
	
	/**
	 * Constructor for Node that sets its parent attribute to an input Node.
	 * @param word is the Node to set parent to.
	 */
	Node(Node parent) {
		this.parent = parent;
	}
	

}
