import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class ChatServer extends Thread implements Runnable{
	private int port;
	private Set<ChatClient> clients = new HashSet<>();

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
				this.register(welcomeSocket.accept());
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void register(Socket socket) {
		clients.add(new ChatClient(socket));
		System.out.println(Thread.currentThread() + ": New client Connected");
	}

}
