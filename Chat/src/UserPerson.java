import java.net.ProtocolException;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPerson extends UserListener{
	private static Set<UserPerson> users = new HashSet<>();
	private static IdGenerator generator = new IdGenerator();

	public UserPerson(Socket clientSocket) throws ProtocolException {
		super(clientSocket);
		id = generator.createID();
	}

	@Override
	void sendPrivate(PrivateMessageCommand message) {
		ChatServer.process(message);
	}

	@Override
	void logout() {
		unregister(id);
	}
	
	public static UserPerson getUserPersonById(String id) {
		List<UserPerson> user = users.stream().filter(s -> id.equals(s.getId())).collect(Collectors.toList());
		return user.size() == 0 ? null : null;
	}
	
	public static void unregister(String id) {
		UserPerson user = UserPerson.getUserPersonById(id);
		users.remove(user);
		System.out.println("Unregistering user: " + user);
	}

	public static Collection<UserPerson> getAll() {
		return users;
	}

	public static void register(Socket accept) {
		try {
			UserPerson user = new UserPerson(accept);
			users.add(user);
			System.out.println(Thread.currentThread() + ": New client connected: " + user);
			listUserPersons();
		} catch (ProtocolException e) {
			System.out.println("Error trying to register user.");
			e.printStackTrace();
		}
	}
	
	private static void listUserPersons(){
		System.out.println("Listing registered users:");
		for(UserPerson chatUserPerson : UserPerson.getAll()){
			System.out.println("\t" + chatUserPerson);
		}
	}

	@Override
	void sendGroup(GroupMessageCommand messageGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void login(LoginCommand login) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void register() {
		// TODO Auto-generated method stub
		
	}

}
