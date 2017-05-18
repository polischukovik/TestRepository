package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

public class Logo {

//	private LogoController controller = new LogoController();
	
	public Logo() {
		try {			
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Logo.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.primaryStage.initStyle(StageStyle.UNDECORATED);
			Main.primaryStage.setScene(scene);
			
			
			Main.primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
