package uk.ac.aston.dc2310.wordnet.dictionary;

/**
 * The LexicalUnit class models a single lexical unit
 * from the wn_s.csv WordNet file.
 * 
 * @author KTHAKER
 *
 */
public class LexicalUnit {

	/**
	 * The Synset ID from the first column of wn_s.csv
	 */
	private int synsetId;
	
	/**
	 * The part of speech that the lexical unit belongs to. E.g. Noun, Verb, Adjective, Verb
	 */
	private PartOfSpeech partOfSpeech;
	
	/**
	 * The lexical unit (word)
	 */
	private String lexicalUnit;
	
	/**
	 * The sense number. If the the sense number is lower, then it is used more
	 * than a lexical unit with the same name but a higher sense number.
	 */
	private byte senseNumber;
	
	/**
	 * The constructor for a LexicalUnit object. Constructed by passing in the
	 * 3 columns retrieved when parsing the wn_s.csv file.
	 * 
	 * @param synsetId The Synset ID of the lexical unit.
	 * @param lexicalUnit The lexical unit (word)
	 * @param senseNumber The sense number of the lexical unit.
	 */
	public LexicalUnit(int synsetId, String lexicalUnit, byte senseNumber) {
		this.synsetId = synsetId;
		this.lexicalUnit = lexicalUnit;
		this.senseNumber = senseNumber;
		
		// Get the correct part of speech from the first digit in the Synset ID.
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
	
	/**
	 * Getter method for Synset ID
	 * @return the Synset ID of the Lexical Unit
	 */
	public int getSynsetId() {
		return this.synsetId;
	}
	
	/**
	 * Getter method for Part Of Speech
	 * @return the Part Of Speech of the Lexical Unit
	 */
	public PartOfSpeech getPartOfSpeech() {
		return this.partOfSpeech;
	}
	
	/**
	 * Getter method for the Lexical Unit (word/phrase)
	 * @return the word/phrase belonging to the Lexical Unit
	 */
	public String getLexicalUnit() {
		return this.lexicalUnit;
	}
	
	/**
	 * Getter method for the Sense Number
	 * @return the Sense number of the Lexical Unit
	 */
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
