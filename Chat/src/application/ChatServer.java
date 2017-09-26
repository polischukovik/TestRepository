package application;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;

import command.Command;
import command.CommandReader;
import command.LoginCommand;
import command.RegisterCommand;
import user.UserPerson;


public class ChatServer extends Thread implements Runnable{
	private int port;
	public ChatServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		System.out.println("Starting server on port: " + port);
		try (ServerSocket welcomeSocket = new ServerSocket(port)) {
			Socket socket = null;
			while(true){
				try{
					socket = welcomeSocket.accept(); 
					connect(socket);
				} catch (ProtocolException e) {
					socket.close();
					e.printStackTrace();					
				} catch (IOException e) {
					System.out.println("Client has disconnected");				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	private void connect(Socket accept) throws IOException, ProtocolException {
		System.out.println("Client has connected");
		
		CommandReader reader = new CommandReader(accept);
		/**
		 * Command in the json string terminated by empty string.
		 * 
		 */
		Command cmd = reader.readCommand();
		
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
			accept.close();
		} else{
			throw new ProtocolException("On this state Command should have attribute 'action'=[login, regiser, exit]");
		}
	}

	public static void process(Command cmd) throws ProtocolException {
		cmd.execute();
	}
	
	
}
