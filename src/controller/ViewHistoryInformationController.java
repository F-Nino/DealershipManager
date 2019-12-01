package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.CustomerObject;
import domain.EmployeeObject;
import domain.PurchaseHistoryObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CarDAO;
import model.CustomerDAO;
import model.EmployeeDAO;

public class ViewHistoryInformationController implements Initializable {

	private CarDAO carDAO;
	private CarObject viewCar;
	private CustomerDAO customerDAO;
	private CustomerObject viewCustomer;

	@FXML
	private Text customerID;

	@FXML
	private Text customerFirstName;

	@FXML
	private Text customerLastName;

	@FXML
	private Text customerEmail;

	@FXML
	private Text carID;

	@FXML
	private Text carBrand;

	@FXML
	private Text carName;

	@FXML
	private Text carColor;

	@FXML
	private Text carYear;

	@FXML
	private Text carPrice;

	@FXML
	private Text datePurchased;

	@FXML
	private Button closeButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		carDAO = new CarDAO();
		customerDAO = new CustomerDAO();
	}

	public void setupHistoryView(PurchaseHistoryObject selectedItem) throws SQLException {
		ResultSet carQuery = carDAO.returnSpecificCar(selectedItem.getCarID());
		if (carQuery == null) {
			System.out.println("that sucks");
		} else {
			while (carQuery.next()) {
				viewCar = new CarObject(carQuery.getInt(1), carQuery.getString(2), carQuery.getString(3),
						carQuery.getString(4), carQuery.getInt(5), carQuery.getInt(6), carQuery.getInt(7));
			}
		}

		ResultSet customerQuery = customerDAO.returnSpecificCustomer(selectedItem.getCustomerID());
		if (customerQuery == null) {
			System.out.println("that sucks");
		} else {
			while (customerQuery.next()) {
				viewCustomer = new CustomerObject(customerQuery.getInt("customerid"),
						customerQuery.getString("firstName"), customerQuery.getString("lastName"),
						customerQuery.getString("email"), customerQuery.getString("custPassword"));
			}
		}

		String customerIDString = Integer.toString(viewCustomer.getCustomerID());
		customerID.setText(customerIDString);
		customerFirstName.setText(viewCustomer.getFirstName());
		customerLastName.setText(viewCustomer.getLastName());
		customerEmail.setText(viewCustomer.getEmail());

		String carIDString = Integer.toString(viewCar.getCarID());
		String carYearString = Integer.toString(viewCar.getCarYear());
		String carPriceString = Integer.toString(viewCar.getCarPrice());
		carID.setText(carIDString);
		carBrand.setText(viewCar.getCarBrand());
		carName.setText(viewCar.getCarName());
		carColor.setText(viewCar.getCarColor());
		carYear.setText(carYearString);
		carPrice.setText(carPriceString);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		String datePurchasedString = df.format(selectedItem.getDatePurchased());
		datePurchased.setText(datePurchasedString);
	}

	public void closeWindow() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
