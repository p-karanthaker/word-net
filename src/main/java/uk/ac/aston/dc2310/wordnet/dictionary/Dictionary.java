package uk.ac.aston.dc2310.wordnet.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.aston.dc2310.wordnet.main.WNControllerStringOnly;

public class Dictionary implements WNControllerStringOnly {
	
	private Map<String, Set<LexicalUnit>> words;
	private Map<Integer, Gloss> glosses;
	
	public Dictionary() {
		words = new HashMap<String, Set<LexicalUnit>>();
		glosses = new HashMap<Integer, Gloss>();
	}
	
	public void add(LexicalUnit word) {
		String lexicalUnit = word.getLexicalUnit().toUpperCase();
		if (words.containsKey(lexicalUnit)) {
			boolean alreadyInSet = false;
			Set<LexicalUnit> lexicalUnits = words.get(lexicalUnit);
			// Loop over lexical units to check if a specific synset 
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
	}
	
	public void add(Gloss gloss) {
		int synsetId = gloss.getSynsetId();
		if (!glosses.containsKey(synsetId)) {
			glosses.put(synsetId, gloss);
		}
	}

	public int getDictionarySize() {
		return this.words.size();
	}
	
	@Override
	public String getMeanings(String word) {
		this.getMeanings(word, PartOfSpeech.NONE.getName());
	    return null;
	}

	@Override
	public String getMeanings(String word, String pos) {
		long startTime = System.nanoTime();
		Set<LexicalUnit> res = words.get(word.toUpperCase());
		List<LexicalUnit> resList = new ArrayList<LexicalUnit>(res);
		
		// Sort the list by POS first, then by Sense Number
		Collections.sort(resList, new Comparator<LexicalUnit>(){
		   public int compare(LexicalUnit o1, LexicalUnit o2){
			   int value1 = o1.getPartOfSpeech().getValue() - o2.getPartOfSpeech().getValue();
		        if (value1 == 0) {
		            return o1.getSenseNumber() - o2.getSenseNumber();
		        }
		        return value1;
		   }
		});
		
		for (LexicalUnit lu : resList) {
			Gloss gloss = glosses.get(lu.getSynsetId());
			PartOfSpeech selector = PartOfSpeech.valueOf(pos.toUpperCase());
			if (selector.equals(lu.getPartOfSpeech()) || selector.equals(PartOfSpeech.NONE)) {
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("[%s] ", lu.getPartOfSpeech().getName()))
					.append(String.format("%s - ", lu.getLexicalUnit()))
					.append(String.format("%s", gloss.getSense()));
				System.out.println(sb.toString());
			}
		}
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		System.out.println("Time taken to find: " + elapsedTime/1000000);
		System.out.println("Gloss length: " + glosses.size());
		return null;
	}

	@Override
	public String getDirectHypernyms(String word) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllHypernyms(String synsetId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
