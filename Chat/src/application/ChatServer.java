package application;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import command.ChatProtocolException;
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
					
					ClientSocket clientSocket = new ClientSocket(socket);
					
				} catch (IOException e) {
					
					System.out.println("Client has disconnected");				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
