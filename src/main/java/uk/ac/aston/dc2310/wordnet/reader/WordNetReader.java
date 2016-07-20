package uk.ac.aston.dc2310.wordnet.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uk.ac.aston.dc2310.wordnet.dictionary.Dictionary;
import uk.ac.aston.dc2310.wordnet.dictionary.LexicalUnit;
import uk.ac.aston.dc2310.wordnet.dictionary.Gloss;

public class WordNetReader {
	private final String DELIMITER;
	private final String WN_S;
	private final String WN_G;
	private final String WN_HYP;
	
	private Dictionary dictionary;
	
	public WordNetReader() {
		this.DELIMITER = "\t";
		this.WN_S = "/wn_s.csv";
		this.WN_G = "/wn_g.csv";
		this.WN_HYP = "/wn_hyp.csv";
		
		this.dictionary = new Dictionary();
	}

	public void readWN_S() {
		this.read(this.WN_S);
	}
	
	public void readWN_G() {
		this.read(this.WN_G);
	}
	
	public void readWN_HYP() {
		this.read(this.WN_HYP);
	}
	
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
	
	public Dictionary getDictionary() {
		return this.dictionary;
	}
	
}
