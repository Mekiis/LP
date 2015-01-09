package anagrames;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.sun.tools.javac.code.Attribute.Array;

public class Reader {

	public void read(){
		Map<String, List<String>> mapAnagrams = new HashMap<String, List<String>>();
		
		try {
			Scanner s = new Scanner(new File("fr-unicode.txt"));
			
			while(s.hasNext()){
				String mot = s.next();
				char[] key = mot.toCharArray();
				if(key.length >= 7){
					Arrays.sort(key);
					String keyStr = new String(key);
					if(!mapAnagrams.containsKey(keyStr))
					{
						mapAnagrams.put(keyStr, new ArrayList<String>());
						
					} 
					mapAnagrams.get(keyStr).add(mot);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error - While read the file");
		}
		
		int nbAnagram = 0;
		for (Entry<String, List<String>> mots : mapAnagrams.entrySet()) {
			if(mots.getValue().size() > 1){
				nbAnagram++;
				System.out.print(mots.getKey()+" "+mots.getValue().size()+" : ");
				for(String mot : mots.getValue()){
					System.out.print(mot + " ");
				}
				System.out.println("");
			}
		}
		System.out.println("Nb anagrams: "+nbAnagram);
	}

}
