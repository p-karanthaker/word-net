package uk.ac.aston.dc2310.wordnet.main;

import java.util.List;
import java.util.Set;

import uk.ac.aston.dc2310.wordnet.data.POS;
import uk.ac.aston.dc2310.wordnet.data.Synset;
import uk.ac.aston.dc2310.wordnet.data.WordSense;

public interface WNController {

	/**
	 * Returns a set of senses (ie meanings) for a specified word.
	 * 
	 * @param word	a sequence of characters corresponding to an English word
	 * @return	a set of word senses representing the meanings of the specified word; null if no meanings found.
	 */
	public abstract Set<WordSense> getMeanings(String word);

	/**
	 * Returns a set of senses (ie meanings) for a specified word in a specified 
	 * syntactic category (ie part-of-speech such as noun, verb, adjective and adverb).
	 * 
	 * @param word	a sequence of characters corresponding to an English word
	 * @param pos	a specified part-of-speech (noun, verb, adjective and adverb)
	 * @return	a set of word senses representing the meanings of the specified word in a specified part-of-speech; null if no meanings found.
	 */
	public abstract Set<WordSense> getMeanings(String word, POS pos);

	/**
	 * Returns a set of direct hypernyms for a specified word.
	 * A hypernym is a more general concept of a word. A direct hypernym of a word is
	 * the least general concept that is linked to the specified word within WordNet.
	 * E.g. 'motor vehicle' is a hypernym of 'car' in WordNet 3.0.
	 * 
	 * @param word	a sequence of characters corresponding to an English word
	 * @return	a set of synsets representing the direct hypernyms of the specified word; null if no direct hypernyms are found
	 */
	public abstract Set<Synset> getDirectHypernyms(String word);

	/**
	 * Returns a list of synsets which correspond to the hypernyms of the synset as
	 * specified by the given synset ID. The synsets in the result list are ordered
	 * according to how general a concept it is when compared with the specified synset,
	 * ie the less general a concept, the closer to the front of the list, with the 
	 * direct hypernym appearing at the first element in the result list.
	 * 
	 * Returns an empty list if no hypernym can be found for the specified synset.
	 * 
	 * @param synsetId
	 * @return	an empty list if no hypernym for the specified synset is found; 
	 * 			a list of Synset objects sorted in an ascending order of their generality. 
	 */
	public abstract List<Synset> getAllHypernyms(String synsetId);

}