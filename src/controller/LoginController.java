package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import domain.CustomerObject;
import domain.EmployeeObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import model.UserDAO;

public class LoginController implements Initializable {

	private UserDAO user;
	
	private CustomerObject co;
	
	private EmployeeObject emp;
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
				ResultSet movePage;
				
				//co = new CustomerObject(movePage.getInt(0), movePage.getString(1), movePage.getString(2), movePage.getString(3),movePage.getString(4));
				
				movePage = user.authCusUser(username.getText(), password.getText());

				if (movePage != null) {
					
					
					System.out.println("ABOUT TO HIT WHILE");
					
					System.out.println("AYOBEFORE" + movePage.getInt(1) + movePage.getString(2));
				
						
						System.out.println("AYO" + movePage.getInt(1) + movePage.getString(2));
						co = new CustomerObject(movePage.getInt(1), movePage.getString(2), movePage.getString(3), movePage.getString(4) , movePage.getString(5));
					
					
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CustomerViewCarTableView.fxml"));    
					Parent root = (Parent)fxmlLoader.load();          
				    CustomerViewCarsController controller = fxmlLoader.<CustomerViewCarsController>getController();
				    controller.setUser(co);
					AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/CustomerViewCarTableView.fxml"));
					loginPane.getChildren().setAll(pane);
				} else {
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
					
					emp = new EmployeeObject(movePage.getInt("employeeid"), movePage.getString("password"),
                            movePage.getString("title"), movePage.getString("firstName"),
                            movePage.getString("lastName"), movePage.getString("position"),
                            movePage.getInt("salary"), movePage.getInt("adminPriviliges"));
					
	
					if(emp.getAdminPrivileges() == 1) {
						
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
