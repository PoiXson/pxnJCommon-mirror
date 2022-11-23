package com.poixson.tools.abstractions;

import java.lang.reflect.Method;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class RemappedDocumentListener
extends RemappedEventListener
implements DocumentListener {

	protected final Method methodInsert;
	protected final Method methodRemove;
	protected final Method methodChanged;



	public RemappedDocumentListener(final Object container, final String methodStr) {
		this(container, methodStr, null, null, null);
	}
	public RemappedDocumentListener(final Object container,
			final String methodStr, final String methodStr_Insert,
			final String methodStr_Remove, final String methodStr_Changed) {
		super(container, methodStr, DocumentEvent.class);
		this.methodInsert  = this.findMethod(methodStr_Insert,  DocumentEvent.class);
		this.methodRemove  = this.findMethod(methodStr_Remove,  DocumentEvent.class);
		this.methodChanged = this.findMethod(methodStr_Changed, DocumentEvent.class);
	}



	@Override
	public void insertUpdate(final DocumentEvent event) {
		this.invoke(this.method,       event);
		this.invoke(this.methodInsert, event);
	}
	@Override
	public void removeUpdate(final DocumentEvent event) {
		this.invoke(this.method,       event);
		this.invoke(this.methodRemove, event);
	}
	@Override
	public void changedUpdate(final DocumentEvent event) {
		this.invoke(this.method,        event);
		this.invoke(this.methodChanged, event);
	}



}
