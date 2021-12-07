package com.poixson.utils;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.tools.Keeper;


public final class guiUtils {
	private guiUtils() {}
	static { Keeper.add(new guiUtils()); }
	private static final String LOG_NAME = "GUI";



	// -------------------------------------------------------------------------------
	// fonts



	// change font size
	public static void ChangeFontSize(final JComponent component, final int size) {
		if (component == null) throw new RequiredArgumentException("component");
		final Font font = component.getFont();
		component.setFont(
			new Font(
				font.getFontName(),
				font.getStyle(),
				font.getSize() + size
			)
		);
	}



	public static void RotateFont(final Component comp, final int degrees) {
		RotateFont(
			comp,
			Math.toRadians(degrees)
		);
	}
	public static void RotateFont(final Component comp, final double rad) {
		comp.setFont(
			RotateFont(
				comp.getFont(),
				rad
			)
		);
	}
	public static Font RotateFont(final Font font, final int degrees) {
		return RotateFont(
			font,
			Math.toRadians(degrees)
		);
	}
	public static Font RotateFont(final Font font, final double rad) {
		final AffineTransform transform = new AffineTransform();
		transform.rotate(rad);
		return font.deriveFont(transform);
	}



	// -------------------------------------------------------------------------------
	// images



	// load image file/resource
	public static ImageIcon LoadImageResource(final String path) {
		// open file
		{
			final File file = new File(path);
			if (file.exists()) {
				try {
					final ImageIcon image = new ImageIcon(path);
					log().finer("Loaded image file:", path);
					return image;
				} catch(Exception ignore) {}
			}
		}
		// open resource
		try {
			final ImageIcon image = new ImageIcon(ClassLoader.getSystemResource(path));
			log().finer("Loaded image resource:", path);
			return image;
		} catch(Exception ignore) {}
		log().warning("Failed to load image:", path);
		return null;
	}



	public static Image IconToImage(final Icon icon) {
		if (icon instanceof ImageIcon)
			return ((ImageIcon) icon).getImage();
		final int w = icon.getIconWidth();
		final int h = icon.getIconHeight();
		final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final GraphicsDevice dev = env.getDefaultScreenDevice();
		final GraphicsConfiguration cfg = dev.getDefaultConfiguration();
		final BufferedImage image = cfg.createCompatibleImage(w, h);
		final Graphics2D g = image.createGraphics();
		icon.paintIcon(null, g, 0, 0);
		g.dispose();
		return image;
	}



	// -------------------------------------------------------------------------------
	// logger



	public static xLog log() {
		return xLog.Get(LOG_NAME);
	}



}
