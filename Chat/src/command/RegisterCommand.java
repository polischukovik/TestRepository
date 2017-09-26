package command;
import java.net.ProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegisterCommand extends ServerCommand{
	private String email;
	private String username;
	private String password;
		
	public RegisterCommand(String str) throws ProtocolException {
		super(str, null);
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public static RegisterCommand valueOf(Command cmd) throws ProtocolException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		RegisterCommand command = gson.fromJson(value, RegisterCommand.class);
		command.validate();
		return command;
	}
	
	@Override 
	public void validate() throws ProtocolException{
		if(this.email == null || this.username == null || this.password == null){
			throw new ProtocolException();
		};
	}

	@Override
	public void execute() throws ProtocolException {
		throw new IllegalStateException("This method is not permited for RegisterCommand");		
	}	

}
