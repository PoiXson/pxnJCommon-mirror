package com.poixson.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.poixson.tools.Keeper;


public final class GsonProvider {
	private GsonProvider() {}
	static { Keeper.Add(new GsonProvider()); }



	public static GsonBuilder GSON_Builder() {
		return
			new GsonBuilder()
			.disableHtmlEscaping()
			.setPrettyPrinting();
	}

	public static Gson GSON(final Object...args) {
		if (args.length == 0)
			return GSON_Builder().create();
		{
			GsonBuilder builder = null;
			int index = 0;
			if (args[index] instanceof GsonBuilder) {
				builder = (GsonBuilder) args[index];
				index++;
			}
			if (builder == null)
				builder = GSON_Builder();
			for (; index<args.length; index+=2) {
				final Type           type;
				final TypeAdapter<?> adapter;
				try {
					type    = (Type)           args[index  ];
					adapter = (TypeAdapter<?>) args[index+1];
				} catch (ClassCastException e) {
					throw new IllegalArgumentException("Invalid argument type/adapter pairs", e);
				}
				builder.registerTypeAdapter(type, adapter);
			}
			return builder.create();
		}
	}



}
