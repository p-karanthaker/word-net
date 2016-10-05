package uk.ac.aston.dc2310.wordnet.main;

/**
 * A controller interface for working with the TUI class WordNetUIStringOnly.
 * 
 * @author S H S Wong
 * @version 21/06/2016
 */
public interface WnControllerStringOnly {

  /**
   * Returns a set of senses (i.e. meanings) for a specified word.
   * 
   * @param word a sequence of characters corresponding to an English word
   * @return a String representation of a set of word senses representing the meanings of the
   *         specified word; a simple String message if no meanings found.
   */
  public abstract String getMeanings(String word);

  /**
   * Returns a set of senses (i.e. meanings) for a specified word in a specified syntactic category
   * (i.e. part-of-speech such as noun, verb, adjective and adverb).
   * 
   * @param word a sequence of characters corresponding to an English word
   * @param pos a specified part-of-speech (noun, verb, adjective and adverb)
   * @return a String representation of a set of word senses representing the meanings of the
   *         specified word in a specified part-of-speech; a simple String if no meanings found.
   */
  public abstract String getMeanings(String word, String pos);

  /**
   * Returns a set of direct hypernyms for a specified word. A hypernym is a more general concept of
   * a word. A direct hypernym of a word is the least general concept that is linked to the
   * specified word within WordNet. E.g. 'motor vehicle' is a hypernym of 'car' in WordNet 3.0.
   * 
   * @param word a sequence of characters corresponding to an English word
   * @return a String representation of a set of synsets representing the direct hypernyms of the
   *         specified word; a simple String message if no direct hypernyms are found
   */
  public abstract String getDirectHypernyms(String word);

  /**
   * Returns a list of synsets which correspond to the hypernyms of the synset as specified by the
   * given synset ID. The synsets in the result list are ordered according to how general a concept
   * it is when compared with the specified synset, i.e. the less general a concept, the closer to
   * the front of the list, with the direct hypernym appearing at the first element in the result
   * list.
   * 
   * <p>Returns an empty list if no hypernym can be found for the specified synset.
   * 
   * @param synsetId the synsetId to get all hypernyms for
   * @return a String representation of a list of Synset objects sorted in an ascending order of
   *         their generality; a simple String message if no hypernym for the specified synset is
   *         found;
   */
  public abstract String getAllHypernyms(String synsetId);

}
