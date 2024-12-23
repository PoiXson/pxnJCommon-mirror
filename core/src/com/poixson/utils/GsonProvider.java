package com.poixson.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.poixson.tools.Keeper;


public final class GsonProvider {
	private GsonProvider() {}
	static { Keeper.add(new GsonProvider()); }



	public static GsonBuilder GSON_Builder() {
		return
			new GsonBuilder()
			.disableHtmlEscaping()
			.setPrettyPrinting();
	}

	public static Gson GSON() {
		return GSON(GSON_Builder());
	}

	public static Gson GSON(final TypeAdapter<?>...adapters) {
		return GSON(GSON_Builder(), adapters);
	}

	public static Gson GSON(final GsonBuilder builder, final TypeAdapter<?>...adapters) {
		for (final TypeAdapter<?> adapter : adapters)
			builder.registerTypeAdapter(adapter.getClass(), adapter);
		return builder.create();
	}



}
