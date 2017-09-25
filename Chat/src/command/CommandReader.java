package command;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.Socket;

public class CommandReader {
	Socket socket = null;

	public CommandReader(Socket socket) {
		this.socket = socket;
	}

	public CommandImpl readCommand() throws IOException, ProtocolException{
		BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
		StringBuilder res = new StringBuilder();
		String line = "";
		while(!"".equals((line = reader.readLine()))){
			if(line == null) throw new IOException("Client has disconnected");
			res.append(line + "\n");
		}
		
		if(res.length() == 0){
			throw new ProtocolException("Empty Command");
		}
		
		return new CommandImpl(res.toString(), socket);
	}

}
