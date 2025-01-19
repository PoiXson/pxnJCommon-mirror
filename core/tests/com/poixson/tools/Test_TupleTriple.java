package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertEquals;

import org.junit.jupiter.api.Test;

import com.poixson.tools.abstractions.Triple;
import com.poixson.tools.abstractions.Tuple;


public class Test_TupleTriple {



	@Test
	public void testTuple() {
		AssertEquals(
			-591262665,
			(new Tuple<Integer, Integer>(
				Integer.valueOf( 123456789),
				Integer.valueOf(-123456789)
			)
		).hashCode());
	}



	@Test
	public void testTriple() {
		AssertEquals(
			-914705540,
			(new Triple<Integer, Integer, Integer>(
				Integer.valueOf( 123456789),
				Integer.valueOf(-123456789),
				Integer.valueOf( 234567891)
			)
		).hashCode());
	}



}
