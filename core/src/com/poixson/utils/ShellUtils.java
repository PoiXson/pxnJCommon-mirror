package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.fusesource.jansi.Ansi;

import com.poixson.tools.Keeper;


public final class ShellUtils {
	private ShellUtils() {}
	static { Keeper.Add(new ShellUtils()); }



	// -------------------------------------------------------------------------------
	// colors



	public static enum AnsiColor {
		BLACK,
		RED,
		GREEN,
		YELLOW,
		BLUE,
		MAGENTA,
		CYAN,
		WHITE
	};



	public static String RenderAnsi(final String line) {
//TODO: if not color
//return StripColorTags(line);
//TODO: AnsiConsole.systemInstall();
		if (IsEmpty(line))
			return line;
		return Ansi.ansi()
			.render(line)
			.toString();
	}

	public static String[] RenderAnsi(final String[] lines) {
//TODO: if not color
//return StripColorTags(lines);
		if (IsEmpty(lines))
			return lines;
		String[] result = new String[ lines.length ];
		for (int index=0; index<lines.length; index++)
			result[index] = (IsEmpty(lines[index]) ? "" : Ansi.ansi().render( lines[index] ).toString());
		return result;
	}



	// strip color tags
	public static String StripColorTags(final String line) {
		if (IsEmpty(line)) return line;
		final StringBuilder result = new StringBuilder(line);
		boolean changed = false;
		while (true) {
			final int posA = result.indexOf("@|");
			if (posA == -1) break;
			final int posB = result.indexOf(" ", posA);
			final int posC = result.indexOf("|@", posB);
			if (posB == -1) break;
			if (posC == -1) break;
			result.replace(posC, posC+2, "");
			result.replace(posA, posB+1, "");
			changed = true;
		}
		if (changed)
			return result.toString();
		return line;
	}
	public static String[] StripColorTags(final String[] lines) {
		if (IsEmpty(lines)) return lines;
		String[] result = new String[ lines.length ];
		for (int index=0; index<lines.length; index++) {
			result[index] = StripColorTags(result[index]);
		}
		return result;
	}



	// -------------------------------------------------------------------------------
	// raw terminal mode



	protected static final AtomicBoolean mode_raw = new AtomicBoolean(false);

	public static void RawTerminal() throws IOException, InterruptedException {
		if (mode_raw.compareAndSet(false, true)) {
			Runtime.getRuntime().exec(new String[] {
				"/bin/sh", "-c", "stty -echo raw </dev/tty"
			}).waitFor();
		}
	}
	public static void RestoreTerminal() throws IOException, InterruptedException {
		if (mode_raw.compareAndSet(true, false)) {
			Runtime.getRuntime().exec(new String[] {
				"/bin/sh", "-c", "stty echo cooked </dev/tty"
			}).waitFor();
		}
	}



}
