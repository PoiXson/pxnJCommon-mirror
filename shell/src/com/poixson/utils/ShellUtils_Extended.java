package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import com.poixson.tools.StdIO;


public final class ShellUtils_Extended extends ShellUtils {



	public ShellUtils_Extended() {
		super();
		StdIO.Init();
		try {
			AnsiConsole.systemInstall();
		} catch (Exception e) {
			e.printStackTrace(StdIO.OriginalErr());
		}
	}



//TODO
/*
	// -------------------------------------------------------------------------------
	// command handler



	protected final AtomicReference<xCommandHandler> handler = new AtomicReference<xCommandHandler>(null);



	@Override
	public xCommandHandler getCommandHandler() {
		return this.handler.get();
	}
	@Override
	public void setCommandHandler(final xCommandHandler handler) {
		if (handler == null) {
			this.handler.set(null);
		} else {
			if (!this.handler.compareAndSet(null, handler))
				throw new IllegalStateException("Command handler already set!");
		}
	}

	@Override
	public boolean process(final String line) {
		final xCommandHandler handler = this.handler.get();
		if (handler == null) throw new UnsupportedOperationException("Command handler not set");
		return handler.process(line);
	}
*/



	// -------------------------------------------------------------------------------
	// colors



	@Override
	protected String _renderAnsi(final String line) {
		if (IsEmpty(line))
			return line;
		return
			Ansi.ansi()
				.render(line)
				.toString();
	}
	@Override
	protected String[] _renderAnsi(final String[] lines) {
		if (IsEmpty(lines))
			return lines;
		String[] result = new String[ lines.length ];
		for (int index=0; index<lines.length; index++)
			result[index] = (IsEmpty(lines[index]) ? "" : Ansi.ansi().render( lines[index] ).toString());
		return result;
	}



}
