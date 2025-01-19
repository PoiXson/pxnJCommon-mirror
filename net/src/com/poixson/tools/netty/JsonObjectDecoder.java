/*
package com.poixson.tools.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;


// forked from: https://github.com/netty/netty/blob/netty-4.1.103.Final/codec/src/main/java/io/netty/handler/codec/json/JsonObjectDecoder.java
public class JsonObjectDecoder extends ByteToMessageDecoder {

	protected static final int MB_1 = 1024 * 1024; // 1MB

	protected final int buffer_size;
	protected final boolean stream_array_elements;

	protected int index  = 0;
	protected int last_index = 0;
	protected int braces = 0;
	protected boolean in_string = false;

	public enum StreamStateJSON {
		STATE_CORRUPTED,
		STATE_INIT,
		STATE_DECODING_NORMAL,
		STATE_DECODING_ARRAY_STREAM,
	}
	protected StreamStateJSON state = StreamStateJSON.STATE_INIT;



	public JsonObjectDecoder() {
		this(MB_1);
	}
	public JsonObjectDecoder(final int buffer_size) {
		this(buffer_size, false);
	}
	public JsonObjectDecoder(final boolean stream_array_elements) {
		this(MB_1, stream_array_elements);
	}
	public JsonObjectDecoder(final int buffer_size, final boolean stream_array_elements) {
		if (buffer_size < 1) throw new IllegalArgumentException("Invalid JSON buffer size: "+Integer.toString(buffer_size));
		this.buffer_size           = buffer_size;
		this.stream_array_elements = stream_array_elements;
	}



	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf in, final List<Object> out) throws Exception {
		if (StreamStateJSON.STATE_CORRUPTED.equals(this.state)) {
			in.skipBytes(in.readableBytes());
			return;
		}
		if (this.index > in.readerIndex() && this.last_index != in.readerIndex())
			this.index = in.readerIndex() + (this.index - this.last_index);
		final int buffer_index = in.writerIndex();
		// discard oversized buffer
		if (buffer_index > this.buffer_size) {
			in.skipBytes(in.readableBytes());
			this.reset();
			throw new TooLongFrameException(String.format("Object length exceeds %d bytes; dropping %d bytes..", this.buffer_size, buffer_index));
		}
		//LOOP_INDEX:
		for (; this.index < buffer_index; this.index++) {
			final byte c = in.getByte(this.index);
			SWITCH_STATE:
			switch (this.state) {
			case STATE_DECODING_NORMAL: {
				this.decode_byte(c, in);
				// braces closed
				if (this.braces == 0) {
					final int len = (this.index - in.readerIndex()) + 1;
					final ByteBuf json = in.slice(in.readerIndex(), len).retain();
					if (json != null)
						out.add(json);
					in.readerIndex(this.index + 1);
					this.reset();
				}
				break SWITCH_STATE;
			}
			case STATE_DECODING_ARRAY_STREAM: {
				this.decode_byte(c, in);
				if (!this.in_string) {
					if ( (this.braces == 1 && c == ',')
					||   (this.braces == 0 && c == ']') ) {
						// skip leading spaces
						for (int i=in.readerIndex(); Character.isWhitespace(in.getByte(i)); i++)
							in.skipBytes(1);
						int index_no_spaces = this.index - 1;
						// skip trailing spaces
						LOOP_SKIP:
						while (true) {
							if (index_no_spaces < in.readerIndex())                   break LOOP_SKIP;
							if (!Character.isWhitespace(in.getByte(index_no_spaces))) break LOOP_SKIP;
							index_no_spaces--;
						}
						final int len = (index_no_spaces - in.readerIndex()) + 1;
						final ByteBuf json = in.slice(in.readerIndex(), len).retain();
						if (json != null)
							out.add(json);
						in.readerIndex(this.index+1);
						if (c == ']')
							this.reset();
					}
				}
				break SWITCH_STATE;
			}
			default: {
				if (c == '{' || c == '[') {
					this.init_decoding(c);
					if (StreamStateJSON.STATE_DECODING_ARRAY_STREAM.equals(this.state))
						in.skipBytes(1);
				} else
				// skip leading spaces
				if (Character.isWhitespace(c)) {
					in.skipBytes(1);
				} else {
					this.state = StreamStateJSON.STATE_CORRUPTED;
					throw new CorruptedFrameException(String.format("Invalid JSON at byte position: %d %s", this.index, ByteBufUtil.hexDump(in)));
				}
				break SWITCH_STATE;
			}
			} // end SWITCH_STATE
		} // end LOOP_INDEX
		if (in.readableBytes() == 0)
			this.index = 0;
		this.last_index = this.index;
	}



	protected void init_decoding(final byte brace) {
		this.braces = 1;
		if (this.stream_array_elements) {
			if (brace == '[') this.state = StreamStateJSON.STATE_DECODING_ARRAY_STREAM;
			else              this.state = StreamStateJSON.STATE_DECODING_NORMAL;
		} else {
			this.state = StreamStateJSON.STATE_DECODING_NORMAL;
		}
	}



	protected void reset() {
		this.state = StreamStateJSON.STATE_INIT;
		this.in_string = false;
		this.braces = 0;
	}



	protected void decode_byte(final byte c, final ByteBuf in) {
		// inside a string
		if (this.in_string) {
			if (c == '"') {
				int count = 0;
				int i = this.index - 1;
				LOOP_SLASH:
				while (i >= 0) {
					if (in.getByte(i) != '\\')
						break LOOP_SLASH;
					count++;
					i--;
				}
				// double-quote is escaped if odd back-slashes
				if (count % 2 == 0)
					this.in_string = false;
			}
		// not in a string
		} else {
			SWITCH_CHAR:
			switch (c) {
			case '"': this.in_string = true;   break SWITCH_CHAR;
			case '{': case '[': this.braces++; break SWITCH_CHAR;
			case '}': case ']': this.braces--; break SWITCH_CHAR;
			default: break SWITCH_CHAR;
			}
		}
	}



}
*/
