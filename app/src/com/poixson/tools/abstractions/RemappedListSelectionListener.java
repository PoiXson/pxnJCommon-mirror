package com.poixson.tools.abstractions;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class RemappedListSelectionListener
extends RemappedEventListener
implements ListSelectionListener {



	public RemappedListSelectionListener(final Object container, final String methodStr) {
		super(container, methodStr, ListSelectionEvent.class);
	}



	@Override
	public void valueChanged(final ListSelectionEvent event) {
		this.invoke(this.method, event);
	}



}
