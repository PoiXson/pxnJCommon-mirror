package com.poixson.scripting;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;


public class PixelsHolder {

	public final ConcurrentHashMap<Integer, Integer> pixels = new ConcurrentHashMap<Integer, Integer>();
	public final AtomicReference<ConcurrentHashMap<Integer, Integer>> frame =
			new AtomicReference<ConcurrentHashMap<Integer, Integer>>(null);

	public final int size;



	public PixelsHolder(final int size) {
		this.size = size;
	}



	public void goNextFrame() {
		final ConcurrentHashMap<Integer, Integer> pixels = new ConcurrentHashMap<Integer, Integer>();
		final Iterator<Entry<Integer, Integer>> it = this.pixels.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<Integer, Integer> entry = it.next();
			pixels.put(entry.getKey(), entry.getValue());
		}
		this.frame.set(pixels);
	}



	public Color get(final int x, final int y) {
		final Integer val = this.pixels.get(Integer.valueOf(x+(y*this.size)));
		return (val==null ? Color.BLACK : new Color(val));
	}
	public void set(final int x, final int y, final Color color) {
		this.pixels.put(Integer.valueOf(x+(y*this.size)), color.getRGB());
	}



	public Color[][] getFrameArray() {
		final ConcurrentHashMap<Integer, Integer> pixels = this.frame.get();
		if (pixels == null)
			return null;
		final Color[][] array = new Color[this.size][];
		for (int iy=0; iy<this.size; iy++) {
			array[iy] = new Color[this.size];
			for (int ix=0; ix<this.size; ix++) {
				final Integer val = pixels.get(Integer.valueOf(ix+(iy*this.size)));
				array[iy][ix] = (val==null ? Color.BLACK : new Color(val));
			}
		}
		return array;
	}



}
