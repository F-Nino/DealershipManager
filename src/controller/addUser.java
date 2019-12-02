
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.UserDAO;

public class addUser implements Initializable {

	private UserDAO userDAO;

	@FXML
	private AnchorPane signUpPane;

	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.

		userDAO = new UserDAO();
	}

	@FXML
	private TextField fName;

	@FXML
	private TextField lName;

	@FXML
	private TextField username;
	@FXML
	private TextField password;

	@FXML
	private TextField email;
	@FXML
	private TextField age;
	@FXML
	private TextField DOB;

	@FXML
	public void cancel() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/LOGIN.fxml"));
		signUpPane.getChildren().setAll(pane);
	}
	
	@FXML
	public void onClickAddUser() throws IOException {
		userDAO.addNewUser(fName.getText(), lName.getText(), username.getText(), password.getText(), email.getText(),
				age.getText(), DOB.getText());

		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/LOGIN.fxml"));
		signUpPane.getChildren().setAll(pane);
	}
	 
}