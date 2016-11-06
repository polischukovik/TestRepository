package polischukovik;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.list.LazyList;

public class PropertyContainer implements Serializable{
	
	private Map<String, List<PropertyComponent>> propertyMap;

    @SuppressWarnings("unchecked")
	public PropertyContainer() {
    	this.propertyMap = MapUtils.lazyMap(new HashMap<String,List<Object>>(), new Factory() {    		
    		public Object create() {
    			return LazyList.decorate(new ArrayList<PropertyComponent>(), 
    					FactoryUtils.instantiateFactory(PropertyComponent.class));
    		}
    	});
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
    
	public void print(){
		for(String s : propertyMap.keySet()){
			System.out.println("\t"+s);
			for(PropertyComponent p : propertyMap.get(s)){
				System.out.println("\t\t"+p.getName() + ":" + p.getValue());
			}
		}
	}
    
}