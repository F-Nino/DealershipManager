package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.CustomerObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CarDAO;
import model.CartDAO;
import model.PurchaseHistoryDAO;

public class CustomerConfirmPurchaseController implements Initializable {

	private PurchaseHistoryDAO phDAO;
	private CartDAO cartDAO;
	private CarDAO carDAO;
	public static ArrayList<CarObject> carObjectArrayList;
	public static CustomerObject currentCustomer;

	@FXML
	private Text carsBeingPurchased;

	@FXML
	private Text totalCostText;

	@FXML
	private Label totalLabel;

	@FXML
	private Button closeButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		phDAO = new PurchaseHistoryDAO();
		cartDAO = new CartDAO();
		carDAO = new CarDAO();
	}

	@FXML
	public void cancelPurchaseScreen() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void confirmPurchase() {
		phDAO.addNewPurchase(carObjectArrayList, currentCustomer.getCustomerID());
		cartDAO.removeCarsFromCart(carObjectArrayList, currentCustomer.getCustomerID());
		carDAO.decreaseQuantity(carObjectArrayList);
		totalLabel.setText(null);
		carsBeingPurchased.setText(null);
		totalCostText.setText("Thank you for your purchase!");
	}

	public void setupInformation(CustomerObject currentCustomerPassedIn, int totalAmount,
			ArrayList<CarObject> carObjectArrayListPassedIn) {
		carObjectArrayList = carObjectArrayListPassedIn;
		currentCustomer = currentCustomerPassedIn;
		String totalAmountString = "Total Cost is: $" + totalAmount;
		totalCostText.setText(totalAmountString);
		String carsBeingPurchasedString = "You are buying a(n) ";
		for (int i = 0; i < carObjectArrayList.size(); i++) {
			carsBeingPurchasedString += carObjectArrayList.get(i).getCarName();
			if (i != carObjectArrayList.size() - 1) {
				carsBeingPurchasedString += ", a(n) ";
			}
		}
		carsBeingPurchased.setText(carsBeingPurchasedString);
	}
}
