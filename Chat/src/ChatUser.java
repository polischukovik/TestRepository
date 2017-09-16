import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ProtocolException;
import java.net.Socket;

public class ChatUser extends Thread implements Runnable{
	public static IdGenerator generator = new IdGenerator();
	
	Socket socket = null;
	BufferedWriter out = null;
	BufferedReader in = null;
	
	private String userName;
	private long id;

	public ChatUser(Socket clientSocket) throws ProtocolException {		
		this.socket = clientSocket;
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChatProtocol chatProtocol = new ChatProtocol(this.socket);
		
		id = generator.createID();
		userName = chatProtocol.getValue("username");
		new Thread(this).start();;
	}
	
	public void run(){
		String message = "";
		while(!message.equals("quit")){
			try {
				message = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			onUserMessage(id + " " + message);
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void onUserMessage(String message) {
		ChatServer.process(message);
	}

	public String getUserName() {
		return userName;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ChatUser [id=" + id + ", userName=" + userName + "]";
	}

	public void send(String message) {
		try{
			out.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
