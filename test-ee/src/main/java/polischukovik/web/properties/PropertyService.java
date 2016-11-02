package polischukovik.web.properties;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import polischukovik.domain.PropertyComponent;
import polischukovik.properties.Properties;
import polischukovik.ui.UITools;
import polischukovik.ui.UserInterfaceSet;

@Service
public class PropertyService {
	
	@Autowired
	Properties prop;

	public List<?> getPropertyList(UserInterfaceSet currentInterfaceSet) throws InstantiationException, IllegalAccessException {
		List<PropertyComponent> result = prop.getAll(UITools.getPropertiesFromAllComponents(currentInterfaceSet));
		
		//grouping functions here
		List<SimpleImmutableEntry<String, List<PropertyComponent>>> categories = new ArrayList<>();
		
		//obtain unique group
		Set<String> listGroup = result.stream().map(y -> y.getGroup()).collect(Collectors.groupingBy(Function.identity())).keySet();
		
		//iterate group adding entries		
		for(String group : listGroup){
			categories.add(new SimpleImmutableEntry<String, List<PropertyComponent>>(group, result.stream().filter(i -> group.equals(i.getGroup())).collect(Collectors.toList())));
		}
		return  categories;		
	}

}
