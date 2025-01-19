package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import com.poixson.tools.JsonChunker.ChunkProcessor;


public class Test_JsonChunker {

	protected static final String JSON_A =
		"{\n" +
			"\t\"key1\": \"Value1\",\n" +
			"\t\"key2\": \"Value2\"\n"  +
		"  }";
	protected static final String JSON_B =
		"{\n"  +
			"\t\"key3\": \"Value3\",\n" +
			"\t\"key4\": \"Value4\"\n"  +
		"}";



	class ChunkProcessorTest implements ChunkProcessor {
		public LinkedList<String> results = new LinkedList<String>();

		@Override
		public void process(final String data) {
			this.results.add(data);
		}

	}



	@Test
	public void testConstruct() {
		final ChunkProcessorTest chunk = new ChunkProcessorTest();
		final JsonChunker buffer = new JsonChunker(chunk);
		buffer.process(" " + JSON_A + " , \n\t" + JSON_B + " \n");
		AssertEquals(JSON_A, chunk.results.get(0));
		AssertEquals(JSON_B, chunk.results.get(1));
	}



}
