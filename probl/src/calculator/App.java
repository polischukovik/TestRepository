package calculator;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import datasource.DataSource;
import datasource.SemiFileDS;
import geometry.Point;
import graphics.Map;
import gui.JACanvas;
import gui.MainWindow;
import logginig.Logging;

public class App{
	public static Logging log;
	public static DataSource ds;
	public static JACanvas canvas;
	public static int COORDINATE_PRECISION = 6;
    
	public static void main(String[] args) throws IOException{		

		log = Logging.createLogging();
		ds = new SemiFileDS(new File("ds.txt"));
//		50.392621, 30.496226
//		50.391877, 30.494816
		canvas = new JACanvas();
		
		MainWindow mw= new MainWindow(ds, log, canvas);
		EventQueue.invokeLater(() -> {
			mw.setVisible(true);
        });
	}
}
