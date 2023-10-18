package com.poixson.tools;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


//TODO: be sure this is loaded
public final class StdIO {
	private StdIO() {}

	private static final AtomicBoolean inited = new AtomicBoolean(false);

	private static final AtomicReference<PrintStream> OriginalOut = new AtomicReference<PrintStream>(null);
	private static final AtomicReference<PrintStream> OriginalErr = new AtomicReference<PrintStream>(null);
	private static final AtomicReference<InputStream> OriginalIn  = new AtomicReference<InputStream>(null);



	public static void Init() {
		if (inited.compareAndSet(false, true)) {
			Keeper.add(new StdIO());
			OriginalOut.set(System.out);
			OriginalErr.set(System.err);
			OriginalIn .set(System.in );
		}
	}



	public static PrintStream OriginalOut() {
		return OriginalOut.get();
	}
	public static PrintStream OriginalErr() {
		return OriginalErr.get();
	}
	public static InputStream OriginalIn() {
		return OriginalIn.get();
	}



}
