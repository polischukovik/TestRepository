package command;

import java.net.ProtocolException;

public abstract class Command {

	public static final String ACTION_EXIT = "exit";

	public abstract String toJSON();

	public abstract void execute() throws ProtocolException;

	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_PRIVATE_MESSAGE = "private_message";
	public static final String ACTION_GROUP_MESSAGE = "group_message";
	public static final String ACTION_REGISTER = "register";
	public static final String ACTION_LOGOUT = "logout";
	public transient String id;
	public transient long time;
	public String action;

	public Command() {
		super();
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

	@Override
	public String toString() {
		return "Message[" + id + "]";
	}

}