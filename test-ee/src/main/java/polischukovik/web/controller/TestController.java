package polischukovik.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import polischukovik.properties.Properties;
import polischukovik.properties.PropertyContainer;
import polischukovik.ui.UserInterfaceSet;
import polischukovik.web.service.PropertyService;
import polischukovik.web.service.TestService;

@Controller
public class TestController {
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	Properties prop;
	
	@Autowired
	TestService testService;
	
	@Autowired
	Map<String, UserInterfaceSet> currentInterfaceContainer;
	
	@RequestMapping(value="/test-main", method = RequestMethod.GET)
	@ModelAttribute("propertyContainer")
	public PropertyContainer testMain(@PathVariable String set) throws InstantiationException, IllegalAccessException{
		return propertyService.getPropertyList(currentInterfaceContainer.get(set));
	}
	
	@RequestMapping(value="/test-main", method = RequestMethod.POST)
	public PropertyContainer createTest( @Valid @ModelAttribute("propertyContainer") PropertyContainer properyContainer, BindingResult result, Model model){
		properyContainer.print();
		propertyService.persist(properyContainer);
		
//		model.addAttribute("createResult", testService.createTest());
		return properyContainer;
	}	
}
