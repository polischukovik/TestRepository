import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.Socket;

public abstract class UserListener extends User{
	public static IdGenerator generator = new IdGenerator();
	
	Socket socket = null;
	OutputStream out = null;
	InputStream in = null;
	
	String welcome_options = "Welcome to chat applications: login send";
	
	public UserListener(Socket clientSocket) throws ProtocolException {
		super();
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
					
			
		}).start();
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

	abstract void sendPrivate(PrivateMessageCommand message);
	
	abstract void sendGroup(GroupMessageCommand messageGroup);
	
	static void login(LoginCommand login) {
		// TODO Auto-generated method stub
		
	}
	
	abstract void logout();


	private void register(RegisterCommand register) {
		// TODO Auto-generated method stub
		
	}

	
}
