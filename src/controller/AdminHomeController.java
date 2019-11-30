package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.EmployeeObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CarDAO;

public class AdminHomeController implements Initializable {

	@FXML
	private AnchorPane adminHomePane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void changeToInventory() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/CarTableView.fxml"));
		adminHomePane.getChildren().setAll(pane);
	}
	
	@FXML
	public void changeToPurchaseHistory() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/PurchaseHistoryTableView.fxml"));
		adminHomePane.getChildren().setAll(pane);
	}

	@FXML
	public void manageEmployees() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/EmployeeTableView.fxml"));
		adminHomePane.getChildren().setAll(pane);
	}

}
