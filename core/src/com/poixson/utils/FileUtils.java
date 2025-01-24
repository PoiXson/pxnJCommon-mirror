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


public final class FileUtils {
	private FileUtils() {}
	static { Keeper.add(new FileUtils()); }

	private static final AtomicReference<String> cwd = new AtomicReference<String>(null);
	private static final AtomicReference<String> pwd = new AtomicReference<String>(null);
	private static final AtomicReference<String> exe = new AtomicReference<String>(null);



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
		// local file
		final File file = new File(file_loc);
		if (file.isFile()) {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException ignore) {}
		}
		// resource file
		return OpenResource(file_res);
	}



	// true  | local file
	// false | resource file
	public static boolean SearchLocalOrResource(
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
			final URL url = FileUtils.class.getResource(ForceStarts(File.separatorChar, file_res));
			if (url != null)
				return false;
		}
		throw new FileNotFoundException(String.format("Loc:%s or Res:%s", file_loc, file_res));
	}



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
	private static void PopulateCwd() {
		if (cwd.get() != null) return;
		final String path = System.getProperty("user.dir");
		if (!IsEmpty(path)) {
			cwd.compareAndSet(null, path);
			return;
		}
		try {
			final File dir = new File(".");
			cwd.compareAndSet(null, dir.getCanonicalPath());
		} catch (IOException ignore) {
			cwd.set(null);
		}
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
	private static void PopulatePwdExe() {
		if (pwd.get() != null && exe.get() != null) return;
		final CodeSource source = FileUtils.class.getProtectionDomain().getCodeSource();
		final String pathRaw = source.getLocation().getPath();
		final String path = DecodeDef(pathRaw, pathRaw);
		if (IsEmpty(path)) throw new RuntimeException("Failed to get pwd path");
		final int pos = path.lastIndexOf('/');
		if (pos < 0) throw new RuntimeException("Invalid pwd path: "+path);
		pwd.compareAndSet(null, ceTrim( path.substring(0, pos),  '/' ));
		exe.compareAndSet(null, cfTrim( path.substring(pos + 1), '/' ));
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



	public static String MergePaths(final String...strings) {
		if (IsEmpty(strings)) return null;
		boolean isAbsolute = false;
		// maintain absolute
		for (int index=0; index<strings.length; index++) {
			if (IsEmpty(strings[index])) continue;
			if (strings[index].startsWith(File.separator))
				isAbsolute = true;
			break;
		}
		// split further
		final LinkedList<String> result = new LinkedList<String>();
		int count = 0;
		//LOOP_PARAMS:
		for (int index=0; index<strings.length; index++) {
			final String[] array = strings[index].split(File.separator);
			// remove nulls/blanks
			PARTS_ARRAY:
			for (final String str : array) {
				if (IsEmpty(str)) continue PARTS_ARRAY;
				final String s = cTrim(str, ' ', '\t', '\r', '\n');
				if (IsEmpty(s)) continue PARTS_ARRAY;
				if (count > 0) {
					if (".".equals(s)) continue PARTS_ARRAY;
					if (",".equals(s)) continue PARTS_ARRAY;
				}
				result.add(s);
				count++;
			} // end PARTS_ARRAY
		} // end LOOP_PARAMS
		if (result.isEmpty())
			return null;
		final String first = result.getFirst();
		// prepend cwd
		if (".".equals(first)) {
			result.removeFirst();
			isAbsolute = true;
			final String[] array = CWD().split(File.separator);
			for (int index=array.length-1; index>=0; index--) {
				if (array[index].length() == 0) continue;
				result.addFirst(array[index]);
			}
		} else
		// prepend pwd
		if (",".equals(first)) {
			result.removeFirst();
			isAbsolute = true;
			final String[] array = PWD().split(File.separator);
			for (int index=array.length-1; index>=0; index--) {
				result.addFirst(array[index]);
			}
		}
		// resolve ../
		for (int index=0; index<result.size(); index++) {
			final String entry = result.get(index);
			if ("..".equals(entry)) {
				result.remove(index);
				if (index > 0) {
					index--;
					result.remove(index);
				}
				index--;
			}
		}
		// build path
		{
			final String path = (IsEmpty(result) ? "" : MergeStrings(File.separatorChar, result.toArray(new String[0])));
			return (isAbsolute ? ForceStarts(File.separatorChar, path) : path);
		}
	}



	/**
	 * Open a resource file which has been compiled into the app jar.
	 * @param clss Reference class contained in the same jar.
	 * @param fileStr Package path to the file.
	 * @return InputStream of the open file, or null on failure.
	 */
	public static InputStream OpenResource(final String file) {
		if (IsEmpty(file)) throw new RequiredArgumentException("file");
		return FileUtils.class.getResourceAsStream(ForceStarts(File.separator, file));
	}



	// copy jar resource to file
	public static void ExportResource(final String target, final InputStream in) throws IOException {
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



}
