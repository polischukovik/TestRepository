package command;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import utils.JsonUtils;

public class CommandReader {
	Socket socket = null;

	public CommandReader(Socket socket) {
		this.socket = socket;
	}

	public ServerCommand readCommand() throws IOException, ChatProtocolException{
		String json = readRawData();
		
		ServerCommand serverCommand = JsonUtils.gson.fromJson(json, ServerCommand.class);
		
		switch (serverCommand.getAction()) {
		case "login":
			new LoginCommand(serverCommand.getParams());
			break;

		default:
			break;
		}
		
		
		return null;
	}

	private String readRawData() throws IOException, ChatProtocolException {
		BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
		StringBuilder res = new StringBuilder();
		String line = "";
		while(!"".equals((line = reader.readLine()))){
			if(line == null) throw new IOException("Client has disconnected");
			res.append(line + "\n");
		}
		
		if(res.length() == 0){
			throw new ChatProtocolException("Empty Command");
		}
		return res.toString();
	}

	public Socket getSocket() {
		return socket;
	}

}
