import java.net.ProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginCommand extends Command{
	public LoginCommand(String id, UserPerson person) {
		super(id, person);
	}

	private String username;
	private String password;

	public static LoginCommand valueOf(Command cmd) throws ProtocolException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		LoginCommand command = gson.fromJson(value, LoginCommand.class);
		command.validate();
		return command;
	}

	@Override 
	public void validate() throws ProtocolException{
		if(this.username == null || this.password == null){
			throw new ProtocolException();
		};
	}

	@Override
	void execute() throws ProtocolException {
		LoginCommand login = LoginCommand.valueOf(this);
		UserPerson user = UserPerson.login(login);
	}
}
