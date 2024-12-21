/*
package com.poixson.tools.events;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.poixson.exceptions.RequiredArgumentException;


public class xHandlerGeneric extends xHandler<xEvent> {

	protected final CopyOnWriteArrayList<xEventListenerDAO> listeners =
			new CopyOnWriteArrayList<xEventListenerDAO>();



	public xHandlerGeneric() {
		super(xEvent.class);
	}



	@Override
	protected boolean register(
			final Object object, final Method method, final xEvent anno) {
		if (object == null) throw new RequiredArgumentException("object");
		if (method == null) throw new RequiredArgumentException("method");
		if (anno == null)   throw new RequiredArgumentException("anno");
		final xEventListenerDAO dao =
			new xEventListenerDAO(
				object, method
			);
		this.listeners.add(dao);
		return true;
	}

	@Override
	public void unregister(final Object object) {
		if (object == null) return;
		final Set<xEventListenerDAO> remove = new HashSet<xEventListenerDAO>();
		final Iterator<xEventListenerDAO> it = this.listeners.iterator();
		while (it.hasNext()) {
			final xEventListenerDAO dao = it.next();
			if (dao.isObject(object)) {
				remove.add(dao);
			}
		}
		if ( ! remove.isEmpty() ) {
			for (final xEventListenerDAO dao : remove) {
				this.listeners.remove(dao);
			}
		}
	}
	@Override
	public void unregister(final Object object, final String methodName) {
		if (object == null || IsEmpty(methodName)) return;
		final Set<xEventListenerDAO> remove = new HashSet<xEventListenerDAO>();
		final Iterator<xEventListenerDAO> it = this.listeners.iterator();
		while (it.hasNext()) {
			final xEventListenerDAO dao = it.next();
			if (dao.isMethod(object, methodName)) {
				remove.add(dao);
			}
		}
		if ( ! remove.isEmpty() ) {
			for (final xEventListenerDAO dao : remove) {
				this.listeners.remove(dao);
			}
		}
	}
	@Override
	public void unregisterAll() {
		this.listeners.clear();
	}



	// trigger event
	public void invoke() {
		final Iterator<xEventListenerDAO> it = this.listeners.iterator();
		while (it.hasNext()) {
			final xEventListenerDAO dao = it.next();
			dao.invoke();
		}
	}



	@Override
	public void unregister(final Object object) {
	}



	@Override
	public void unregister(final Object object, final String methodName) {
	}



}
*/
