package user;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.Socket;

import command.Command;
import command.CommandImpl;
import command.CommandReader;
import command.RegisterCommand;

public abstract class UserListener extends User{	
	Socket socket = null;
	OutputStream out = null;
	InputStream in = null;
	
	protected UserListener(RegisterCommand cmd) {
		super(cmd);
	}
	
	

	public Socket getSocket() {
		return socket;
	}

	public void send(Command message){
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			writer.write(message.toJSON() + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initiateSocket(Socket clientSocket){
		this.socket = clientSocket;
		try {
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		new Thread(() -> {
			System.out.println("Strting input thread for user: " + this);
			CommandImpl cmd = null;
			CommandReader reader = new CommandReader(clientSocket);
			
			try {
				while(!(cmd = reader.readCommand()).getAction().equals(Command.ACTION_LOGOUT)){
					try {
						cmd.execute();
					} catch (ProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}).start();
	}
	
}
