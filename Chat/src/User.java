public class User{

	protected String userName;
	protected String id;
	
	public String getUserName() {
		return userName;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + "]";
	}
}
