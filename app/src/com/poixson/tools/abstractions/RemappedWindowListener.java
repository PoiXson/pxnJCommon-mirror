package com.poixson.tools.abstractions;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Method;


public class RemappedWindowListener
extends RemappedEventListener
implements WindowListener {

	protected final Method methodOpened;
	protected final Method methodClosing;
	protected final Method methodClosed;

	protected final Method methodIconified;
	protected final Method methodDeiconified;

	protected final Method methodActivated;
	protected final Method methodDeactivated;



	public RemappedWindowListener(final Object container, final String methodStr) {
		this(container, methodStr, null, null, null, null, null, null, null);
	}
	public RemappedWindowListener(final Object container, final String methodStr,
			final String methodStr_Opened,
			final String methodStr_Closing,   final String methodStr_Closed,
			final String methodStr_Iconified, final String methodStr_Deiconified,
			final String methodStr_Activated, final String methodStr_Deactivated) {
		super(container, methodStr, WindowEvent.class);
		this.methodOpened      = this.findMethod(methodStr_Opened,      WindowEvent.class);
		this.methodClosing     = this.findMethod(methodStr_Closing,     WindowEvent.class);
		this.methodClosed      = this.findMethod(methodStr_Closed,      WindowEvent.class);
		this.methodIconified   = this.findMethod(methodStr_Iconified,   WindowEvent.class);
		this.methodDeiconified = this.findMethod(methodStr_Deiconified, WindowEvent.class);
		this.methodActivated   = this.findMethod(methodStr_Activated,   WindowEvent.class);
		this.methodDeactivated = this.findMethod(methodStr_Deactivated, WindowEvent.class);
	}



	@Override
	public void windowOpened(final WindowEvent event) {
		this.invoke(this.method,       event);
		this.invoke(this.methodOpened, event);
	}
	@Override
	public void windowClosing(final WindowEvent event) {
		this.invoke(this.method,        event);
		this.invoke(this.methodClosing, event);
	}
	@Override
	public void windowClosed(final WindowEvent event) {
		this.invoke(this.method,       event);
		this.invoke(this.methodClosed, event);
	}



	@Override
	public void windowIconified(final WindowEvent event) {
		this.invoke(this.method,          event);
		this.invoke(this.methodIconified, event);
	}
	@Override
	public void windowDeiconified(final WindowEvent event) {
		this.invoke(this.method,            event);
		this.invoke(this.methodDeiconified, event);
	}



	@Override
	public void windowActivated(final WindowEvent event) {
		this.invoke(this.method,          event);
		this.invoke(this.methodActivated, event);
	}
	@Override
	public void windowDeactivated(final WindowEvent event) {
		this.invoke(this.method,            event);
		this.invoke(this.methodDeactivated, event);
	}



}
