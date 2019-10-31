package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			 Parent root = FXMLLoader.load(getClass().getResource("../view/customer_signup.fxml"));
		        primaryStage.setTitle("Enter New Student");
		        Scene scene = new Scene(root, 350, 450);
		        scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
		        primaryStage.setScene(scene);
		        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
