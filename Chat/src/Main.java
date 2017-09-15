
public class Main {
	static int port = 5000; 
	
	public static void main(String[] args) {
		try{
			if(args.length > 0){
				port = Integer.valueOf(args[0]);
			}
		}
		catch(NumberFormatException e){
			System.err.println("Not valid value: " + args[0]);
		}
		new ChatServer(port).start();
	}
}
