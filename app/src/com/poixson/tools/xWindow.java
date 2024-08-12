package com.poixson.tools;

import static com.poixson.utils.Utils.IsEmpty;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.threadpool.types.xThreadPool_GUI;
import com.poixson.tools.abstractions.xCloseable;
import com.poixson.utils.ReflectUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.ThreadUtils;
import com.poixson.utils.guiUtils;


public abstract class xWindow extends JFrame implements xCloseable {
	private static final long serialVersionUID = 1L;

	protected static final ConcurrentHashMap<String, xWindow> all = new ConcurrentHashMap<String, xWindow>();

	public final String key;

	protected final AtomicBoolean closed = new AtomicBoolean(false);



	public xWindow() {
		this(null);
	}
	public xWindow(final String key) {
		super();
		// ensure dispatch thread
		if (!xThreadPool_GUI.Get().isCurrentThread() ) {
			throw new RuntimeException(String.format(
				"Cannot load xWindow %s from thread: %s",
				this.getClass().getName(),
				Thread.currentThread().getName()
			));
		}
		final String name;
		if (IsEmpty(key)) {
			name = ReflectUtils.GetClassName(this);
			if (IsEmpty(name)) throw new RuntimeException("Failed to detect window class name");
		} else {
			name = key;
		}
		this.key = StringUtils.PutUnique(all, name, this);
		if (IsEmpty(this.key)) throw new RuntimeException("Failed to find a unique window key");
//TODO
//		// hooks/listeners
//		this.addWindowListener(
//			new RemappedWindowListener(this, "",
//				null,      // methodStr_Opened,
//				"closing", // methodStr_Closing,
//				null,      // methodStr_Closed,
//				null,      // methodStr_Iconified,
//				null,      // methodStr_Deiconified,
//				null,      // methodStr_Activated,
//				null       // methodStr_Deactivated
//			)
//		);
		this.log().fine("New window created:", this.key);
	}



	public void setVisible() {
		this.setVisible(true);
	}
	public void setHidden() {
		this.setVisible(false);
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
	public void setFocused() {
		if (xThreadPool_GUI.Get().proper(this, "setFocused")) return;
		this.requestFocus();
	}



/*
	public void autoHeight(final int width) {
		if (xThreadPool_GUI.Get().proper(this, "autoHeight", Integer.valueOf(width))) return;
		this.pack();
		this.setSize(width, this.getHeight());
	}
	public void autoHeight(final Integer width) {
		if (width == null) throw new RequiredArgumentException("width");
		this.autoHeight(width.intValue());
	}
*/



	public String getKey() {
		return this.key;
	}



	// -------------------------------------------------------------------------------
	// close window



	public void onClosing() {}



	@Override
	public void close() {
		if (xThreadPool_GUI.Get().proper(this, "close")) return;
		if (!this.closed.compareAndSet(false, true)) return;
		this.log().fine("Closing window:", this.key);
		this.onClosing();
		this.dispose();
	}
	public static void CloseAll() {
		boolean changed = false;
		LOOP_OUTER:
		while (true) {
			if (changed)
				ThreadUtils.Sleep(10L);
			changed = false;
			final Iterator<xWindow> it = all.values().iterator();
			//LOOP_IT:
			while (it.hasNext()) {
				final xWindow window = it.next();
				if (!window.isClosed()) {
					window.close();
					changed = true;
				}
			} // end LOOP_IT
			if (!changed)
				break LOOP_OUTER;
		} // end LOOP_OUTER
	}



	@Override
	public boolean isOpen() {
		if (this.isClosed())
			return false;
		return this.isVisible();
	}
	@Override
	public boolean isClosed() {
		return this.closed.get();
	}



	// -------------------------------------------------------------------------------
	// logger



	public xLog log() {
		return guiUtils.log();
	}



}
