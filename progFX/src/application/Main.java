package application;
	
import java.awt.Dimension;
import java.awt.Toolkit;

import com.sun.javafx.application.LauncherImpl;


public class Main{

	public static Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	public static void main(String[] args) {
		LauncherImpl.launchApplication(MainApplication.class, FirstPreloader.class, args);
		   		
	}	
	
}
