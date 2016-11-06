package polischukovik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("propertyContainer")
public class FormController {
	@RequestMapping(value="/form", method=RequestMethod.GET)
	@ModelAttribute("propertyContainer") 
	public PropertyContainer mainForm(){
		PropertyComponent component = new PropertyComponent();
		component.setName(PropertyName.BASIC_QUESTIONS);
		component.setSelectValues("");
		component.setType(PropertyType.FILE);
		component.setValue("value1");	
		
		PropertyComponent component2 = new PropertyComponent();
		component2.setName(PropertyName.BASIC_TEST_NAME);
		component2.setSelectValues("");
		component2.setType(PropertyType.FILE);
		component2.setValue("value2");	
		
		List<PropertyComponent> list1 = new ArrayList<>();
		list1.add(component);
		list1.add(component2);
		
		PropertyComponent component3 = new PropertyComponent();
		component3.setName(PropertyName.F_QUESTION_BOLD);
		component3.setSelectValues("");
		component3.setType(PropertyType.FILE);
		component3.setValue("value3");	
		
		PropertyComponent component4 = new PropertyComponent();
		component4.setName(PropertyName.F_QUESTION_SPACING);
		component4.setSelectValues("");
		component4.setType(PropertyType.FILE);
		component4.setValue("value4");	
		
		PropertyComponent component5 = new PropertyComponent();
		component5.setName(PropertyName.P_PUNCTUATION_ANSWER);
		component5.setSelectValues("");
		component5.setType(PropertyType.FILE);
		component5.setValue("value5");	
		
		List<PropertyComponent> list2 = new ArrayList<>();
		list2.add(component3);
		list2.add(component4);
		list2.add(component5);
		
		Map<String,List<PropertyComponent>> map = new HashMap<>();
		map.put("group1", list1);
		map.put("groupp2", list2);
		
		PropertyContainer propertyContainer = new PropertyContainer();
		propertyContainer.setPropertyMap(map);
		
		return propertyContainer;
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public PropertyContainer saveForm(@ModelAttribute("properyContainer") PropertyContainer properyContainer, BindingResult result){
		properyContainer.print();
		return properyContainer;
	}
}
