package polischukovik.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import polischukovik.domain.PropertyComponent;
import polischukovik.properties.Properties;
import polischukovik.properties.PropertyContainer;
import polischukovik.ui.UITools;
import polischukovik.ui.UserInterfaceSet;

@Service
public class PropertyService {
	
	@Autowired
	Properties prop;

	public PropertyContainer getPropertyList(UserInterfaceSet currentInterfaceSet) throws InstantiationException, IllegalAccessException {
		List<PropertyComponent> result = prop.getAll(UITools.getPropertiesFromAllComponents(currentInterfaceSet));
				
		Map<String, List<PropertyComponent>> categories = new HashMap<>();
		
		//grouping functions here
		//obtain unique groups
		Set<String> listGroup = result.stream()
				.map(y -> y.getGroup())
				.collect(Collectors.groupingBy(Function.identity()))
				.keySet();
		
		//iterate group adding entries				
		for(String group : listGroup){
			categories.put(group, result.stream()
					.filter(i -> group.equals(i.getGroup()))
					.collect(Collectors.toList()));
		}
		return new PropertyContainer(categories);		
	}
	
	public void persist(PropertyContainer container){
		prop.persistPropertyContainer(container);
	}
}
