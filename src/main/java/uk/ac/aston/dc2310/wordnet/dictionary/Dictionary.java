package uk.ac.aston.dc2310.wordnet.dictionary;

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

import uk.ac.aston.dc2310.wordnet.main.WNControllerStringOnly;

public class Dictionary implements WNControllerStringOnly {
	
	private Map<String, Set<LexicalUnit>> words;
	private Map<Integer, Set<LexicalUnit>> synsets;
	private Map<Integer, Gloss> glosses;
	private TreeMap<Integer, TreeSet<Integer>> hyponyms;
	
	public Dictionary() {
		words = new HashMap<String, Set<LexicalUnit>>();
		synsets = new HashMap<Integer, Set<LexicalUnit>>();
		glosses = new HashMap<Integer, Gloss>();
		hyponyms = new TreeMap<Integer, TreeSet<Integer>>();
	}
	
	public void add(LexicalUnit word) {
		String lexicalUnit = word.getLexicalUnit().toUpperCase();
		if (words.containsKey(lexicalUnit)) {
			boolean alreadyInSet = false;
			Set<LexicalUnit> lexicalUnits = words.get(lexicalUnit);
			/* Loop over lexical units to check if a specific synset
			 * is already in the set.
			 */
			for (LexicalUnit lu : lexicalUnits) {
				if (lu.getSynsetId() == word.getSynsetId()) {
					alreadyInSet = true;
				}
			}
			if (!alreadyInSet) {
				lexicalUnits.add(word);
				words.put(lexicalUnit, lexicalUnits);
			}
		} else {
			Set<LexicalUnit> lexicalUnits = new HashSet<LexicalUnit>();
			lexicalUnits.add(word);
			words.put(lexicalUnit, lexicalUnits);
		}
		
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
	
	public void add(Gloss gloss) {
		int synsetId = gloss.getSynsetId();
		if (!glosses.containsKey(synsetId)) {
			glosses.put(synsetId, gloss);
		}
	}
	
	public void add(int synsetId1, int synsetId2) {
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

	public int getDictionarySize() {
		return this.words.size();
	}
	
	private List<LexicalUnit> sortLexicalUnits(Set<LexicalUnit> lexicalUnits) {
		List<LexicalUnit> resList = new ArrayList<LexicalUnit>(lexicalUnits);
		
		// Sort the list by POS first, then by Sense Number
		Collections.sort(resList, new Comparator<LexicalUnit>(){
		   public int compare(LexicalUnit o1, LexicalUnit o2){
			   int partOfSpeech = o1.getPartOfSpeech().getValue() - o2.getPartOfSpeech().getValue();
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
		Set<LexicalUnit> lexicalUnits = words.get(word.toUpperCase());
		
		List<LexicalUnit> sortedLexicalUnits = this.sortLexicalUnits(lexicalUnits);
		StringBuilder sb = new StringBuilder();
		for (LexicalUnit lu : sortedLexicalUnits) {
			Gloss gloss = glosses.get(lu.getSynsetId());
			PartOfSpeech selector = PartOfSpeech.valueOf(pos.toUpperCase());
			if (selector.equals(lu.getPartOfSpeech()) || selector.equals(PartOfSpeech.NONE)) {
				sb.append(String .format("(%s) ", lu.getSynsetId()))
					.append(String.format("[%s] ", lu.getPartOfSpeech().getName()))
					.append(String.format("%s - ", lu.getLexicalUnit()))
					.append(String.format("%s%n", gloss.getSense()));
			}
		}
		return sb.toString();
	}

	@Override
	public String getDirectHypernyms(String word) {
		Set<LexicalUnit> lexicalUnits = words.get(word.toUpperCase());
		List<LexicalUnit> sortedLexicalUnits = this.sortLexicalUnits(lexicalUnits);
		
		StringBuilder sb = new StringBuilder();
		for (LexicalUnit lexicalUnit : sortedLexicalUnits) {
			Set<Integer> hypernymSet = hyponyms.get(lexicalUnit.getSynsetId());
			if (hypernymSet != null) {
				// Loop over hypernymSet in case a synset has multiple hypernyms
				for (Integer i : hypernymSet) {
					sb.append(String.format("(%d) ", i));
					sb.append(String.format("[%s]\t", new LexicalUnit(i, "", Byte.MIN_VALUE).getPartOfSpeech().getName()));
					sb.append(String.format("%s%n", glosses.get(i).getSense()));
					for (LexicalUnit lu : synsets.get(i)) {
						sb.append(String.format("\t\t\t%s (%d)%n", lu.getLexicalUnit(), lu.getSenseNumber()));
					}
					sb.append("\n");
				}
			}
		}
		
		/*for(Map.Entry<Integer,TreeSet<Integer>> entry : hyponyms.entrySet()) {
		  Integer key = entry.getKey();
		  Set<Integer> value = entry.getValue();

		  System.out.println(key + " => " + value);
		}*/
		return sb.toString();
	}

	@Override
	public String getAllHypernyms(String synsetId) {
		TreeSet<Integer> hypernyms = hyponyms.get(Integer.parseInt(synsetId));
		//List<Integer> synsetList = new ArrayList<Integer>();
		
		StringBuilder sb = new StringBuilder();
		
		// Loop over hypernyms to find all hypernyms
		int tabs = 0;
		while (hypernyms != null) {
			for (Integer synsetId1 : hypernyms) {
				if (tabs > 0) {
					for (int i = 0; i < tabs; i++) {
						sb.append("\t");
					}
				}
				tabs++;
				sb.append(String.format("(%d) ", synsetId1));
				sb.append(String.format("[%s] ", new LexicalUnit(synsetId1, "", Byte.MIN_VALUE).getPartOfSpeech().getName()));
				sb.append(String.format("%s: ", glosses.get(synsetId1).getSense()));
				for (LexicalUnit lu : synsets.get(synsetId1)) {
					sb.append(String.format("%s (%d), ", lu.getLexicalUnit(), lu.getSenseNumber()));
				}
				sb.append("\n");
				hypernyms = hyponyms.get(synsetId1);
			}
		}
		/*if (hypernyms != null) {
			for (Integer synsetId1 : hypernyms) {
				if (synsetId1 != null) {
					sb.append(String.format("(%d) ", synsetId1));
					sb.append(String.format("[%s] ", new LexicalUnit(synsetId1, "", Byte.MIN_VALUE).getPartOfSpeech().getName()));
					sb.append(String.format("%s: ", glosses.get(synsetId1).getSense()));
					for (LexicalUnit lu : synsets.get(synsetId1)) {
						sb.append(String.format("%s (%d), ", lu.getLexicalUnit(), lu.getSenseNumber()));
					}
					this.getAllHypernyms(Integer.toString(synsetId1));
				}
			}
		}*/
		return sb.toString();
	}
	
}
