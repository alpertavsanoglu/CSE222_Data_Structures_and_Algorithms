import java.util.Map;
import java.util.Iterator;

public class decryptor {
	private Map<Character, Map<Character, Character>> map;
	private String key;
	private String keystream = "";
	private String plain_text = "";
	private String cipher_text;
	
	public decryptor(Map<Character, Map<Character, Character>> _map, String _key, String text) {		// Constructor for initializes
		map = _map;
		key = _key;
		cipher_text = text;
	}

	public void decrypt() {				//  for generate keystream and plain text
		// do not edit this method
		generate_keystream();
		generate_plain_text();
	}
	
	private void generate_keystream() {		// Generating keystream by repeating the decryption key
		int cipherTextLength = cipher_text.length();			// Get length of the cipher text
		char[] keyChars = key.toCharArray();
		int keyLength = keyChars.length;				// Get length of the key
		int i = 0;
		while(i < cipherTextLength){					 // Generate keystream by repeating key
			keystream = keystream + keyChars[i % keyLength];
			i++;
		}
	}
	
	private void generate_plain_text() {	// Decrypts ciphertext by using the generated keystream and the Vigenère cipher table
		// You must use map.get(x).keySet() with an iterator in this method
		char[] keystreamArray = keystream.toCharArray();
		char[] cipherArray = cipher_text.toCharArray();
		int length = keystreamArray.length;					// loop condition which is keystream length
		int i = 0;
		while(i < length){
			char key_chr = keystreamArray[i];
			char cip_chr = cipherArray[i];
			Iterator<Character> map_itr = map.get(key_chr).keySet().iterator();		//Iterator for current keystream character
			while(map_itr.hasNext()){
				char check_itr = map_itr.next();
				if(map.get(key_chr).get(check_itr) == cip_chr){		// Check for character matches the ciphertext character
					plain_text = plain_text + check_itr;
					break;						// Exits while loop so we dont have to trace more
				}
			}
			i++;
		}
	}

	public String get_keystream() {						//To get keystream used for decryption
		return keystream;
	}
	
	public String get_plain_text() {					//To get decrypted plaintext
		return plain_text;
	}
}
