/*
package com.poixson.tools.netty;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.tools.abstractions.xCloseable;


public abstract class NetConnection implements xCloseable {

	protected static final AtomicInteger next_index = new AtomicInteger(0);

	public final int index;
	public final String key;

	protected final AtomicReference<String> user = new AtomicReference<String>(null);
	protected final AtomicBoolean closed = new AtomicBoolean(false);



	public NetConnection() {
		this.index = next_index.incrementAndGet();
		this.key = this.build_key();
	}



	protected abstract String build_key();



	@Override
	public void close() throws IOException {
		this.closed.set(true);
	}

	@Override
	public boolean isOpen() {
		return !this.isClosed();
	}
	@Override
	public boolean isClosed() {
		return this.closed.get();
	}
	public boolean isAuthed() {
		if (this.isClosed())
			return false;
		return ! IsEmpty(this.user.get());
	}

	public boolean setAuthed(final String user) {
		if (this.isClosed()) throw new IllegalStateException("Socket already closed");
		return this.user.compareAndSet(null, user);
	}



}
*/
