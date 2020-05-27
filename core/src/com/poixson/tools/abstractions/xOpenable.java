package com.poixson.tools.abstractions;

import java.io.IOException;


public interface xOpenable extends xCloseable {


	public void open() throws IOException;

	public boolean isOpen();
	public boolean notOpen();


}
