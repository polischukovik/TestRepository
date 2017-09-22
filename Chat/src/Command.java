public abstract class Command {

	public static final String ACTION_EXIT = "exit";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_PRIVATE_MESSAGE = "private_message";
	public static final String ACTION_GROUP_MESSAGE = "group_message";

	private String action;
	private String value;
	private String time;
	
	public String getAction() {
		return action;
	}
	public String getValue() {
		return value;
	}
	public String getTime() {
		return time;
	}
	
	abstract void validate() throws UnformattedException;
	
}
