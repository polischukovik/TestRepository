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

	public UserPerson(RegisterCommand register) throws ProtocolException{
		super(register);
		id = generator.next();
		
	}
	
	@Override
	void logout() {
		unregister(id);
	}
	
	public static UserPerson getById(String id) {
		List<UserPerson> user = users.stream().filter(s -> id.equals(s.getId())).collect(Collectors.toList());
		return user.size() == 0 ? null : null;
	}
	
	public static void unregister(String id) {
		UserPerson user = UserPerson.getById(id);
		users.remove(user);
		System.out.println("Unregistering user: " + user);
	}

	public static Collection<UserPerson> getAll() {
		return users;
	}

	public static UserPerson register(RegisterCommand register) {
		UserPerson user = new UserPerson(register);
		users.add(user);
		System.out.println(Thread.currentThread() + ": User has registered: " + user);
		listUserPersons();
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

	public static UserPerson login(LoginCommand login) {
		return null;
		
	}

	public static void connect(Socket accept) {
		// TODO Auto-generated method stub
		
	}

}
