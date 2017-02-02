package calculator;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import datasource.DataSource;
import datasource.SemiFileDS;
import geometry.Point;
import gui.JACanvas;
import gui.MainWindow;
import logginig.Logging;

public class App{
	public static Logging log;
	public static DataSource ds;
	public static JACanvas canvas;
	public static int COORDINATE_PRECISION = 6;
    
	public static void main(String[] args) throws IOException{		
		Point p = new Point(50.392621, 30.496226);
		Point n = p.moveTo(360, 100);
		System.out.println(n);
		
		
		log = Logging.createLogging();
		ds = new SemiFileDS(new File("ds.txt"));
		canvas = new JACanvas();
		
		MainWindow mw= new MainWindow(ds, log, canvas);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mw.setSize(screenSize.width*3/4, screenSize.height*3/4);
		mw.setLocationByPlatform(true);
		EventQueue.invokeLater(() -> {
			mw.setVisible(true);
        });
	}
}
