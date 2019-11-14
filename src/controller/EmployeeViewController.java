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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.CarDAO;
import model.EmployeeDAO;

public class EmployeeViewController implements Initializable {

	private EmployeeDAO empDAO;
	private ArrayList<EmployeeObject> employeeObjectArrayList = new ArrayList<EmployeeObject>();

	@FXML
	private ComboBox comboBoxEmps;

	@FXML
	private TextField carBrand;

	@FXML
	private TextField carName;

	@FXML
	private TextField carColor;

	@FXML
	private TextField carYear;

	@FXML
	private TextField carPrice;

	@FXML
	private Text informationTxt;

	@FXML
	private TableColumn<EmployeeObject, Integer> empIDColumn;

	@FXML
	private TableColumn<EmployeeObject, String> empFirstNameColumn;

	@FXML
	private TableColumn<EmployeeObject, String> empLastNameColumn;

	@FXML
	private TableView<EmployeeObject> employeeTableView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		empDAO = new EmployeeDAO();
		comboBoxEmps.getItems().clear();
		comboBoxEmps.getItems().addAll("Employee ID", "First Name", "Last Name");
		comboBoxEmps.getSelectionModel().select("Employee ID");
		try {
			loadEmployeeDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void loadEmployeeDetails() throws SQLException {
		empIDColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, Integer>("employeeID"));
		empFirstNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, String>("firstName"));
		empLastNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, String>("lastName"));
		ResultSet employeeQuery = empDAO.returnCustomers();
		if (empDAO == null) {
			System.out.println("that sucks");
		} else {
			while (employeeQuery.next()) {
				employeeObjectArrayList.add(new EmployeeObject(employeeQuery.getInt("employeeid"),
						employeeQuery.getString("employeeid"), employeeQuery.getString("firstName"),
						employeeQuery.getString("lastName"), employeeQuery.getInt("adminPriviliges")));
			}
			changeSort();
		}

	}

	@FXML
	public void changeSort() {
		String comboBoxValue = (String) comboBoxEmps.getValue();
		// bubble sort
		if (comboBoxEmps.equals("Employee ID")) {
			for (int i = 0; i < employeeObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < employeeObjectArrayList.size() - i - 1; j++) {
					if (employeeObjectArrayList.get(j).getEmployeeID() > employeeObjectArrayList.get(j + 1)
							.getEmployeeID()) {
						Collections.swap(employeeObjectArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxEmps.equals("First Name")) {
			for (int i = 0; i < employeeObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < employeeObjectArrayList.size() - i - 1; j++) {
					if ((employeeObjectArrayList.get(j).getFirstName()
							.compareTo(employeeObjectArrayList.get(j + 1).getFirstName())) > 0) {
						Collections.swap(employeeObjectArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxEmps.equals("Last Name")) {
			for (int i = 0; i < employeeObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < employeeObjectArrayList.size() - i - 1; j++) {
					if ((employeeObjectArrayList.get(j).getLastName()
							.compareTo(employeeObjectArrayList.get(j + 1).getLastName())) > 0) {
						Collections.swap(employeeObjectArrayList, j, j + 1);
					}
				}
			}
		}

		employeeTableView.getItems().clear();
		for (EmployeeObject coo : employeeObjectArrayList) {
			employeeTableView.getItems().add(coo);
		}

	}

}
