package com.poixson.tools;

import java.awt.Font;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLog;
import com.poixson.utils.NumberUtils;
import com.poixson.utils.Utils;
import com.poixson.utils.guiUtils;


public class xFont {

	public final AtomicReference<String> family = new AtomicReference<String>(null);
	public final AtomicReference<Style>  style  = new AtomicReference<Style>(Style.PLAIN);
	public final AtomicInteger size = new AtomicInteger(0);
	public final int base = 12;



	public enum Style {
		PLAIN (Font.PLAIN),
		BOLD  (Font.BOLD),
		ITALIC(Font.ITALIC);
		public final int value;
		Style(final int value) {
			this.value = value;
		}
	}



	public xFont(final String format) {
		this();
		if (Utils.notEmpty(format)) {
			this.apply(format);
		}
	}
	public xFont(final xFont font) {
		this();
		this.family.set(font.family.get());
		this.style.set( font.style.get() );
		this.size.set(  font.size.get()  );
	}
	public xFont() {
	}



	public Font font(final String format) {
		if (Utils.isEmpty(format))
			return this.font();
		final xFont font = new xFont(this);
		font.apply(format);
		return font.font();
	}
	public Font font() {
		return new Font(
			this.family.get(),
			this.style.get().value,
			this.base + this.size.get()
		);
	}



	public xFont apply(final String format) {
		if (Utils.isEmpty(format))
			return this;
		final StringParts parts = StringParts.getNew(format);
		parts.delim(",");
		while (parts.hasNext()) {
			String part = parts.part();
			if (part == null) break;
			part = part.trim().toLowerCase();
			if (part.isEmpty()) continue;
			// font style
			switch (part) {
			case "b":
			case "bold":
				this.style.set(Style.BOLD);
				continue;
			case "i":
			case "italic":
				this.style.set(Style.ITALIC);
				continue;
			case "p":
			case "plain":
				this.style.set(Style.PLAIN);
				continue;
			default:
			}
			// font family
			if (part.startsWith("fam")) {
				String str = ( part.startsWith("family") ? part.substring(6) : part.substring(3) ).trim();
				if (str.startsWith("=") || str.startsWith(":")) {
					str = str.substring(1).trim();
				}
				this.family.set(str);
				continue;
			}
			// font size
			if (part.startsWith("size")) {
				String str = part.substring(4).trim();
				if (str.startsWith("=") || str.startsWith(":")) {
					str = str.substring(1).trim();
				}
				if (str.startsWith("+")) {
					str = str.substring(1).trim();
				}
				final Integer i = NumberUtils.ToInteger(str);
				if (i == null) {
					this.log().warning("Invalid font size value:", part);
					continue;
				}
				this.size.set(i.intValue());
				continue;
			}
			// unknown format
			this.log().warning("Unknown font formatting:", part);
		}
		return this;
	}



	// logger
	public xLog log() {
		return guiUtils.log();
	}



}
