package uk.ac.aston.dc2310.wordnet.dictionary;

public class LexicalUnit {

	private int synsetId;
	private PartOfSpeech partOfSpeech;
	private String lexicalUnit;
	private byte senseNumber;
	
	public LexicalUnit(int synsetId, String lexicalUnit, byte senseNumber) {
		this.synsetId = synsetId;
		this.lexicalUnit = lexicalUnit;
		this.senseNumber = senseNumber;
		
		int partOfSpeechValue = Integer.parseInt(Integer.toString(synsetId).substring(0, 1));
		for (PartOfSpeech pos : PartOfSpeech.values()) {
			if(pos.getValue() == partOfSpeechValue) {
				this.partOfSpeech = pos;
				break;
			} else {
				this.partOfSpeech = PartOfSpeech.NONE;
			}
		}
	}
	
	public int getSynsetId() {
		return this.synsetId;
	}
	
	public PartOfSpeech getPartOfSpeech() {
		return this.partOfSpeech;
	}
	
	public String getLexicalUnit() {
		return this.lexicalUnit;
	}
	
	public byte getSenseNumber() {
		return this.senseNumber;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("SynsetID: %d%n", this.synsetId))
			.append(String.format("PartOfSpeech: %s%n", this.partOfSpeech))
			.append(String.format("Lexical Unit: %s%n", this.lexicalUnit))
			.append(String.format("Sense Number: %d%n", this.senseNumber));
		return sb.toString();
	}
	
}
