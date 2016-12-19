package logginig;

import java.io.PrintStream;

public class Logging {
	private PrintStream ps;
	private int pad = 25;
	private static Logging logging;
	
	private Logging (){
	}
	
	public Logging (PrintStream ps){
		this.ps = ps;
	}
	
	public Logging getLogging(){
		return logging;
	}
	
	public static Logging createLogging (PrintStream ps){
		logging = new Logging(ps) ;
		return logging;
	}
	
	public void info(Class<?> clazz, String message){
		String source = (clazz == null) ? String.format("") : clazz.getName();
		
		if("\n".equals(message.substring(0, 1))){
			message = message.substring(1, message.length());
			ps.println(String.format("%1$-" + pad + "s", source));
		}
		ps.println(String.format("%1$-" + pad + "s", source) + message);
	}
}
