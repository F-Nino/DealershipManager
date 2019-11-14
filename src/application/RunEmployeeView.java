package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunEmployeeView extends Application{
	   @Override
	    public void start(Stage primaryStage) throws Exception{
	        Parent root = FXMLLoader.load(getClass().getResource("../view/EmployeeTableView.fxml"));
	        primaryStage.setTitle("View Employees");
	        Scene scene = new Scene(root, 452, 499);
	        //scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	   public void changeScene(Stage primaryStage) throws Exception{
		   Parent root = FXMLLoader.load(getClass().getResource("../view/EmployeeTableView.fxml"));
	        primaryStage.setTitle("View Employees");
	        Scene scene = new Scene(root, 452, 499);
	        //scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
	        primaryStage.setScene(scene);
	        primaryStage.show();
	   }

	    public static void main(String[] args) {
	        launch(args);
	    }
}
