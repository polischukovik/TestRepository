package application;

import java.io.IOException;
import java.net.Socket;

import command.ChatProtocolException;
import command.Command;
import command.CommandReader;

public class ClientSocket{
	CommandReader reader = null;
	boolean enabled = false;
	
	public ClientSocket(Socket socket) {
		this.reader = new CommandReader(socket);
		
		enable();
		System.out.println("Client has connected");		
		
		/**
		 * Command in the json string terminated by empty string.
		 * 
		 */
		
		new Thread(() -> {

			Command cmd = null;
			while(isEnabled()){
				try {
					cmd = reader.readCommand();
					cmd.execute(this);
				} catch (IOException e) {		
					disable();
					e.printStackTrace();
				} catch (ChatProtocolException e) {
					e.printStackTrace();
				}				
				
			}			

			System.out.println(String.format("Input thread [%s] was stopped", reader.getSocket().getInetAddress()));		
		});
	}

	private boolean isEnabled() {
		return enabled;
	}

	private void disable() {
		this.enabled = false;
	}

	private void enable() {
		this.enabled = true;
	}

}
