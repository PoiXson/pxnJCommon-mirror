package com.poixson.tools.gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;


public class GsonAdapter_LangTable extends TypeAdapter<Map<String, String[]>> {



	public GsonAdapter_LangTable() {
		super();
	}



	@Override
	public Map<String, String[]> read(final JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) { in.nextNull(); return null; }
		final HashMap<String, LinkedList<String>> result = new HashMap<String, LinkedList<String>>();
		in.beginObject();
		//LOOP_KEYS:
		while (in.hasNext()) {
			final String key = in.nextName();
			LinkedList<String> list = result.get(key);
			if (list == null) {
				list = new LinkedList<String>();
				result.put(key, list);
			}
			SWITCH_TYPE:
			switch (in.peek()) {
			case BEGIN_ARRAY: {
				in.beginArray();
				while (in.hasNext())
					list.addLast(in.nextString());
				in.endArray();
				break SWITCH_TYPE;
			}
			case STRING:
				list.addLast(in.nextString());
				break SWITCH_TYPE;
			default: throw new JsonSyntaxException("Invalid json structure for language file");
			}
		} // end LOOP_KEYS
		in.endObject();
		// convert to map of arrays
		final Map<String, String[]> map = new HashMap<String, String[]>();
		for (final Entry<String, LinkedList<String>> entry : result.entrySet())
			map.put(entry.getKey(), entry.getValue().toArray(new String[0]));
		return map;
	}



	@Override
	public void write(final JsonWriter out, final Map<String, String[]> map) throws IOException {
		if (map == null) { out.nullValue(); return; }
		out.beginObject();
		for (final Entry<String, String[]> entry : map.entrySet()) {
			final String   key     = entry.getKey();
			final String[] phrases = entry.getValue();
			out.name(key);
			if (phrases.length > 1) {
				for (final String phrase : phrases)
					out.value(phrase);
			} else {
				out.value(phrases[0]);
			}
		}
		out.endObject();
	}



}
