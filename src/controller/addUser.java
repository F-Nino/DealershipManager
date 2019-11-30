
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.UserDAO;

public class addUser implements Initializable {
	
	private UserDAO userDAO;
	
	 public void initialize(URL location, ResourceBundle resources) {
	        //Calling here to check if table exists.
	       
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
	 
	 public void onClickAddUser() {
		 userDAO.addNewUser(fName.getText(), lName.getText(), username.getText(), password.getText(), email.getText(), age.getText(), DOB.getText());
	 }
	 
}