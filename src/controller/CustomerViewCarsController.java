package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.CustomerObject;
import domain.PurchaseHistoryObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CarDAO;
import model.CartDAO;

public class CustomerViewCarsController implements Initializable {

	public static CustomerObject currentCustomer;
	private CarDAO carDAO;
	private CartDAO cartDAO;
	private ArrayList<CarObject> carObjectArrayList = new ArrayList<CarObject>();

	@FXML
	private AnchorPane customerCarViewPane;

	@FXML
	private ComboBox<String> comboBoxCars;

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
	private TableColumn<CarObject, Integer> carQuantityColumn;

	@FXML
	private TableView<CarObject> carTableView;

	@FXML
	private Text additionText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		carTableView.getSelectionModel().setSelectionMode(
			    SelectionMode.MULTIPLE
			);
		carDAO = new CarDAO();
		cartDAO = new CartDAO();
		comboBoxCars.getItems().clear();
		comboBoxCars.getItems().addAll("Car Brand", "Car Name", "Car Color", "Car Year", "Car Price");
		comboBoxCars.getSelectionModel().select("Car Brand");
		try {
			loadCarDetails();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void loadCarDetails() throws SQLException {
		carBrandColumn.setCellValueFactory(new PropertyValueFactory<CarObject, String>("carBrand"));
		carNameColumn.setCellValueFactory(new PropertyValueFactory<CarObject, String>("carName"));
		carColorColumn.setCellValueFactory(new PropertyValueFactory<CarObject, String>("carColor"));
		carYearColumn.setCellValueFactory(new PropertyValueFactory<CarObject, Integer>("carYear"));
		carPriceColumn.setCellValueFactory(new PropertyValueFactory<CarObject, Integer>("carPrice"));
		carQuantityColumn.setCellValueFactory(new PropertyValueFactory<CarObject, Integer>("carQuantity"));
		ResultSet carsQuery = carDAO.returnCars();
		if (carsQuery == null) {
			System.out.println("that sucks");
		} else {
			while (carsQuery.next()) {
				carObjectArrayList
						.add(new CarObject(carsQuery.getInt(1), carsQuery.getString(2), carsQuery.getString(3),
								carsQuery.getString(4), carsQuery.getInt(5), carsQuery.getInt(6), carsQuery.getInt(7)));
			}
			changeSort();
		}

	}

	public ArrayList<CarObject> mergeSort(ArrayList<CarObject> carObjectArrayList2) {
		ArrayList<CarObject> left = new ArrayList<>();
		ArrayList<CarObject> right = new ArrayList<>();
		int center;

		if (carObjectArrayList2.size() == 1) {
			return carObjectArrayList2;
		} else {
			center = carObjectArrayList2.size() / 2;
			for (int i = 0; i < center; i++) {
				left.add(carObjectArrayList2.get(i));
			}

			for (int i = center; i < carObjectArrayList2.size(); i++) {
				right.add(carObjectArrayList2.get(i));
			}

			left = mergeSort(left);
			right = mergeSort(right);

			merge(left, right, carObjectArrayList2);
		}
		return carObjectArrayList2;
	}

	private void merge(ArrayList<CarObject> left, ArrayList<CarObject> right,
			ArrayList<CarObject> carObjectArrayList2) {
		int leftIndex = 0;
		int rightIndex = 0;
		int wholeIndex = 0;

		String comboBoxValue = (String) comboBoxCars.getValue();
		if (comboBoxValue.equals("Car Brand")) {
			while (leftIndex < left.size() && rightIndex < right.size()) {
				if ((left.get(leftIndex).getCarBrand().compareTo(right.get(rightIndex).getCarBrand())) < 0) {
					carObjectArrayList2.set(wholeIndex, left.get(leftIndex));
					leftIndex++;
				} else {
					carObjectArrayList2.set(wholeIndex, right.get(rightIndex));
					rightIndex++;
				}
				wholeIndex++;
			}
		} else if (comboBoxValue.equals("Car Name")) {
			while (leftIndex < left.size() && rightIndex < right.size()) {
				if ((left.get(leftIndex).getCarName().compareTo(right.get(rightIndex).getCarName())) < 0) {
					carObjectArrayList2.set(wholeIndex, left.get(leftIndex));
					leftIndex++;
				} else {
					carObjectArrayList2.set(wholeIndex, right.get(rightIndex));
					rightIndex++;
				}
				wholeIndex++;
			}
		} else if (comboBoxValue.equals("Car Color")) {
			while (leftIndex < left.size() && rightIndex < right.size()) {
				if ((left.get(leftIndex).getCarColor().compareTo(right.get(rightIndex).getCarColor())) < 0) {
					carObjectArrayList2.set(wholeIndex, left.get(leftIndex));
					leftIndex++;
				} else {
					carObjectArrayList2.set(wholeIndex, right.get(rightIndex));
					rightIndex++;
				}
				wholeIndex++;
			}
		}
		ArrayList<CarObject> rest;
		int restIndex;
		if (leftIndex >= left.size()) {
			rest = right;
			restIndex = rightIndex;
		} else {
			rest = left;
			restIndex = leftIndex;
		}
		
		for (int i = restIndex; i < rest.size(); i++) {
			carObjectArrayList2.set(wholeIndex, rest.get(i));
			wholeIndex++;
		}
	}

	@FXML
	public void changeSort() {
		String comboBoxValue = (String) comboBoxCars.getValue();
		// bubble sort
		if ((comboBoxValue.equals("Car Brand")) || (comboBoxValue.equals("Car Name"))
				|| (comboBoxValue.equals("Car Color"))) {
			carObjectArrayList = mergeSort(carObjectArrayList);
		} else if (comboBoxValue.equals("Car Year")) {
			for (int i = 0; i < carObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < carObjectArrayList.size() - i - 1; j++) {
					if (carObjectArrayList.get(j).getCarYear() > (carObjectArrayList.get(j + 1).getCarYear())) {
						Collections.swap(carObjectArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxValue.equals("Car Price")) {
			for (int i = 0; i < carObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < carObjectArrayList.size() - i - 1; j++) {
					if (carObjectArrayList.get(j).getCarPrice() > (carObjectArrayList.get(j + 1).getCarPrice())) {
						Collections.swap(carObjectArrayList, j, j + 1);
					}
				}
			}
		}
		carTableView.getItems().clear();
		Iterator<CarObject> carObjectIterator = carObjectArrayList.iterator();
		while (carObjectIterator.hasNext()) {
			carTableView.getItems().add(carObjectIterator.next());
		}
	}

	public void logOut() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/LOGIN.fxml"));
		customerCarViewPane.getChildren().setAll(pane);
	}

	public void setUser(CustomerObject customer) {
		currentCustomer = customer;
	}

	@FXML
	public void customerHome() {
		System.out.println("yo");
	}

	@FXML
	public void addToCart() throws IOException {
		CarObject selectedCar = carTableView.getSelectionModel().getSelectedItem();
		if (selectedCar.getCarQuantity() > 0) {
			CustomerObject cc = currentCustomer;
			try {
				cartDAO.addNewItemToCart(selectedCar, cc);
				String atText = "Successfully added " + (selectedCar.getCarName()) + " to the cart";
				additionText.setText(atText);
				additionText.setFill(Color.GREEN);
			} catch (Exception e) {
				String atText = "Already in cart";
				additionText.setText(atText);
				additionText.setFill(Color.RED);
			}

		} else {
			String atText = "Not Enough Quantity";
			additionText.setText(atText);
			additionText.setFill(Color.RED);
		}

	}

	@FXML
	public void viewCart() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CustomerViewCartTableView.fxml"));

		Parent root = (Parent) fxmlLoader.load();
		CustomerViewCartController controller = fxmlLoader.<CustomerViewCartController>getController();
		controller.setUserForCart(currentCustomer);
		System.out.println(currentCustomer);
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/CustomerViewCartTableView.fxml"));
		customerCarViewPane.getChildren().setAll(pane);
	}

}