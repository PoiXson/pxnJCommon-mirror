package com.poixson.tools.abstractions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;


public class RemappedActionListener
extends RemappedEventListener
implements ActionListener {



	public RemappedActionListener(final Object container, final String methodStr) {
		super(container, methodStr, ActionEvent.class);
	}



	@Override
	public void actionPerformed(final ActionEvent event) {
		try {
			this.method.invoke(this.container, event);
		} catch (IllegalAccessException e) {
			this.log().trace(e);
		} catch (IllegalArgumentException e) {
			this.log().trace(e);
		} catch (InvocationTargetException e) {
			this.log().trace(e);
		} catch (Exception e) {
			this.log().trace(e);
		}
	}



}
