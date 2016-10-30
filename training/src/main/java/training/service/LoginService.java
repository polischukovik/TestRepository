package training.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	private static Map<String, String> users;
	
	public LoginService(){
		users  = new HashMap<>();
		users.put("user1", "asdf");
	}
	
	public boolean isValidUser(String username, String password){
		String vPassword = users.get(username.toLowerCase());
		return vPassword != null && vPassword.equals(password);
	}
	
	public void addUser(String user, String password){
		users.put(user, password);
	}
}
