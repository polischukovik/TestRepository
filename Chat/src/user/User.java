package user;
import command.RegisterCommand;

public abstract class User{

	protected String id;
	protected String email;
	protected String name;
	protected String password;
	
	protected User(RegisterCommand register) {
		super();
		this.id = generateId();
		this.email = register.getEmail();
		this.name = register.getUsername();
		this.password = register.getPassword();
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
