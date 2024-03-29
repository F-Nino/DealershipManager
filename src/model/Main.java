package model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	   @Override
	    public void start(Stage primaryStage) throws Exception{
	        Parent root = FXMLLoader.load(getClass().getResource("../view/RegistrationGUI.fxml"));
	        primaryStage.setTitle("Enter New Student");
	        Scene scene = new Scene(root, 400, 300);
	        scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }


	    public static void main(String[] args) {
	        launch(args);
	    }
}
