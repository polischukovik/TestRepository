package application;
	
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	public static Stage primaryStage = null;
	public static Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		new Logo();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
