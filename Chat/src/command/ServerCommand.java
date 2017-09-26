package command;
import java.net.ProtocolException;
import java.net.Socket;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import user.UserListener;
import user.UserPerson;
import utils.IdGenerator;

/***
 * Command is basic-level class for client communication.
 * {
 *   "action": "login",
 *   "value": "{...}"
 * }
 */
public class ServerCommand extends Command {
	public static IdGenerator generator = new IdGenerator();

	public String id;
	public String sender;
	public long time;
	
	public ServerCommand() throws ProtocolException {		
		this.id = generator.next();
		this.time = new Date().getTime();		
	}
	
	@Override
	public void execute(UserListener source) throws ProtocolException{
		throw new IllegalStateException("Method must be overriden");
	};
	
	public String toJSON(){
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		return gson.toJson(this);
	}
}
