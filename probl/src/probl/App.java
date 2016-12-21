package probl;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import datasource.DataSource;
import datasource.SemiFileDS;
import geometry.Point;
import graphics.JGPoint;
import graphics.Map;
import gui.JACanvas;
import gui.MainWindow;
import logginig.Logging;

public class App{
	public static Logging log;
	public static DataSource ds;
	public static JACanvas canvas;
    
	public static void main(String[] args) throws IOException{		

		log = Logging.createLogging();
		ds = new SemiFileDS(new File("ds.txt"));
		canvas = new JACanvas(new Map(new Point(0, 0), new Point(50, 50)));
		
		MainWindow mw= new MainWindow(ds, log, canvas);
		EventQueue.invokeLater(() -> {
			mw.setVisible(true);
        });
		
		
		//canvas.addObject(new JGPoint(500, 200, new Color(255, 5, 5, 255)));
		
		
//		WaypointFinder waypointFinder = new WaypointFinder(ds);
//		
//		System.out.println(String.format("----------RESULT-----------"));
//		for(Point p : waypointFinder.getWaypoints()){	
//			System.out.println(p.getX() + ", " + p.getY());
//		}
	}
}
