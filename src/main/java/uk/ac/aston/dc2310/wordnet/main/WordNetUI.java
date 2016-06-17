/**
 * 
 */
package uk.ac.aston.dc2310.wordnet.main;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.aston.dc2310.wordnet.data.POS;
import uk.ac.aston.dc2310.wordnet.data.Synset;
import uk.ac.aston.dc2310.wordnet.data.WordSense;

/**
 * A simple text-based user interface for looking up from WordNet 3.0:
 * - meanings of a word
 * - meanings of a word in a specified part-of-speech
 * - hypernyms of a word
 * 
 * @author S H S Wong
 * @version 01/06/2016
 */
public class WordNetUI {

	private static final Pattern RE_LOOK_UP_WORD_IN_POS = Pattern.compile("ws -(n|v|adj|adv) (.+)");
	private static final Pattern RE_LOOK_UP_WORD = Pattern.compile("ws (.+)");
	private static final Pattern RE_LOOK_UP_ALL_HYP = Pattern.compile("hyp -all ([0-9]+)");
	private static final Pattern RE_LOOK_UP_HYP = Pattern.compile("hyp (.+)");
	
	private WNController controller;
	private Scanner in;
	
	/**
	 * Constructor
	 * 
	 * @param controller	the controller to be used with this UI
	 */
	public WordNetUI(WNController controller) {
		
		this.controller = controller;
		
		while (true) {
			displayMenu();
			getAndProcessUserOption();
		}
	}
	
	/**
	 * Displays the header of this application and a summary of menu options.
	 */
	private void displayMenu() {
		display(header());
		display(menu());
	}
	
	/**
	 * Obtains an user option and processes it.
	 */
	private void getAndProcessUserOption() {
		in = new Scanner(System.in);
		String command = in.nextLine();
		command = command.trim();      // Trims all leading and ending whitespaces.
		if (command.equalsIgnoreCase("q")) {
			in.close();
			// exits the application
			display("Thank you for using WordNet 3.0. Goodbye!");
			System.exit(0);
		} 
		Matcher matcher_RE_LOOK_UP_WORD = RE_LOOK_UP_WORD.matcher(command);
		Matcher matcher_RE_LOOK_UP_WORD_IN_POS = RE_LOOK_UP_WORD_IN_POS.matcher(command);
		Matcher matcher_RE_LOOK_UP_HYP = RE_LOOK_UP_HYP.matcher(command);
		Matcher matcher_RE_LOOK_UP_ALL_HYP = RE_LOOK_UP_ALL_HYP.matcher(command);
		if (matcher_RE_LOOK_UP_WORD_IN_POS.matches()) {
			String category = matcher_RE_LOOK_UP_WORD_IN_POS.group(1);      // the required syntactic category
			String word = matcher_RE_LOOK_UP_WORD_IN_POS.group(2);          // the lexical unit to be looked up
			POS pos = POS.valueOf(category.toUpperCase());
			// looks up the meanings of a word in a specified part-of-speech
			Set<WordSense> meanings = controller.getMeanings(word, pos);
			if (meanings != null) {
				displayWordSenses(meanings, "Meanings for {" + word + "} as" + pos.determiner() + pos.knownAs() + ":\n");
			}
			else {
				display("No meaning for " + word + " as a " + category + " found!\n");
			}
		}
		else if (matcher_RE_LOOK_UP_WORD.matches()) {
			String word = matcher_RE_LOOK_UP_WORD.group(1);          // the lexical unit to be looked up
			// looks up all of the known meanings of the given lexical unit
			Set<WordSense> meanings = controller.getMeanings(word);
			if (meanings != null) {
				displayWordSenses(meanings, "All known meanings for {" + word + "}:\n");
			}
			else {
				display("No known meanings for {" + word + "} found!\n");
			}
		}
		else if (matcher_RE_LOOK_UP_ALL_HYP.matches()) {
			String synsetId = matcher_RE_LOOK_UP_ALL_HYP.group(1);         // the synset ID to be looked up        
			// looks up all hypernyms of a specified synset
			List<Synset> allHypernyms = controller.getAllHypernyms(synsetId);
			String info = "";
			if (allHypernyms.isEmpty()) {
				info += "No known hypernyms for Synset ID " + synsetId + " found!\n";
			}
			else {
				info = "All known hypernyms for Synset ID " + synsetId + ":\n";
				int count = 0;
				for (Synset hypernym : allHypernyms) {
					for (int i=0; i<count; i++) {
						info += '\t';
					}
					info += String.format("(%s) [%s] %s: ", 
							hypernym.id(), 
							hypernym.pos().toString(), 
							hypernym.meaning());
					Iterator<WordSense> itr = hypernym.words().iterator();
					while (itr.hasNext()) {
						WordSense ws = itr.next();
						info += String.format("%s (%d)", ws.word(), ws.senseNum());
						if (itr.hasNext()) {
							info += ", ";
						}
						else {
							info += '\n';
						}
					}
					count++;
				}
			}
			display(info);
		}
		else if (matcher_RE_LOOK_UP_HYP.matches()) {
			String word = matcher_RE_LOOK_UP_HYP.group(1);         // the lexical unit to be looked up
			// looks up all hypernyms of a word
			Set<Synset> hyperSynsets = controller.getDirectHypernyms(word);
			String info = "";
			if (hyperSynsets != null) {
				info += "All known direct hypernyms for {" + word + "}:\n";
				int count = 1;
				for (Synset s : hyperSynsets) {
					info += String.format("%2d. (%s) [%s]\t%s%n", 
							count, s.id(), s.pos().toString(), s.meaning());
					for (WordSense ws : s.words()) {
						info += String.format("\t\t\t%s (%d) %n",ws.word(), ws.senseNum());
					}
					info += "\n";
					count++;
				}
			}
			else {
				info += "No known direct hypernyms for {" + word + "} found!\n";
			}
			display(info);
		}
		else {  // Not a recognised command.
			display(unrecogniseCommandErrorMsg(command));
		}
	}

	/**
	 * Displays the specified word senses for the user to view.
	 * 
	 * @param meanings	word senses to be displayed on the screen
	 * @param header	a header explaining the nature of the info to be displayed
	 */
	private void displayWordSenses(Set<WordSense> meanings, String header) {
		String info = header;
		int count = 1;
		for (WordSense ws : meanings) {
			info += String.format("%2d. (%s) [%s-%d]\t%s", 
					count, ws.owningSynset().id(), ws.owningSynset().pos().toString(), 
					ws.senseNum(),
					ws.owningSynset().meaning());
			if (!ws.owningSynset().sampleSentences().equals("")) {
				info += "\n\t\t\t\tAS IN: " +  ws.owningSynset().sampleSentences();
			}
			info += "\n";
			count++;
		}
		display(info);
	}
	
	/**
	 * Returns an error message for an unrecognised command.
	 * 
	 * @param error	the unrecognised command
	 * @return	an error message
	 */
	private String unrecogniseCommandErrorMsg(String error) {
		return String.format("Cannot recognise the given command: %s.%n", error);
	}
	
	/**
	 * Returns a string representation of a brief title for this application as the header.
	 * @return	a header
	 */
	private String header() {
		return "WordNet 3.0 - meanings and hypernyms look up\n";
	}
	
	/**
	 * Returns a string representation of the user menu.
	 * @return	the user menu
	 */
	private String menu() {
		return "ws {word} : lookup the meaning(s) of the specified word\n" + 
			   "ws -p {pos} {word} : lookup the meaning(s) of the specified word in the given part-of-speech (ie noun, verb, adjective, adverb)\n" +
			   "hyp {word} : lookup the direct hypernyms of the specified word\n" + 
			   "hyp -all {synsetID} : lookup all hypernyms of the specified synset\n" +
			   "q : quit\n";
	}
	
	/**
	 * Displays the specified info for the user to view.
	 * @param info	info to be displayed on the screen
	 */
	private void display(String info) {
		System.out.println(info);
	}
}
