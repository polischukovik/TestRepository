import java.io.IOException;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ChatServer extends Thread implements Runnable{
	private int port;
	private static Set<UserListener> users = new HashSet<>();
	final static long ALL_RECIPIENTS = -1;

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
			UserListener user = new UserListener(socket);
			users.add(user);
			System.out.println(Thread.currentThread() + ": New client connected: " + user);
			listUsers();
		} catch (ProtocolException e) {
			System.out.println("Error trying to register user.");
			e.printStackTrace();
		}
	}
	
	private void listUsers(){
		System.out.println("Listing registered users:");
		for(UserListener chatUser : users){
			System.out.println("\t" + chatUser);
		}
	}

	public static void process(Message message) {
		System.out.println(String.format("Server: Message from user was recieved <<%s>>", message));
		if(ALL_RECIPIENTS == message.recipientId){
			for(UserListener user : users){
				user.send(message);			
			}
		}else{
			UserListener recipient = getUserById(message.recipientId);
			if(recipient != null){
				recipient.send(message);
			}
		}
	}

	private static UserListener getUserById(long recipientId) {
		List<UserListener> user = users.stream().filter(s -> s.getId() == recipientId).collect(Collectors.toList());
		return user.size() == 0 ? null : null;
	}
}
