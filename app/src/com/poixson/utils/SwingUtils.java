package com.poixson.utils;

import java.awt.Font;
import java.io.File;

import javax.swing.ImageIcon;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;


public final class SwingUtils {
	private SwingUtils() {}



	// load image file/resource
	public static ImageIcon LoadImageResource(final String path) {
		// open file
		if ((new File(path)).exists()) {
			try {
				final ImageIcon image = new ImageIcon(path);
				log().fine("Loaded image file:", path);
				return image;
			} catch(Exception ignore) {}
		}
		// open resource
		try {
			final ImageIcon image = new ImageIcon(ClassLoader.getSystemResource(path));
			log().fine("Loaded image resource:", path);
			return image;
		} catch(Exception ignore) {}
		log().warning("Failed to load image:", path);
		return null;
	}



	// logger
	public static xLog log() {
		return guiUtils.log();
	}



}
