package application;

import java.util.HashMap;
import java.util.Map;

public class Properties {
	private static Map<String, Object> properties = new HashMap<>();
	
	static{
		properties.put("logo_image_path", "res/logo.jpg");
	}
	
	public static String getString(String propertyName){
		return (String) properties.get(propertyName);
	}
}
