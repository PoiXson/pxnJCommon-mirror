package com.poixson.utils;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertFalse;
import static com.poixson.tools.Assertions.AssertTrue;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.tools.Assertions;


@ExtendWith(Assertions.class)
public class Test_FileUtils {



	@Test
	public void testPaths() {
		AssertFalse( IsEmpty(FileUtils.cwd()) );
		AssertFalse( IsEmpty(FileUtils.pwd()) );
		AssertFalse( null == FileUtils.exe()  );
		AssertTrue(FileUtils.isDir("/"));
	}



	@Test
	public void testListDirContents() {
		final File path = new File( FileUtils.pwd() );
		final File[] result = FileUtils.ListDirContents( path );
		AssertFalse( IsEmpty(result) );
	}



	@Test
	public void testMergePaths() {
		AssertEquals( "/var/log", FileUtils.MergePaths("/", "var", "log"  ) );
		AssertEquals(  "var/log", FileUtils.MergePaths(     "var", "log"  ) );
		AssertEquals( "/.debug",  FileUtils.MergePaths("/", ".debug"      ) );
		AssertEquals( "/var",     FileUtils.MergePaths("/var", "log", "..") );
		AssertEquals( "/var/log", FileUtils.MergePaths("/var/log/"        ) );
		AssertEquals( "var/log",  FileUtils.MergePaths( "var", "/log"     ) );
		AssertEquals( "/",        FileUtils.MergePaths("/", ".."          ) );
		AssertEquals( null, FileUtils.MergePaths() );
		AssertEquals( FileUtils.cwd()+"/abc", FileUtils.MergePaths(".", "abc") );
		AssertEquals( FileUtils.pwd()+"/abc", FileUtils.MergePaths(",", "abc") );
		AssertEquals( null, FileUtils.MergePaths((String[])null) );
	}



}
