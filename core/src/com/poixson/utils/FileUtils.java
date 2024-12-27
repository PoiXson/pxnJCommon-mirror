package com.poixson.utils;

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
			FileUtils.inRunDir()
			? new String[] { FileUtils.cwd() }
			: new String[] { FileUtils.cwd(), FileUtils.pwd() }
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
							StringUtils.Repeat(parentIndex, "../"),
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



	public static InputStream OpenLocalOrResource(final Class<?> clss,
			final String file_loc, final String file_res) {
		// local file
		final File file = new File(file_loc);
		if (file.isFile()) {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException ignore) {}
		}
		// resource file
		return OpenResource(clss, file_res);
	}



	public static boolean SearchLocalOrResource(final Class<?> clss,
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
			final URL url = clss.getResource(file_res);
			if (url != null)
				return false;
		}
		throw new FileNotFoundException(String.format("Loc:%s or Res:%s in %s", file_loc, file_res, clss.getName()));
	}



	public static boolean inRunDir() {
		final String cwd = cwd();
		if (cwd == null) return false;
		final String pwd = pwd();
		if (pwd == null) return false;
		return (cwd.equals(pwd));
	}



	// get current working directory
	public static String cwd() {
		if (cwd.get() == null)
			populateCwd();
		return cwd.get();
	}
	private static void populateCwd() {
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
	public static String pwd() {
		if (pwd.get() == null)
			populatePwdExe();
		return pwd.get();
	}
	public static String exe() {
		if (exe.get() == null)
			populatePwdExe();
		return exe.get();
	}
	private static void populatePwdExe() {
		if (pwd.get() != null && exe.get() != null) return;
		final CodeSource source = FileUtils.class.getProtectionDomain().getCodeSource();
		final String pathRaw = source.getLocation().getPath();
		final String path = StringUtils.decodeDef(pathRaw, pathRaw);
		if (IsEmpty(path)) throw new RuntimeException("Failed to get pwd path");
		final int pos = path.lastIndexOf('/');
		if (pos < 0) throw new RuntimeException("Invalid pwd path: "+path);
		pwd.compareAndSet(null, StringUtils.ceTrim(   path.substring(0, pos),  '/' ));
		exe.compareAndSet(null, StringUtils.cfTrim( path.substring(pos + 1), '/' ));
	}



	public static boolean isDir(final String pathStr) {
		if (IsEmpty(pathStr)) return false;
		final File path = new File(pathStr);
		return ( path.exists() && path.isDirectory() );
	}
	public static boolean isFile(final String fileStr) {
		if (IsEmpty(fileStr)) return false;
		final File file = new File(fileStr);
		return ( file.exists() && file.isFile() );
	}
	public static boolean isReadable(final String pathStr) {
		if (IsEmpty(pathStr)) return false;
		final File path = new File(pathStr);
		return ( path.exists() && path.canRead() );
	}
	public static boolean isWritable(final String pathStr) {
		if (IsEmpty(pathStr)) return false;
		final File path = new File(pathStr);
		return ( path.exists() && path.canWrite() );
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



	public static long GetLastModified(final String fileStr) throws IOException {
		if (IsEmpty(fileStr)) return 0;
		return GetLastModified( Paths.get(fileStr) );
	}
	public static long GetLastModified(final File file) throws IOException {
		if (file == null) return 0;
		return GetLastModified( file.toPath() );
	}
	public static long GetLastModified(final Path path) throws IOException {
		BasicFileAttributes attr =
			Files.readAttributes(path, BasicFileAttributes.class);
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
			if (strings[index].startsWith("/")
			||  strings[index].startsWith("\\")) {
				isAbsolute = true;
			}
			break;
		}
		// split further
		final LinkedList<String> result = new LinkedList<String>();
		int count = 0;
		//LOOP_PARAMS:
		for (int index=0; index<strings.length; index++) {
			final String[] array = strings[index].split("/");
			// remove nulls/blanks
			PARTS_ARRAY:
			for (final String str : array) {
				if (IsEmpty(str)) continue PARTS_ARRAY;
				final String s =
					StringUtils.sTrim(
						str,
						" ", "\t", "\r", "\n"
					);
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
			final String[] array = cwd().split("/");
			for (int index=array.length-1; index>=0; index--) {
				if (array[index].length() == 0) continue;
				result.addFirst(array[index]);
			}
		} else
		// prepend pwd
		if (",".equals(first)) {
			result.removeFirst();
			isAbsolute = true;
			final String[] array = pwd().split("/");
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
		final String path =
			StringUtils.MergeStrings(
				File.separator,
				result.toArray(new String[0])
			);
		if (isAbsolute) {
			return
				StringUtils.ForceStarts(
					File.separator,
					path
				);
		}
		return path;
	}



	/**
	 * Open a resource file which has been compiled into the app jar.
	 * @param clss Reference class contained in the same jar.
	 * @param fileStr Package path to the file.
	 * @return InputStream of the open file, or null on failure.
	 */
	public static InputStream OpenResource(final Class<? extends Object> clssRef, final String fileStr) {
		if (IsEmpty(fileStr)) throw new RequiredArgumentException("fileStr");
		final Class<? extends Object> clss = (clssRef==null ? FileUtils.class : clssRef);
		return clss.getResourceAsStream( StringUtils.ForceStarts("/", fileStr) );
	}



	// copy jar resource to file
	public static void ExportResource(final String targetFileStr, final InputStream in) throws IOException {
		if (IsEmpty(targetFileStr)) throw new RequiredArgumentException("targetFileStr");
		if (in == null)             throw new RequiredArgumentException("in");
		final File file = new File(targetFileStr);
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
