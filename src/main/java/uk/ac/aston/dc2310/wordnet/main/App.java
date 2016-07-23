package uk.ac.aston.dc2310.wordnet.main;

import uk.ac.aston.dc2310.wordnet.reader.WordNetReader;

public class App {

	/**
	 * Application entry point.
	 * @param args command line args
	 */
	public static void main(String[] args) {
		WordNetReader wnRead = new WordNetReader();
		wnRead.readWN_S();
		wnRead.readWN_G();
		wnRead.readWN_HYP();
		//wnRead.getDictionary().getDirectHypernyms("quit");
		new WordNetUIStringOnly(wnRead.getDictionary());
	}
	
}
