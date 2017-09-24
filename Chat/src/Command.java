import java.net.ProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Command {

	public static final String ACTION_EXIT = "exit";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_PRIVATE_MESSAGE = "private_message";
	public static final String ACTION_GROUP_MESSAGE = "group_message";
	public static final String ACTION_REGISTER = "register";

	private String id;
	private String action;
	private String value;
	private String time;
	protected UserPerson sender;
	
	public Command(String id, UserPerson person) {
		this.id = id;
		this.sender = person;
	}
	public String getAction() {
		return action;
	}
	public String getValue() {
		return value;
	}
	public String getTime() {
		return time;
	}
	
	abstract void validate() throws ProtocolException;
	
	@Override
	public String toString() {
		return "Message[" + id + "]";
	}
	public UserPerson getSender() {
		return sender;
	}
	abstract  void execute() throws ProtocolException;
	
	public String toJSON(){
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		return gson.toJson(this);
	};
}
