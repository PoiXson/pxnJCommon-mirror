package com.poixson.app.commands;

import com.poixson.app.xApp;


public class xCommands_ExitKill {

	protected final xApp app;



	public xCommands_ExitKill(final xApp app) {
		this.app = app;
	}



	@xCommand(Name="exit", Aliases="e,stop,quit")
	public void __COMMAND_exit() {
		this.app.stop();
	}
	@xCommand(Name="kill", Aliases="k")
	public void __COMMAND__kill() {
		this.app.kill();
		System.exit(1);
	}



}
