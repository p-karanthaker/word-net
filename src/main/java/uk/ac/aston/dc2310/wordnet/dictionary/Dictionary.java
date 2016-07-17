package uk.ac.aston.dc2310.wordnet.dictionary;

import java.util.HashMap;
import java.util.HashSet;
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
		if (!words.containsKey(lexicalUnit)) {
			Set<LexicalUnit> lexicalUnits = new HashSet<LexicalUnit>();
			lexicalUnits.add(word);
			words.put(lexicalUnit, lexicalUnits);
		} else {
			Set<LexicalUnit> lexicalUnits = words.get(lexicalUnit);
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
		long startTime = System.nanoTime();
		Set<LexicalUnit> res = words.get(word.toUpperCase());
		for (LexicalUnit lu : res) {
			Gloss gloss = glosses.get(lu.getSynsetId());
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("[%s] ", lu.getPartOfSpeech()))
				.append(String.format("%s ", lu.getLexicalUnit()))
				.append(String.format("%s", gloss.getSense()));
			System.out.println(sb.toString());
		}
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		System.out.println("Time taken to find: " + elapsedTime/1000000);
		/*Iterator it = this.words.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        if (pair.getKey().equals(word.toUpperCase())) {
	        	System.out.println(pair.getKey() + " = " + pair.getValue());
	        }
	    }*/
		System.out.println("Gloss length: " + glosses.size());
	    return null;
	}

	@Override
	public String getMeanings(String word, String pos) {
		// TODO Auto-generated method stub
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
