package uk.ac.aston.dc2310.wordnet.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uk.ac.aston.dc2310.wordnet.dictionary.Dictionary;
import uk.ac.aston.dc2310.wordnet.dictionary.Gloss;
import uk.ac.aston.dc2310.wordnet.dictionary.LexicalUnit;

/**
 * The WordNetReader class reads through each WordNet file
 * (wn_s.csv, wn_g.csv, wn_hyp.csv) and adds the parsed data into
 * Dictionary object to be used by the WordNet application.
 * 
 * @author KTHAKER
 *
 */
public class WordNetReader {
	
	/**
	 * The delimiter used in the CSV files. In this case a tab (\t).
	 */
	private final String DELIMITER;
	
	/**
	 * The variable to store the wn_s.csv filename.
	 */
	private final String WN_S;
	
	/**
	 * The variable to store the wn_g.csv filename.
	 */
	private final String WN_G;
	
	/**
	 * The variable to store the wn_hyp.csv filename.
	 */
	private final String WN_HYP;
	
	/**
	 * The dictionary object which stores the parsed data.
	 */
	private Dictionary dictionary;
	
	/**
	 * WordNetReader constructor. Initialises the final variables 
	 * and the Dictionary object.
	 */
	public WordNetReader() {
		this.DELIMITER = "\t";
		this.WN_S = "/wn_s.csv";
		this.WN_G = "/wn_g.csv";
		this.WN_HYP = "/wn_hyp.csv";
		
		this.dictionary = new Dictionary();
	}

	/**
	 * Calls the WordNetReader.read() method  for the 
	 * wn_s.csv file to guarantee that a valid filename 
	 * is passed to the method.
	 */
	public void readWN_S() {
		this.read(this.WN_S);
	}
	
	/**
	 * Calls the WordNetReader.read() method for the
	 * wn_g.csv file to guarantee that a valid filename 
	 * is passed to the method.
	 */
	public void readWN_G() {
		this.read(this.WN_G);
	}
	
	/**
	 * Calls the WordNetReader.read() method for the 
	 * wn_hyp.csv file to guarantee that a vlid filename
	 * is passed to the method.
	 */
	public void readWN_HYP() {
		this.read(this.WN_HYP);
	}
	
	/**
	 * Reads the WordNet CSV files and adds the parsed data into 
	 * the Dictionary object.
	 * @param filename the filename of the WordNet CSV file.
	 */
	private void read(String filename) {
		InputStream in = this.getClass().getResourceAsStream(filename);
		long startTime = System.nanoTime();
		// the process to be timed
		try(BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	String[] str = line.split(DELIMITER);
				int synsetID = Integer.parseInt(str[0]);
				if(filename.equals(this.WN_S)) {
					String lexicalUnit = str[1];
					byte senseNumber = Byte.parseByte(str[2]);
					dictionary.add(new LexicalUnit(synsetID, lexicalUnit, senseNumber));
				} else if (filename.equals(this.WN_G)) {
					String sense = str[1];
					String example = str.length == 3 ? str[2] : null;
					dictionary.add(new Gloss(synsetID, sense, example));
				} else if (filename.equals(this.WN_HYP)) {
					int synsetID_2 = Integer.parseInt(str[1]);
					dictionary.add(synsetID, synsetID_2);
				}
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		System.out.println("Time taken to read: " + elapsedTime/1000000);
	}
	
	/**
	 * Allows retrieval of the WordNet Dictionary object.
	 * @return a Dictionary object
	 */
	public Dictionary getDictionary() {
		return this.dictionary;
	}
	
}
