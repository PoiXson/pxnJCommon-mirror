package com.poixson.tools;

import java.awt.Font;

import com.poixson.utils.NumberUtils;
import com.poixson.utils.Utils;


public class xFont {

	public String family = null;
	public int style = Font.PLAIN;
	public int size  = xFontDefault.Get().size;



	public static xFont Build(final String format) {
		return new xFont(format);
	}
	public static xFont Build(final xFont clone) {
		return new xFont(clone);
	}
	public static xFont Build() {
		return new xFont();
	}

	public static Font Font(final String format) {
		return Build(format)
				.font();
	}



	public xFont(final String format) {
		this.apply(format);
	}
	public xFont(final xFont clone) {
		this.apply(clone);
	}
	public xFont() {
	}



	public xFont apply(final String format) {
		if (Utils.isEmpty(format))
			return this;
		final String[] parts = format.split(",");
		PARTS_LOOP:
		for (String part : parts) {
			part = part.trim().toLowerCase();
			if (part.isEmpty()) continue PARTS_LOOP;
			// size
			if (part.startsWith("size")) {
				part = part.substring(4).trim();
				if (part.startsWith("=") || part.startsWith(":")) {
					part = part.substring(1).trim();
				}
				if (part.startsWith("+")) {
					part = part.substring(1).trim();
					final Integer size = NumberUtils.ToInteger(part);
					if (size == null) throw new RuntimeException("Invalid font size: "+part);
					final int sizeDef = xFontDefault.Get().size;
					this.size(sizeDef + size.intValue());
				} else {
					final Integer size = NumberUtils.ToInteger(part);
					if (size == null) throw new RuntimeException("Invalid font size: "+part);
					this.size(size.intValue());
				}
				continue PARTS_LOOP;
			}
			// family
			if (part.startsWith("fam")) {
				part = (
					part.startsWith("family")
					? part.substring(6)
					: part.substring(3)
				);
				if (part.startsWith("=") || part.startsWith(":")) {
					part = part.substring(1).trim();
				}
				this.family(part);
				continue PARTS_LOOP;
			}
			// style
			switch (part) {
			case "b":
			case "bold":
				this.bold();
				continue PARTS_LOOP;
			case "i":
			case "italic":
				this.italic();
				continue PARTS_LOOP;
			case "p":
			case "plain":
				this.plain();
				continue PARTS_LOOP;
			default:
			}
			// unknown format
			throw new RuntimeException("Unknown font format: "+part);
		} // end PARTS_LOOP
		return this;
	}
	public xFont apply(final xFont clone) {
		this.family = clone.family;
		this.style  = clone.style;
		this.size   = clone.size;
		return this;
	}



	// ------------------------------------------------------------------------------- //
	// size



	public xFont size(final int size) {
		this.size = size;
		return this;
	}
	public xFont sizePlus(final int add) {
		this.size += add;
		return this;
	}



	// ------------------------------------------------------------------------------- //
	// style



	public xFont plain() {
		this.style = Font.PLAIN;
		return this;
	}
	public xFont bold() {
		this.style |= Font.BOLD;
		return this;
	}
	public xFont italic() {
		this.style |= Font.ITALIC;
		return this;
	}



	// ------------------------------------------------------------------------------- //
	// font family



	public xFont family(final String family) {
		this.family = family;
		return this;
	}



	// ------------------------------------------------------------------------------- //



	// to java font
	public Font font() {
		return
			new Font(
				this.family,
				this.style,
				this.size
			);
	}



}
