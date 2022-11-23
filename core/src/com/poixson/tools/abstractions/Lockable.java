package com.poixson.tools.abstractions;

import com.poixson.tools.xTime;


public interface Lockable {


	public xTime lock();
	public boolean isLocked();


}
