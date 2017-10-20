package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();
	
	public JsonUtils() {
		// TODO Auto-generated constructor stub
	}

}
