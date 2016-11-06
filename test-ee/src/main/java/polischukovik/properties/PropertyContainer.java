package polischukovik.properties;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import polischukovik.domain.PropertyComponent;

public class PropertyContainer implements Serializable{
	
	private Map<String, List<PropertyComponent>> propertyMap;

    public PropertyContainer() {
    }
    
    public PropertyContainer(Map<String, List<PropertyComponent>> propertyMap) {
    	this.propertyMap = propertyMap;
    }
	public Map<String, List<PropertyComponent>> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, List<PropertyComponent>> propertyMap) {
		this.propertyMap = propertyMap;
	}

	@Override
	public String toString() {
		return "PropertyContainer [propertyMap=" + propertyMap + "]";
	}
    
    
}