package logginig;

import java.io.PrintStream;

import gui.Consumer;
import gui.JAConsole;
import gui.StreamCapturer;

public class Logging {
	private PrintStream ps;
	private int pad = 25;
	private static Logging logging = null;
	private Consumer consumer;
		
	public Logging (PrintStream ps, Consumer consumer){
		this.ps = ps;
		this.consumer = consumer;
	}
	
	public static Logging getLogging(){
		return logging;
	}
	
	public static Logging createLogging (){
		Consumer consumer = new JAConsole();
		PrintStream ps = new PrintStream(new StreamCapturer("out: ", consumer, System.out));
		logging = new Logging(ps, consumer) ;
		return logging;
	}
	
	public void info(Class<?> clazz, String message){
		if(message == null) return;
		String source = (clazz == null) ? String.format("") : clazz.getName();
		
		if(message.length() > 1 && "\n".equals(message.substring(0, 1))){
			message = message.substring(1, message.length());
			ps.println(String.format("%1$-" + pad + "s", source));
		}
		ps.println(String.format("%1$-" + pad + "s", source) + message);
	}

	public Consumer getConsumer() {
		return consumer;
	}
	
}
