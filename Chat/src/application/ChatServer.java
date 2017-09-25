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
		Command cmd = reader.readCommand();
		
		if (Command.ACTION_LOGIN.equals(cmd.getAction()) || Command.ACTION_REGISTER.equals(cmd.getAction())) {
			LoginCommand loginCommand= LoginCommand.valueOf(cmd);
			try{
				UserPerson user = UserPerson.authenticate(loginCommand);
				user.login(accept);
			} catch (NullPointerException e){
				System.out.println("Such combination of username/password has not accepted");
			}
			
		} else if(Command.ACTION_REGISTER.equals(cmd.getAction())){
			RegisterCommand register = RegisterCommand.valueOf(cmd);
			UserPerson user = UserPerson.register(register);
			user.login(accept);
			
		} else if(Command.ACTION_EXIT.equals(cmd.getAction())){
			accept.close();
		} else{
			throw new ProtocolException("Unexpected communication");
		}
	}

	public static void process(Command cmd) throws ProtocolException {
		cmd.execute();
	}
	
	
}
