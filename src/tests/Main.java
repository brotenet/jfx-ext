package tests;

import api.commons.WindowManager;
import api.commons.Environment.Resources;
import javafx.application.Application;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		System.out.println(WindowManager.Dialogs.showChoiceDialog("test", "Header Text testing", "Content text testing blah bla bla", "/tests/java_logo.png", new String[] {"one","two","three","four"}, "two"));
		
		System.out.println(WindowManager.Dialogs.showAlertDialog(AlertType.NONE, "test", "Header Text testing", "Content text testing blah bla bla", "/tests/java_logo.png", new String[] {"one","two","three","four"}));
		
		Stage stage = WindowManager.getStage("/tests/StageMain.fxml", "/tests/application.css", Modality.NONE, "JavaFX - API Testing", Resources.getImage("/tests/application.png"), 800, 600);
		stage.setResizable(true);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
