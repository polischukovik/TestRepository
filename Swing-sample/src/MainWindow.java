import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MainWindow extends JFrame {

	public MainWindow() throws HeadlessException {
		super();
		initGUI();
	}

	private void initGUI() {
		DemoPanel one = new DemoPanel("1");
		DemoPanel two = new DemoPanel("2");
		DemoPanel three = new DemoPanel("3");
		DemoPanel four = new DemoPanel("4");
		DemoPanel five = new DemoPanel("5");
		DemoPanel six = new DemoPanel("6");
		DemoPanel seven = new DemoPanel("7");
		
		one.setMinimumSize(new Dimension(800, 600));
		two.setPreferredSize(new Dimension(300,500));
		three.setPreferredSize(new Dimension(200, 500));
		
		two.setLayout(new BoxLayout(two, BoxLayout.Y_AXIS));
		two.add(three);
		two.add(four);
		two.add(five);
		
		add(one, BorderLayout.CENTER);
		add(two, BorderLayout.EAST);
	}
	
	

}
