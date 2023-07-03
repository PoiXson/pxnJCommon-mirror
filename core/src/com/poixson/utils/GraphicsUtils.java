package com.poixson.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class GraphicsUtils {
	private GraphicsUtils() {}



	public static Image LoadImage(final InputStream stream) {
		if (stream != null) {
			try {
				return ImageIO.read(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	public static BufferedImage ImageToBufferedImage(final Image img) {
		final BufferedImage buf = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D graphics = buf.createGraphics();
		graphics.drawImage(img, 0, 0, null);
		graphics.dispose();
		return buf;
	}



}
