package logginig;

import java.io.PrintStream;

public class Logging {
	private PrintStream ps;
	private int pad = 25;
	
	public Logging (PrintStream ps){
		this.ps = ps;
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
