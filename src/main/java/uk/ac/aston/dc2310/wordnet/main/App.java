package uk.ac.aston.dc2310.wordnet.main;

import uk.ac.aston.dc2310.wordnet.reader.WordNetReader;

public class App {

	public static void main(String[] args) {
		WordNetReader wnRead = new WordNetReader();
		wnRead.readWN_S();
	}
	
}
