
public class Palindrome {

	public static boolean isPalindrome(String str) {
		if(str == null)
			return false;
		if(str == "")
			return false;
		
		int i = 0;
		int lastCharIndex = str.length() - 1;
		
		while(i < lastCharIndex - i){
			if(str.charAt(i) != str.charAt(lastCharIndex - i))
				return false;
			i++;
		}
		
		return true;
	}

}
