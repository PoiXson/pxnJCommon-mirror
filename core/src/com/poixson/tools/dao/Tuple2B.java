package com.poixson.tools.dao;


public class Tuple2B extends Bab {
	private static final long serialVersionUID = 1L;



	public Tuple2B() {
		super();
	}
	public Tuple2B(final boolean a, final boolean b) {
		super(a, b);
	}
	public Tuple2B(final Tuple2B tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2B(this.a, this.b);
	}



	public void get(final Tuple2B tup) {
		tup.a = this.a;
		tup.b = this.b;
	}



	public void set(final boolean a, final boolean b) {
		this.a = a;
		this.b = b;
	}
	public void set(final Tuple2B tup) {
		this.a = tup.a;
		this.b = tup.b;
	}

	public void setA(final boolean a) {
		this.a = a;
	}
	public void setB(final boolean b) {
		this.b = b;
	}



}
