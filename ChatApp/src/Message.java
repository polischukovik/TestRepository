import java.nio.ByteBuffer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
	long senderId;
	long recipientId;
	String text = "";
	long timestamp;
	public Message(long senderId, long recipientId, String text, long timestamp) {
		super();
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.text = text;
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Message [senderId=" + senderId + ", recipientId=" + recipientId + ", text=" + text + ", timestamp="
				+ timestamp + "]";
	}	
	
	public String toJSON(){
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		return gson.toJson(this);
	}
	
	public byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
	
}
