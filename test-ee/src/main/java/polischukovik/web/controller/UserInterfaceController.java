package polischukovik.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import polischukovik.properties.Properties;
import polischukovik.ui.UserInterfaceContainer;
import polischukovik.web.service.PropertyService;
import polischukovik.web.service.TestService;

@Controller
@SessionAttributes("userInterfaceContainer")
public class UserInterfaceController {
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	Properties prop;
	
	@Autowired
	TestService testService;
	
	@Autowired
	UserInterfaceContainer userInterfaceContainer;

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String userInterfaceGet(Model model){
		model.addAttribute("userInterfaceContainer",userInterfaceContainer);
		return "welcome";
	}
}
