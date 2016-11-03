package polischukovik.ui;

import java.util.ArrayList;
import java.util.List;

import polischukovik.domain.enums.PropertyName;
import polischukovik.properties.RequiredPropertyNameProvider;

public class UITools {
	
	public static List<PropertyName> getPropertiesFromAllComponents(UserInterfaceSet currentInterfaceSet){
		
		List<PropertyName> result = new ArrayList<>();
		for(RequiredPropertyNameProvider provider : currentInterfaceSet.getPropertyProviders()){
			result.addAll(provider.getRequiredProperties());
		}
		return result;
		
	}

}
