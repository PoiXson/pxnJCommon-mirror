package com.poixson.tests.utils;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.utils.MathUtils;


public class Test_MathUtils {



	@Test
	public void testRemap() {
		// remap percent
		Assert.assertEquals(  0, MathUtils.Remap(0, 10, 0.0) );
		Assert.assertEquals( 10, MathUtils.Remap(0, 10, 1.0) );
		Assert.assertEquals(  5, MathUtils.Remap(1, 10, 0.5) );
		// remap range
		Assert.assertEquals( 25, MathUtils.Remap(1, 10, 21, 30,  5) );
		Assert.assertEquals( 30, MathUtils.Remap(1, 10, 21, 30, 10) );
		// remap 8 bit color
		Assert.assertEquals( Color.BLACK, MathUtils.Remap8BitColor(0B00000000) );
		Assert.assertEquals( Color.WHITE, MathUtils.Remap8BitColor(0B11111111) );
		Assert.assertEquals( Color.RED,   MathUtils.Remap8BitColor(0B00000111) );
		Assert.assertEquals( Color.GREEN, MathUtils.Remap8BitColor(0B00111000) );
		Assert.assertEquals( Color.BLUE,  MathUtils.Remap8BitColor(0B11000000) );
		Assert.assertEquals( 0B00000111,   7);
		Assert.assertEquals( 0B00111000,  56);
		Assert.assertEquals( 0B11000000, 192);
	}



}
