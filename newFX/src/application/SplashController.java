package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SplashController implements Initializable{
	
	@FXML
	private AnchorPane rootPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new SplashScreen().start();
	}
	
	class SplashScreen extends Thread{

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("MainApp.fxml"));
						Parent root = null;
						try {
							root = loader.load();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Scene scene = new Scene(root);
						
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.show();
						
						rootPane.getScene().getWindow().hide();			
					}
				});						
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
