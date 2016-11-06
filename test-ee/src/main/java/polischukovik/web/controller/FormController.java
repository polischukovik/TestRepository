package polischukovik.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import polischukovik.properties.PropertyContainer;
import polischukovik.ui.UserInterfaceSet;
import polischukovik.web.service.PropertyService;

@Controller
@SessionAttributes("propertyContainer")
public class FormController {
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	UserInterfaceSet currentInterfaceSet;
//	
	@RequestMapping(value="/main-form")
	
	@ModelAttribute("propertyContainer")
	public  PropertyContainer showForm(ModelMap model) throws InstantiationException, IllegalAccessException{
		return propertyService.getPropertyList(currentInterfaceSet);		
	}
	
	@RequestMapping(value="/main-form", method = RequestMethod.POST)
	public PropertyContainer showForm(
			@Valid @ModelAttribute("propertyContainer") PropertyContainer propertyContainer,
			BindingResult bindResult){
		System.err.println(propertyContainer.getPropertyMap());
		System.err.println(bindResult);
		return propertyContainer;		
	}
}