package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class EmpHomeController implements Initializable{
	
	@FXML
	private AnchorPane EmpHomePane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void changeToInventory() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/CarTableViewEmpHome.fxml"));
		EmpHomePane.getChildren().setAll(pane);
	}
	
	@FXML
	public void logOut() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/LOGIN.fxml"));
		EmpHomePane.getChildren().setAll(pane);
	}
	
	@FXML
	public void purchaseHistory() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/PurchaseHistoryTableViewEmpHome.fxml"));
		EmpHomePane.getChildren().setAll(pane);
	}

}
