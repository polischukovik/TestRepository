package polischukovik.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
public class TestController {
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	Properties prop;
	
	@Autowired
	TestService testService;
	
	@Autowired
	UserInterfaceSet currentInterfaceSet;
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) throws Exception {
//		binder.registerCustomEditor(String.class, new CustomBooleanEditor(false));
//	}
	
	@RequestMapping("/")
	public String rootMapping(){
		return "redirect:/test-main";
	}
	
	@RequestMapping(value="/test-main", method = RequestMethod.GET)
	@ModelAttribute("propertyContainer")
	public PropertyContainer testMain() throws InstantiationException, IllegalAccessException{
		return propertyService.getPropertyList(currentInterfaceSet);
	}
	
	@RequestMapping(value="/test-main", method = RequestMethod.POST)
	public PropertyContainer createTest( @Valid @ModelAttribute("propertyContainer") PropertyContainer properyContainer, BindingResult result, Model model){
		properyContainer.print();
		propertyService.persist(properyContainer);
		
//		model.addAttribute("createResult", testService.createTest());
		return properyContainer;
	}	
}
