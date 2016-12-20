package probl;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintStream;

import datasource.DataSource;
import graphics.JGPoint;
import gui.JACanvas;
import gui.MainWindow;
import gui.StreamCapturer;
import logginig.Logging;

public class App{
	public static Logging log;
	public static DataSource ds;
	public static JACanvas canvas;
    
	public static void main(String[] args) throws IOException{		
		MainWindow mw= new MainWindow();
		EventQueue.invokeLater(() -> {
			mw.setVisible(true);
        });
				
		App.canvas.addObject(new JGPoint(500, 200, new Color(255, 5, 5, 255)));
		
		log = Logging.createLogging((new PrintStream(new StreamCapturer("out: ", mw.getConsole(), System.out))));
		
		
//		WaypointFinder waypointFinder = new WaypointFinder(ds);
//		
//		System.out.println(String.format("----------RESULT-----------"));
//		for(Point p : waypointFinder.getWaypoints()){	
//			System.out.println(p.getX() + ", " + p.getY());
//		}
	}
}
