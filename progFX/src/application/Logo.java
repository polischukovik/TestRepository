package application;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

public class Logo {
	public static String APPLICATION_ICON = null;
    public static String SPLASH_IMAGE = null;

	private double[] scale = new double[]{0.30, 0.30};
	private int width = (int) (Main.screeSize.width * scale[0]);
	private int height = (int) (Main.screeSize.height * scale[1]);

	private LogoController controller;
	
	public Logo() {

		try {
			APPLICATION_ICON = new File(Properties.getString("logo_image_path")).toURI().toURL().toString();
			SPLASH_IMAGE = new File(Properties.getString("logo_image_path")).toURI().toURL().toString();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		
		try {			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Logo.fxml"));     
			AnchorPane root = (AnchorPane)fxmlLoader.load();
			
			Scene scene = new Scene(root,width,height);	        
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.primaryStage.initStyle(StageStyle.UNDECORATED);
			Main.primaryStage.setScene(scene);
			
			controller = fxmlLoader.getController();
			controller.initData(width);			        
			
			Main.primaryStage.show();

			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			Main.primaryStage.setX((primScreenBounds.getWidth() - Main.primaryStage.getWidth()) / 2);
			Main.primaryStage.setY((primScreenBounds.getHeight() - Main.primaryStage.getHeight()) / 2);		
			
			initApplication();
			
			//Main.primaryStage.hide();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void initApplication() {
		controller.setStatus("Starting application...");		
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		controller.setStatus("Application was started");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
