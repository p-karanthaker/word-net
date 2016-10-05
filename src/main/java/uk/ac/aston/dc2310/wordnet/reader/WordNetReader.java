package uk.ac.aston.dc2310.wordnet.reader;

import uk.ac.aston.dc2310.wordnet.dictionary.Dictionary;
import uk.ac.aston.dc2310.wordnet.dictionary.Gloss;
import uk.ac.aston.dc2310.wordnet.dictionary.LexicalUnit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The WordNetReader class reads through each WordNet file (wn_s.csv, wn_g.csv, wn_hyp.csv) and adds
 * the parsed data into Dictionary object to be used by the WordNet application.
 * 
 * @author KTHAKER
 *
 */
public class WordNetReader {

  /**
   * The delimiter used in the CSV files. In this case a tab (\t).
   */
  private static final String DELIMITER = "\t";

  /**
   * The variable to store the wn_s.csv filename.
   */
  private static final String WN_S = "/wn_s.csv";

  /**
   * The variable to store the wn_g.csv filename.
   */
  private static final String WN_G = "/wn_g.csv";

  /**
   * The variable to store the wn_hyp.csv filename.
   */
  private static final String WN_HYP = "/wn_hyp.csv";

  /**
   * The dictionary object which stores the parsed data.
   */
  private Dictionary dictionary;

  /**
   * WordNetReader constructor. Initialises the final variables and the Dictionary object.
   */
  public WordNetReader() {
    this.dictionary = new Dictionary();
  }

  /**
   * Calls the WordNetReader.read() method to read all WordNet files to guarantee that a valid
   * filename is passed to the method.
   */
  public void readFiles() {
    System.out.println("Loading WordNet...");
    long startTime = System.nanoTime();
    this.read(WN_S);
    this.read(WN_G);
    this.read(WN_HYP);
    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;
    System.out.printf("Completed loading in: %dms%n", elapsedTime / 1000000);
  }

  /**
   * Reads the WordNet CSV files and adds the parsed data into the Dictionary object.
   * 
   * @param filename the filename of the WordNet CSV file.
   */
  private void read(String filename) {
    InputStream in = this.getClass().getResourceAsStream(filename);
    try (BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
      for (String line; (line = br.readLine()) != null;) {
        String[] str = line.split(DELIMITER);
        int synsetId = Integer.parseInt(str[0]);
        if (filename.equals(WordNetReader.WN_S)) {
          String lexicalUnit = str[1];
          byte senseNumber = Byte.parseByte(str[2]);
          dictionary.add(new LexicalUnit(synsetId, lexicalUnit, senseNumber));
        } else if (filename.equals(WordNetReader.WN_G)) {
          String sense = str[1];
          String example = str.length == 3 ? str[2] : null;
          dictionary.add(new Gloss(synsetId, sense, example));
        } else if (filename.equals(WordNetReader.WN_HYP)) {
          int synsetId2 = Integer.parseInt(str[1]);
          dictionary.add(synsetId, synsetId2);
        }
      }
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Allows retrieval of the WordNet Dictionary object.
   * 
   * @return a Dictionary object
   */
  public Dictionary getDictionary() {
    return this.dictionary;
  }

}
