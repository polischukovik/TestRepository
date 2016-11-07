package polischukovik.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import polischukovik.properties.Properties;
import polischukovik.properties.PropertyContainer;
import polischukovik.ui.UserInterfaceSet;
import polischukovik.web.service.PropertyService;
import polischukovik.web.service.TestService;

@Controller
@SessionAttributes("propertyContainer")
public class TestController {
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	Properties prop;
	
	@Autowired
	TestService testService;
	
	@Autowired
	UserInterfaceSet currentInterfaceSet;
	
	@RequestMapping("/")
	public String rootMapping(){
		return "redirect:/test-main";
	}
	
	@RequestMapping(name="/test-main", method = RequestMethod.GET)
	@ModelAttribute("propertyContainer")
	public PropertyContainer testMain() throws InstantiationException, IllegalAccessException{
		return propertyService.getPropertyList(currentInterfaceSet);
	}
	
	@RequestMapping(name="/test-main", method = RequestMethod.POST)
	public PropertyContainer createTest( @Valid @ModelAttribute("propertyContainer") PropertyContainer properyContainer, BindingResult result){
		properyContainer.print();
		return properyContainer;
	}
}
