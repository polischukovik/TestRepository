package probl;

import java.io.IOException;
import java.io.PrintStream;

import datasource.DataSource;
import gui.MainWindow;
import gui.StreamCapturer;
import logginig.Logging;

public class App{
	public static Logging log;
	public static DataSource ds;
    
	public static void main(String[] args) throws IOException{		
		MainWindow mw= new MainWindow();
		mw.setVisible(true);
//		EventQueue.invokeLater(() -> {
//			mw.setVisible(true);
//        });
				
		log = Logging.createLogging((new PrintStream(new StreamCapturer("out: ", mw.getConsole(), System.out))));
		
		
//		WaypointFinder waypointFinder = new WaypointFinder(ds);
//		
//		System.out.println(String.format("----------RESULT-----------"));
//		for(Point p : waypointFinder.getWaypoints()){	
//			System.out.println(p.getX() + ", " + p.getY());
//		}
	}
}
