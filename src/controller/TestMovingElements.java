package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain.CustomerObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class TestMovingElements implements Initializable {
	
	@FXML
	private AnchorPane testingPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void nextScene() throws IOException {
	
	CustomerObject co = new CustomerObject(1, "Nathan", "Enger", "potatoe", "potatoe");
    
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CustomerViewCarTableView.fxml"));    
   
    Parent root = (Parent)fxmlLoader.load();          
    CustomerViewCarsController controller = fxmlLoader.<CustomerViewCarsController>getController();
    controller.setUser(co);
    /*
    Scene scene = new Scene(root);
   
    stage.setScene(scene);    
   
    stage.show();  
    */
	AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/CustomerViewCarTableView.fxml"));
    testingPane.getChildren().setAll(pane);
	}

}
