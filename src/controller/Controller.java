package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
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

public class Controller implements Initializable {

	private CarDAO carDAO;
	private int carYearText;
	private int carPriceText;
	private ArrayList<CarObject> carObjectArrayList = new ArrayList<CarObject>();

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
		ResultSet carsQuery = carDAO.returnCars();
		if (carsQuery == null) {
			System.out.println("that sucks");
		} else {
			while (carsQuery.next()) {
				carObjectArrayList.add(new CarObject(carsQuery.getInt(1), carsQuery.getString(2),
						carsQuery.getString(3), carsQuery.getString(4), carsQuery.getInt(5), carsQuery.getInt(6)));
			}
			changeSort();
		}

	}

	@FXML
	public void sortCars() {

	}

	@FXML
	public void changeSort() {
		// "Car Name", "Car Color", "Car Year", "Car Price"
		String comboBoxValue = (String) comboBoxCars.getValue();
		System.out.println(comboBoxValue);

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
