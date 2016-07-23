package uk.ac.aston.dc2310.wordnet.dictionary;

/**
 * PartOfSpeech shows the parts of speech which are
 * used within this implementation of WordNet and the
 * value which maps the first digit in the Synset ID
 * to the part-of-speech.
 * 
 * @author Prem-Karan Thaker
 *
 */
public enum PartOfSpeech {

	/**
	 * Noun - a noun Synset ID will start with 1.
	 */
	N(1, "NOUN"),
	
	/**
	 * Verb - a verb Synset ID will start with 2.
	 */
	V(2, "VERB"),
	
	/**
	 * Adjective - an adjective Synset ID will start with 3.
	 */
	ADJ(3, "ADJECTIVE"),
	
	/**
	 * Adverb - an adverb Synset ID will start with 4.
	 */
	ADV(4, "ADVERB"),
	
	/**
	 * None - part of speech not found.
	 */
	NONE(0, "NONE");
	
	/**
	 * The value which maps the first digit in the
	 * Synset ID to the part-of-speech.
	 */
	private int value;
	
	/**
	 * The full name of the Part Of Speech. E.g. PartOfSpeech.N == NOUN
	 */
	private String name;
	
	/**
	 * Constructor for a PartOfSpeech. Pass the value which is assigned.
	 * 
	 * @param value	The value assigned to the part of speech. 
	 * @param name The long/full name given to the Part Of Speech
	 */
	private PartOfSpeech(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * Get the value which maps the first digit in the Synset
	 * ID to the part-of-speech.
	 * 
	 * @return the value of the part-of-speech
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Return the full name of the Part Of Speech. E.g. PartOfSpeech.N.getName() would return "NOUN"
	 * @return the full name of the Part Of Speech
	 */
	public String getName() {
		return this.name;
	}
	
}
