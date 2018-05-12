package command;

import java.net.ProtocolException;

import application.ClientSocket;
import user.UserPerson;

public class Command {

	public static final String ACTION_EXIT = "exit";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_PRIVATE_MESSAGE = "private_message";
	public static final String ACTION_GROUP_MESSAGE = "group_message";
	public static final String ACTION_REGISTER = "register";
	public static final String ACTION_LOGOUT = "logout";
	
	public Command() {
		super();
	}

	public void execute(ClientSocket clientSocket) throws ChatProtocolException {
		if (Command.ACTION_LOGIN.equals(cmd.getAction()) || Command.ACTION_REGISTER.equals(cmd.getAction())) {
			LoginCommand loginCommand= LoginCommand.valueOf(cmd);
			
			UserPerson user = UserPerson.authenticate(loginCommand);
			if(user ==  null){
				System.out.println("Such combination of username/password has not accepted");
				return;
			}
			user.login(reader);
			
		} else if(Command.ACTION_REGISTER.equals(cmd.getAction())){
			RegisterCommand register = RegisterCommand.valueOf(cmd);
			UserPerson user = UserPerson.register(register);
			user.login(reader);
			
		} else if(Command.ACTION_EXIT.equals(cmd.getAction())){
			clientSocket.disable();
		} else{
			throw new ChatProtocolException("On this state Command should have attribute 'action'=[login, regiser, exit]");
		}
	};

}