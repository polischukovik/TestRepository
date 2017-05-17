package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LogoController implements Initializable {

	@FXML private Label status;
	@FXML private ImageView image;
	@FXML private Button buttonAbort;
	
	public void initData(double width) {
		Image logoImage = null; 
		try {
			String url = new File(Properties.getString("logo_image_path")).toURI().toURL().toString();
			logoImage = new Image(url, width, -1, true, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		image.setImage(logoImage);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.err.println("asd");
	}
	
	
	
}