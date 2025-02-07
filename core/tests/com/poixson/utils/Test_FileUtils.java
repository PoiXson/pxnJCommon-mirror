package com.poixson.utils;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertFalse;
import static com.poixson.tools.Assertions.AssertTrue;
import static com.poixson.utils.FileUtils.CWD;
import static com.poixson.utils.FileUtils.EXE;
import static com.poixson.utils.FileUtils.IsDir;
import static com.poixson.utils.FileUtils.ListDirContents;
import static com.poixson.utils.FileUtils.MergePaths;
import static com.poixson.utils.FileUtils.PWD;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.tools.Assertions;


@ExtendWith(Assertions.class)
public class Test_FileUtils {



	public static void TestPaths() {
		AssertTrue ( IsDir("/")     );
		AssertFalse( IsEmpty(CWD()) );
		AssertFalse( IsEmpty(PWD()) );
		AssertFalse( null == EXE()  );
	}



	@Test
	public void testListDirContents() {
		final File path = new File( PWD() );
		final File[] result = ListDirContents( path );
		AssertFalse( IsEmpty(result) );
	}



	@Test
	public void testMergePaths() {
		AssertEquals( "/var/log",   MergePaths("/", "var", "log"  ) );
		AssertEquals(  "var/log",   MergePaths(     "var", "log"  ) );
		AssertEquals( "/.debug",    MergePaths("/", ".debug"      ) );
		AssertEquals( "/var",       MergePaths("/var", "log", "..") );
		AssertEquals( "/var/log",   MergePaths("/var/log/"        ) );
		AssertEquals( "var/log",    MergePaths( "var", "/log"     ) );
		AssertEquals( "/",          MergePaths("/", ".."          ) );
		AssertEquals( null,         MergePaths(                   ) );
		AssertEquals( CWD()+"/abc", MergePaths(".", "abc"         ) );
		AssertEquals( PWD()+"/abc", MergePaths(",", "abc"         ) );
		AssertEquals( null,         MergePaths( (String[]) null   ) );
	}



}
