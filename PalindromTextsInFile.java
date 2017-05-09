package com.easa;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Program that reads text from a text file and efficiently finds all the words that are the reverse of other words, 
 * and prints these out as a list sorted by string length with each pair occurring once
 * This should have the time complexity of O(n)
 * @author j.nicholas
 *
 */
public class PalindromTextsInFile {
	
	static final Comparator<String> STRING_LENGTH_ORDER = new Comparator<String>() {
        public int compare(String s1, String s2) {            
        	 if (s1.length() > s2.length()) {
                 return 1;
             } else if (s1.length() < s2.length()) {
                 return -1;
             } else {
                 return s1.compareTo(s2);
             }

        }

	};
	
	public static void main(String arg[])
	{
		@SuppressWarnings("resource")
		Scanner readData = new Scanner(System.in);   
		System.out.println("Enter the input text file path :");
		String file = readData.nextLine();
		System.out.println("Reading file "+file);
		Set<String> phraseSet = buildPhrase(file); //Example path : "C:/Users/j.nicholas/Desktop/PalindromeTexts.txt"
		
		LinkedHashMap<String, String> sortedMap = getReversedWordMap(phraseSet);
		 
		 Set<String> printList = sortedMap.keySet();
		 System.out.println("Words that are the reverse of other words in the file are :"+(printList.size() == 0 ? " None found " : ""));
		 for(String word : printList)
		 {
			 System.out.println(word+" , "+sortedMap.get(word));
		 }
		 
		 
	}
	
	private static Set<String> buildPhrase(String fileName){
		Set<String> phraseSet = new LinkedHashSet<>();
		//Using try-with-resource statements for automatic resource management in Java8. All resources opened in try block will automatically closed by Java
		try (
			FileInputStream fis = new FileInputStream(fileName);
			DataInputStream dis = new DataInputStream(fis);
			//BufferedReader reads the file in chunks, so doesn't need huge heap to read
			BufferedReader br = new BufferedReader(new InputStreamReader(dis))
			){
			String line = null;
			Pattern p = Pattern.compile("\\s");

			while((line = br.readLine())!=null)
			{
				line = line.toLowerCase();
				String[] phrases = p.split(line);
				//Set does not allow duplicates and hashset implementation makes it ordered 
				for(String str : phrases)
				{
					phraseSet.add(str);
				}
				
			}
		} 
		catch(FileNotFoundException fnfe){
			System.out.println("Sorry, this file does not exist");
		}
		catch (IOException e) {
			System.out.println("Problem accessing file");
		}
		return phraseSet;
	}
	
	

	public static LinkedHashMap<String, String> getReversedWordMap(Set<String> phraseList) {
		Set<String> palindromTexts = new HashSet<>(phraseList);
		Map<String,String> palindromTextsInFile = new HashMap<>();
		for(String text : phraseList)
		{
			//what if 'text' in itself is palindrome?
			if(!isPalindrome(text))
			{
				String reversedString = new StringBuffer(text).reverse().toString();
				//if reverse of the string cannot be added to the set(since it can have only unique elements), then it says that palindrome word exists
				if(text.length()>1 && !palindromTexts.add(reversedString) && !palindromTextsInFile.containsKey(reversedString))
					palindromTextsInFile.put(text,reversedString);
			}
			
		}
		
		//Sorting the map based on string length, should be a linkedhashmap if we want to keep the order
		 LinkedHashMap<String, String> sortedMap = 
				 palindromTextsInFile.entrySet().stream()
                .sorted(Entry.comparingByKey(STRING_LENGTH_ORDER))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                          (e1, e2) -> e1, LinkedHashMap::new));
		 
		return sortedMap;
	}
	
	
	public static boolean isPalindrome(String s) {
	    int len = s.length();
	    //return TRUE if empty of single string
	    if (len <= 1) {
	        return true;
	    }
	    String lower = s.toLowerCase();
	    
	    return (lower.charAt(0) == lower.charAt(len - 1)) &&
	           isPalindrome(lower.substring(1, len - 1));
	}
}