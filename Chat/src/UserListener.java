import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.Socket;
import java.time.Instant;

public abstract class UserListener extends User{
	public static IdGenerator generator = new IdGenerator();
	
	Socket socket = null;
	OutputStream out = null;
	InputStream in = null;
	
	String welcome_options = "Welcome to chat applications: login send";
	
	public UserListener(Socket clientSocket) throws ProtocolException {		
		this.socket = clientSocket;
		try {
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		new Thread(() -> {
			Command cmd = null;
			CommandReader reader = new CommandReader(in);
					
			try {				
				while(!(Command.ACTION_EXIT == (cmd = reader.readCommand()).getAction())){
					switch (cmd.getAction()) {
					case Command.ACTION_LOGIN:
						LoginCommand login;
						try {
							login = LoginCommand.valueOf(cmd);
							login(login);
						} catch (UnformattedException e) {
							e.printStackTrace();
						}	
						break;
						
					case Command.ACTION_PRIVATE_MESSAGE:
						PrivateMessageCommand message;
						try {
							message = PrivateMessageCommand.valueOf(cmd);
							sendPrivate(message);
						} catch (UnformattedException e) {
							e.printStackTrace();
						}						
						break;
						
					case Command.ACTION_GROUP_MESSAGE:
						GroupMessageCommand messageGroup;
						try {
							messageGroup = GroupMessageCommand.valueOf(cmd);
							sendGroup(messageGroup);
						} catch (UnformattedException e) {
							e.printStackTrace();
						}						
						break;
						
					default:
						break;
					}	
					
				}
			} catch (IOException e) {
				e.printStackTrace();
				logout();
			}			
		}).start();
	}

	public void send(PrivateMessageCommand message){
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			writer.write(message.toJSON() + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	abstract void sendPrivate(PrivateMessageCommand message);
	
	abstract void sendGroup(GroupMessageCommand messageGroup);
	
	abstract void login(LoginCommand login);
	
	abstract void logout();
	
	abstract void register();

}
