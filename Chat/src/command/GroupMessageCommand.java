package command;
import java.net.ProtocolException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GroupMessageCommand extends ServerCommand{
	String senderId;
	String groupId;
	String text;
	
	public GroupMessageCommand(String str) throws ProtocolException {
		super(str, null);		
	}
		
	public String getSenderId() {
		return senderId;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getText() {
		return text;
	}

	public static GroupMessageCommand valueOf(Command cmd) throws ProtocolException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		GroupMessageCommand command = gson.fromJson(value, GroupMessageCommand.class);
		command.validate();
		return command;
	}

	@Override 
	public void validate() throws ProtocolException{
		if(this.senderId == null || this.groupId == null || this.text == null){
			throw new ProtocolException();
		};
	}

	@Override
	public void execute() throws ProtocolException {
		throw new IllegalStateException("Not implemented yet");
	}

}
