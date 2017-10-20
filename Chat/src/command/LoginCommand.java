package command;
import java.net.ProtocolException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoginCommand extends ServerCommand{
	private String username;
	private String password;
	requiredKeys = Arrays.	
			
	private LoginCommand(Map<String, String> params) throws ProtocolException {


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
