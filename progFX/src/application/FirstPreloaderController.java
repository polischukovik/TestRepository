package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

public class FirstPreloaderController {
	@FXML
    private ImageView image;

    @FXML
    private Button buttonAbort;

    @FXML
    private Label status;
    
    @FXML
    private ProgressBar bar;

    @FXML
    void handleButtonAction(ActionEvent event) {
    	Platform.exit();
    }

	public ProgressBar getBar() {
		return bar;
	}
    
}
