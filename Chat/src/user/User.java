package user;
import javax.xml.ws.ProtocolException;

import command.RegisterCommand;

public abstract class User{

	protected String id;
	protected String email;
	protected String name;
	protected String password;
	
	/**
	 * {
	 *   id: "a1gww3"
	 *   email: "dummy@email.com"
	 *   name: "username"
	 *   password: "Hy$SK_Q)"
	 * }
	 * @param register
	 */
	protected User(RegisterCommand register) throws ProtocolException{
		super();
		this.id = generateId();
		this.email = register.getEmail();
		this.name = register.getUsername();
		this.password = register.getPassword();
		
		if(email == null || name == null || password == null){
			throw new ProtocolException("Missing mandatry attribute of RegisterCommand");
		}
		
	}
	public String getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	
	abstract String generateId();
	
	@Override
	public String toString() {
		return id;
	}
		
}
