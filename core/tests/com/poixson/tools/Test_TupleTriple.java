package com.poixson.tools;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.tools.abstractions.Triple;
import com.poixson.tools.abstractions.Tuple;


public class Test_TupleTriple {



	@Test
	public void testTuple() {
		Assert.assertEquals(
			-591262665,
			(new Tuple<Integer, Integer>(
				Integer.valueOf( 123456789),
				Integer.valueOf(-123456789)
			)
		).hashCode());
	}



	@Test
	public void testTriple() {
		Assert.assertEquals(
			-914705540,
			(new Triple<Integer, Integer, Integer>(
				Integer.valueOf( 123456789),
				Integer.valueOf(-123456789),
				Integer.valueOf( 234567891)
			)
		).hashCode());
	}



}
