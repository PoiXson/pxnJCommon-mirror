/*
package com.poixson.tools;

import java.awt.LayoutManager;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.threadpool.types.xThreadPool_GUI;
import com.poixson.tools.abstractions.RemappedWindowListener;
import com.poixson.tools.abstractions.xCloseable;
import com.poixson.utils.ReflectUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.Utils;
import com.poixson.utils.guiUtils;


public abstract class xWindow extends JFrame implements xCloseable {
	private static final long serialVersionUID = 1L;

	protected static final ConcurrentHashMap<String, xWindow> all = new ConcurrentHashMap<String, xWindow>();

	public final String name;
	public final String key;

	protected final AtomicBoolean closed = new AtomicBoolean(false);



	public xWindow(final String title, final LayoutManager layout) {
		this();
		this.setTitle(title);
		this.setLayout(layout);
	}
	public xWindow(final String title) {
		this();
		this.setTitle(title);
	}
	public xWindow(final LayoutManager layout) {
		this();
		this.setLayout(layout);
	}
	public xWindow() {
		super();
		// ensure dispatch thread
		if ( ! xThreadPool_GUI.Get().isCurrentThread() ) {
			throw new RuntimeException(
				String.format(
					"Cannot load xWindow %s from thread: %s",
					this.getClass().getName(),
					Thread.currentThread().getName()
				)
			);
		}
		// find a unique name
		this.name = ReflectUtils.GetClassName(this);
		if (Utils.isEmpty(this.name)) throw new RuntimeException("Failed to detect window class name");
		this.key = StringUtils.PutUnique(all, this.name, this);
		if (Utils.isEmpty(this.key)) throw new RuntimeException("Failed to find a unique window key");
		// close hook
		this.addWindowListener(
			new RemappedWindowListener(this, "",
				"", //methodStr_Opened,
				"close",
				"", //methodStr_Closed,
				"", //methodStr_Iconified,
				"", //methodStr_Deiconified,
				"", //methodStr_Activated,
				""  //methodStr_Deactivated
			)
		);
		this.log().fine("New window created:", this.key);
	}



	// -------------------------------------------------------------------------------
	// closing window



	protected void closing() {
	}



	@Override
	public void close() {
		if (xThreadPool_GUI.Get().proper(this, "close")) return;
		if (!this.closed.compareAndSet(false, true)) return;
		this.log().fine("Closing window:", this.getUniqueKey());
		this.closing();
		this.dispose();
	}
	public static void closeAll() {
		boolean changed = false;
		OUTER_LOOP:
		while (true) {
			if (changed)
				ThreadUtils.Sleep(10L);
			changed = false;
			final Iterator<xWindow> it = all.values().iterator();
			//IT_LOOP:
			while (it.hasNext()) {
				final xWindow window = it.next();
				if (!window.isClosed()) {
					window.close();
					changed = true;
				}
			} // end IT_LOOP
			if (!changed)
				break OUTER_LOOP;
		} // end OUTER_LOOP
	}



	// -------------------------------------------------------------------------------
	// state



	@Override
	public boolean isClosed() {
		return this.closed.get();
	}
	@Override
	public boolean notClosed() {
		return ! this.closed.get();
	}



	// -------------------------------------------------------------------------------



	@Override
	public String getName() {
		return this.name;
	}
	public String getUniqueKey() {
		return this.key;
	}



	@Override
	public void setVisible(final boolean visible) {
		if (xThreadPool_GUI.Get().proper(this, "setVisible", Boolean.valueOf(visible))) return;
		super.setVisible(visible);
	}
	public void setVisible(final Boolean visible) {
		if (visible == null) throw new RequiredArgumentException("visible");
		this.setVisible(visible.booleanValue());
	}
	public void setVisible() {
		this.setVisible(true);
	}
	public void setHidden() {
		this.setVisible(false);
	}
	public void setFocused() {
		if (xThreadPool_GUI.Get().proper(this, "setFocused")) return;
		this.requestFocus();
	}



	public void autoHeight(final int width) {
		if (xThreadPool_GUI.Get().proper(this, "autoHeight", Integer.valueOf(width))) return;
		this.pack();
		this.setSize(width, this.getHeight());
	}
	public void autoHeight(final Integer width) {
		if (width == null) throw new RequiredArgumentException("width");
		this.autoHeight(width.intValue());
	}



	// -------------------------------------------------------------------------------
	// logger



	@Override
	public xLog log() {
		return guiUtils.log();
	}



}
*/
