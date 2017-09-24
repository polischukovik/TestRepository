import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RegisterCommand extends Command{
	private String email;
	private String username;
	private String password;
	
	public RegisterCommand(String id, String email, String username, String password) {
		super(id);
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public static RegisterCommand valueOf(Command cmd) throws UnformattedException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		RegisterCommand command = gson.fromJson(value, RegisterCommand.class);
		command.validate();
		return command;
	}
	
	@Override 
	public void validate() throws UnformattedException{
		if(this.email == null || this.username == null || this.password == null){
			throw new UnformattedException();
		};
	}	

}
