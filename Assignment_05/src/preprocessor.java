public class preprocessor {
	private String initial_string;
	private String preprocessed_string;
		
	public preprocessor(String str) {			// Constructor for initialize
		initial_string = str;
	}

	public void preprocess() {				//  for capitalize and clean 
		// do not edit this method
		capitalize();
		clean();
	}
	
	private void capitalize() {				// Converts the initial string to uppercase except for specific characters(e.g. Turkish lowercase 'ı')
		preprocessed_string = "";					// assign to empty string
		int length = initial_string.length();				// Determine length of the string
		int i = 0;
		while(i < length){
			char check_exp = initial_string.charAt(i);		// Skip the Turkish lowercase 'ı'
			if(check_exp == 'ı'){
				i++;						// Move to the next character
				continue;					// Skip the current iteration
			}
			else{							// Convert all other characters to uppercase normally
				preprocessed_string = preprocessed_string + Character.toUpperCase(check_exp);
				i++;
			}
		}
	}

	private void clean() {				// To Clean the capitalized string according to English uppercase letters
		String str_cl = "";						// assign to empty string
		int size_cl = preprocessed_string.length();
		char[] characters = preprocessed_string.toCharArray();
		int count_cl = 0;
		while(count_cl < size_cl){
			char letter_cl = characters[count_cl];
			str_cl = str_cl + ((letter_cl >= 65 && letter_cl <= 90) ? letter_cl : "");			 // 'A' = 65, 'Z' = 90
			count_cl++;
		}
		preprocessed_string = str_cl;
	}
	
	public String get_preprocessed_string() {				// Returning preprocessed string which is clean and capital
		return preprocessed_string;
	}
}
