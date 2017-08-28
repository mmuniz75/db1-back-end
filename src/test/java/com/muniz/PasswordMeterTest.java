package com.muniz;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordMeterTest {

	@Test
	public void testCheckNumberOfCharacters() {
		assertEquals(7,new PasswordMeter().scorePassword("qwert"));
	}
	
	
	@Test
	public void testCheckUppercaseLetters() {
		assertEquals(25,new PasswordMeter().scorePassword("QwErT"));
	}

	@Test
	public void testCheckLowercaseLetters() {
		assertEquals(25,new PasswordMeter().scorePassword("QwErT"));
	}
		
	@Test
	public void testCheckNumbers() {
		assertEquals(80,new PasswordMeter().scorePassword("Qw2E6r7T"));
	}
	
	@Test
	public void testCheckcheckSymbols() {
		assertEquals(100,new PasswordMeter().scorePassword("Q!w#2E%6r*7T"));
	}

		
	@Test
	public void testCheckRequirements() {
		assertEquals(92,new PasswordMeter().scorePassword("qA1!gT4%"));
	}
		
	
	@Test
	public void testCheckLettersOnly() {
		assertEquals(22,new PasswordMeter().scorePassword("abcdABCD"));
	}
	
	@Test
	public void testCheckLettersOnly2() {
		assertEquals(0,new PasswordMeter().scorePassword("abcdefghi"));
	}
		
	@Test
	public void testCheckNumbersOnly() {
		assertEquals(4,new PasswordMeter().scorePassword("123456789"));
	}
		
	@Test
	public void testMiddleNumbersOrSymbols() {
		assertEquals(89,new PasswordMeter().scorePassword("1ds2d22##d"));
	}
	
	@Test
	public void testConsecutiveUppercaseLetters() {
		assertEquals(16,new PasswordMeter().scorePassword("aAAaAAsSSaSSaSS"));
	}
	
	@Test
	public void testConsecutiveLowercaseLetters() {
		assertEquals(16,new PasswordMeter().scorePassword("AaaAaaSssAssAss"));
	}
	
	@Test
	public void testConsecutiveNumbers() {
		assertEquals(100,new PasswordMeter().scorePassword("A23A45S67sA90A27s"));
	}
	
	@Test
	public void testSequentialLetters() {
		assertEquals(23,new PasswordMeter().scorePassword("aBcdREFG"));
	}
	
	@Test
	public void testSequentialNumbers() {
		assertEquals(10,new PasswordMeter().scorePassword("2345789"));
	}
	
	@Test
	public void testSequentialSymbols() {
		assertEquals(85,new PasswordMeter().scorePassword("#$%*&*(#"));
	}
	
	
}
