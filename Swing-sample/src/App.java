import java.awt.Dimension;
import java.awt.Toolkit;

public class App {

	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mw.setSize(screenSize.width*3/4, screenSize.height*3/4);
		//mw.setSize(300,300);
		
		mw.setLocationByPlatform(true);
		mw.setVisible(true);
		
	}
}
