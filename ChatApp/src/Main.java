import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	
	public static void main(String args[]){
		String host = "localhost";
		int port = 5000;
		
		try {
			Socket socket = new Socket(host, port);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			Gson gson = new GsonBuilder()
					.setPrettyPrinting()
					.create();
			while(true){
				String inputStr;
				StringBuilder result = new StringBuilder();
				while (!(inputStr = reader.readLine()).equals("\n")){
					result.append(inputStr);
				}
				gson.fromJson(result.toString(), Message.class);
			}
		} catch (IOException e) {			
			System.out.println(String.format("Cannot open socket: %s:%d", host, port));
			e.printStackTrace();
		}
		
	}

}
