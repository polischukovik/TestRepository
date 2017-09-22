import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrivateMessageCommand extends Command{
	String senderId;
	String recipientId;
	String text = "";
	
	public PrivateMessageCommand(String senderId, String recipientId, String text, long timestamp) {
		super();
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "Message [senderId=" + senderId + ", recipientId=" + recipientId + ", text=" + text + "]";
	}	
	
	public String toJSON(){
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		return gson.toJson(this);
	}

	public static PrivateMessageCommand valueOf(Command cmd) throws UnformattedException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		PrivateMessageCommand command = gson.fromJson(value, PrivateMessageCommand.class);
		command.validate();
		return command;
	}
	
	@Override 
	public void validate() throws UnformattedException{
		if(this.senderId == null || this.recipientId == null || this.text == null){
			throw new UnformattedException();
		};
	}
}
