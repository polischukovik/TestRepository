package command;
import java.net.ProtocolException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import application.ClientSocket;
import utils.IdGenerator;
import utils.JsonUtils;

/***
 * ServerCommand is basic-level class for server communication.
 * {
 *   "action": "login",
 *   "params": "[...]"
 * }
 */
public class ServerCommand extends Command {
	public static IdGenerator generator = new IdGenerator();
	public List<String> requiredKeys; 

	public transient String id;
	public transient String sender;
	public transient long time;
	
	public String action;
	public Map<String, String> params;
	
	public ServerCommand() throws ProtocolException {		
		this.id = generator.next();
		this.time = new Date().getTime();		
	}
			
	public String getAction() {
		return action;
	}

	@Override
	public void execute(ClientSocket clientSocket) throws ChatProtocolException{
		throw new IllegalStateException("Method must be overriden");
	};
	
	public String toJSON(){
		
		return JsonUtils.gson.toJson(this);
	}

	public Map<String, String> getParams() {
		return params;
	}
}
