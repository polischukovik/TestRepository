package user;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import command.Command;
import command.CommandReader;
import command.LoginCommand;
import command.RegisterCommand;
import utils.IdGenerator;

public class UserPerson extends UserListener{
	private static Set<UserPerson> users = new HashSet<>();
	private static IdGenerator generator = new IdGenerator();

	private UserPerson(RegisterCommand register) throws ProtocolException{
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

	public static Collection<UserPerson> getAll() {
		return users;
	}

	/**
	 * Checks if there is registered user with combination of username and password 
	 * @param loginCommand LoginCommand instance containing combination of username and password as attributes
	 * @return UserPerson instance if given password matches username
	 * @throws ProtocolException when some attributes are missing on LoginCommand
	 */
	public static UserPerson authenticate(LoginCommand loginCommand) throws ProtocolException{
		String username = loginCommand.getUsername();
		String password = loginCommand.getPassword();
		
		if(username == null|| password == null){
			throw new ProtocolException("LoginCommand shoud have username and password attributes");
		}
		
		UserPerson user = getByName(username);
		return (user != null && user.getPassword().equals(password)) ? user : null;
	}	
	
	/**
	 * Creates UserPerson and ands it to static set that represents registered users
	 * @param register RegisterCommand to sign up user
	 * @return an instance of UserPerson that was just registered
	 * @throws ProtocolException when there are some mandatory attributes of RegisterCommand missing
	 */
	public static UserPerson register(RegisterCommand register) throws ProtocolException{
		UserPerson user = new UserPerson(register);
		users.add(user);
		return user;
	}
	
	public void login(CommandReader reader) {
		startInputLoop(reader);		
	}

	@Override
	protected String generateId() {		
		return generator.next();
	}

	public void sendTo(Command message){
		
	}

}
