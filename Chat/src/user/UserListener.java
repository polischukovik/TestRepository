package user;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;

import command.Command;
import command.ServerCommand;
import command.CommandReader;
import command.RegisterCommand;

public abstract class UserListener extends User{	
	CommandReader reader = null;
	OutputStream out = null;
	InputStream in = null;
	
	protected UserListener(RegisterCommand cmd) throws ProtocolException{
		super(cmd);
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
	
	public void startInputLoop(CommandReader reader){
		this.reader = reader;
		try {
			this.in = reader.getSocket().getInputStream();
			this.out = reader.getSocket().getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		new Thread(() -> {
			System.out.println("Starting input thread for user: " + this);
			ServerCommand cmd = null;
			
			try {
				while(!Command.ACTION_LOGOUT.equals((cmd = reader.readCommand()).getAction())){
					try {
						cmd.execute(this);
					} catch (ProtocolException e) {
						e.printStackTrace();
					}
				}
				
				reader.getSocket().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}).start();
	}
	
}
