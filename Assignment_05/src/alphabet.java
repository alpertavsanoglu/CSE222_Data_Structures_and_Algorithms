import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;

public class alphabet {
	private Set<Character> english_alphabet = new LinkedHashSet<Character>();
	private Map<Character, Map<Character, Character>> map = new HashMap<Character,  Map<Character, Character>>();
	
	public alphabet() {			// Constructor for define English alphabet and Vigenère cipher table
		// do not edit this method
		fill_english_alphabet();
		fill_map();
	}
	
	private void fill_english_alphabet() {		// Define English alphabet uppercase letters to Set
		// do not edit this method
		for(char c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
		    english_alphabet.add(c);						// Add uppercase letters to Set with loop
		}
	}
	
	private void fill_map() {						//Filling the map with using set (along with an iterator) for English shifted aphabet
		// You must use the "english_alphabet" variable in this method, to fill the "map" variable.
		// You can define 1 or 2 iterators to iterate through the set items.
		Iterator<Character> keyIterator = english_alphabet.iterator();		// Iterate letters in English alphabet
		while(keyIterator.hasNext()){
			Iterator<Character> valueIterator = english_alphabet.iterator();
			Map<Character,Character> tem_map = new HashMap<>();
			char letter = keyIterator.next();		
			int shift = letter - 65;						//to Compute shift amount based on letter position
			while(valueIterator.hasNext()){
				char originalChar = valueIterator.next();
				int originalAscii = (int) originalChar;				// Convert character to ASCII
				int shiftedAscii = (originalAscii - 65 + shift) % 26 + 65;	// Apply shift 
				char shiftedChar = (char) shiftedAscii;				// Convert back to character
				tem_map.put(originalChar, shiftedChar);				// Shifted map
			}
			map.put(letter,tem_map);						// put shifted map 
		}
}

	public void print_map() {
		// do not edit this method
		System.out.println("*** Viegenere Cipher ***\n\n");
		System.out.println("    " + english_alphabet);							// To printing English letters
		System.out.print("    ------------------------------------------------------------------------------");
		for(Character k: map.keySet()) {
			System.out.print("\n" + k + " | ");
			System.out.print(map.get(k).values());						// To printing shifted English letters
		}
		System.out.println("\n");
		
	}


/*
public Map<Character,Map<Character, Character>> get_map() {		// it should be like this i guess. but i didnt change according to the pdf file
		return map;
	}
*/
	public Map get_map() {								// Returns cipher table
		return map;
	}
}
