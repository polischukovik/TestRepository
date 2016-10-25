package polischukovik.mslibrary;

import java.util.HashMap;
import java.util.Map;

import polischukovik.domain.enums.PropertyNames;

public class Properties {

	private Map<PropertyNames, String> properties;
	private Map<PropertyNames, String> defaults;
	
	public Properties() {
		super();
		this.properties = new HashMap<>();
		this.defaults = new HashMap<>();
	}

	public void add(PropertyNames nameEnum, String value, String defaultVal){
		properties.put(nameEnum, value);
		defaults.put(nameEnum, defaultVal);
	}

	public String get(PropertyNames nameEnum){
		return properties.containsKey(nameEnum) ? properties.get(nameEnum) : defaults.get(nameEnum);		
	}
	
	public Map<PropertyNames, String> getAllProperties(){
		return properties;
	}
}