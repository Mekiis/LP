import static org.junit.Assert.*;

import org.junit.Test;


public class PalindromeTest {

	@Test
	public void onNullStringIsNotAPalindrome() {
		assertFalse(Palindrome.isPalindrome(null));
	}

}
