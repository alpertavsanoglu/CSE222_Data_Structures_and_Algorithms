import java.util.Map;

public class encryptor {
	private Map<Character, Map<Character, Character>> map;
	private String key;
	private String keystream = "";
	private String plain_text;
	private String cipher_text = "";
	
	public encryptor(Map<Character, Map<Character, Character>> _map, String _key, String text) {			// Constructor for initializes
		map = _map;
		key = _key;
		plain_text = text;
	}
	
	public void encrypt() {				//  for generate keystream and cipher text
		// do not edit this method
		generate_keystream();
		generate_cipher_text();
	}
	
	private void generate_keystream() {		// Generating keystream by repeating the decryption key
		int plainTextLength = plain_text.length();			// Get length of the plain text
		char[] keyChars = key.toCharArray();
		int keyLength = keyChars.length;				// Get length of the key
		int i = 0;
		while(i < plainTextLength){					 // Generate keystream by repeating key
			keystream = keystream + keyChars[i % keyLength];
			i++;
		}
	}
	
	private void generate_cipher_text() {			// encrypt with generated keystream and the Vigenère cipher table
		char[] plainChars = plain_text.toCharArray();
		char[] keystreamChars = keystream.toCharArray();
		int keystreamLength = keystreamChars.length;		// loop condition which is keystream length
		int i = 0;
		while(i < keystreamLength){
			char str_cipher = map.get(plainChars[i]).get(keystreamChars[i]);	// Find plaintext character using the keystream character
			cipher_text = cipher_text + str_cipher;
			i++;
		}
	}

	public String get_keystream() {						//To get keystream used for encryption
		return keystream;
	}
	
	public String get_cipher_text() {					//To get encrypted ciphertext
		return cipher_text;
	}
}
