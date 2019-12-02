package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.CustomerObject;
import domain.EmployeeObject;
import domain.PurchaseHistoryObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CarDAO;
import model.CartDAO;

public class CustomerViewCartController implements Initializable {

	public static CustomerObject currentCustomer;
	private CarDAO carDAO;
	private CartDAO cartDAO;
	private ArrayList<CarObject> carObjectArrayList = new ArrayList<CarObject>();
	private int totalAmount = 0;

	@FXML
	private AnchorPane customerCartViewPane;

	@FXML
	private TableColumn<CarObject, String> carBrandColumn;

	@FXML
	private TableColumn<CarObject, String> carNameColumn;

	@FXML
	private TableColumn<CarObject, String> carColorColumn;

	@FXML
	private TableColumn<CarObject, Integer> carYearColumn;

	@FXML
	private TableColumn<CarObject, Integer> carPriceColumn;

	@FXML
	private TableView<CarObject> carTableView;

	@FXML
	private Text totalCostText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		Platform.runLater(() -> {

			carDAO = new CarDAO();
			cartDAO = new CartDAO();
			try {
				loadCarDetails();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});

	}

	@FXML
	public void loadCarDetails() throws SQLException {
		carBrandColumn.setCellValueFactory(new PropertyValueFactory<CarObject, String>("carBrand"));
		carNameColumn.setCellValueFactory(new PropertyValueFactory<CarObject, String>("carName"));
		carColorColumn.setCellValueFactory(new PropertyValueFactory<CarObject, String>("carColor"));
		carYearColumn.setCellValueFactory(new PropertyValueFactory<CarObject, Integer>("carYear"));
		carPriceColumn.setCellValueFactory(new PropertyValueFactory<CarObject, Integer>("carPrice"));
		ResultSet cartQuery = cartDAO.returnCarsForSpecificUser(currentCustomer);
		ResultSet carsQuery = carDAO.returnCarsFromCart(cartQuery);
		if (carsQuery == null) {
			System.out.println("that sucks");
		} else {
			while (carsQuery.next()) {
				carObjectArrayList
						.add(new CarObject(carsQuery.getInt(1), carsQuery.getString(2), carsQuery.getString(3),
								carsQuery.getString(4), carsQuery.getInt(5), carsQuery.getInt(6), carsQuery.getInt(7)));
				totalAmount += carsQuery.getInt("carPrice");
			}
			carTableView.getItems().clear();
			for (CarObject coo : carObjectArrayList) {
				carTableView.getItems().add(coo);
			}

		}
		String totalAmountString = "Total Cost is: $" + totalAmount;
		totalCostText.setText(totalAmountString);
	}

	public void setUserForCart(CustomerObject customer) {
		currentCustomer = customer;
		System.out.println("eree");
	}

	@FXML
	public void removeFromCart() {
		CarObject selectedCar = carTableView.getSelectionModel().getSelectedItem();
		cartDAO.removeCarFromCart(selectedCar.getCarID(), currentCustomer.getCustomerID());
		carTableView.getItems().remove(selectedCar);
		carObjectArrayList.remove(selectedCar);
	}

	@FXML
	public void ConfirmPurchase() throws IOException {
		if (carObjectArrayList == null) {
			System.out.println("dont... dont play with a ");
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/confirmPurchase.fxml"));
			Parent root = (Parent) loader.load();
			CustomerConfirmPurchaseController confirmPurchaseLoader = loader.getController();
			confirmPurchaseLoader.setupInformation(currentCustomer, totalAmount, carObjectArrayList);

			Stage stage = new Stage();

			stage.setScene(new Scene(root));
			stage.show();
		}
	}

	@FXML
	public void reloadPage() {
		try {
			totalAmount = 0;
			carObjectArrayList.clear();
			carTableView.getItems().clear();
			loadCarDetails();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logOut() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/LOGIN.fxml"));
		customerCartViewPane.getChildren().setAll(pane);
	}

	@FXML
	public void goBackToPreviousPage() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CustomerViewCarTableView.fxml"));

		Parent root = (Parent) fxmlLoader.load();
		CustomerViewCarsController controller = fxmlLoader.<CustomerViewCarsController>getController();
		controller.setUser(currentCustomer);
		System.out.println(currentCustomer);
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/CustomerViewCarTableView.fxml"));
		customerCartViewPane.getChildren().setAll(pane);
	}

}
