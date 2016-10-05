package uk.ac.aston.dc2310.wordnet.dictionary;

import uk.ac.aston.dc2310.wordnet.main.WnControllerStringOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The Dictionary class models an electronic dictionary holding the parsed data from the WordNet
 * files (wn_s.csv, wn_g.csv, wn_hyp.csv).
 * 
 * @author KTHAKER
 *
 */
public class Dictionary implements WnControllerStringOnly {

  /**
   * A HashMap which maps a string representation of a word to a set of LexicalUnit objects which
   * have the same name/lexical unit.
   */
  private Map<String, Set<LexicalUnit>> words;

  /**
   * A HashMap which maps a Synset Id to a Set of LexicalUnits which are in the same synset.
   */
  private Map<Integer, Set<LexicalUnit>> synsets;

  /**
   * A HashMap which maps a Synset id to a Gloss object (Stores the meaning of a synset id).
   */
  private Map<Integer, Gloss> glosses;

  /**
   * A HashMap which maps the synset id of a Hyponym to a TreeSet of it's Hypernyms.
   */
  private TreeMap<Integer, TreeSet<Integer>> hyponyms;

  /**
   * Constructor for a Dictionary object which initialises the Map structures.
   */
  public Dictionary() {
    words = new HashMap<String, Set<LexicalUnit>>(100000);
    synsets = new HashMap<Integer, Set<LexicalUnit>>(100000);
    glosses = new HashMap<Integer, Gloss>(100000);
    hyponyms = new TreeMap<Integer, TreeSet<Integer>>();
  }

  /**
   * Method to add a LexicalUnit to the word and synset lists.
   * 
   * @param word The word to be added into the Dictionary.
   */
  public void add(LexicalUnit word) {
    // Convert all words to UPPER CASE to be used as keys.
    String lexicalUnit = word.getLexicalUnit().toUpperCase(); 

    /*
     * Check if the word is already a key in the words map. If it is, then update the current Set of
     * LexicalUnits in the map which correspond to that word. Else, add the word to the words map
     * with a new Set containing only the word which was passed to be added.
     */
    if (words.containsKey(lexicalUnit)) {
      boolean alreadyInSet = false;
      Set<LexicalUnit> lexicalUnits = words.get(lexicalUnit);
      // Loop over lexical units to check if a specific synset is already in the set.
      for (LexicalUnit lu : lexicalUnits) {
        if (lu.getSynsetId() == word.getSynsetId()) {
          alreadyInSet = true;
        }
      }
      // Add LexicalUnit to set if the word with synset id was not found in the set.
      if (!alreadyInSet) {
        lexicalUnits.add(word);
        words.put(lexicalUnit, lexicalUnits);
      }
    } else {
      Set<LexicalUnit> lexicalUnits = new HashSet<LexicalUnit>();
      lexicalUnits.add(word);
      words.put(lexicalUnit, lexicalUnits);
    }

    /*
     * Check if the synsets map already contains the synset of the LexicalUnit (word). If it does,
     * then update the current Set of LexicalUnits. Else, add the LexicalUnit into a new Set of
     * LexicalUnits (synset) and map the synset id to the new Set.
     */
    if (synsets.containsKey(word.getSynsetId())) {
      boolean alreadyInSet = false;
      Set<LexicalUnit> synset = synsets.get(word.getSynsetId());
      for (LexicalUnit lu : synset) {
        if (lu.getLexicalUnit().equals(word.getLexicalUnit())) {
          alreadyInSet = true;
        }
      }
      if (!alreadyInSet) {
        synset.add(word);
        synsets.put(word.getSynsetId(), synset);
      }
    } else {
      Set<LexicalUnit> synset = new HashSet<LexicalUnit>();
      synset.add(word);
      synsets.put(word.getSynsetId(), synset);
    }
  }

  /**
   * Method to add a Gloss (meaning) to the map of glosses. Maps a synset id to a Gloss object.
   * 
   * @param gloss the gloss to be added to the glosses map
   */
  public void add(Gloss gloss) {
    int synsetId = gloss.getSynsetId();
    // If the synset id is not in the glosses keys, then add a new entry to the map.
    if (!glosses.containsKey(synsetId)) {
      glosses.put(synsetId, gloss);
    }
  }

  /**
   * Method to map a Hyponym to a Hypernym.
   * 
   * @param synsetId1 The Hyponym synset id
   * @param synsetId2 The Hypernym synset id
   */
  public void add(int synsetId1, int synsetId2) {
    /*
     * Checks if hyponym synset id is already in the map
     */
    if (hyponyms.containsKey(synsetId1)) {
      boolean alreadyInSet = false;
      TreeSet<Integer> hypernyms = hyponyms.get(synsetId1);
      for (Integer synsetId : hypernyms) {
        if (synsetId == synsetId2) {
          alreadyInSet = true;
        }
      }
      if (!alreadyInSet) {
        hypernyms.add(synsetId2);
        hyponyms.put(synsetId1, hypernyms);
      }
    } else {
      TreeSet<Integer> hypernyms = new TreeSet<Integer>();
      hypernyms.add(synsetId2);
      hyponyms.put(synsetId1, hypernyms);
    }
  }

  /**
   * Return the size of all data structures used by the Dictionary.
   * 
   * @return a string representation of the sizes of the data structures used by the Dictionary.
   */
  public String getDictionarySize() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Words map size: [%d]%n", words.size()))
        .append(String.format("Synsets map size: [%d]%n", synsets.size()))
        .append(String.format("Glosses map size: [%d]%n", glosses.size()))
        .append(String.format("Hyponyms map size: [%d]%n", hyponyms.size()));
    return sb.toString();
  }

  /**
   * Sorts a Set of LexicalUnit objects by their Part of Speech numerical value, and then by their
   * Sense Number.
   * 
   * @param lexicalUnits the Set of LexicalUnits to be sorted
   * @return a sorted List of LexicalUnit objects.
   */
  private List<LexicalUnit> sortLexicalUnits(Set<LexicalUnit> lexicalUnits) {
    List<LexicalUnit> resList = new ArrayList<LexicalUnit>(lexicalUnits);

    Collections.sort(resList, new Comparator<LexicalUnit>() {
      @Override
      public int compare(LexicalUnit o1, LexicalUnit o2) {
        int partOfSpeech = o1.getPartOfSpeech().getValue() - o2.getPartOfSpeech().getValue();
        // Sort by sense number if the part of speech is the same
        if (partOfSpeech == 0) {
          return o1.getSenseNumber() - o2.getSenseNumber();
        }
        return partOfSpeech;
      }
    });
    return resList;
  }

  @Override
  public String getMeanings(String word) {
    return this.getMeanings(word, PartOfSpeech.NONE.getName());
  }

  @Override
  public String getMeanings(String word, String pos) {
    if (words.containsKey(word.toUpperCase())) {
      Set<LexicalUnit> lexicalUnits = words.get(word.toUpperCase());
      List<LexicalUnit> sortedLexicalUnits = this.sortLexicalUnits(lexicalUnits);

      StringBuilder sb = new StringBuilder();
      for (LexicalUnit lu : sortedLexicalUnits) {
        Gloss gloss = glosses.get(lu.getSynsetId());
        PartOfSpeech selector = PartOfSpeech.valueOf(pos.toUpperCase());
        if (selector.equals(lu.getPartOfSpeech()) || selector.equals(PartOfSpeech.NONE)) {
          sb.append(String.format("(%s) ", lu.getSynsetId()))
              .append(String.format("[%s] ", lu.getPartOfSpeech().getName()))
              .append(String.format("%s - ", lu.getLexicalUnit()))
              .append(String.format("%s%n", gloss.getSense()));
        }
      }
      return sb.toString();
    }
    return "Word was not found in the WordNet dictionary.";
  }

  @Override
  public String getDirectHypernyms(String word) {
    if (words.containsKey(word.toUpperCase())) {
      Set<LexicalUnit> lexicalUnits = words.get(word.toUpperCase());
      List<LexicalUnit> sortedLexicalUnits = this.sortLexicalUnits(lexicalUnits);

      StringBuilder sb = new StringBuilder();
      for (LexicalUnit lexicalUnit : sortedLexicalUnits) {
        Set<Integer> hypernymSet = hyponyms.get(lexicalUnit.getSynsetId());
        if (hypernymSet != null) {
          // Loop over hypernymSet in case a synset has multiple hypernyms
          for (Integer i : hypernymSet) {
            sb.append(String.format("(%d) ", i));
            sb.append(String.format("[%s]\t",
                new LexicalUnit(i, "", Byte.MIN_VALUE).getPartOfSpeech().getName()));
            sb.append(String.format("%s%n", glosses.get(i).getSense()));
            for (LexicalUnit lu : synsets.get(i)) {
              sb.append(String.format("\t\t\t%s (%d)%n", lu.getLexicalUnit(), lu.getSenseNumber()));
            }
            sb.append("\n");
          }
        }
      }
      return sb.toString();
    }
    return "No hypernyms found for the specified word.";
  }

  @Override
  public String getAllHypernyms(String synsetId) {
    if (hyponyms.containsKey(Integer.parseInt(synsetId))) {
      // Get the set of hypernyms which the synset id is mapped to
      TreeSet<Integer> hypernyms = hyponyms.get(Integer.parseInt(synsetId));

      StringBuilder sb = new StringBuilder();
      // Loop over hypernyms to find all hypernyms
      int tabs = 0;
      while (hypernyms != null) {
        for (Integer synsetId1 : hypernyms) {
          // Code for adding tabs to new lines
          if (tabs > 0) {
            for (int i = 0; i < tabs; i++) {
              sb.append("\t");
            }
          }
          tabs++;

          sb.append(String.format("(%d) ", synsetId1));
          sb.append(String.format("[%s] ",
              new LexicalUnit(synsetId1, "", Byte.MIN_VALUE).getPartOfSpeech().getName()));
          sb.append(String.format("%s: ", glosses.get(synsetId1).getSense()));
          for (LexicalUnit lu : synsets.get(synsetId1)) {
            sb.append(String.format("%s (%d), ", lu.getLexicalUnit(), lu.getSenseNumber()));
          }
          sb = new StringBuilder(sb.toString().replaceAll(", $", ""));
          sb.append("\n");

          /*
           * Set hypernyms to the set which is mapped to the synset id of the last hypernym so we
           * can continue looping through all hypernyms.
           */
          hypernyms = hyponyms.get(synsetId1);
        }
      }
      return sb.toString();
    }
    return "No hypernyms found for the specified Synset.";
  }

}
