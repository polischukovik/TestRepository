package user;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import command.LoginCommand;
import command.RegisterCommand;
import utils.IdGenerator;

public class UserPerson extends UserListener{
	private static Set<UserPerson> users = new HashSet<>();
	private static IdGenerator generator = new IdGenerator();

	private UserPerson(RegisterCommand register){
		super(register);
	}
	
	public static UserPerson getById(String id) {
		Set<UserPerson> user = users.stream().filter(s -> id.equals(s.getId())).collect(Collectors.toSet());
		return user.size() == 0 ? null : null;
	}
	
	private static UserPerson getByName(String username) {
		Set<UserPerson> user = users.stream().filter(s -> username.equals(s.getName())).collect(Collectors.toSet());
		return user.size() == 0 ? null : null;
	}

	public static String getBySocket(Socket socket) {
		Set<UserPerson> user = users.stream().filter(s -> socket.equals(s.getSocket())).collect(Collectors.toSet());
		return user.size() == 0 ? null : null;
	}

	public static Collection<UserPerson> getAll() {
		return users;
	}

	public static UserPerson authenticate(LoginCommand loginCommand) throws NullPointerException{
		UserPerson user = getByName(loginCommand.getUsername());
		return user.getPassword().equals(loginCommand.getPassword()) ? user : null;
	}	
	
	public static UserPerson register(RegisterCommand register) {
		UserPerson user = new UserPerson(register);
		users.add(user);
		return user;
	}
	
	public void login(Socket accept) {
		initiateSocket(accept);		
	}

	@Override
	protected String generateId() {		
		return generator.next();
	}

	

}
