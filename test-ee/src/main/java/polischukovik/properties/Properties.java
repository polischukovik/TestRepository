package polischukovik.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import polischukovik.domain.PropertyComponent;
import polischukovik.domain.enums.PropertyName;
import polischukovik.domain.enums.PropertyType;

public class Properties {
		
	/*
	 * PropertyComponent already has PropertyName. Probably should use collection here
	 */
	private Map<PropertyName, PropertyComponent> componentMap;
	
	public Properties() {
		this.componentMap = new TreeMap<>();
	}

	public void addProperty(PropertyName name, String group, PropertyType type){
		componentMap.put(name, new PropertyComponent(name, group, type));
	}
	
	public void setValue(PropertyName name, String value){
		componentMap.get(name).addValue(value);
	}

	public String get(PropertyName name){	
		if(componentMap.containsKey(name)){
			return componentMap.get(name).getValue();
		} else {
			throw new IllegalArgumentException("Property is not defined in configuration"); 
		}			
	}
	
	public List<PropertyComponent> getAll(List<PropertyName> propertyNames){
		return componentMap.values().stream().filter(u-> propertyNames.contains(u.getName())).collect(Collectors.toList());	
	}
	
	public List<PropertyComponent> getAll(){
		return new ArrayList<PropertyComponent>(componentMap.values());	
	}
	
}