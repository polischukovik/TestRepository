package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application{

	
	@Override
	public void init() throws Exception {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(createScene());
		primaryStage.show();
		
//		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
//		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
//		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);		

		primaryStage = new Stage(StageStyle.DECORATED);
		primaryStage.setTitle("Application title");
		primaryStage.setIconified(true);
	}

	private Scene createScene() {
		Scene scene = null;
		try{
		FXMLLoader fxmlLoaderApp = new FXMLLoader(getClass().getResource("Application.fxml"));     
		AnchorPane rootApp = (AnchorPane)fxmlLoaderApp.load();
		
		Scene sceneApp = new Scene(rootApp,800,600);	        
		sceneApp.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (IOException e){
			e.printStackTrace();
		}
		return scene;
	}

}
