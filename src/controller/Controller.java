package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import domain.UserObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.UserDAO;

public class Controller implements Initializable {

	private UserDAO userDAO;
	private ArrayList<UserObject> userObjectArrayList = new ArrayList<UserObject>();

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
	private TableColumn<UserObject, String> userfName;
	@FXML
	private TableColumn<UserObject, String> userlName;
	@FXML
	private TableColumn<UserObject, String> userUsername;
	@FXML
	private TableColumn<UserObject, String> userPassword;
	@FXML
	private TableColumn<UserObject, String> userEmail;
	@FXML
	private TableColumn<UserObject, String> userAge;
	@FXML
	private TableColumn<UserObject, String> userDOB;

	@FXML
	private TableView<UserObject> userTableView;

	public void onClickAddUser() {
		userDAO.addNewUser(fName.getText(), lName.getText(), username.getText(), password.getText(), email.getText(),
				age.getText(), DOB.getText());
	}

	public void grabUsers() {
		userDAO.grabUsers();
	}

	@FXML
	public void loadUserDetails() throws SQLException {
		userfName.setCellValueFactory(new PropertyValueFactory<UserObject, String>("fname"));
		userlName.setCellValueFactory(new PropertyValueFactory<UserObject, String>("lname"));
		userUsername.setCellValueFactory(new PropertyValueFactory<UserObject, String>("username"));
		userPassword.setCellValueFactory(new PropertyValueFactory<UserObject, String>("password"));
		userEmail.setCellValueFactory(new PropertyValueFactory<UserObject, String>("email"));
		userAge.setCellValueFactory(new PropertyValueFactory<UserObject, String>("age"));
		userDOB.setCellValueFactory(new PropertyValueFactory<UserObject, String>("DOB"));
		ResultSet usersQuery = userDAO.grabUsers();
		if (usersQuery == null) {
			System.out.println("that sucks lol nate");
		} else {
			while (usersQuery.next()) {
				userObjectArrayList.add(new UserObject(usersQuery.getString(1), usersQuery.getString(2),
						usersQuery.getString(3), usersQuery.getString(4), usersQuery.getString(5),
						usersQuery.getString(6), usersQuery.getString(7)));
			}

		}
	}
}
