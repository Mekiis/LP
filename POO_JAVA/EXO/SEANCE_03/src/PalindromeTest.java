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

	public void shouldBeAPalindrome(String str) {
		assertTrue(Palindrome.isPalindrome(str));
	}

	public void shouldNotBeAPalindrome(String str) {
		assertFalse(Palindrome.isPalindrome(str));
	}

}
