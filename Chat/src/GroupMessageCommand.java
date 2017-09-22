import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GroupMessageCommand extends Command{
	String senderId;
	String groupId;
	String text;

	public static GroupMessageCommand valueOf(Command cmd) throws UnformattedException{
		String value = cmd.getValue();
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		GroupMessageCommand command = gson.fromJson(value, GroupMessageCommand.class);
		command.validate();
		return command;
	}

	@Override 
	public void validate() throws UnformattedException{
		if(this.senderId == null || this.groupId == null || this.text == null){
			throw new UnformattedException();
		};
	}

}
