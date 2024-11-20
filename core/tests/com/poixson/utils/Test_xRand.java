package com.poixson.utils;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.tools.xRand;


public class Test_xRand {



	@Test
	public void testXRand() {
		final int low  = 1;
		final int high = 5;
		boolean found_low  = false;
		boolean found_high = false;
		final xRand rnd = (new xRand()).seed_time();
		for (int i=0; i<1000; i++) {
			final int val = rnd.nextInt(low, high+1);
			if (val == low ) found_low  = true;
			if (val == high) found_high = true;
			if (found_low && found_high) break;
			Assert.assertFalse(val < low );
			Assert.assertFalse(val > high);
		}
		Assert.assertTrue(found_low);
		Assert.assertTrue(found_high);
	}



}
