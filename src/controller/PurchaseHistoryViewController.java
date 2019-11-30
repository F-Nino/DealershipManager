package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.EmployeeObject;
import domain.PurchaseHistoryObject;
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
import model.PurchaseHistoryDAO;

public class PurchaseHistoryViewController implements Initializable {

	private PurchaseHistoryDAO phDAO;
	private ArrayList<PurchaseHistoryObject> purchaseHistoryArrayList = new ArrayList<PurchaseHistoryObject>();

	@FXML
	private AnchorPane purchaseHistoryViewPane;

	@FXML
	private ComboBox comboBoxHistory;

	@FXML
	private TableView<PurchaseHistoryObject> purchaseHistoryTableView;

	@FXML
	private TableColumn<PurchaseHistoryObject, Integer> historyCustomerIDColumn;

	@FXML
	private TableColumn<PurchaseHistoryObject, Integer> historyCarIDColumn;

	@FXML
	private TableColumn<PurchaseHistoryObject, Date> historyDateColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		phDAO = new PurchaseHistoryDAO();
		comboBoxHistory.getItems().clear();
		comboBoxHistory.getItems().addAll("Customer ID", "Car ID", "Date");
		comboBoxHistory.getSelectionModel().select("Customer ID");
		try {
			loadHistoryDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void loadHistoryDetails() throws SQLException {
		historyCarIDColumn.setCellValueFactory(new PropertyValueFactory<PurchaseHistoryObject, Integer>("carID"));
		historyCustomerIDColumn
				.setCellValueFactory(new PropertyValueFactory<PurchaseHistoryObject, Integer>("customerID"));
		historyDateColumn.setCellValueFactory(new PropertyValueFactory<PurchaseHistoryObject, Date>("datePurchased"));
		ResultSet historyQuery = phDAO.returnHistory();
		if (historyQuery == null) {
			System.out.println("that sucks");
		} else {
			while (historyQuery.next()) {
				purchaseHistoryArrayList.add(new PurchaseHistoryObject(historyQuery.getInt("carid"),
						historyQuery.getInt("customerid"), historyQuery.getDate("datePurchased")));
			}
			changeSort();
		}

	}

	@FXML
	public void changeSort() {
		String comboBoxValue = (String) comboBoxHistory.getValue();
		// bubble sort
		if (comboBoxValue.equals("Customer ID")) {
			for (int i = 0; i < purchaseHistoryArrayList.size() - 1; i++) {
				for (int j = 0; j < purchaseHistoryArrayList.size() - i - 1; j++) {
					if (purchaseHistoryArrayList.get(j).getCustomerID() > purchaseHistoryArrayList.get(j + 1)
							.getCustomerID()) {
						Collections.swap(purchaseHistoryArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxValue.equals("Car ID")) {
			for (int i = 0; i < purchaseHistoryArrayList.size() - 1; i++) {
				for (int j = 0; j < purchaseHistoryArrayList.size() - i - 1; j++) {
					if (purchaseHistoryArrayList.get(j).getCarID() > purchaseHistoryArrayList.get(j + 1).getCarID()) {
						Collections.swap(purchaseHistoryArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxValue.equals("Date")) {
			for (int i = 0; i < purchaseHistoryArrayList.size() - 1; i++) {
				for (int j = 0; j < purchaseHistoryArrayList.size() - i - 1; j++) {
					if ((purchaseHistoryArrayList.get(j).getDatePurchased()
							.compareTo(purchaseHistoryArrayList.get(j + 1).getDatePurchased())) > 0) {
						Collections.swap(purchaseHistoryArrayList, j, j + 1);
					}
				}
			}
		}

		purchaseHistoryTableView.getItems().clear();
		for (PurchaseHistoryObject pho : purchaseHistoryArrayList) {
			purchaseHistoryTableView.getItems().add(pho);
		}

	}

	@FXML
	public void adminHomeButton() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/AdminHome.fxml"));
		purchaseHistoryViewPane.getChildren().setAll(pane);
	}

	@FXML
	public void viewInformation() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/viewHistoryInformation.fxml"));
		Parent root = (Parent) loader.load();
		ViewHistoryInformationController view = loader.getController();

		PurchaseHistoryObject selectedItem = purchaseHistoryTableView.getSelectionModel().getSelectedItem();
		view.setupHistoryView(selectedItem);

		Stage stage = new Stage();

		stage.setScene(new Scene(root));
		stage.show();
	}

	/**
	 * @FXML public void addEmployee() throws IOException { AnchorPane pane =
	 *       FXMLLoader.load(getClass().getResource("../view/AddEmployee.fxml"));
	 *       employeeViewPane.getChildren().setAll(pane); }
	 * 
	 * @FXML public void deleteRow() { EmployeeObject selectedItem =
	 *       employeeTableView.getSelectionModel().getSelectedItem();
	 *       employeeTableView.getItems().remove(selectedItem);
	 *       empDAO.deleteItem(selectedItem);
	 *       employeeObjectArrayList.remove(selectedItem); }
	 * 
	 * @FXML public void editRow(ActionEvent event) throws IOException, SQLException
	 *       {
	 * 
	 *       FXMLLoader loader = new
	 *       FXMLLoader(getClass().getResource("../view/editEmployee.fxml")); Parent
	 *       root = (Parent) loader.load(); EditEmployeeController edit =
	 *       loader.getController();
	 * 
	 *       EmployeeObject selectedItem =
	 *       employeeTableView.getSelectionModel().getSelectedItem();
	 *       System.out.println(selectedItem.getPassword());
	 *       edit.setupEmployee(selectedItem);
	 * 
	 *       Stage stage = new Stage();
	 * 
	 *       stage.setScene(new Scene(root)); stage.show(); }
	 */

	@FXML
	public void reloadPage() throws SQLException {
		ResultSet historyQuery = phDAO.returnHistory();
		purchaseHistoryArrayList.clear();
		if (historyQuery == null) {
			System.out.println("that sucks");
		} else {
			while (historyQuery.next()) {
				purchaseHistoryArrayList.add(new PurchaseHistoryObject(historyQuery.getInt("carid"),
						historyQuery.getInt("customerid"), historyQuery.getDate("datePurchased")));
			}
			changeSort();
		}
	}
}
