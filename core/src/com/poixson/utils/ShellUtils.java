package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.tools.Keeper;


public class ShellUtils {

	protected static final AtomicReference<ShellUtils> instance = new AtomicReference<ShellUtils>(null);



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



	protected static ShellUtils Get() {
		if (instance.get() == null) {
			// extended utility
			final Class<ShellUtils> clss =
				ReflectUtils.GetClass(
					"com.poixson.utils.ShellUtils_Extended"
				);
			if (clss != null) {
				final ShellUtils utility = (ShellUtils) ReflectUtils.NewInstance(clss);
				if (utility == null) throw new RuntimeException("Unable to initialize ShellUtils instance");
				if (instance.compareAndSet(null, utility))
					return utility;
				return instance.get();
			}
			// default utility
			final ShellUtils utility = new ShellUtils();
			if (instance.compareAndSet(null, utility))
				return utility;
		}
		return instance.get();
	}
	protected ShellUtils() {
		Keeper.add(this);
	}



	// -------------------------------------------------------------------------------
	// colors



	public static String RenderAnsi(final String line) {
		return Get()._renderAnsi(line);
	}
	protected String _renderAnsi(final String line) {
		return StripColorTags(line);
	}

	public static String[] RenderAnsi(final String[] lines) {
		return Get()._renderAnsi(lines);
	}
	protected String[] _renderAnsi(final String[] lines) {
		return StripColorTags(lines);
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



}
