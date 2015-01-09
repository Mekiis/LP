import java.text.Normalizer;


public class Palindrome {

	public static boolean isPalindrome(String str) {
		if(str == null)
			return false;
		if(str == "")
			return false;
		
		str = processString(str);
		
		int i = 0;
		int lastCharIndex = str.length() - 1;
		
		while(i < lastCharIndex - i){
			if(str.charAt(i) != str.charAt(lastCharIndex - i))
				return false;
			i++;
		}
		
		return true;
	}
	
	private static String processString(String str){
		str = removeAccent(str);
		str = removeSpaces(str);
		str = removeNonWordCharacters(str);
		return str.toLowerCase();
	}

	public static String removeAccent(String str) {
		String nfkdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFKD);
		// Remove all characters belonging to the category Mark (M)
		return nfkdNormalizedString.replaceAll("\\p{M}", "");
	}
	
	public static String removeNonWordCharacters(String str) {
		return str.replaceAll("\\W", "");
	}
	
	public static String removeSpaces(String str) {
		return str.replaceAll("\\s", "");
	}

}
