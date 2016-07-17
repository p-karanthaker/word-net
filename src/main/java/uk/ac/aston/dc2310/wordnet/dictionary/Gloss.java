package uk.ac.aston.dc2310.wordnet.dictionary;

public class Gloss {

	private int synsetId;
	private String sense;
	private String example;
	
	public Gloss(int synsetId, String sense, String example) {
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("SynsetID: %d%n", this.synsetId))
			.append(String.format("Sense: %s%n", this.sense))
			.append(String.format("Example: %s%n", this.example));
		return sb.toString();
	}
	
}
