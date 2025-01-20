package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertFalse;
import static com.poixson.tools.Assertions.AssertTrue;

import org.junit.jupiter.api.Test;


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
			AssertFalse((value<min), value+"<min"+min);
			AssertFalse((value>max), value+">max"+max);
			if (value == min  ) found_min = true;
			if (value == max-1) found_max = true;
		}
		AssertTrue(found_min, "min int not found");
		AssertTrue(found_max, "max int not found");
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
			AssertFalse((value<min), value+"<min"+min);
			AssertFalse((value>max), value+">max"+max);
			if (value == min   ) found_min = true;
			if (value == max-1L) found_max = true;
		}
		AssertTrue(found_min, "min long not found");
		AssertTrue(found_max, "max long not found");
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
			AssertFalse((value<min), value+"<min"+min);
			AssertFalse((value>max), value+">max"+max);
			if (value >= min      && value < min+1.0f) found_min = true;
			if (value >= max-1.0f && value < max     ) found_max = true;
		}
		AssertTrue(found_min, "min float not found");
		AssertTrue(found_max, "max float not found");
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
			AssertFalse((value<min), value+"<min"+min);
			AssertFalse((value>max), value+">max"+max);
			if (value >= min     && value < min+1.0) found_min = true;
			if (value >= max-1.0 && value < max    ) found_max = true;
		}
		AssertTrue(found_min, "min double not found");
		AssertTrue(found_max, "max double not found");
	}



}
