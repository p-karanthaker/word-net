package uk.ac.aston.dc2310.wordnet.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.aston.dc2310.wordnet.dictionary.Synset;

public class WordNetReader {
	private final String DELIMITER;
	private final String WN_S;
	private final String WN_G;
	private final String WN_HYP;
	
	public WordNetReader() {
		this.DELIMITER = "\t";
		this.WN_S = "/wn_s.csv";
		this.WN_G = "/wn_g.csv";
		this.WN_HYP = "/wn_hyp.csv";
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
		String line = "";
		long startTime = System.nanoTime();
		// the process to be timed (e.g. a method call)
		try(BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			List<Synset> synsets = new ArrayList<Synset>();
			while ((line = br.readLine()) != null) {
				String[] str = line.split(DELIMITER);
				Synset synset;
				synset = new Synset(Integer.parseInt(str[0]), str[1], Byte.parseByte(str[2]));
				synsets.add(synset);
			}
			/*for (Synset synset : synsets) {
				System.out.println(synset);
			}*/
			System.out.println("List length: " + synsets.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		System.out.println("Time taken to read: " + elapsedTime/1000000);
	}
	
}
