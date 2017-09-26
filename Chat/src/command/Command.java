package command;

import java.net.ProtocolException;

import user.UserListener;

public class Command {

	public static final String ACTION_EXIT = "exit";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_PRIVATE_MESSAGE = "private_message";
	public static final String ACTION_GROUP_MESSAGE = "group_message";
	public static final String ACTION_REGISTER = "register";
	public static final String ACTION_LOGOUT = "logout";
	
	public String action;

	public Command() {
		super();
	}

	public String getAction() {
		return action;
	}

	public void execute(UserListener source) throws ProtocolException{
		new IllegalStateException("This method is not allowed");
	};

}