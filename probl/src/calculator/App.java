package calculator;

import java.io.IOException;
import datasource.DataSource;
import datasource.FileDS;
import gui.JACanvas;
import logginig.Logging;
import logic.WaypointFinder;

public class App{
	public static Logging log;
	public static DataSource ds;
	public static JACanvas canvas;
	public static int COORDINATE_PRECISION = 6;
	public static WaypointFinder wpf;
    
	public static void main(String[] args) throws IOException{		
//		Point p = new Point(50.392621, 30.496226);
//		Point n = p.moveTo(360, 100);
//		System.out.println(n);
////		
		
		log = Logging.createLogging();
		//ds = new SemiFileDS(new File("ds.txt"));
		ds = new FileDS("ds.txt");
		//canvas = new JACanvas();
		
		wpf = new WaypointFinder(ds, log);
		
		System.out.println(wpf.getWaypoints());
		
//		MainWindow mw= new MainWindow(ds, log, canvas);
//		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		mw.setSize(screenSize.width*3/4, screenSize.height*3/4);
//		mw.setLocationByPlatform(true);
//		EventQueue.invokeLater(() -> {
//			mw.setVisible(true);
//        });
	}
}
