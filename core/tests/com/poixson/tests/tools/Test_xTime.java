package com.poixson.tests.tools;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.exceptions.UnmodifiableObjectException;
import com.poixson.tools.xTime;
import com.poixson.tools.xTimeU;


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
		Assert.assertEquals( 123L, time.get(xTimeU.MS) );
		Assert.assertEquals( 123L, time.get(TimeUnit.MILLISECONDS) );
		Assert.assertEquals( "123ms", time.toString() );
		Assert.assertEquals( 123L, time.ms() );
		Assert.assertEquals( 20, (new xTime( 1L, xTimeU.S)).ticks()   );
		Assert.assertEquals( 20, (new xTime(20L, xTimeU.S)).seconds() );
		Assert.assertEquals( 20, (new xTime(20L, xTimeU.M)).minutes() );
		Assert.assertEquals( 20, (new xTime(20L, xTimeU.H)).hours()   );
		Assert.assertEquals( 20, (new xTime(20L, xTimeU.D)).days()    );
		Assert.assertEquals( 20, (new xTime(20L, xTimeU.W)).weeks()   );
		Assert.assertEquals( 20, (new xTime(20L, xTimeU.MO)).months() );
		Assert.assertEquals( 20, (new xTime(20L, xTimeU.Y)).years()   );
	}



	@Test
	public void testSet() {
		final xTime time = new xTime();
		// (ms)
		time.set(123L);
		Assert.assertEquals( 123L, time.ms() );
		// (value, xunit)
		time.set(123L, xTimeU.S);
		Assert.assertEquals( 123000L, time.ms() );
		// (value, unit)
		time.set(321L, TimeUnit.SECONDS);
		Assert.assertEquals( 321000L, time.ms() );
		// (string)
		time.set("123s");
		Assert.assertEquals( 123000L, time.ms() );
		// (xtime)
		time.set( new xTime(123L) );
		Assert.assertEquals( 123L, time.ms() );
		// reset()
		time.reset();
		Assert.assertEquals( 0L, time.ms() );
	}



	@Test
	public void testAdd() {
		final xTime time = new xTime(123L);
		Assert.assertEquals(123L, time.ms() );
		// (ms)
		time.add(123L);
		Assert.assertEquals(246L, time.ms() );
		// (value, xunit)
		time.add(123L, xTimeU.S);
		Assert.assertEquals(123246L, time.ms() );
		// (value, unit)
		time.add(123L, TimeUnit.SECONDS);
		Assert.assertEquals(246246L, time.ms() );
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
		Assert.assertEquals(123L,        xTime.ParseToLong("123" ));
		Assert.assertEquals(123000L,     xTime.ParseToLong("123s"));
		Assert.assertEquals(123000L*60L, xTime.ParseToLong("123m"));
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
		Assert.assertEquals( "123ms",     (new xTime(123L)).toString()           );
		Assert.assertEquals( "2m 3s",     (new xTime(123000L)).toString()        );
		Assert.assertEquals( "2 minutes", (new xTime(123000L).toRoundedString()) );
		Assert.assertEquals( "2 minutes 3 seconds", (new xTime(123000L)).toFullString() );
	}



}
