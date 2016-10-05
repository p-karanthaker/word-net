package uk.ac.aston.dc2310.wordnet.dictionary;

/**
 * The Gloss class models a "gloss" (meaning) of a particular Synset. The data is collected from the
 * wn_g.csv file.
 * 
 * @author KTHAKER
 *
 */
public class Gloss {

  /**
   * The ID of the Synset which the Gloss describes.
   */
  private int synsetId;

  /**
   * The definition/meaning of the Synset within the Gloss.
   */
  private String sense;

  /**
   * A short example sentence using a word from the Synset in the correct context.
   */
  private String example;

  /**
   * The constructor for a Gloss object. Constructed by passing in 3 columns from the wn_g.csv file.
   * 
   * @param synsetId the ID of the Synset which the Gloss describes
   * @param sense the definition/meaning of the Synset within the Gloss
   * @param example a short example sentence using a word from the Synset in the correct context
   */
  public Gloss(int synsetId, String sense, String example) {
    this.synsetId = synsetId;
    this.sense = sense;
    this.example = example;
  }

  /**
   * Getter method for the ID of the Synset belonging to the gloss.
   * 
   * @return the ID of the synset belonging to the Gloss
   */
  public int getSynsetId() {
    return this.synsetId;
  }

  /**
   * Getter method for the definition/meaning of the Synset within the Gloss.
   * 
   * @return the definition of the Synset within the Gloss
   */
  public String getSense() {
    return this.sense;
  }

  /**
   * Getter method for a sample sentence using a word from the Synset belonging to the Gloss.
   * 
   * @return a sample sentence using a word from the Synset belonging to the Gloss
   */
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
