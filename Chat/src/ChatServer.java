import java.io.IOException;
import java.net.ServerSocket;


public class ChatServer extends Thread implements Runnable{
	private int port;
	final static String ALL_RECIPIENTS = "";

	public ChatServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		System.out.println("Starting server on port: " + port);
		ServerSocket welcomeSocket = null; 
		try {			
			welcomeSocket = new ServerSocket(port);		
		
			while(true){
				UserPerson.register(welcomeSocket.accept());
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	

	public static void process(PrivateMessageCommand message) {
		System.out.println(String.format("Server: Message from user was recieved <<%s>>", message));
		if(message.recipientId.equals(ALL_RECIPIENTS) ){
			for(UserPerson user : UserPerson.getAll()){
				user.send(message);			
			}
		}else{
			UserPerson recipient = UserPerson.getUserPersonById(message.recipientId);
			if(recipient != null){
				recipient.send(message);
			}
		}
	}
	
}
