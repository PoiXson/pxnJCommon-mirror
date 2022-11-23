package com.poixson.tools;


public class StringParser {

	public final String data;
	public final int data_len;
	public int pos = 0;



	public StringParser(final String data) {
		this.data     = data;
		this.data_len = data.length();
	}



	public void trim(final int len) {
		this.pos += len;
	}

	public void trim() {
		while (!this.eof()) {
			final char chr = this.data.charAt(this.pos);
			if (chr != ' '
			&&  chr != '\t'
			&&  chr != '\n'
			&&  chr != '\r')
				break;
			this.pos++;
			continue;
		}
	}



	@Override
	public String toString() {
		if (this.eof())
			return "";
		return this.data.substring(this.pos);
	}



	public char getChar(final int pos) {
		return this.data.charAt(pos + this.pos);
	}
	public char getChar() {
		return this.data.charAt(this.pos);
	}



	public boolean matchNext(final char chr) {
		if (this.eof())
			return false;
		return (this.data.charAt(this.pos) == chr);
	}
	public boolean matchNext(final String match) {
		if (this.pos >= this.data_len-1)
			return false;
		return (this.data.substring(this.pos, match.length()) == match);
	}



	public boolean eof() {
		return (this.pos >= this.data_len - 1);
	}



}
