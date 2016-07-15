package uk.ac.aston.dc2310.wordnet.dictionary;

public class WordSense {

	private int synsetId;
	private String sense;
	private String example;
	
	public WordSense(int synsetId, String sense, String example) {
		this.synsetId = synsetId;
		this.sense = sense;
		this.example = example;
	}
	
	public int getSynsetId() {
		return this.synsetId;
	}
	
	public String getSense() {
		return this.sense;
	}
	
	public String getExample() {
		return this.example;
	}
	
}
