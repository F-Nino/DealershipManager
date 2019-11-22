package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.EmployeeObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.CarDAO;
import model.EmployeeDAO;

public class EditEmployeeController implements Initializable {

	private EmployeeDAO empDAO;
	private int employeeIDText;
	private int employeeSalaryText;
	private int adminPrivileges;
	private String titleComboBoxValue;
	private EmployeeObject employee;

	@FXML
	private Text employeeID;

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
	}

	@FXML
	public void submitEmployee() {
		boolean saveEmployee = true;

		titleComboBoxValue = (String) titleComboBox.getValue();
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
			informationTxt.setText("SALARY ID MUST BE A NUMBER");
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
				empDAO.editEmployee(employeeIDText, employeePassword.getText(), titleComboBoxValue,
						employeeFName.getText(), employeeLName.getText(), employeePosition.getText(),
						employeeSalaryText, adminPrivileges);
				informationTxt.setText("successfully edited Employee");
				informationTxt.setFill(Color.GREEN);
			}
		} catch (Exception e) {
			informationTxt.setText("Information invalid");
			informationTxt.setFill(Color.BLACK);
			e.getStackTrace();
		}

	}

	public void setupEmployee(EmployeeObject emp) {
		employee = new EmployeeObject(emp.getEmployeeID(), emp.getPassword(), emp.getTitle(), emp.getFirstName(),
				emp.getLastName(), emp.getPosition(), emp.getSalary(), emp.getAdminPrivileges());
		String empID = Integer.toString(employee.getEmployeeID());
		String empSal = Integer.toString(employee.getSalary());
		employeeID.setText(empID);
		employeeSalary.setText(empSal);
		employeePosition.setText(employee.getPosition());
		employeeFName.setText(employee.getFirstName());
		employeeLName.setText(employee.getLastName());
		employeePassword.setText(employee.getPassword());
		if (employee.getAdminPrivileges() == 1) {
			employeePrivileges.setSelected(true);
		}
		titleComboBox.getSelectionModel().select(emp.getTitle());

	}
}
