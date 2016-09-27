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


package assignment3;
import java.util.*;
import java.io.*;


public class Main {

	public static ArrayList<String> deadendDFS; // keeps track of words that are not one letter away from any words
	public static ArrayList<String> wordsCheckedDFS; // keeps track of words already looked at in DFS
	public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	public static Set<String> dict;
		
	
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
				
		if(input != null) { // user did not choose to quit
			String start = input.remove(0);
			String end = input.remove(0);
			
			// BFS
			
			ArrayList<String> ladder = getWordLadderDFS(start,end);
			printLadder(ladder, start, end);
		}
	}
	
	public static void initialize() {
		deadendDFS = new ArrayList<String>();
		wordsCheckedDFS = new ArrayList<String>();
		dict = makeDictionary();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> input = new ArrayList<String>();
		
		String start = keyboard.next();
		
		if(start.equals("/quit")) { // user chose to quit
			input = null;
			return input;
		}
		
		start = start.trim();
		start = start.toLowerCase();
		String end = keyboard.next();
		end = end.trim();
		end = end.toLowerCase();
		
		input.add(start);
		input.add(end);
		
		return input;
	}
	
	/**
	 * This method takes in two Strings and attempts to find a word ladder between them
	 * using depth-first search.
	 * @param start is the beginning String.
	 * @param end is the end String.
	 * @return ArrayList of Strings in the word ladder between start and end. 
	 * If no word ladder is found, an empty ladder will be returned.
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		ArrayList<String> ladder = new ArrayList<String>();
		
		depthFirstSearch(start, end, ladder, true);
		return ladder;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
    
	/**
	 * This method takes a file and puts each String in the file into a HashSet.
	 * @return the Set containing each String in the file.
	 */
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
	
	/**
	 * This method prints the word ladder that exists between two Strings.
	 * @param ladder is the word ladder.
	 * @param start is the beginning String.
	 * @param end is the end String.
	 */
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
	
	/**
	 * This method finds the word ladder between two Strings using depth-first search.
	 * @param start is the beginning String.
	 * @param end is the end String.
	 * @param ladder is the word ladder to be constructed.
	 * @param first is true when this is the first call to depthFirstSearch, false otherwise.
	 * @return true if a word ladder was found, false otherwise.
	 */
	public static boolean depthFirstSearch(String start, String end, ArrayList<String> ladder, boolean first) {

		wordsCheckedDFS.add(start);
		int childCount = 0; // tracks the number of words that are one letter apart from start
		int diff = 0; // tracks the number of characters that are different between start and end
		int i = 0;
		int j = 0;
		
		for(i = 0; i < start.length(); i++) { // check how many characters start and end have in common
			if(start.charAt(i) != end.charAt(i)) {
				diff +=1;	
			}
		}
		if(diff == 1) { // if start and end are only one character apart, we've found a ladder
			if(first) { // create a two-rung ladder if this occurred on the first call to depthFirstSearch
				ladder.add(0, start);
				ladder.add(1,end);
			} else { ladder.add(0,end); } // add word to word ladder
			return true;
		}
		
		
		for(i = 0; i < start.length(); i++) { // iterate through all character spots in start
			for(j = 0; j < alphabet.length(); j++) { // iterate through all letters of the alphabet
				String newWord = start;
				char[] newWordChars = start.toCharArray();
				newWordChars[i] = alphabet.charAt(j); // change start at i to contain j'th letter in alphabet
				newWord = String.valueOf(newWordChars);
				
				if(dict.contains(newWord.toUpperCase()) && !wordsCheckedDFS.contains(newWord)) { // the word is in the dictionary and it has not been checked before
					childCount++; 
					if(!deadendDFS.contains(newWord)) { // the word has not been marked as producing no children
						if(depthFirstSearch(newWord, end, ladder, false)) { // true if a word ladder was produced
							ladder.add(0,newWord);
							if(first) {
								ladder.add(0,start);
							}
							return true;
						}
					}
				}
			}
		}
		
		if(childCount == 0) { // there are no words that can be produced from start
			deadendDFS.add(start);
		}
		return false;
	}
}

