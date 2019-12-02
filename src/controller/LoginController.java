package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import model.UserDAO;

public class LoginController implements Initializable {

	private UserDAO user;

	@FXML
	private AnchorPane loginPane;

	@FXML
	private TextField username;
	@FXML
	private TextField password;

	@FXML
	private ToggleGroup WhoAreYou;

	@FXML
	private RadioButton customer;

	@FXML
	private RadioButton employee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		user = new UserDAO();
	}

	@FXML
	public void singUp() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/customer_signup.fxml"));
		loginPane.getChildren().setAll(pane);
	}

	@FXML
	public void login() throws IOException, SQLException {

		if (WhoAreYou.getSelectedToggle() != null) {

			RadioButton selectedRadioButton = (RadioButton) WhoAreYou.getSelectedToggle();
			String toggleGroupValue = selectedRadioButton.getText();

			if (toggleGroupValue.equalsIgnoreCase("customer")) {
				System.out.println(toggleGroupValue);

				
				// authenticate customer
				boolean movePage = false;
				
				movePage = user.authCusUser(username.getText(), password.getText());

				if (movePage) {
					AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/CustomerViewCarTableView.fxml"));
					loginPane.getChildren().setAll(pane);
				} else {
					// login failed for customer
					System.out.println("try again login");
				}

			}

			if (toggleGroupValue.equalsIgnoreCase("employee")) {
				System.out.print(toggleGroupValue);
				// authenticate employee

				ResultSet movePage = user.authEmpUser(username.getText(), password.getText());; // equal return method
				boolean admin = false; // equal return to see if user is admin

				if(movePage.next()) {
					System.out.print("Yo000" + movePage.getString(1));
					if(movePage.getBoolean(8)) {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/AdminHome.fxml"));
						loginPane.getChildren().setAll(pane);
					}else {
						AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/employeeHomePage.fxml"));
						loginPane.getChildren().setAll(pane);
					}
					
				}else {
					System.out.println("new not in db");
				}
//				if (movePage && admin) {
//
//					// push user to home page = cartableview
//					AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/AdminHome.fxml"));
//					loginPane.getChildren().setAll(pane);
//
//				} else if (movePage) {
//					AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/employeeHomePage.fxml"));
//					loginPane.getChildren().setAll(pane);
//				} else {
//					// no login for that employee
//					System.out.println("try again login");
//				}
			}

		} else {
			// login failed by no radio buttons are selected
			System.out.print("WHAT ARE YOU DOING");
		}

		// String toogleGroupValue = selectedRadioButton.getText();

	}

}
