package com.poixson.tools;

import static com.poixson.utils.FileUtils.MergePaths;
import static com.poixson.utils.FileUtils.cwd;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.tools.lang.LangTable;


public class Test_LangTable {



	@Test
	public void testConstruct() {
		final LangTable lang = LangTable.Create()
			.path_loc(MergePaths(cwd(), "languages"))
			.path_res("languages")
			.lang("en");
		// resource file
		Assert.assertEquals("Hello, welcome to the server!",            lang.msg("greeting"));
		Assert.assertEquals("Welcome back, user123!",                   lang.msg("welcome_back", "player", "user123"));
		Assert.assertEquals("Goodbye, see you next time!",              lang.msg("farewell"));
		Assert.assertEquals("Type /help for a list of commands.",       lang.msg("help"    ));
		Assert.assertEquals("An error has occurred. Please try again.", lang.msg("error"   ));
		Assert.assertEquals("You have joined the game.",                lang.msg("join"    ));
		Assert.assertEquals("You have left the game.",                  lang.msg("leave"   ));
		// local file
		lang.lang("es");
		Assert.assertEquals("Hola, bienvenido al servidor!",                     lang.msg("greeting"));
		Assert.assertEquals("Bienvenido de nuevo, user123!",                     lang.msg("welcome_back", "player", "user123"));
		Assert.assertEquals("Adios, nos vemos la proxima!",                      lang.msg("farewell"));
		Assert.assertEquals("Escriba /help para obtener una lista de comandos.", lang.msg("help"    ));
		Assert.assertEquals("Se ha producido un error. Inténtalo de nuevo.",     lang.msg("error"   ));
		Assert.assertEquals("Te has unido al juego.",                            lang.msg("join"    ));
		Assert.assertEquals("Has abandonado el juego.",                          lang.msg("leave"   ));
	}



}
