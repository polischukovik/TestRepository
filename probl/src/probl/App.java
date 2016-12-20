package probl;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import datasource.DataSource;
import datasource.SemiFileDS;
import graphics.JGPoint;
import gui.JACanvas;
import gui.JAConsole;
import gui.MainWindow;
import gui.StreamCapturer;
import logginig.Logging;

public class App{
	public static Logging log;
	public static DataSource ds;
	public static JACanvas canvas;
    
	public static void main(String[] args) throws IOException{		

		log = Logging.createLogging();
		ds = new SemiFileDS(new File("ds.txt"));
		
		MainWindow mw= new MainWindow(ds, log);
		EventQueue.invokeLater(() -> {
			mw.setVisible(true);
        });
		
		
		App.canvas.addObject(new JGPoint(500, 200, new Color(255, 5, 5, 255)));
		
		
//		WaypointFinder waypointFinder = new WaypointFinder(ds);
//		
//		System.out.println(String.format("----------RESULT-----------"));
//		for(Point p : waypointFinder.getWaypoints()){	
//			System.out.println(p.getX() + ", " + p.getY());
//		}
	}
}
