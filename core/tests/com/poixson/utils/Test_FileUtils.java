package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;


public class Test_FileUtils {



	@Test
	public void testPaths() {
		Assert.assertFalse( IsEmpty(FileUtils.cwd()) );
		Assert.assertFalse( IsEmpty(FileUtils.pwd()) );
		Assert.assertFalse( null == FileUtils.exe()  );
		Assert.assertTrue(FileUtils.isDir("/"));
	}



	@Test
	public void testListDirContents() {
		final File path = new File( FileUtils.pwd() );
		final File[] result = FileUtils.ListDirContents( path );
		Assert.assertFalse( IsEmpty(result) );
	}



	@Test
	public void testMergePaths() {
		Assert.assertEquals( "/var/log", FileUtils.MergePaths("/", "var", "log"  ) );
		Assert.assertEquals(  "var/log", FileUtils.MergePaths(     "var", "log"  ) );
		Assert.assertEquals( "/.debug",  FileUtils.MergePaths("/", ".debug"      ) );
		Assert.assertEquals( "/var",     FileUtils.MergePaths("/var", "log", "..") );
		Assert.assertEquals( "/var/log", FileUtils.MergePaths("/var/log/"        ) );
		Assert.assertEquals( "var/log",  FileUtils.MergePaths( "var", "/log"     ) );
		Assert.assertEquals( "/",        FileUtils.MergePaths("/", ".."          ) );
		Assert.assertEquals( null, FileUtils.MergePaths() );
		Assert.assertEquals( FileUtils.cwd()+"/abc", FileUtils.MergePaths(".", "abc") );
		Assert.assertEquals( FileUtils.pwd()+"/abc", FileUtils.MergePaths(",", "abc") );
		Assert.assertEquals( null, FileUtils.MergePaths((String[])null) );
	}



}
