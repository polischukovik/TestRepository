package polischukovik;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FormController {
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {

	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	@ModelAttribute("propertyComponent") 
	public PropertyComponent mainForm(){
		PropertyComponent propertyComponent = new PropertyComponent();
		propertyComponent.setName(PropertyName.BASIC_QUESTIONS);
		propertyComponent.setSelectValues("asd,sdfg,fh");
		propertyComponent.setType(PropertyType.STRING);
		propertyComponent.setValue("djfh");
		
		return propertyComponent;
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public PropertyComponent saveForm(@Valid @ModelAttribute("propertyComponent") PropertyComponent propertyComponent, BindingResult result){
		System.out.println("\n\n" + propertyComponent.toString());
		return propertyComponent;
	}
}
