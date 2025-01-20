package com.poixson.tools.abstractions;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.tools.Assertions;


@ExtendWith(Assertions.class)
public class Test_HashMapTimeout {



	@Test
	public void testTimeout() {
		final HashMapTimeout<String, String> map = new HashMapTimeout<String, String>();
		map.setTimeoutTicks(5L);
		map.put("abc", "def");
		for (int i=0; i<10; i++) {
			if (i == 2) map.put("ghi", "jkl");
			if (i < 2) AssertEquals(1, map.size(), "Index: "+Integer.toString(i)); else
			if (i < 6) AssertEquals(2, map.size(), "Index: "+Integer.toString(i)); else
			if (i < 8) AssertEquals(1, map.size(), "Index: "+Integer.toString(i)); else
				AssertEquals(0, map.size());
			map.run();
		}
		AssertTrue(map.isEmpty());
	}



}
