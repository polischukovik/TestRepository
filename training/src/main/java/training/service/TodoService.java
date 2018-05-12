package training.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import training.domain.Todo;

@Service
public class TodoService {
	
	private static List<Todo> todos = new ArrayList<>();
	private static int counter = 3;
	
	static{
		todos.add(new Todo(0, "alex", "Hug my wife", new Date(), true));
		todos.add(new Todo(1, "alex", "Go to sleep", new Date(), true));
		todos.add(new Todo(2, "alex", "Have fun", new Date(), true));
	}
	
	public TodoService(){
	}
	
	public List<Todo> getTodosByUser(String username){
		return todos.stream().filter(a -> a.getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
	}
	
	public Todo getTodoById(int id){
		return todos.stream()
				.peek(Objects::requireNonNull)
				.filter(b -> b.getId() == id)
				.collect(Collectors.toList()).get(0); //kostyl'
	}
	
	public void add(String username, String description, Date date, boolean isActive){
		todos.add(new Todo(counter++, username, description, date, isActive));
	}
	
	public void delete(int id){
		todos.remove(getTodoById(id));
	}
	
	public void update(int id, String username, String description, Date date, boolean isActive){
		Todo todo = getTodoById(id);
		todo.setUsername(username);
		todo.setDescription(description);
		todo.setDate(date);
		todo.setIsActive(isActive);
	}

}
