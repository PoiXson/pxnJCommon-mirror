/*
package com.poixson.tools;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import com.poixson.logger.xLog;


public abstract class xSystemTray {

	protected final SystemTray tray;
	protected final TrayIcon trayIcon;



	public xSystemTray() {
		if (!SystemTray.isSupported()) {
			this.tray = null;
			this.trayIcon = null;
			return;
		}
		this.tray = SystemTray.getSystemTray();
		// icon
		final Image icon = this.loadIcon();
		this.trayIcon = new TrayIcon(icon);
		try {
			this.tray.add(this.trayIcon);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
		xLog.GetRoot().fine("Loaded system tray icon");
	}



	public void setMenu(final PopupMenu menu) {
		this.trayIcon.setPopupMenu(menu);
	}



	public void remove() {
		this.tray.remove(this.trayIcon);
	}



	protected abstract Image loadIcon();



}
*/
