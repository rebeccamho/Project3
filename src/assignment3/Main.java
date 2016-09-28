/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */

//hello
package assignment3;
import java.util.*;
import java.io.*;

public class Main {

	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		ArrayList<String> input = parse(kb);
		ArrayList<String> wordLadder = new ArrayList<String>();
		wordLadder = getWordLadderBFS(input.get(0), input.get(1));
		printLadder(wordLadder, input.get(0), input.get(1));
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> input = new ArrayList<String>();
		
		String start = keyboard.next();
		start = start.trim();
		start = start.toLowerCase();
		String end = keyboard.next();
		end = end.trim();
		end = end.toLowerCase();
		
		input.add(start);
		input.add(end);
		
		return input;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
	
    /**This method attempts to find a word ladder between two words with 
     * breadth-first search
     * @param start
     * @param end
     * @return arraylist of words in ladder if one exists, 
     * an empty arraylist otherwise
     */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		ArrayList<String> wordLadder = new ArrayList<String>();
		Set<String> dict = makeDictionary();
		Node topNode = new Node(start);
		ArrayList<Node> checkNodes = new ArrayList<Node>();
		checkNodes.add(topNode);
		while(checkNodes.size() > 0) {
			int size = checkNodes.size();
			//add children of nodes in checkNodes to checkNodes
			for(int node = 0; node < size; node++) {
				for(int index = 0; index < start.length(); index++) {
					String beginning = checkNodes.get(node).word.substring(0,index);
					String ending = checkNodes.get(node).word.substring(index + 1);
					Node child;
					for(char letter = 'a';letter <= 'z'; letter++) {
						String s = beginning + letter + ending;
						if(dict.contains(s.toUpperCase()) && !s.equals(checkNodes.get(node).word)) {
							if(checkNodes.get(node).parent == null || (!s.equals(checkNodes.get(node).parent.word))) {
								boolean duplicate = false;
								for(int i = 0; i < checkNodes.size(); i++) {
									if(checkNodes.get(i).word.equals(s)) {
										duplicate = true;
										break;
									}
								}
								if(!duplicate) {
									child = new Node(checkNodes.get(node));
									child.word = s;
									checkNodes.get(node).children.add(child);
									checkNodes.add(child);
								}
							}
						}
					}
				}
			}
			//remove already checked nodes from checkNodes
			while(size > 0) {
				checkNodes.remove(0);
				size--;
			}
			//check if new additions to checkNodes are end
			for(int check = 0; check < checkNodes.size(); check++) {
				if(checkNodes.get(check).word.equals(end)) {
					Node ladderNode = checkNodes.get(check);
					while(ladderNode.parent != null) {
						wordLadder.add(ladderNode.word);
						ladderNode = ladderNode.parent;
					}
					wordLadder.add(ladderNode.word);
					ArrayList<String> returnLadder = new ArrayList<String>();
					for(int i = wordLadder.size() - 1; i >=0; i--) {
						returnLadder.add(wordLadder.get(i));
					}
					return returnLadder;
				}
			}
		}
		return wordLadder;
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder, String start, String end) {
		int size = ladder.size();

		if(size > 0) { // a word ladder was found
			System.out.println("a " + size + "-rung word ladder exists between " + start + " and " + end + ".");
			for(int i = 0; i < size; i++) {
				// for testing purposes
				String word = ladder.remove(0);
				if(ladder.contains(word)) {
					System.out.println("BAD!!! CONTAINS DUPLICATES!");
				}
				System.out.println(word);
				
				//System.out.println(ladder.remove(0));
			}	
		} else { // no word ladder exists between start and end
			System.out.println("no word ladder can be found between " + start + " and " + end + ".");
		}
	}
	// TODO
	// Other private static methods here
}

