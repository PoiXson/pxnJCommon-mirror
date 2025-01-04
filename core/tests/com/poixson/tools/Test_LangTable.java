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
		for (int i=0; i<3; i++) {
			Assert.assertEquals("Hello, welcome to the server!",      lang.getPhrase("greeting"));
			Assert.assertEquals("Greetings, welcome to the server!",  lang.getPhrase("greeting"));
			Assert.assertEquals("Howdy, welcome to the server!",      lang.getPhrase("greeting"));
			Assert.assertEquals("Ahoy matey, welcome to the server!", lang.getPhrase("greeting"));
		}
		Assert.assertEquals("Welcome back, user123!",                   lang.getPhrase("welcome_back", "player", "user123"));
		Assert.assertEquals("Goodbye, see you next time!",              lang.getPhrase("farewell"));
		Assert.assertEquals("Type /help for a list of commands.",       lang.getPhrase("help"    ));
		Assert.assertEquals("An error has occurred. Please try again.", lang.getPhrase("error"   ));
		Assert.assertEquals("You have joined the game.",                lang.getPhrase("join"    ));
		Assert.assertEquals("You have left the game.",                  lang.getPhrase("leave"   ));
		// local file
		lang.lang("es");
		for (int i=0; i<3; i++) {
			Assert.assertEquals("Hola, bienvenido al servidor!",           lang.getPhrase("greeting"));
			Assert.assertEquals("Saludos, bienvenido al servidor!",        lang.getPhrase("greeting"));
			Assert.assertEquals("Hola, bienvenido al servidor!",           lang.getPhrase("greeting"));
			Assert.assertEquals("Ahoy compañero, bienvenido al servidor!", lang.getPhrase("greeting"));
		}
		Assert.assertEquals("Bienvenido de nuevo, user123!",                     lang.getPhrase("welcome_back", "player", "user123"));
		Assert.assertEquals("Adios, nos vemos la proxima!",                      lang.getPhrase("farewell"));
		Assert.assertEquals("Escriba /help para obtener una lista de comandos.", lang.getPhrase("help"    ));
		Assert.assertEquals("Se ha producido un error. Inténtalo de nuevo.",     lang.getPhrase("error"   ));
		Assert.assertEquals("Te has unido al juego.",                            lang.getPhrase("join"    ));
		Assert.assertEquals("Has abandonado el juego.",                          lang.getPhrase("leave"   ));
	}



}
