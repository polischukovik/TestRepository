package command;
import java.net.ProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginCommand extends ServerCommand{
	private String username;
	private String password;

	private LoginCommand(String str) throws ProtocolException {
		super(str, null);
	}
	
	public static LoginCommand valueOf(Command cmd) throws ProtocolException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		LoginCommand command = gson.fromJson(value, LoginCommand.class);
		command.validate();
		return command;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override 
	public void validate() throws ProtocolException{
		if(this.username == null || this.password == null){
			throw new ProtocolException();
		};
	}

	@Override
	public void execute() throws ProtocolException {
		throw new IllegalStateException("This method is not permited for LoginCommand");
	}
	
	
}
