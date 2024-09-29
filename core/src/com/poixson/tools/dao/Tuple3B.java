package com.poixson.tools.dao;


public class Tuple3B extends Babc {
	private static final long serialVersionUID = 1L;



	public Tuple3B() {
		super();
	}
	public Tuple3B(final boolean a, final boolean b, final boolean c) {
		super(a, b, c);
	}
	public Tuple3B(final Tuple3B tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple3B(this.a, this.b, this.c);
	}



	public void get(final Tuple3B tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
	}



	public void set(final boolean a, final boolean b, final boolean c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public void set(final Tuple3B tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
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



}
