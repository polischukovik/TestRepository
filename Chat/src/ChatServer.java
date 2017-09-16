import java.io.IOException;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class ChatServer extends Thread implements Runnable{
	private int port;
	private static Set<ChatUser> users = new HashSet<>();

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
				register(welcomeSocket.accept());
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void register(Socket socket) {
		try {
			ChatUser user = new ChatUser(socket);
			users.add(user);
			System.out.println(Thread.currentThread() + ": New client connected: " + user);
		} catch (ProtocolException e) {
			System.out.println("Error trying to register user.");
			e.printStackTrace();
		}
	}
	
	private void listUsers(){
		System.out.println("Listing registered users:");
		for(ChatUser chatUser : users){
			System.out.println("\t" + chatUser);
		}
	}

	public static void process(String message) {
		//if SEND_TO_ALL
		System.out.println(String.format("Server: Message from user was recieved <<%s>>", message));
		for(ChatUser user : users){
			user.send(message);
			
		}
	}
}
