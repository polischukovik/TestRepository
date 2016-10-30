package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import training.service.LoginService;

@Controller
@SessionAttributes("username")
public class LoginController {
	@Autowired
	private LoginService service;
	
	@RequestMapping(value="/")
	public String getIndex(){
		return "redirect:/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginPage(){
		return "login";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String getLogoutPage(SessionStatus status){
		status.setComplete();
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String getWelcomePage(@RequestParam String username,
			@RequestParam String password, ModelMap model){
		if(!service.isValidUser(username, password)){
			return "login";
		}
		model.addAttribute("username", username);
		return "redirect:/todo-list";
	}

}
