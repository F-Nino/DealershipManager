package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.UserObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.UserDAO;

public class CustomerViewController implements Initializable {

	private UserDAO userDAO;
	private ArrayList<UserObject> userObjectArrayList = new ArrayList<UserObject>();
	
	@FXML
	private AnchorPane customerViewPane;

	
	@FXML
	private ComboBox<String> comboBoxUsers;

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
	
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.

		userDAO = new UserDAO();
		
		comboBoxUsers.getItems().clear();
		comboBoxUsers.getItems().addAll("First Name", "Last Name", "Username", "Email", "Age");
		comboBoxUsers.getSelectionModel().select("First Name");
		try {
			loadUserDetails();
			userTableView.getItems().clear();
			for (UserObject coo : userObjectArrayList) {
				System.out.println(coo.getfName());
				userTableView.getItems().add(coo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void onClickAddUser() {
		userDAO.addNewUser(fName.getText(), lName.getText(), username.getText(), password.getText(), email.getText(),
				age.getText(), DOB.getText());
	}

	

	@FXML
	public void loadUserDetails() throws SQLException {
		userfName.setCellValueFactory(new PropertyValueFactory<UserObject, String>("fName"));
		userlName.setCellValueFactory(new PropertyValueFactory<UserObject, String>("lName"));
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
				System.out.println(usersQuery.getString(1));
				System.out.println(usersQuery.getString(2));
				userObjectArrayList.add(new UserObject(usersQuery.getString(1), usersQuery.getString(2),
						usersQuery.getString(3), usersQuery.getString(4), usersQuery.getString(5),
						usersQuery.getString(6), usersQuery.getString(7)));
			}

		}
	}
	
	@FXML
	public void adminHomeButton() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/AdminHome.fxml"));
		customerViewPane.getChildren().setAll(pane);
	}

	
	public void changeSort() {
		String comboBoxValue = (String) comboBoxUsers.getValue();
		
		if (comboBoxValue.equals("First Name")) {
			for (int i = 0; i < userObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < userObjectArrayList.size() - i - 1; j++) {
					if ((userObjectArrayList.get(j).getfName()
							.compareTo(userObjectArrayList.get(j + 1).getfName())) > 0) {
						Collections.swap(userObjectArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxValue.equals("Last Name")) {
			for (int i = 0; i < userObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < userObjectArrayList.size() - i - 1; j++) {
					if ((userObjectArrayList.get(j).getlName()
							.compareTo(userObjectArrayList.get(j + 1).getlName())) > 0) {
						Collections.swap(userObjectArrayList, j, j + 1);
					}
				}
			}
		}
		else if (comboBoxValue.equals("Username")) {
			for (int i = 0; i < userObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < userObjectArrayList.size() - i - 1; j++) {
					if ((userObjectArrayList.get(j).getUsername()
							.compareTo(userObjectArrayList.get(j + 1).getUsername())) > 0) {
						Collections.swap(userObjectArrayList, j, j + 1);
					}
				}
			}
		}else if (comboBoxValue.equals("Email")) {
			for (int i = 0; i < userObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < userObjectArrayList.size() - i - 1; j++) {
					if ((userObjectArrayList.get(j).getEmail()
							.compareTo(userObjectArrayList.get(j + 1).getEmail())) > 0) {
						Collections.swap(userObjectArrayList, j, j + 1);
					}
				}
			}
		}else if (comboBoxValue.equals("Age")) {
			for (int i = 0; i < userObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < userObjectArrayList.size() - i - 1; j++) {
					if ((userObjectArrayList.get(j).getAge()
							.compareTo(userObjectArrayList.get(j + 1).getAge())) > 0) {
						Collections.swap(userObjectArrayList, j, j + 1);
					}
				}
			}
		}
		
		userTableView.getItems().clear();
		for (UserObject coo : userObjectArrayList) {
			userTableView.getItems().add(coo);
		}
	}
}
