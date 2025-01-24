package com.poixson.tools.indexselect;


public interface IndexSelect {


	public int next();
	public int next(final int max);
	public int next(final int min, final int max);


}
