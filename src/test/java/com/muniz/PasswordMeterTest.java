package com.muniz;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordMeterTest {

	@Test
	public void testCheckNumberOfCharacters() {
		
		assertEquals(100,PasswordMeter.scorePassword("21"));
	}

}
