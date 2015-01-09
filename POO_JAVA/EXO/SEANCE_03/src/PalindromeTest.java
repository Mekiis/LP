import static org.junit.Assert.*;

import org.junit.Test;


public class PalindromeTest {

	@Test
	public void onNullStringIsNotAPalindrome() {
		shouldNotBeAPalindrome(null);
	}
	
	@Test
	public void onEmptyStringShouldNotBeAPalindrome() {
		shouldNotBeAPalindrome("");
	}
	
	@Test
	public void aSingleCharacterStringShouldBeAPalindrome(){
		shouldBeAPalindrome("a");
	}
	
	@Test
	public void twoCharactersStringsTests(){
		shouldBeAPalindrome("aa");
		shouldNotBeAPalindrome("ab");
	}
	
	@Test
	public void threeCharactersStringsTests(){
		shouldBeAPalindrome("aba");
		shouldNotBeAPalindrome("abc");
	}
	
	@Test
	public void fourCharactersStringsTests(){
		shouldBeAPalindrome("abba");
		shouldNotBeAPalindrome("abca");
	}
	
	@Test
	public void accentsShouldBeIgnored(){
		shouldBeAPalindrome("étè");
	}
	
	@Test
	public void spacesShouldBeIgnored(){
		shouldBeAPalindrome("le sel");
	}
	
	@Test
	public void majusculesShouldBeIgnored(){
		shouldBeAPalindrome("Le sel");
	}
	
	@Test
	public void apostrophesShouldBeIgnored(){
		shouldBeAPalindrome("Tu l'as trop écrasé César ce port salut");
	}
	
	@Test
	public void ponctuationShouldBeIgnored(){
		shouldBeAPalindrome("Tu l'as trop écrasé César, ce port salut !");
	}

	public void shouldBeAPalindrome(String str) {
		assertTrue(Palindrome.isPalindrome(str));
	}

	public void shouldNotBeAPalindrome(String str) {
		assertFalse(Palindrome.isPalindrome(str));
	}

}
