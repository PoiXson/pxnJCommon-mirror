package com.poixson.utils;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.tools.xRand;


public class Test_xRand {



	@Test
	public void testXRand_int() {
		final int min = 1;
		final int max = 5;
		boolean found_min = false;
		boolean found_max = false;
		final xRand rnd = (new xRand()).seed_time();
		for (int i=0; i<1000; i++) {
			final int value = rnd.nextInt(min, max);
			Assert.assertFalse(value+"<min"+min, value < min);
			Assert.assertFalse(value+">max"+max, value > max);
			if (value == min  ) found_min = true;
			if (value == max-1) found_max = true;
		}
		Assert.assertTrue("min int not found", found_min);
		Assert.assertTrue("max int not found", found_max);
	}

	@Test
	public void testXRand_long() {
		final long min = 1L;
		final long max = 5L;
		boolean found_min = false;
		boolean found_max = false;
		final xRand rnd = (new xRand()).seed_time();
		for (int i=0; i<1000; i++) {
			final long value = rnd.nextLong(min, max);
			Assert.assertFalse(value+"<min"+min, value < min);
			Assert.assertFalse(value+">max"+max, value > max);
			if (value == min   ) found_min = true;
			if (value == max-1L) found_max = true;
		}
		Assert.assertTrue("min long not found", found_min);
		Assert.assertTrue("max long not found", found_max);
	}

	@Test
	public void testXRand_float() {
		final float min = 1.0f;
		final float max = 5.0f;
		boolean found_min = false;
		boolean found_max = false;
		final xRand rnd = (new xRand()).seed_time();
		for (int i=0; i<1000; i++) {
			final float value = rnd.nextFloat(min, max);
			Assert.assertFalse(value+"<min"+min, value < min);
			Assert.assertFalse(value+">max"+max, value > max);
			if (value >= min      && value < min+1.0f) found_min = true;
			if (value >= max-1.0f && value < max     ) found_max = true;
		}
		Assert.assertTrue("min float not found", found_min);
		Assert.assertTrue("max float not found", found_max);
	}

	@Test
	public void testXRand_double() {
		final double min = 1.0;
		final double max = 5.0;
		boolean found_min = false;
		boolean found_max = false;
		final xRand rnd = (new xRand()).seed_time();
		for (int i=0; i<1000; i++) {
			final double value = rnd.nextDouble(min, max);
			Assert.assertFalse(value+"<min"+min, value < min);
			Assert.assertFalse(value+">max"+max, value > max);
			if (value >= min     && value < min+1.0) found_min = true;
			if (value >= max-1.0 && value < max    ) found_max = true;
		}
		Assert.assertTrue("min double not found", found_min);
		Assert.assertTrue("max double not found", found_max);
	}



}
