package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FirstPreloader extends Preloader {	
	
	FirstPreloaderController controller = null;
	FXMLLoader fxmlLoader = null;
	
    Stage stage;
    
	private String APPLICATION_ICON = null;
    private String SPLASH_IMAGE = null;
	private double[] scale = new double[]{0.30, 0.30};
	private int width = 0;
	private int height = 0;
 
    @Override
	public void init() throws Exception {
    	try {
			APPLICATION_ICON = new File("res/icon64.png").toURI().toURL().toString();
			SPLASH_IMAGE = new File("res/logo.jpg").toURI().toURL().toString();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}		
    	
    	this.width = (int) (Main.screeSize.width * scale[0]);
    	this.height = (int) (Main.screeSize.height * scale[1]);
    	
    	fxmlLoader = new FXMLLoader(getClass().getResource("Logo.fxml"));     
	
		
		controller = fxmlLoader.getController();		
	}

	private Scene createScene() {
    	Scene scene = null;
    	try {	    	
    		AnchorPane root = (AnchorPane)fxmlLoader.load();
			scene = new Scene(root,500,300);	        
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			return scene;
			
    	} catch(IOException e) {
			e.printStackTrace();
		}	
		return scene;
    }
    
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createScene());        
        stage.show();
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        controller.getBar().setProgress(pn.getProgress());
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }    
}