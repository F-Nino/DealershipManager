package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.CarDAO;
import model.EmployeeDAO;

public class NewEmployeeController implements Initializable {

	private EmployeeDAO empDAO;
	private int employeeIDText;
	private String comboBoxValueTitle;
	private int employeeSalaryText;
	private int adminPrivileges;

	@FXML
	private AnchorPane addEmployeePane;
	
	@FXML
	private TextField employeeID;

	@FXML
	private ComboBox titleComboBox;

	@FXML
	private TextField employeeFName;

	@FXML
	private TextField employeeLName;

	@FXML
	private TextField employeePassword;

	@FXML
	private TextField employeePosition;

	@FXML
	private TextField employeeSalary;

	@FXML
	private CheckBox employeePrivileges;

	@FXML
	private Text informationTxt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		empDAO = new EmployeeDAO();
		titleComboBox.getItems().clear();
		titleComboBox.getItems().addAll("Mr", "Mrs", "Ms", "Miss");
		titleComboBox.getSelectionModel().select("Mr");
	}

	@FXML
	public void cancelSubmit() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/EmployeeTableView.fxml"));
		addEmployeePane.getChildren().setAll(pane);
	}
	
	@FXML
	public void submitEmployee() {
		comboBoxValueTitle = (String) titleComboBox.getValue();
		boolean saveEmployee = true;
		try {
			employeeIDText = Integer.parseInt(employeeID.getText());
		} catch (Exception e) {
			informationTxt.setText("EMPLOYEE ID MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveEmployee = false;
		}
		
		try {
			employeeSalaryText = Integer.parseInt(employeeSalary.getText());
		} catch (Exception e) {
			informationTxt.setText("SALARY MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveEmployee = false;
		}

		if (employeePrivileges.isSelected()) {
			adminPrivileges = 1;
		} else {
			adminPrivileges = 0;
		}

		try {
			if (saveEmployee) {
				empDAO.addNewEmployee(employeeIDText, comboBoxValueTitle, employeePassword.getText(), employeeFName.getText(),
						employeeLName.getText(), employeePosition.getText(), employeeSalaryText, adminPrivileges);
				informationTxt.setText("successfully added new Employee");
				informationTxt.setFill(Color.GREEN);
			}
		} catch (Exception e) {
			informationTxt.setText("Information invalid");
			informationTxt.setFill(Color.BLACK);
			e.getStackTrace();
		}

	}

}
