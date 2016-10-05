package uk.ac.aston.dc2310.wordnet.main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple text-based user interface for looking up from WordNet 3.0: - meanings of a word -
 * meanings of a word in a specified part-of-speech - hypernyms of a word
 * 
 * <p>NOTE: This UI works with string inputs and outputs only.
 * 
 * @author S H S Wong
 * @version 24/06/2016
 */
public class WordNetUiStringOnly {

  private static final Pattern RE_LOOK_UP_WORD_IN_POS = Pattern.compile("ws -(n|v|adj|adv) (.+)");
  private static final Pattern RE_LOOK_UP_WORD = Pattern.compile("ws (.+)");
  private static final Pattern RE_LOOK_UP_ALL_HYP = Pattern.compile("hyp -all ([0-9]+)");
  private static final Pattern RE_LOOK_UP_HYP = Pattern.compile("hyp (.+)");

  private WnControllerStringOnly controller;
  private Scanner in;

  /**
   * Constructor.
   * 
   * @param controller the controller to be used with this UI
   */
  public WordNetUiStringOnly(WnControllerStringOnly controller) {

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
    command = command.trim(); // Trims all leading and ending whitespaces.
    if (command.equalsIgnoreCase("q")) {
      in.close();
      // exits the application
      display("Thank you for using WordNet 3.0. Goodbye!");
      System.exit(0);
    }
    Matcher matcherReLookUpWord = RE_LOOK_UP_WORD.matcher(command);
    Matcher matcherReLookUpWordInPos = RE_LOOK_UP_WORD_IN_POS.matcher(command);
    Matcher matcherReLookUpHyp = RE_LOOK_UP_HYP.matcher(command);
    Matcher matcherReLookUpAllHyp = RE_LOOK_UP_ALL_HYP.matcher(command);
    if (matcherReLookUpWordInPos.matches()) {
      String category = matcherReLookUpWordInPos.group(1); // the required syntactic category
      String word = matcherReLookUpWordInPos.group(2); // the lexical unit to be looked up
      // looks up the meanings of a word in a specified part-of-speech
      String results = controller.getMeanings(word, category);
      display(results);
    } else if (matcherReLookUpWord.matches()) {
      String word = matcherReLookUpWord.group(1); // the lexical unit to be looked up
      // looks up all of the known meanings of the given lexical unit
      String results = controller.getMeanings(word);
      display(results);
    } else if (matcherReLookUpAllHyp.matches()) {
      String synsetId = matcherReLookUpAllHyp.group(1); // the synset ID to be looked up
      // looks up all hypernyms of a specified synset
      String results = controller.getAllHypernyms(synsetId);
      display(results);
    } else if (matcherReLookUpHyp.matches()) {
      String word = matcherReLookUpHyp.group(1); // the lexical unit to be looked up
      // looks up all hypernyms of a word
      String results = controller.getDirectHypernyms(word);
      display(results);
    } else { // Not a recognised command.
      display(unrecogniseCommandErrorMsg(command));
    }
  }

  /**
   * Returns an error message for an unrecognised command.
   * 
   * @param error the unrecognised command
   * @return an error message
   */
  private String unrecogniseCommandErrorMsg(String error) {
    return String.format("Cannot recognise the given command: %s.%n", error);
  }

  /**
   * Returns a string representation of a brief title for this application as the header.
   * 
   * @return a header
   */
  private String header() {
    return "WordNet 3.0 - meanings and hypernyms look up\n";
  }

  /**
   * Returns a string representation of the user menu.
   * 
   * @return the user menu
   */
  private String menu() {
    return "ws {word} : lookup the meaning(s) of the specified word\n"
        + "ws -{pos} {word} : lookup the meaning(s) of the specified word in the given "
        + "part-of-speech (i.e. n, v, adj or adv)\n"
        + "hyp {word} : lookup the direct hypernyms of the specified word\n"
        + "hyp -all {synsetID} : lookup all hypernyms of the specified synset\n" + "q : quit\n";
  }

  /**
   * Displays the specified info for the user to view.
   * 
   * @param info info to be displayed on the screen
   */
  private void display(String info) {
    System.out.println(info);
  }
}
