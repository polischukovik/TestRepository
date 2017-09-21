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

public class UserListener{
	public static IdGenerator generator = new IdGenerator();
	
	Socket socket = null;
	OutputStream out = null;
	InputStream in = null;
	
	private String userName;
	private long id;

	public UserListener(Socket clientSocket) throws ProtocolException {		
		this.socket = clientSocket;
		try {
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChatProtocol chatProtocol = new ChatProtocol(this.socket);
		
		id = generator.createID();
		userName = chatProtocol.getValue("username");
		new Thread(() -> {
			String text = "";
			BufferedReader reader = new BufferedReader( new InputStreamReader(in));			
			try {
				while(!text.equals("quit")){
					text = reader.readLine();
					ChatServer.process(new Message(id, -1, text, Instant.now().getEpochSecond()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}).start();
	}

	public String getUserName() {
		return userName;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + "]";
	}

	public void send(Message message) {
		try{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			writer.write(message.toJSON());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
