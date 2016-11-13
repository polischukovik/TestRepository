package polischukovik.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import polischukovik.properties.Properties;
import polischukovik.ui.UserInterfaceSet;
import polischukovik.web.service.PropertyService;
import polischukovik.web.service.TestService;

@Controller
public class UserInterfaceController {
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	Properties prop;
	
	@Autowired
	TestService testService;
	
	@Resource(name="interfaceContainer")
	Map<String, UserInterfaceSet> userInterfaceList;

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String userInterfaceGet(Model model){
		model.addAttribute("interfaceList",userInterfaceList);
		return "welcome";
	}
}
