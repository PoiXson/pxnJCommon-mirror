package com.poixson.tools.dao;


public class Tuple4B extends Babcd {
	private static final long serialVersionUID = 1L;



	public Tuple4B() {
		super();
	}
	public Tuple4B(final boolean a, final boolean b, final boolean c, final boolean d) {
		super(a, b, c, d);
	}
	public Tuple4B(final Tuple4B tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple4B(this.a, this.b, this.c, this.d);
	}



	public void get(final Tuple4B tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
		tup.d = this.d;
	}



	public void set(final boolean a, final boolean b, final boolean c, final boolean d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public void set(final Tuple4B tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
		this.d = tup.d;
	}

	public void setA(final boolean a) {
		this.a = a;
	}
	public void setB(final boolean b) {
		this.b = b;
	}
	public void setC(final boolean c) {
		this.c = c;
	}
	public void setD(final boolean d) {
		this.d = d;
	}



}
