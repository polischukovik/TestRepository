package command;
import java.net.ProtocolException;
import java.net.Socket;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import user.UserPerson;
import utils.IdGenerator;

/***
 * Command is basic-level class for client communication.
 * {
 *   "action": "login",
 *   "value": "{...}"
 * }
 */
public class CommandImpl extends Command {
	public static IdGenerator generator = new IdGenerator();

	public transient String json;
	public CommandImpl(String json, Socket socket) throws ProtocolException {	
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		CommandImpl command = null;
		try{
			command = gson.fromJson(json, CommandImpl.class);
		} catch (JsonSyntaxException e){
			throw new ProtocolException("Incorrect json format");
		}
		
		this.id = generator.next();
		this.time = new Date().getTime();
		this.action = command.getAction();
		this.json = json;
		
	}
	
	@Override
	public void execute() throws ProtocolException{
		throw new IllegalStateException("Method must be overriden");
	};
	
	@Override
	public String toJSON(){
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		return gson.toJson(this);
	}
	
	public void validate() throws ProtocolException {
		// TODO Auto-generated method stub
		
	};
}
