package com.poixson.tools.abstractions;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;


public class RemappedItemListener
extends RemappedEventListener
implements ItemListener {



	public RemappedItemListener(final Object container, final String methodStr) {
		super(container, methodStr, ItemEvent.class);
	}



	@Override
	public void itemStateChanged(final ItemEvent event) {
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
