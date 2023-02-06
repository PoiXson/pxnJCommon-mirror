package com.poixson.tools;


public class JsonChunker {

	protected final StringBuilder buffer = new StringBuilder();

	protected boolean insideSingleQuote = false;
	protected boolean insideDoubleQuote = false;
	protected int insideBrackets = 0;

	protected final ChunkProcessor processor;



	public interface ChunkProcessor {
		public void process(final String data);
	}



	public JsonChunker(final ChunkProcessor processor) {
		this.processor = processor;
	}



	public void process(final String data) {
		final int len = data.length();
		for (int i=0; i<len; i++) {
			this.process(data.charAt(i));
		}
	}
	public void process(final char chr) {
		if (chr == '\r') return;
		if (this.buffer.isEmpty()) {
			switch (chr) {
			case '{': break;
			case '\n': case ',':
			case '\t': case ' ': return;
			default:
				throw new RuntimeException(
					String.format(
						"JSON must start with { bracket, found %s <%d>",
						Character.valueOf(chr),
						Integer.valueOf(chr)
					)
				);
			}
		}
		switch (chr) {
		case '{': {
			if (this.insideSingleQuote) break;
			if (this.insideDoubleQuote) break;
			this.insideBrackets++;
			break;
		}
		case '}': {
			if (this.insideSingleQuote) break;
			if (this.insideDoubleQuote) break;
			this.insideBrackets--;
			if (this.insideBrackets == 0) {
				this.buffer.append('}');
				this.processor.process(this.buffer.toString());
				this.buffer.setLength(0);
				return;
			} else
			if (this.insideBrackets < 0) {
				throw new RuntimeException("Invalid brackets in json");
			}
			break;
		}
		case '\'': {
			if (this.insideDoubleQuote) break;
			this.insideSingleQuote = !this.insideSingleQuote;
			break;
		}
		case '"': {
			if (this.insideSingleQuote) break;
			this.insideDoubleQuote = !this.insideDoubleQuote;
			break;
		}
		default: break;
		}
		this.buffer.append(chr);
	}



}
