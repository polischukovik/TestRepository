import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends Thread implements Runnable{
	Socket socket = null;

	public ChatClient(Socket clientSocket) {		
		this.socket = clientSocket;
		new Thread(this).start();;
	}
	
	public void run(){
		InputStream is = null;
		try {
			is = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner sc = new Scanner(is);
		String message = "";
		while(!message.equals("quit")){
			message = sc.nextLine();
			System.out.println(message);
		}
		sc.close();
	}

}
