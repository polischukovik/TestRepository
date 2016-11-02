package polischukovik.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import polischukovik.ui.UserInterfaceSet;
import polischukovik.web.properties.PropertyService;

@Controller
public class TestController {
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	UserInterfaceSet currentInterfaceSet;
	
	@RequestMapping("/")
	public String rootMapping(){
		return "redirect:/test-main";
	}
	
	@RequestMapping("/test-main")
	public String testMain(ModelMap model) throws InstantiationException, IllegalAccessException{
		model.addAttribute("categories", propertyService.getPropertyList(currentInterfaceSet));
		return "test-main";
	}

}
