package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.logger.xLogRoot;
import com.poixson.tools.localization.LangShelf;


@ExtendWith(Assertions.class)
public class Test_LangBookShelf {



	@Test
	public void testLangBookShelf() {
		final LangShelf lang = xLogRoot.GetLangShelf()
			.pathLocal("./testresources/local")
			.pathResource("languages");
		// files exist
		AssertTrue(lang.isLangExisting("en"), "en.json not found");
		AssertTrue(lang.isLangExisting("es"), "es.json not found");
		AssertTrue(lang.isLangExisting("fr"), "fr.json not found");
		// resource file
		for (int i=0; i<3; i++) {
			AssertEquals("Hello, welcome to the server!",      lang.getPhrase("en", "greeting"));
			AssertEquals("Greetings, welcome to the server!",  lang.getPhrase("en", "greeting"));
			AssertEquals("Howdy, welcome to the server!",      lang.getPhrase("en", "greeting"));
			AssertEquals("Ahoy matey, welcome to the server!", lang.getDefaultPhrase("greeting"));
		}
		AssertEquals("Welcome back, user123!",                   lang.getDefaultPhrase("welcome_back", "player", "user123"));
		AssertEquals("Goodbye, see you next time!",              lang.getDefaultPhrase("farewell"));
		AssertEquals("Type /help for a list of commands.",       lang.getDefaultPhrase("help"    ));
		AssertEquals("An error has occurred. Please try again.", lang.getDefaultPhrase("error"   ));
		AssertEquals("You have joined the game.",                lang.getDefaultPhrase("join"    ));
		AssertEquals("You have left the game.",                  lang.getDefaultPhrase("leave"   ));
		// local file (es.json)
		for (int i=0; i<3; i++) {
			AssertEquals("Hola, bienvenido al servidor!",           lang.getPhrase("es", "greeting"));
			AssertEquals("Saludos, bienvenido al servidor!",        lang.getPhrase("es", "greeting"));
			AssertEquals("Hola, bienvenido al servidor!",           lang.getPhrase("es", "greeting"));
			AssertEquals("Ahoy compañero, bienvenido al servidor!", lang.getPhrase("es", "greeting"));
		}
		AssertEquals("Bienvenido de nuevo, user123!",                     lang.getPhrase("es", "welcome_back", "player", "user123"));
		AssertEquals("Adios, nos vemos la proxima!",                      lang.getPhrase("es", "farewell"));
		AssertEquals("Escriba /help para obtener una lista de comandos.", lang.getPhrase("es", "help"    ));
		AssertEquals("Se ha producido un error. Inténtalo de nuevo.",     lang.getPhrase("es", "error"   ));
		AssertEquals("Te has unido al juego.",                            lang.getPhrase("es", "join"    ));
		AssertEquals("Has abandonado el juego.",                          lang.getPhrase("es", "leave"   ));
		// local file overrides resource (fr.json
		AssertEquals("Hello local", lang.getPhrase("fr", "greeting"));
	}



}
