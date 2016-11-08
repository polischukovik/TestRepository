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
		componentMap.get(name).setValue(value);
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
	
	public Map<PropertyName, PropertyComponent> getComponentMap() {
		return componentMap;
	}

	public void print(){
		System.out.println("\tPrinting Properties");
		for(PropertyComponent c : componentMap.values()){
			System.out.println("\t\t" + c);
		}
	}
	
	public void persistPropertyContainer(PropertyContainer container){
		List<PropertyComponent> components = new ArrayList<>();
		for(List<PropertyComponent> c : container.getPropertyMap().values()){
			components.addAll(c);
		}
		for(PropertyComponent component : components){
			if(PropertyType.BOOLEAN.equals(component.getType())){
				this.setValue(component.getName(), component.getValue());
				this.setValue(component.getName(), String.valueOf(component.getBool()));
			} else {
				this.setValue(component.getName(), component.getValue());
			}
		}
	}
	
}