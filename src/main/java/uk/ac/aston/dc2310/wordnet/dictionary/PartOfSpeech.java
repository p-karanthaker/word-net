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
	N(1),
	
	/**
	 * Verb - a verb Synset ID will start with 2.
	 */
	V(2),
	
	/**
	 * Adjective - an adjective Synset ID will start with 3.
	 */
	ADJ(3),
	
	/**
	 * Adverb - an adverb Synset ID will start with 4.
	 */
	ADV(4),
	
	/**
	 * None - part of speech not found.
	 */
	NONE(0);
	
	/**
	 * The value which maps the first digit in the
	 * Synset ID to the part-of-speech.
	 */
	private int value;
	
	/**
	 * Constructor for a PartOfSpeech. Pass the value which is assigned.
	 * 
	 * @param value	The value assigned to the part of speech. 
	 */
	private PartOfSpeech(int value) {
		this.value = value;
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
}
