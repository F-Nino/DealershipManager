package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
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
		int carIDs[] = new int[carObjectArrayList.size()];
		for(int i = 0; i < carObjectArrayList.size(); i++) {
			carIDs[i] = carObjectArrayList.get(i).getCarID();
		}
		phDAO.addNewPurchase(carIDs, currentCustomer.getCustomerID());
		cartDAO.removeCarsFromCart(carObjectArrayList, currentCustomer.getCustomerID());
		carDAO.decreaseQuantity(carObjectArrayList);
		totalLabel.setText(null);
		carsBeingPurchased.setText(null);
		totalCostText.setText("Thank you for your purchase!");
	}

	public void setupInformation(CustomerObject currentCustomerPassedIn, int totalAmount,
			ArrayList<CarObject> carObjectArrayList2) {
		carObjectArrayList = carObjectArrayList2;
		currentCustomer = currentCustomerPassedIn;
		LinkedList<CarObject> carObjectLinkedList = new LinkedList<CarObject>(carObjectArrayList); 
		Collections.sort(carObjectLinkedList, new SortByCarName());
		String totalAmountString = "Total Cost is: $" + totalAmount;
		totalCostText.setText(totalAmountString);
		String carsBeingPurchasedString = "You are buying a(n) ";
		for (int i = 0; i < carObjectLinkedList.size(); i++) {
			carsBeingPurchasedString += ((CarObject) carObjectLinkedList.get(i)).getCarName();
			if (i != carObjectLinkedList.size() - 1) {
				carsBeingPurchasedString += ", a(n) ";
			}
		}
		carsBeingPurchased.setText(carsBeingPurchasedString);
	}
}

class SortByCarName implements Comparator<CarObject> {

	@Override
	public int compare(CarObject carA, CarObject carB) {
		// TODO Auto-generated method stub
		return carA.getCarName().compareTo(carB.getCarName()); 
	} 
}
