/*
package com.poixson.tools.localization;

import static com.poixson.utils.SanUtils.IsAlpha;
import static com.poixson.utils.Utils.IsEmpty;


public class LangToken {

	public final char a;
	public final char b;



	public LangToken Create(final String token) {
		return (IsEmpty(token) ? null : new LangToken(token));
	}
	protected LangToken(final String token) {
		if (token == null) throw new NullPointerException();
		if (token.length() != 2) throw new IllegalArgumentException("Invalid language: "+token);
		this.a = token.charAt(0);
		this.b = token.charAt(1);
		if (!IsAlpha(this.a)) throw new IllegalArgumentException("Invalid language: "+token);
		if (!IsAlpha(this.b)) throw new IllegalArgumentException("Invalid language: "+token);
	}



	@Override
	public String toString() {
		return (new StringBuilder()).append(this.a).append(this.b).toString();
	}
	@Override
	public int hashCode() {
		return this.a + (this.b * 256);
	}



	@Override
	public boolean equals(final Object match) {
		if (match == null) throw new NullPointerException();
		return this.equals((LangToken)match);
	}
	public boolean equals(final LangToken match) {
		if (match == null) throw new NullPointerException();
		return
			match.a == this.a &&
			match.b == this.b;
	}
	public boolean equals(final String match) {
		if (match == null) throw new NullPointerException();
		if (match.length() != 2) throw new IllegalArgumentException("Invalid language: "+match);
		return
			match.charAt(0) == this.a &&
			match.charAt(1) == this.b;
	}
	public boolean equalsIgnoreCase(final String match) {
		return (
			IsEmpty(match)
			? false
			: this.equals(match.toLowerCase())
		);
	}



}
*/
