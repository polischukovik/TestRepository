package probl;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;

import datasource.FileDS;
import geometry.Point;
import logginig.Logging;
import logic.WaypointFinder;

public class App extends JFrame {
	public static Logging log;
	
    public App() {
        initUI();
    }

    private void initUI() {    
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
	public static void main(String[] args) throws IOException{		
		PrintStream ps = System.out;
		log = new Logging(ps);
		WaypointFinder waypointFinder = new WaypointFinder(new FileDS("ds.txt"));
		
		System.out.println(String.format("----------RESULT-----------"));
		for(Point p : waypointFinder.getWaypoints()){	
			System.out.println(p.getX() + ", " + p.getY());
		}
		
		EventQueue.invokeLater(() -> {
            App ex = new App();
            ex.setVisible(true);
        });
	}
}
