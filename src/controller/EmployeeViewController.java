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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.EmployeeDAO;

public class EmployeeViewController implements Initializable {

	private EmployeeDAO empDAO;
	private ArrayList<EmployeeObject> employeeObjectArrayList = new ArrayList<EmployeeObject>();

	@FXML
	private AnchorPane employeeViewPane;

	@FXML
	private Button editBtn;

	@FXML
	private TextField searchEmployeeID;

	@FXML
	private ComboBox<String> comboBoxEmps;

	@FXML
	private Text informationTxt;

	@FXML
	private TableColumn<EmployeeObject, Integer> empIDColumn;

	@FXML
	private TableColumn<EmployeeObject, String> empTitleColumn;

	@FXML
	private TableColumn<EmployeeObject, String> empFirstNameColumn;

	@FXML
	private TableColumn<EmployeeObject, String> empLastNameColumn;

	@FXML
	private TableColumn<EmployeeObject, String> empPositionColumn;

	@FXML
	private TableColumn<EmployeeObject, Integer> empSalaryColumn;

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
		empTitleColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, String>("title"));
		empFirstNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, String>("firstName"));
		empLastNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, String>("lastName"));
		empPositionColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, String>("position"));
		empSalaryColumn.setCellValueFactory(new PropertyValueFactory<EmployeeObject, Integer>("salary"));
		ResultSet employeeQuery = empDAO.returnEmployees();
		if (empDAO == null) {
			System.out.println("that sucks");
		} else {
			while (employeeQuery.next()) {
				employeeObjectArrayList
						.add(new EmployeeObject(employeeQuery.getInt("employeeid"), employeeQuery.getString("password"),
								employeeQuery.getString("title"), employeeQuery.getString("firstName"),
								employeeQuery.getString("lastName"), employeeQuery.getString("position"),
								employeeQuery.getInt("salary"), employeeQuery.getInt("adminPriviliges")));
			}
			changeSort();
		}

	}

	public static ArrayList<EmployeeObject> quickSort(ArrayList<EmployeeObject> list) {
		if (list.isEmpty())
			return list; // start with recursion base case
		ArrayList<EmployeeObject> sorted; // this shall be the sorted list to return, no needd to initialise
		ArrayList<EmployeeObject> smaller = new ArrayList<EmployeeObject>(); // Vehicles smaller than pivot
		ArrayList<EmployeeObject> greater = new ArrayList<EmployeeObject>(); // Vehicles greater than pivot
		EmployeeObject pivot = list.get(0); // first Vehicle in list, used as pivot
		int i;
		EmployeeObject j; // Variable used for Vehicles in the loop
		for (i = 1; i < list.size(); i++) {
			j = list.get(i);
			if (j.getEmployeeID() < pivot.getEmployeeID()) // make sure Vehicle has proper compareTo method
				smaller.add(j);
			else
				greater.add(j);
		}
		smaller = quickSort(smaller); // capitalise 's'
		greater = quickSort(greater); // sort both halfs recursively
		smaller.add(pivot); // add initial pivot to the end of the (now sorted) smaller Vehicles
		smaller.addAll(greater); // add the (now sorted) greater Vehicles to the smaller ones (now smaller is
									// essentially your sorted list)
		sorted = smaller; // assign it to sorted; one could just as well do: return smaller

		return sorted;
	}

	@FXML
	public void changeSort() {
		String comboBoxValue = (String) comboBoxEmps.getValue();
		// bubble sort
		if (comboBoxValue.equals("Employee ID")) {
			employeeObjectArrayList = quickSort(
					employeeObjectArrayList);/*
												 * for (int i = 0; i < employeeObjectArrayList.size() - 1; i++) { for
												 * (int j = 0; j < employeeObjectArrayList.size() - i - 1; j++) { if
												 * (employeeObjectArrayList.get(j).getEmployeeID() >
												 * employeeObjectArrayList.get(j + 1) .getEmployeeID()) {
												 * Collections.swap(employeeObjectArrayList, j, j + 1); } } }
												 */
		} else if (comboBoxValue.equals("First Name")) {
			for (int i = 0; i < employeeObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < employeeObjectArrayList.size() - i - 1; j++) {
					if ((employeeObjectArrayList.get(j).getFirstName()
							.compareTo(employeeObjectArrayList.get(j + 1).getFirstName())) > 0) {
						Collections.swap(employeeObjectArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxValue.equals("Last Name")) {
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

	@FXML
	public void searchEmployees() {
		String empID = searchEmployeeID.getText();
		int matchingEmpID = -1;
		if ((!empID.isEmpty()) || (empID != null)) {
			int empIDInt = Integer.parseInt(empID);
			for (int i = 0; i < employeeObjectArrayList.size(); i++) {
				if (empIDInt == employeeObjectArrayList.get(i).getEmployeeID()) {
					matchingEmpID = i;
				}
			}
			if (matchingEmpID != -1) {
				employeeTableView.requestFocus();
				employeeTableView.getSelectionModel().select(matchingEmpID);
				employeeTableView.getFocusModel().focus(matchingEmpID);
			}
		}
	}

	@FXML
	public void adminHomeButton() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/AdminHome.fxml"));
		employeeViewPane.getChildren().setAll(pane);
	}

	@FXML
	public void addEmployee() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/AddEmployee.fxml"));
		employeeViewPane.getChildren().setAll(pane);
	}

	@FXML
	public void deleteRow() {
		EmployeeObject selectedItem = employeeTableView.getSelectionModel().getSelectedItem();
		employeeTableView.getItems().remove(selectedItem);
		empDAO.deleteItem(selectedItem);
		employeeObjectArrayList.remove(selectedItem);
	}

	@FXML
	public void editRow(ActionEvent event) throws IOException, SQLException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editEmployee.fxml"));
		Parent root = (Parent) loader.load();
		EditEmployeeController edit = loader.getController();

		EmployeeObject selectedItem = employeeTableView.getSelectionModel().getSelectedItem();
		System.out.println(selectedItem.getPassword());
		edit.setupEmployee(selectedItem);

		Stage stage = new Stage();

		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	public void reloadPage() throws SQLException {
		ResultSet employeeQuery = empDAO.returnEmployees();
		employeeObjectArrayList.clear();
		if (empDAO == null) {
			System.out.println("that sucks");
		} else {
			while (employeeQuery.next()) {
				employeeObjectArrayList
						.add(new EmployeeObject(employeeQuery.getInt("employeeid"), employeeQuery.getString("password"),
								employeeQuery.getString("title"), employeeQuery.getString("firstName"),
								employeeQuery.getString("lastName"), employeeQuery.getString("position"),
								employeeQuery.getInt("salary"), employeeQuery.getInt("adminPriviliges")));
			}
			changeSort();
		}
	}
}
