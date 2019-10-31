package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import domain.CarObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		carDAO = new CarDAO();
	}

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

	@FXML
	public void myAction() {
		try {
			carYearText = Integer.parseInt(carYear.getText());
			carPriceText = Integer.parseInt(carYear.getText());
			carDAO.addNewCar(carBrand.getText(), carName.getText(), carColor.getText(), carYearText, carPriceText);
			informationTxt.setText("successfully added new car");
			informationTxt.setFill(Color.GREEN);
		} catch (Exception e) {
			informationTxt.setText("YEAR MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
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
			ArrayList<CarObject> co = new ArrayList<CarObject>();
			while (carsQuery.next()) {
				co.add(new CarObject(carsQuery.getInt(1), carsQuery.getString(2), carsQuery.getString(3),
						carsQuery.getString(4), carsQuery.getInt(5), carsQuery.getInt(6)));
			}
			for (CarObject coo : co) {
				carTableView.getItems().add(coo);
			}
		}

	}
}
