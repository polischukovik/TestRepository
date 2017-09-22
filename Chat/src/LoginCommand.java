import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginCommand extends Command{
	private String username;
	private String password;

	public static LoginCommand valueOf(Command cmd) throws UnformattedException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		LoginCommand command = gson.fromJson(value, LoginCommand.class);
		command.validate();
		return command;
	}

	@Override 
	public void validate() throws UnformattedException{
		if(this.username == null || this.password == null){
			throw new UnformattedException();
		};
	}

}
