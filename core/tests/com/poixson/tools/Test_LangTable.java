package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.utils.FileUtils.MergePaths;
import static com.poixson.utils.FileUtils.cwd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.tools.localization.LangTable;


@ExtendWith(Assertions.class)
public class Test_LangTable {



	@Test
	public void testConstruct() {
		final LangTable lang = LangTable.Create()
			.path_loc(MergePaths(cwd(), "languages"))
			.path_res("languages")
			.lang("en");
		// resource file
		for (int i=0; i<3; i++) {
			AssertEquals("Hello, welcome to the server!",      lang.getPhrase("greeting"));
			AssertEquals("Greetings, welcome to the server!",  lang.getPhrase("greeting"));
			AssertEquals("Howdy, welcome to the server!",      lang.getPhrase("greeting"));
			AssertEquals("Ahoy matey, welcome to the server!", lang.getPhrase("greeting"));
		}
		AssertEquals("Welcome back, user123!",                   lang.getPhrase("welcome_back", "player", "user123"));
		AssertEquals("Goodbye, see you next time!",              lang.getPhrase("farewell"));
		AssertEquals("Type /help for a list of commands.",       lang.getPhrase("help"    ));
		AssertEquals("An error has occurred. Please try again.", lang.getPhrase("error"   ));
		AssertEquals("You have joined the game.",                lang.getPhrase("join"    ));
		AssertEquals("You have left the game.",                  lang.getPhrase("leave"   ));
		// local file
		lang.lang("es");
		for (int i=0; i<3; i++) {
			AssertEquals("Hola, bienvenido al servidor!",           lang.getPhrase("greeting"));
			AssertEquals("Saludos, bienvenido al servidor!",        lang.getPhrase("greeting"));
			AssertEquals("Hola, bienvenido al servidor!",           lang.getPhrase("greeting"));
			AssertEquals("Ahoy compañero, bienvenido al servidor!", lang.getPhrase("greeting"));
		}
		AssertEquals("Bienvenido de nuevo, user123!",                     lang.getPhrase("welcome_back", "player", "user123"));
		AssertEquals("Adios, nos vemos la proxima!",                      lang.getPhrase("farewell"));
		AssertEquals("Escriba /help para obtener una lista de comandos.", lang.getPhrase("help"    ));
		AssertEquals("Se ha producido un error. Inténtalo de nuevo.",     lang.getPhrase("error"   ));
		AssertEquals("Te has unido al juego.",                            lang.getPhrase("join"    ));
		AssertEquals("Has abandonado el juego.",                          lang.getPhrase("leave"   ));
	}



}
