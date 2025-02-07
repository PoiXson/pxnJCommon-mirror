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
			Keeper.Add(new StdIO());
			OriginalOut.set(System.out);
			OriginalErr.set(System.err);
			OriginalIn .set(System.in );
		}
	}



	public static PrintStream OriginalOut() {
		final PrintStream out = OriginalOut.get();
		return (out==null ? System.out : out);
	}
	public static PrintStream OriginalErr() {
		final PrintStream err = OriginalErr.get();
		return (err==null ? System.err : err);
	}
	public static InputStream OriginalIn() {
		final InputStream in = OriginalIn.get();
		return (in==null ? System.in : in);
	}



}
