import java.net.ProtocolException;
import java.net.Socket;

public class ChatProtocol {

	public ChatProtocol(Socket socket) throws ProtocolException {
		if(false){
			throw new ProtocolException("Invalid protocol");
		}
	}

	public static boolean validate(Socket socket) {
		return true;
	}

	public String getValue(String key) {
		return "";
	}

}
