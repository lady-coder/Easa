package com.easa;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

public class PalindromTextsInFileTest {

	@Test
	public void testIsPalindrome() {
		assertEquals(PalindromTextsInFile.isPalindrome("civic"), true);
		assertFalse(PalindromTextsInFile.isPalindrome("12345678"));
	}
	
	@Test
	public void mapFromListCheck() {
		Set<String> testSet = new LinkedHashSet<>();
		testSet.add("saw");
		testSet.add("ton");
		testSet.add("was");
		testSet.add("saw");
		testSet.add("not");
		testSet.add("check");
		testSet.add("was");
		testSet.add("other");
		LinkedHashMap<String, String> values = PalindromTextsInFile.getReversedWordMap(testSet);
		assertNotNull(values);
		assertEquals(2, values.size());
		
	}
	
	@Test
	public void mapOrderedByTextLength(){
		Set<String> sampleSet = new LinkedHashSet<>();
		sampleSet.add("abcdefghij");
		sampleSet.add("jihgfedcba");
		sampleSet.add("noon");
		sampleSet.add("swan");
		sampleSet.add("naws");
		LinkedHashMap<String, String> values = PalindromTextsInFile.getReversedWordMap(sampleSet);
		assertEquals(2 , values.size());
		//a palindrome word should not be included
		assertFalse(values.containsKey("noon"));
		//sort by text length, so first should be 4 and not 11
		assertTrue(values.keySet().iterator().next().length()==4);
		
	}

}
