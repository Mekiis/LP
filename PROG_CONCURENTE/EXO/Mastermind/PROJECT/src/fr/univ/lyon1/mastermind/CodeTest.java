package fr.univ.lyon1.mastermind;

import static org.junit.Assert.*;
import static fr.univ.lyon1.mastermind.Peg.*;

import org.junit.Test;

public class CodeTest {

	@Test
	public void testNextCode() {
		assertEquals(Code.valueOf(GREEN,RED,RED,RED), Code.valueOf(RED,RED,RED,RED).nextCode());
		assertEquals(Code.valueOf(RED,GREEN,RED,RED), Code.valueOf(PURPLE,RED,RED,RED).nextCode());
		assertEquals(Code.valueOf(RED,BLUE,RED,RED), Code.valueOf(PURPLE,GREEN,RED,RED).nextCode());
		assertEquals(Code.valueOf(RED,RED,GREEN,RED), Code.valueOf(PURPLE,PURPLE,RED,RED).nextCode());
		assertEquals(Code.valueOf(RED,RED,RED,YELLOW), Code.valueOf(PURPLE,PURPLE,PURPLE,BLUE).nextCode());
	}
	
	@Test
	public void nextCodeShouldOverflowToFirstCode(){
		assertEquals(Code.valueOf(RED), Code.valueOf(PURPLE).nextCode());
		assertEquals(Code.valueOf(RED,RED), Code.valueOf(PURPLE,PURPLE).nextCode());
	}

}
