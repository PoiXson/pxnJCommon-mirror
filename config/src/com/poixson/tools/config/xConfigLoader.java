package com.poixson.tools.config;

import static com.poixson.utils.FileUtils.ExportResource;
import static com.poixson.utils.FileUtils.OpenResource;
import static com.poixson.utils.FileUtils.ReadInputStream;
import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.poixson.exceptions.CreateDefaultYmlFileException;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;


public final class xConfigLoader {
	private xConfigLoader() {}



	// new xConfig child instance
	public static <T extends xConfig> T NewConfig(
			final Map<String, Object> datamap, final Class<T> clss) {
		if (datamap == null) {
			return NewConfig(
				new HashMap<String, Object>(),
				clss
			);
		}
		if (clss == null)
			return CastConfig(NewConfig(datamap, xConfig.class));
		// get construct
		final Constructor<? extends xConfig> construct;
		try {
			construct = clss.getDeclaredConstructor(Map.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		if (construct == null)
			throw new RuntimeException("xConfig constructor not found!");
		// get new instance
		try {
			return CastConfig( construct.newInstance(datamap) );
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	@SuppressWarnings("unchecked")
	private static <T extends xConfig> T CastConfig(final Object obj) {
		if (obj == null) return null;
		return (T) obj;
	}



	/**
	 * Load and parse yaml data from an input stream. 
	 * @param in InputStream to read from.
	 * @return Map<String, Object> datamap contents of yml file.
	 */
	public static Map<String, Object> LoadYamlFromStream(final InputStream in)
			throws IOException {
		if (in == null) throw new RequiredArgumentException("in");
		final Yaml yml = new Yaml();
		String data = ReadInputStream(in);
		data = data.replace('\t', ' ');
		final Map<String, Object> datamap = yml.loadAs(data, Map.class);
		return datamap;
	}



	// load file
	public static <T extends xConfig> T FromFile(final String filepath, final Class<T> clss)
			throws IOException {
		if (IsEmpty(filepath)) throw new RequiredArgumentException("filePath");
		if (clss == null)
			return CastConfig(FromFile(filepath, xConfig.class));
		if (!filepath.endsWith(".yml")
		&&  !filepath.endsWith(".json"))
			return CastConfig(FromFile(filepath+".json", clss));
		final File file = new File(filepath);
		if (file.isFile()) {
			InputStream in = null;
			try {
				in = new FileInputStream(filepath);
				final Map<String, Object> datamap = LoadYamlFromStream(in);
				return NewConfig(datamap, clss);
			} catch (FileNotFoundException ignore) {
			} finally {
				SafeClose(in);
			}
		}
		return null;
	}



	public static <T extends xConfig> T FromFileOrEmpty(final String filepath, final Class<T> clss)
			throws IOException {
		{
			final T config = FromFile(filepath, clss);
			if (config != null)
				return config;
		}
		{
			final Map<String, Object> datamap = new HashMap<String, Object>();
			return NewConfig(datamap, clss);
		}
	}



	// load jar resource
	public static <T extends xConfig> T FromJar(final String filepath, final Class<T> clss)
			throws IOException {
		if (IsEmpty(filepath)) throw new RequiredArgumentException("filePath");
		if (clss == null)
			return CastConfig(FromJar(filepath, xConfig.class));
		if (!filepath.endsWith(".yml")
		&&  !filepath.endsWith(".json"))
			return CastConfig(FromJar(filepath+".json", clss));
		InputStream in = null;
		try {
			in = OpenResource(clss, filepath);
			if (in == null) return null;
			final Map<String, Object> datamap = LoadYamlFromStream(in);
			if (datamap == null) return null;
			return NewConfig(datamap, clss);
		} finally {
			SafeClose(in);
		}
	}



	// load file or jar (copy from jar to filesystem if doesn't exist)
	public static <T extends xConfig> T FromFileOrJar(final String filepath, final Class<T> clss)
			throws CreateDefaultYmlFileException {
		if (IsEmpty(filepath)) throw new RequiredArgumentException("filePath");
		if (clss == null)
			return CastConfig(FromFileOrJar(filepath, xConfig.class));
		if (!filepath.endsWith(".yml")
		&&  !filepath.endsWith(".json"))
			return CastConfig(FromFileOrJar(filepath+".json", clss));
		try {
			// attempt loading from file
			{
				final T cfg = FromFile(filepath, clss);
				if (cfg != null) return cfg;
			}
			// attempt loading from resource
			{
				final T cfg = FromJar(filepath, clss);
				if (cfg != null) {
					// copy default file
					try {
						xLog.Get().info("Creating default file:", filepath);
						ExportResource(filepath, OpenResource(clss, filepath));
						return cfg;
					} catch (Exception e) {
						throw new CreateDefaultYmlFileException(filepath, e);
					}
				}
			}
		} catch (Exception e) {
			xLog.Get().trace(e);
		}
		return null;
	}



}
