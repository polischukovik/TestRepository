package training.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import training.domain.Todo;
import training.service.TodoService;

@Controller
@SessionAttributes("username")
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
	
	@RequestMapping(value="/")
	public String getIndex(){
		return "redirect:/todo-list";
	}
	
	@RequestMapping(value="/todo-list", method =RequestMethod.GET)
	public String getTodoList(ModelMap model){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		model.put("todos", service.getTodosByUser(username));
		return "todo-list";
	}
	
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public String newTodo(@RequestParam(required = false) Integer id, ModelMap model){
		Todo todo;
		if(id == null){
			todo = new Todo();
		}else{
			todo = service.getTodoById(id);
		}
		model.addAttribute(todo);
		return "todo";
	}	
	
	@RequestMapping(value = "/todo", method = RequestMethod.POST)
	public String addAndUpdateTodo( ModelMap model, @Valid Todo todo, BindingResult binding){
		if(binding.hasErrors()){
			return "todo";
		}
		
		if(todo.getId() == -1){
			service.add(model.get("username").toString(), todo.getDescription(), new Date(), true);
		}else{
			service.update(todo.getId(), (String) model.get("username"), todo.getDescription(), todo.getDate(), todo.getIsActive());
		}
		
		return "redirect:/todo-list";		
	}
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String addTodo(@RequestParam int id, ModelMap model){
		service.delete(id);
		return "redirect:/todo-list";		
	}
}
