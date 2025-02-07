package com.poixson.utils;

import static com.poixson.utils.StringUtils.DecodeDef;
import static com.poixson.utils.StringUtils.ForceStarts;
import static com.poixson.utils.StringUtils.MergeStrings;
import static com.poixson.utils.StringUtils.Repeat;
import static com.poixson.utils.StringUtils.cTrim;
import static com.poixson.utils.StringUtils.ceTrim;
import static com.poixson.utils.StringUtils.cfTrim;
import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.CodeSource;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.Keeper;
import com.poixson.tools.abstractions.Tuple;


public final class FileUtils {
	private FileUtils() {}
	static { Keeper.Add(new FileUtils()); }

	private static final AtomicReference<String> cwd = new AtomicReference<String>(null);
	private static final AtomicReference<String> pwd = new AtomicReference<String>(null);
	private static final AtomicReference<String> exe = new AtomicReference<String>(null);



	// -------------------------------------------------------------------------------
	// files and resources



	public static String SearchLocalFile(final String filenames[], final int parents) {
		if (IsEmpty(filenames)) throw new RequiredArgumentException("filenames");
		final String[] workingPaths = (
			FileUtils.InRunDir()
			? new String[] { FileUtils.CWD() }
			: new String[] { FileUtils.CWD(), FileUtils.PWD() }
		);
		//LOOP_PARENTS:
		for (int parentIndex=0; parentIndex<parents+1; parentIndex++) {
			//LOOP_PATH:
			for (final String workPath : workingPaths) {
				//LOOP_FILE:
				for (final String fileName : filenames) {
					final String path =
						FileUtils.MergePaths(
							workPath,
							Repeat(parentIndex, "../"),
							fileName
						);
					final File file = new File(path);
					if (file.exists()) {
						return path;
					}
				} // end LOOP_FILE
			} // end LOOP_PATH
		} // end LOOP_PARENTS
		return null;
	}



	public static InputStream OpenLocalOrResource(
			final String file_loc, final String file_res) {
		return OpenLocalOrResource(FileUtils.class, file_loc, file_res);
	}
	public static InputStream OpenLocalOrResource(final Class<?> ref,
			final String file_loc, final String file_res) {
		return OpenLocalOrResource(ref.getClassLoader(), file_loc, file_res);
	}
	public static InputStream OpenLocalOrResource(final ClassLoader ldr,
			final String file_loc, final String file_res) {
		// local file
		final File file = new File(file_loc);
		if (file.isFile()) {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException ignore) {}
		}
		// resource file
		return OpenResource(ldr, file_res);
	}



	// true  | local file
	// false | resource file
	public static boolean SearchLocalOrResource(
			final String file_loc, final String file_res)
			throws FileNotFoundException {
		return SearchLocalOrResource(FileUtils.class, file_loc, file_res);
	}
	public static boolean SearchLocalOrResource(final Class<?> ref,
			final String file_loc, final String file_res)
			throws FileNotFoundException {
		return SearchLocalOrResource(ref.getClassLoader(), file_loc, file_res);
	}
	public static boolean SearchLocalOrResource(final ClassLoader ldr,
			final String file_loc, final String file_res)
			throws FileNotFoundException {
		// local file
		if (!IsEmpty(file_loc)) {
			final File path = new File(file_loc);
			if (path.isFile())
				return true;
		}
		// resource file
		if (!IsEmpty(file_res)) {
			final URL url = ldr.getResource(file_res);
			if (url != null)
				return false;
		}
		throw new FileNotFoundException(String.format("Loc:%s or Res:%s", file_loc, file_res));
	}



	/**
	 * Open a resource file which has been compiled into the app jar.
	 * @param clss Reference class contained in the same jar.
	 * @param fileStr Package path to the file.
	 * @return InputStream of the open file, or null on failure.
	 */
	public static InputStream OpenResource(final String file) {
		return OpenResource(FileUtils.class, file);
	}
	public static InputStream OpenResource(final Class<?> ref, final String file) {
		return OpenResource(ref.getClassLoader(), file);
	}
	public static InputStream OpenResource(final ClassLoader ldr, final String file) {
		if (ldr == null) return null;
		if (IsEmpty(file)) throw new RequiredArgumentException("file");
		return ldr.getResourceAsStream(file);
	}



	public static void ExportInputStreamToFile(final String target, final InputStream in) throws IOException {
		if (IsEmpty(target)) throw new RequiredArgumentException("target");
		if (in == null)      throw new RequiredArgumentException("in");
		final File file = new File(target);
		try {
			Files.copy(in, file.toPath());
		} finally {
			SafeClose(in);
		}
	}



	public static String ReadInputStream(final InputStream in) throws IOException {
		if (in == null) return null;
		final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return ReadBufferedReader(reader);
	}
	public static String ReadBufferedReader(final BufferedReader reader) throws IOException {
		if (reader == null) return null;
		final StringBuilder result = new StringBuilder();
		String line;
		while ( (line=reader.readLine()) != null)
			result.append(line).append('\n');
		return result.toString();
	}



	// -------------------------------------------------------------------------------
	// paths



	public static boolean InRunDir() {
		final String cwd = CWD(); if (cwd == null) return false;
		final String pwd = PWD(); if (pwd == null) return false;
		return (cwd.equals(pwd));
	}



	// get current working directory
	public static String CWD() {
		if (cwd.get() == null)
			PopulateCwd();
		return cwd.get();
	}
	private static String PopulateCwd() {
		// cached
		{
			final String path = cwd.get();
			if (!IsEmpty(path))
				return path;
		}
		// user.dir property
		{
			final String path = System.getProperty("user.dir");
			if (!IsEmpty(path)) {
				if (cwd.compareAndSet(null, path))
					return path;
			}
		}
		// Paths.get()
		{
			final String path = Paths.get("").toAbsolutePath().toString();
			if (!IsEmpty(path)) {
				if (cwd.compareAndSet(null, path))
					return path;
			}
		}
		return cwd.get();
	}



	// get running directory
	public static String PWD() {
		if (pwd.get() == null)
			PopulatePwdExe();
		return pwd.get();
	}
	public static String EXE() {
		if (exe.get() == null)
			PopulatePwdExe();
		return exe.get();
	}
	private static Tuple<String, String> PopulatePwdExe() {
		{
			final String path_pwd = pwd.get();
			final String path_exe = exe.get();
			if (!IsEmpty(path_pwd)
			&&  !IsEmpty(path_exe))
				return new Tuple<String, String>(path_pwd, path_exe);
		}
		{
			final CodeSource source = FileUtils.class.getProtectionDomain().getCodeSource();
			final String path_raw = source.getLocation().getPath();
			final String path = DecodeDef(path_raw, path_raw);
			if (IsEmpty(path)) throw new RuntimeException("Failed to get pwd path");
			final int pos = path.lastIndexOf('/');
			if (pos < 0) throw new RuntimeException("Invalid pwd path: "+path);
			final String path_pwd = ceTrim( path.substring(0, pos),  '/' );
			final String path_exe = cfTrim( path.substring(pos + 1), '/' );
			if (!IsEmpty(path_pwd)) {
				final boolean result_pwd = pwd.compareAndSet(null, (IsEmpty(path_pwd) ? "" : path_pwd));
				final boolean result_exe = exe.compareAndSet(null, (IsEmpty(path_exe) ? "" : path_exe));
				if (result_pwd && result_exe)
					return new Tuple<String, String>(path_pwd, path_exe);
			}
		}
		return new Tuple<String, String>(pwd.get(), exe.get());
	}



	public static boolean IsDir(final String path) {
		if (IsEmpty(path)) return false;
		final File p = new File(path);
		return (p.exists() && p.isDirectory());
	}
	public static boolean IsFile(final String file) {
		if (IsEmpty(file)) return false;
		final File f = new File(file);
		return (f.exists() && f.isFile());
	}
	public static boolean IsReadable(final String path) {
		if (IsEmpty(path)) return false;
		final File p = new File(path);
		return (p.exists() && p.canRead());
	}
	public static boolean IsWritable(final String path) {
		if (IsEmpty(path)) return false;
		final File p = new File(path);
		return (p.exists() && p.canWrite());
	}



	public static long GetLastModifiedSafe(final String file) {
		try {
			return GetLastModified(file);
		} catch (IOException ignore) {}
		return Long.MIN_VALUE;
	}
	public static long GetLastModifiedSafe(final File file) {
		try {
			return GetLastModified(file);
		} catch (IOException ignore) {}
		return Long.MIN_VALUE;
	}
	public static long GetLastModifiedSafe(final Path path) {
		try {
			return GetLastModified(path);
		} catch (IOException ignore) {}
		return Long.MIN_VALUE;
	}



	public static long GetLastModified(final String file) throws IOException {
		return (IsEmpty(file) ? 0L : GetLastModified(Paths.get(file)));
	}
	public static long GetLastModified(final File file) throws IOException {
		return (file==null ? 0L : GetLastModified(file.toPath()));
	}
	public static long GetLastModified(final Path path) throws IOException {
		final BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		if (attr == null) throw new IOException("Failed to get file attributes: "+path.toString());
		final FileTime time = attr.lastModifiedTime();
		return time.to(TimeUnit.SECONDS);
	}



	/**
	 * List contents of a directory.
	 * @param dir The directory path to query.
	 * @param extensions File extensions to include, filtering out all others.
	 * To list all contents, set this to null.
	 * @return
	 */
	public static File[] ListDirContents(final File dir, final String[] extensions) {
		if (dir == null) throw new RequiredArgumentException("dir");
		if (!dir.isDirectory()) return null;
		return dir.listFiles(
			new FileFilter() {
				private String[] exts;
				public FileFilter init(final String[] extens) {
					this.exts = extens;
					return this;
				}
				@Override
				public boolean accept(File path) {
					if (this.exts == null)
						return true;
					final String pathStr = path.toString();
					for (final String ext : this.exts){
						if (pathStr.endsWith(ext))
							return true;
					}
					return false;
				}
			}.init(extensions)
		);
	}
	public static File[] ListDirContents(final File dir, final String extension) {
		return ListDirContents(dir, new String[] {extension});
	}
	public static File[] ListDirContents(final File dir) {
		return ListDirContents(dir, (String[]) null);
	}



	public static String MergePaths(final String...paths) {
		if (IsEmpty(paths)) return null;
		final LinkedList<String> parts = new LinkedList<String>();
		boolean is_absolute = false;
		// prep parts
		{
			boolean first = true;
			LOOP_PATHS:
			for (final String path : paths) {
				if (IsEmpty(path))
					continue LOOP_PATHS;
				if (first) {
					// cwd
					if (".".equals(path)) {
						is_absolute = true;
						parts.addLast(".");
					} else
					// pwd
					if (",".equals(path)) {
						is_absolute = true;
						parts.addLast(",");
					} else
					// absolute path
					if (path.startsWith(File.separator)) {
						is_absolute = true;
					}
				}
				final String pth = StringUtils.cTrim(path, File.separatorChar);
				// multiple parts
				if (pth.contains(File.separator)) {
					final String[] array = pth.split(File.separator);
					for (final String p : array) {
						if (!IsEmpty(p)) {
							final String add = cTrim(p, ' ', '\t', '\r', '\n');
							if (!IsEmpty(add)
							&& !".".equals(add)
							&& !",".equals(add))
								parts.addLast(add);
						}
					}
				// single path part
				} else {
					final String add = cTrim(pth, ' ', '\t', '\r', '\n');
					if (!IsEmpty(add)
					&& !".".equals(add)
					&& !",".equals(add))
						parts.addLast(add);
				}
				first = false;
			} // end LOOP_PATHS
		}
		// resolve paths
		{
			final String first = parts.getFirst();
			// cwd
			if (".".equals(first)) {
				parts.removeFirst();
				final String[] array = CWD().split(File.separator);
				for (int index=array.length-1; index>=0; index--) {
					if (!IsEmpty(array[index]))
						parts.addFirst(array[index]);
				}
			} else
			// pwd
			if (",".equals(first)) {
				parts.removeFirst();
				final String[] array = PWD().split(File.separator);
				for (int index=array.length-1; index>=0; index--) {
					if (!IsEmpty(array[index]))
						parts.addFirst(array[index]);
				}
			}
			// resolve ../
			int num_parts = parts.size();
			for (int index=0; index<num_parts; index++) {
				final String entry = parts.get(index);
				if ("..".equals(entry)) {
					parts.remove(index);
					if (index > 0)
						parts.remove(--index);
					index--;
					num_parts -= 2;
				}
			}
		}
		// build path
		{
			final String path = (IsEmpty(parts) ? "" : MergeStrings(File.separatorChar, parts.toArray(new String[0])));
			return (is_absolute ? ForceStarts(File.separatorChar, path) : path);
		}
	}

	public static String MergPths(final String...paths) {
		if (IsEmpty(paths)) return null;
		final LinkedList<String> parts = new LinkedList<String>();
		boolean is_absolute = false;
		// prep parts
		{
			boolean first = true;
			LOOP_PATHS:
			for (final String path : paths) {
				if (IsEmpty(path))
					continue LOOP_PATHS;
				if (first) {
					// absolute path
					if (path.startsWith(File.separator))
						is_absolute = true;
				}
				final String pth = StringUtils.cTrim(path, File.separatorChar);
				// multiple parts
				if (pth.contains(File.separator)) {
					final String[] array = pth.split(File.separator);
					for (final String p : array) {
						if (!IsEmpty(p)) {
							final String add = cTrim(p, ' ', '\t', '\r', '\n');
							if (!IsEmpty(add))
								parts.addLast(add);
						}
					}
				// single path part
				} else {
					final String add = cTrim(pth, ' ', '\t', '\r', '\n');
					if (!IsEmpty(add))
						parts.addLast(add);
				}
				first = false;
			} // end LOOP_PATHS
		}
		// resolve ../
		int num_parts = parts.size();
		for (int index=0; index<num_parts; index++) {
			final String entry = parts.get(index);
			if ("..".equals(entry)) {
				parts.remove(index);
				if (index > 0)
					parts.remove(--index);
				index--;
				num_parts -= 2;
			}
		}
		// build path
		final String path = (IsEmpty(parts) ? "" : MergeStrings(File.separatorChar, parts.toArray(new String[0])));
		return (is_absolute ? ForceStarts(File.separatorChar, path) : path);
	}



}
