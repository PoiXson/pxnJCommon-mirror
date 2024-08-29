package com.poixson.tools;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;


public class Test_xTime {



	@Test
	public void testConstruct() {
		// ()
		{
			final xTime time = new xTime();
			Assert.assertEquals( 0L, time.ms() );
		}
		// (ms)
		{
			final xTime time = new xTime(123L);
			Assert.assertEquals( 123L, time.ms() );
		}
		// (value, xunit)
		{
			final xTime time = new xTime(123L, xTimeU.S);
			Assert.assertEquals( 123000L, time.ms() );
		}
		// (value, unit)
		{
			final xTime time = new xTime(123L, TimeUnit.SECONDS);
			Assert.assertEquals( 123000L, time.ms() );
		}
		// (string)
		{
			final xTime time = new xTime("123s");
			Assert.assertEquals( 123000L, time.ms() );
		}
		// (xtime)
		{
			final xTime timeA = new xTime(123L);
			final xTime timeB = new xTime(timeA);
			Assert.assertNotEquals( timeA, timeB );
			Assert.assertEquals( 123L, timeA.ms() );
			Assert.assertEquals( 123L, timeB.ms() );
		}
		// clone()
		{
			final xTime timeA = new xTime(123L);
			final xTime timeB = timeA.clone();
			Assert.assertNotEquals( timeA, timeB );
			Assert.assertEquals( 123L, timeA.ms() );
			Assert.assertEquals( 123L, timeB.ms() );
		}
	}



	@Test
	public void testGet() {
		final xTime time = new xTime(123L);
		Assert.assertEquals( 123L,   time.get(xTimeU.MS) );
		Assert.assertEquals( 123L,   time.get(TimeUnit.MILLISECONDS) );
		Assert.assertEquals("123ms", time.toString() );
		Assert.assertEquals( 123L,   time.ms() );
		Assert.assertEquals( 20, (new xTime( 1L, xTimeU.S)).ticks(50L));
	}



	@Test
	public void testSet() {
		final xTime time = new xTime();
		time.set(           123L                  ); Assert.assertEquals(    123L, time.ms() ); // (ms)
		time.set(           123L, xTimeU.S        ); Assert.assertEquals( 123000L, time.ms() ); // (value, xunit)
		time.set(           321L, TimeUnit.SECONDS); Assert.assertEquals( 321000L, time.ms() ); // (value, unit)
		time.set(          "123s"                 ); Assert.assertEquals( 123000L, time.ms() ); // (string)
		time.set( new xTime(123L)                 ); Assert.assertEquals(    123L, time.ms() ); // (xtime)
		// reset()
		time.reset();
		Assert.assertEquals( 0L, time.ms() );
	}



	@Test
	public void testAdd() {
		final xTime time = new xTime(123L);
		Assert.assertEquals(123L, time.ms() );
		time.add(123L                  ); Assert.assertEquals(   246L, time.ms() ); // (ms)
		time.add(123L, xTimeU.S        ); Assert.assertEquals(123246L, time.ms() ); // (value, xunit)
		time.add(123L, TimeUnit.SECONDS); Assert.assertEquals(246246L, time.ms() ); // (value, unit)
		// (string)
		time.reset();
		time.add("123s");
		Assert.assertEquals(123000L, time.ms() );
		// (xtime)
		time.add( new xTime(123L) );
		Assert.assertEquals(123123L, time.ms() );
	}



	@Test
	public void testParse() {
		Assert.assertEquals(       123L, xTime.ParseToLong("123" ));
		Assert.assertEquals(    123000L, xTime.ParseToLong("123s"));
		Assert.assertEquals(60L*123000L, xTime.ParseToLong("123m"));
		Assert.assertEquals(       500L, xTime.ParseToLong("0.5s"));
		{
			final xTime time = xTime.Parse("123");
			Assert.assertNotNull(time);
			Assert.assertEquals(123L, time.ms());
		}
		{
			final xTime time = xTime.Parse("123m");
			Assert.assertNotNull(time);
			Assert.assertEquals(7380000L, time.ms());
		}
		{
			final xTime time = xTime.Parse("123 hours");
			Assert.assertNotNull(time);
			Assert.assertEquals(442800000L, time.ms());
		}
		{
			final xTime time = xTime.Parse("1h 2m 3s 4");
			Assert.assertNotNull(time);
			Assert.assertEquals(3723004L, time.ms());
		}
		{
			final xTime time = xTime.Parse("1 hours 2 minutes 3 seconds 4");
			Assert.assertNotNull(time);
			Assert.assertEquals(3723004L, time.ms());
		}
	}



	@Test
	public void testToString() {
		Assert.assertEquals("123ms",                     (new xTime(123L)).toString()          );
		Assert.assertEquals("2m 3s 456ms",               (new xTime(123456L)).toString()       );
		Assert.assertEquals("2 minutes",                 (new xTime(123456L)).toRoundedString());
		Assert.assertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toFullString()   );
		Assert.assertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toRoundedString(-1));
		Assert.assertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toRoundedString(0));
		Assert.assertEquals("2 minutes",                 (new xTime(123456L)).toRoundedString(1));
		Assert.assertEquals("2 minutes 3 seconds",       (new xTime(123456L)).toRoundedString(2));
		Assert.assertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toRoundedString(3));
	}



}
