package uk.ac.aston.dc2310.wordnet.main;

import uk.ac.aston.dc2310.wordnet.reader.WordNetReader;

public class App {

  /**
   * Application entry point. Read files, then start WordNet TUI.
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    WordNetReader wnRead = new WordNetReader();
    wnRead.readFiles();
    new WordNetUiStringOnly(wnRead.getDictionary());
  }

}
