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
public class Command {
	public static IdGenerator generator = new IdGenerator();

	public static final String ACTION_EXIT = "exit";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_PRIVATE_MESSAGE = "private_message";
	public static final String ACTION_GROUP_MESSAGE = "group_message";
	public static final String ACTION_REGISTER = "register";
	public static final String ACTION_LOGOUT = "logout";

	public transient String id;
	public String action;
	public transient String json;
	public transient long time;
	public transient String sender;

	public Command(String json, Socket socket) throws ProtocolException {	
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		Command command = null;
		try{
			command = gson.fromJson(json, Command.class);
		} catch (JsonSyntaxException e){
			throw new ProtocolException("Incorrect json format");
		}
		
		this.id = generator.next();
		this.time = new Date().getTime();
		this.sender = UserPerson.getBySocket(socket);
		this.action = command.getAction();
		this.json = json;
		
	}
	
	public String getAction() {
		return action;
	}
	public String getValue() {
		return json;
	}
	public long getTime() {
		return time;
	}
	public String getSender() {
		return sender;
	}	
	
	@Override
	public String toString() {
		return "Message[" + id + "]";
	}
	
	public void execute() throws ProtocolException{
		throw new IllegalStateException("Method must be overriden");
	};
	
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
