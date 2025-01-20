package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(Assertions.class)
public class Test_xTime {



	@Test
	public void testConstruct() {
		// ()
		{
			final xTime time = new xTime();
			AssertEquals( 0L, time.ms() );
		}
		// (ms)
		{
			final xTime time = new xTime(123L);
			AssertEquals( 123L, time.ms() );
		}
		// (value, xunit)
		{
			final xTime time = new xTime(123L, xTimeU.S);
			AssertEquals( 123000L, time.ms() );
		}
		// (value, unit)
		{
			final xTime time = new xTime(123L, TimeUnit.SECONDS);
			AssertEquals( 123000L, time.ms() );
		}
		// (string)
		{
			final xTime time = new xTime("123s");
			AssertEquals( 123000L, time.ms() );
		}
		// (xtime)
		{
			final xTime timeA = new xTime(123L);
			final xTime timeB = new xTime(timeA);
			AssertNotEquals( timeA, timeB );
			AssertEquals( 123L, timeA.ms() );
			AssertEquals( 123L, timeB.ms() );
		}
		// clone()
		{
			final xTime timeA = new xTime(123L);
			final xTime timeB = timeA.clone();
			AssertNotEquals( timeA, timeB );
			AssertEquals( 123L, timeA.ms() );
			AssertEquals( 123L, timeB.ms() );
		}
	}



	@Test
	public void testGet() {
		final xTime time = new xTime(123L);
		AssertEquals( 123L,   time.get(xTimeU.MS) );
		AssertEquals( 123L,   time.get(TimeUnit.MILLISECONDS) );
		AssertEquals("123ms", time.toString() );
		AssertEquals( 123L,   time.ms() );
		AssertEquals( 20, (new xTime( 1L, xTimeU.S)).ticks(50L));
	}



	@Test
	public void testSet() {
		final xTime time = new xTime();
		time.set(           123L                  ); AssertEquals(    123L, time.ms() ); // (ms)
		time.set(           123L, xTimeU.S        ); AssertEquals( 123000L, time.ms() ); // (value, xunit)
		time.set(           321L, TimeUnit.SECONDS); AssertEquals( 321000L, time.ms() ); // (value, unit)
		time.set(          "123s"                 ); AssertEquals( 123000L, time.ms() ); // (string)
		time.set( new xTime(123L)                 ); AssertEquals(    123L, time.ms() ); // (xtime)
		// reset()
		time.reset();
		AssertEquals( 0L, time.ms() );
	}



	@Test
	public void testAdd() {
		final xTime time = new xTime(123L);
		AssertEquals(123L, time.ms() );
		time.add(123L                  ); AssertEquals(   246L, time.ms() ); // (ms)
		time.add(123L, xTimeU.S        ); AssertEquals(123246L, time.ms() ); // (value, xunit)
		time.add(123L, TimeUnit.SECONDS); AssertEquals(246246L, time.ms() ); // (value, unit)
		// (string)
		time.reset();
		time.add("123s");
		AssertEquals(123000L, time.ms() );
		// (xtime)
		time.add( new xTime(123L) );
		AssertEquals(123123L, time.ms() );
	}



	@Test
	public void testParse() {
		AssertEquals(       123L, xTime.ParseToLong("123" ));
		AssertEquals(    123000L, xTime.ParseToLong("123s"));
		AssertEquals(60L*123000L, xTime.ParseToLong("123m"));
		AssertEquals(       500L, xTime.ParseToLong("0.5s"));
		{
			final xTime time = xTime.Parse("123");
			assertNotNull(time);
			AssertEquals(123L, time.ms());
		}
		{
			final xTime time = xTime.Parse("123m");
			assertNotNull(time);
			AssertEquals(7380000L, time.ms());
		}
		{
			final xTime time = xTime.Parse("123 hours");
			assertNotNull(time);
			AssertEquals(442800000L, time.ms());
		}
		{
			final xTime time = xTime.Parse("1h 2m 3s 4");
			assertNotNull(time);
			AssertEquals(3723004L, time.ms());
		}
		{
			final xTime time = xTime.Parse("1 hours 2 minutes 3 seconds 4");
			assertNotNull(time);
			AssertEquals(3723004L, time.ms());
		}
	}



	@Test
	public void testToString() {
		AssertEquals("123ms",                     (new xTime(123L)).toString()          );
		AssertEquals("2m 3s 456ms",               (new xTime(123456L)).toString()       );
		AssertEquals("2 minutes",                 (new xTime(123456L)).toRoundedString());
		AssertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toFullString()   );
		AssertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toRoundedString(-1));
		AssertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toRoundedString(0));
		AssertEquals("2 minutes",                 (new xTime(123456L)).toRoundedString(1));
		AssertEquals("2 minutes 3 seconds",       (new xTime(123456L)).toRoundedString(2));
		AssertEquals("2 minutes 3 seconds 456ms", (new xTime(123456L)).toRoundedString(3));
	}



}
