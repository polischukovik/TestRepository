package training.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@RequestMapping(value="/login" , method=RequestMethod.GET)
	public String getLoginPage(){
		return "login";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied(Principal user, ModelMap model) {


		if (user != null) {
			model.addAttribute("msg", "Hi " + user.getName()
			+ ", you do not have permission to access this page!");
		} else {
			model.addAttribute("msg",
			"You do not have permission to access this page!");
		}

		return "error/403";

	}
}
