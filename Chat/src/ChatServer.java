import java.io.IOException;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer extends Thread implements Runnable{
	private int port;
	public ChatServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		System.out.println("Starting server on port: " + port);
		ServerSocket welcomeSocket = null; 
					
		while(true){
			try {
				welcomeSocket = new ServerSocket(port);		
				connect(welcomeSocket.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}	

	private void connect(Socket accept) throws IOException, ProtocolException {
		CommandReader reader = new CommandReader(accept.getInputStream());
		Command cmd = reader.readCommand();
		try {	
			if (cmd.getAction() == Command.ACTION_LOGIN || cmd.getAction() == Command.ACTION_REGISTER) {
				cmd.execute();
			} else if(cmd.getAction() == Command.ACTION_EXIT){
			} else{
				throw new ProtocolException("Unexpected communication");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void process(Command cmd) throws ProtocolException {
		cmd.execute();
	}
//		try {				
//			while(!(Command.ACTION_EXIT == (cmd = reader.readCommand()).getAction())){
//				switch (cmd.getAction()) {
//				case Command.ACTION_LOGIN:
//					LoginCommand login;
//					try {
//						login = LoginCommand.valueOf(cmd);
//						login(login);
//					} catch (UnformattedException e) {
//						e.printStackTrace();
//					}	
//					break;
//					
//				case Command.ACTION_PRIVATE_MESSAGE:
//					PrivateMessageCommand message;
//					try {
//						message = PrivateMessageCommand.valueOf(cmd);
//						sendPrivate(message);
//					} catch (UnformattedException e) {
//						e.printStackTrace();
//					}						
//					break;
//					
//				case Command.ACTION_GROUP_MESSAGE:
//					GroupMessageCommand messageGroup;
//					try {
//						messageGroup = GroupMessageCommand.valueOf(cmd);
//						sendGroup(messageGroup);
//					} catch (UnformattedException e) {
//						e.printStackTrace();
//					}						
//					break;
//					
//				default:
//					break;
//				}	
//				
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			logout();
//		}			
	
}
