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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class CarViewControllerEmpHome implements Initializable {

	private CarDAO carDAO;
	private int carYearText;
	private int carPriceText;
	private ArrayList<CarObject> carObjectArrayList = new ArrayList<CarObject>();

	@FXML
	private AnchorPane rootPane;

	@FXML
	private ComboBox comboBoxCars;

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
	private TableColumn<CarObject, Integer> carIDColumn;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		carDAO = new CarDAO();
		comboBoxCars.getItems().clear();
		comboBoxCars.getItems().addAll("Car Brand", "Car Name", "Car Color", "Car Year", "Car Price");
		comboBoxCars.getSelectionModel().select("Car Brand");
		try {
			loadCarDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@FXML
	public void loadCarDetails() throws SQLException {
		carIDColumn.setCellValueFactory(new PropertyValueFactory<CarObject, Integer>("carID"));
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

	@FXML
	public void empHome() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/employeeHomePage.fxml"));
		rootPane.getChildren().setAll(pane);
	}
	
	@FXML
	public void addCar() throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/AddCar.fxml"));
		rootPane.getChildren().setAll(pane);
	}
	
	@FXML
	public void reloadPage() throws SQLException {
		carObjectArrayList.clear();
		loadCarDetails();
	}

	@FXML
	public void deleteRow() {
		CarObject selectedItem = carTableView.getSelectionModel().getSelectedItem();
		carTableView.getItems().remove(selectedItem);
		carDAO.deleteItem(selectedItem);
		carObjectArrayList.remove(selectedItem);
	}

	@FXML
	public void editRow() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editCars.fxml"));
		Parent root = (Parent) loader.load();
		EditCarController edit = loader.getController();

		CarObject selectedItem = carTableView.getSelectionModel().getSelectedItem();
		edit.setupCar(selectedItem);

		Stage stage = new Stage();

		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	public void changeSort() {
		String comboBoxValue = (String) comboBoxCars.getValue();
		// bubble sort
		if (comboBoxValue.equals("Car Brand")) {
			for (int i = 0; i < carObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < carObjectArrayList.size() - i - 1; j++) {
					if ((carObjectArrayList.get(j).getCarBrand()
							.compareTo(carObjectArrayList.get(j + 1).getCarBrand())) > 0) {
						Collections.swap(carObjectArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxValue.equals("Car Name")) {
			for (int i = 0; i < carObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < carObjectArrayList.size() - i - 1; j++) {
					if ((carObjectArrayList.get(j).getCarName()
							.compareTo(carObjectArrayList.get(j + 1).getCarName())) > 0) {
						Collections.swap(carObjectArrayList, j, j + 1);
					}
				}
			}
		} else if (comboBoxValue.equals("Car Color")) {
			for (int i = 0; i < carObjectArrayList.size() - 1; i++) {
				for (int j = 0; j < carObjectArrayList.size() - i - 1; j++) {
					if ((carObjectArrayList.get(j).getCarColor()
							.compareTo(carObjectArrayList.get(j + 1).getCarColor())) > 0) {
						Collections.swap(carObjectArrayList, j, j + 1);
					}
				}
			}
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
		for (CarObject coo : carObjectArrayList) {
			carTableView.getItems().add(coo);
		}

	}

}
