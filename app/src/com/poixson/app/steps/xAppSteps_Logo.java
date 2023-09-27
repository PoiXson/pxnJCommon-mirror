/*
package com.poixson.app.steps;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.poixson.app.xApp;
import com.poixson.app.xAppStep;
import com.poixson.app.xAppStep.StepType;
import com.poixson.logger.xLog;
import com.poixson.tools.AsciiArtBuilder;
import com.poixson.utils.ProcUtils;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


/ *
 * Startup sequence
 *   50  display logo
 * /
public class xAppSteps_Logo {

	protected static final int INDENT        = 2;
	protected static final int VERSION_WIDTH = 15;

	protected final xApp app;



	public xAppSteps_Logo(final xApp app) {
		this.app = app;
	}



	// -------------------------------------------------------------------------------
	// startup steps



	// display logo
	@xAppStep(type=StepType.STARTUP, step=50, title="Display Logo")
	public void __START_display_logo() {
		final xLog log = this.app.log();
		log.publish();
		this.displayLogo(log);
		log.publish();
		this.displayLegal(log);
		log.publish();
		DisplayStartupVars(this.app, log);
		log.publish();
	}



	protected void displayLegal(final xLog log) {
		log.publish(" This program comes with absolutely no warranty. This is free ");
		log.publish(" software and you are welcome to modify it or redistribute it ");
		log.publish(" under certain conditions. Type 'show license' at the command ");
		log.publish(" prompt for license details, or go to www.growcontrol.com for ");
		log.publish(" more information.                                            ");
	}



	protected String getAppVersion() {
		return this.app.getVersion();
	}
	protected String getAppVersionPadded() {
		return StringUtils.PadCenter(VERSION_WIDTH, this.getAppVersion(), ' ');
	}



	public static Map<String, String> GetStartupVars(final xApp app) {
		final Map<String, String> result = new LinkedHashMap<String, String>();
		result.put( "Pid",         Integer.toString(ProcUtils.getPid()) );
		result.put( "Version",     app.getVersion()                     );
		result.put( "Commit",      app.getCommitHashShort()             );
		result.put( "Running as",  System.getProperty("user.name")      );
		result.put( "Current dir", System.getProperty("user.dir")       );
		result.put( "java home",   System.getProperty("java.home")      );
		final String[] args = app.getArgs();
		if (Utils.notEmpty(args)) {
			result.put("Args", StringUtils.MergeStrings(", ", args));
		}
		return result;
	}
	public static void DisplayStartupVars(final xApp app, final xLog log) {
		final Map<String, String> varsMap = GetStartupVars(app);
		final Iterator<Entry<String, String>> it = varsMap.entrySet().iterator();
		final int maxLineSize =
			StringUtils.FindLongestLine(
				varsMap.keySet().toArray(new String[0])
			) + 1;
		final StringBuilder str = new StringBuilder();
		while (it.hasNext()) {
			final Entry<String, String> entry = it.next();
			final String key = entry.getKey();
			final String val = entry.getValue();
			str.setLength(0);
			str.append(key)
				.append(':')
				.append( StringUtils.Repeat(maxLineSize - key.length(), ' ') )
				.append(val);
			log.publish( str.toString() );
		}
	}



// 0 |                                     _/\_                        |
// 1 |         |`-.__     PoiXson          (('>  C      _   _          |
// 2 |         / ' _/     Software    _    /^|         /\\_/ \         |
// 3 |   A   -****\"  <---version---> =>--/__|m---    / 0  0  \        |
// 4 |      /    }                         ^^        /_   v   _\       |
// 5 |     /    \               @..@                   \__^___/        |
// 6 | \ /`    \\\          B  (----)              D   /  0    \       |
// 7 |  `\     /_\\           ( >__< )                /        \__     |
// 8 |   `~~~~~~``~`          ^^ ~~ ^^                \_(_|_)___  \    |
// 9 |^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^(____//^/^|
//10 |/////////////////////////////////////////////////////////////////|
//   0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6 8 0 2 4 6
//             1         2         3         4         5         6
	protected void displayLogo(final xLog log) {
		// define colors
		final String COLOR_BG = "black";
		final String COLOR_PXN_P       = "bold,green";
		final String COLOR_PXN_OI      = "bold,blue";
		final String COLOR_PXN_X       = "bold,green";
		final String COLOR_PXN_SON     = "bold,blue";
		final String COLOR_SOFTWARE    = "bold,black";
		final String COLOR_VERSION     = "cyan";
		final String COLOR_GRASS       = "green";
		final String COLOR_DOG         = "yellow";
		final String COLOR_DOG_EYES    = "cyan";
		final String COLOR_DOG_MOUTH   = "red";
		final String COLOR_DOG_COLLAR  = "red";
		final String COLOR_DOG_NOSE    = "bold,black";
		final String COLOR_FROG        = "green";
		final String COLOR_FROG_EYES   = "bold,black";
		final String COLOR_WITCH       = "bold,black";
		final String COLOR_WITCH_EYES  = "red";
		final String COLOR_WITCH_BROOM = "yellow";
		final String COLOR_CAT         = "white";
		final String COLOR_CAT_EYES    = "white";
		final String COLOR_CAT_MOUTH   = "red";
		final String COLOR_CAT_COLLAR  = "blue";
		final String COLOR_CAT_NOSE    = "bold,black";
		// ascii art
		final String version = this.getAppVersionPadded();
		final AsciiArtBuilder art =
			new AsciiArtBuilder(
				"                                     _/\\_                        ",
				"         |`-.__     PoiXson          (('>         _   _          ",
				"         / ' _/     Software    _    /^|         /\\\\_/ \\         ",
				"       -****\\\"  "+version+" =>--/_\\|m---    / 0  0  \\        ",
				"      /    }                         ^^        /_   v   _\\       ",
				"     /    \\               @..@                   \\__^___/        ",
				" \\ /`    \\\\\\             (----)                  /  0    \\       ",
				"  `\\     /_\\\\           ( >__< )                /        \\__     ",
				"   `~~~~~~``~`          ^^ ~~ ^^                \\_(_|_)___  \\    ",
				"^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^/^(____//^/^",
				"/////////////////////////////////////////////////////////////////"
			);
		art.setIndent(INDENT);
		art.setBgColor(COLOR_BG);
		// line 1    color               x   y
		art.setColor(COLOR_WITCH,       37,  0);
		// line 2    color               x   y
		art.setColor(COLOR_DOG,          9,  1);
		art.setColor(COLOR_PXN_P,       20,  1);
		art.setColor(COLOR_PXN_OI,      21,  1);
		art.setColor(COLOR_PXN_X,       23,  1);
		art.setColor(COLOR_PXN_SON,     24,  1);
		art.setColor(COLOR_WITCH,       37,  1);
		art.setColor(COLOR_WITCH_EYES,  39,  1);
		art.setColor(COLOR_WITCH,       40,  1);
		art.setColor(COLOR_CAT,         50,  1);
		// line 3    color               x   y
		art.setColor(COLOR_DOG,          9,  2);
		art.setColor(COLOR_DOG_EYES,    11,  2);
		art.setColor(COLOR_DOG_MOUTH,   13,  2);
		art.setColor(COLOR_DOG_NOSE,    14,  2);
		art.setColor(COLOR_SOFTWARE,    20,  2);
		art.setColor(COLOR_WITCH_BROOM, 32,  2);
		art.setColor(COLOR_WITCH,       37,  2);
		art.setColor(COLOR_CAT,         49,  2);
		// line 4    color               x   y
		art.setColor(COLOR_DOG,          7,  3);
		art.setColor(COLOR_DOG_COLLAR,   8,  3);
		art.setColor(COLOR_DOG_NOSE,    12,  3);
		art.setColor(COLOR_DOG_MOUTH,   13,  3);
		art.setColor(COLOR_VERSION,     16,  3);
		art.setColor(COLOR_WITCH_BROOM, 32,  3);
		art.setColor(COLOR_WITCH,       36,  3);
		art.setColor(COLOR_WITCH_BROOM, 41,  3);
		art.setColor(COLOR_CAT,         48,  3);
		art.setColor(COLOR_CAT_EYES,    50,  3);
		art.setColor(COLOR_CAT,         56,  3);
		// line 5    color               x   y
		art.setColor(COLOR_DOG,          6,  4);
		art.setColor(COLOR_WITCH,       37,  4);
		art.setColor(COLOR_CAT,         47,  4);
		art.setColor(COLOR_CAT_NOSE,    52,  4);
		art.setColor(COLOR_CAT,         56,  4);
		// line 6    color               x   y
		art.setColor(COLOR_DOG,          5,  5);
		art.setColor(COLOR_FROG_EYES,   26,  5);
		art.setColor(COLOR_FROG,        27,  5);
		art.setColor(COLOR_FROG_EYES,   29,  5);
		art.setColor(COLOR_CAT,         49,  5);
		art.setColor(COLOR_CAT_MOUTH,   52,  5);
		art.setColor(COLOR_CAT,         53,  5);
		// line 7    color               x   y
		art.setColor(COLOR_DOG,          1,  6);
		art.setColor(COLOR_FROG,        23,  6);
		art.setColor(COLOR_CAT,         49,  6);
		art.setColor(COLOR_CAT_COLLAR,  52,  6);
		art.setColor(COLOR_CAT,         57,  6);
		// line 8    color               x   y
		art.setColor(COLOR_DOG,          2,  7);
		art.setColor(COLOR_FROG,        22,  7);
		art.setColor(COLOR_CAT,         48,  7);
		// line 9    color               x   y
		art.setColor(COLOR_DOG,          3,  8);
		art.setColor(COLOR_FROG,        22,  8);
		art.setColor(COLOR_CAT,         48,  8);
		// line 10   color               x   y
		art.setColor(COLOR_GRASS,        0,  9);
		art.setColor(COLOR_CAT,         55,  9);
		art.setColor(COLOR_GRASS,       61,  9);
		// line 11   color               x   y
		art.setColor(COLOR_GRASS,        0, 10);
		// display generated art
		log.publish(art.build());
	}



}
*/
