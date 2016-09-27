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

	public static ArrayList<String> deadend; 
	public static ArrayList<String> wordsChecked;
	public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	public static Set<String> dict;
		
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
		
		//System.out.println(input.get(0) + input.get(1));

		// TODO methods to read in words, output ladder
		String start = input.remove(0);
		String end = input.remove(0);
		
		ArrayList<String> ladder = getWordLadderDFS(start,end);
		if(ladder != null) {
			int size = ladder.size();
			for(int i = 0; i < size; i++) {
				System.out.println(ladder.remove(0));
			}	
			//System.out.println("ladder size is " + ladder.size());
		}
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		deadend = new ArrayList<String>();
		wordsChecked = new ArrayList<String>();
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
		ArrayList<String> ladder = new ArrayList<String>();
		
		if(depthFirstSearch(start, end, ladder, true)) {
			return ladder;
		} else {
			return null;
		}
		 // replace this line later with real return
	}
	
	public static boolean depthFirstSearch(String start, String end, ArrayList<String> ladder, boolean first) {
		if(start.equals(end)) { 
			System.out.println("found the end, start is " + start + " end is " + end);
			//ladder.add(start);
			return true; 
		}
		
		wordsChecked.add(start);
		int childCount = 0;
		int i = 0;
		int j = 0;
		int diff = 0;
		int index = 0;
		
		for(i = 0; i < start.length(); i++) {
			if(start.charAt(i) != end.charAt(i)) {
				index = i;
				diff +=1;	
			}
		}
		if(diff == 1){
			if(first) {
				ladder.add(0, start);
				ladder.add(1,end);
			} else { ladder.add(0,end); }
			return true;
		}
		
		
		for(i = 0; i < start.length(); i++) {
			for(j = 0; j < alphabet.length(); j++) {
				String newWord = start;
				char[] newWordChars = start.toCharArray();
				newWordChars[i] = alphabet.charAt(j);
				newWord = String.valueOf(newWordChars);
				
				if(dict.contains(newWord.toUpperCase()) && !wordsChecked.contains(newWord)) {
					//System.out.println(newWord);
					childCount++;
					if(!deadend.contains(newWord)) {
						if(depthFirstSearch(newWord, end, ladder,false)) {
							ladder.add(0,newWord);
							return true;
						}
	
					}
				}
			}
		}
		
		if(childCount == 0) { // there are no words that can be produced from start
			deadend.add(start);
		}
		return false;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
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
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	// TODO
	// Other private static methods here
}

