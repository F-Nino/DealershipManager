package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import domain.CarObject;
import domain.EmployeeObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.CarDAO;
import model.EmployeeDAO;

public class EditCarController implements Initializable {

	private CarDAO carDAO;
	private int carIDText;
	private int carYearText;
	private int carPriceText;
	private int carQuantityText;
	private CarObject car;

	@FXML
	private Text carID;

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
	private TextField carQuantity;

	@FXML
	private Text informationTxt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		carDAO = new CarDAO();
	}

	@FXML
	public void submitCar() {
		boolean saveCar = true;
		try {
			carIDText = Integer.parseInt(carID.getText());
		} catch (Exception e) {
			informationTxt.setText("CAR ID MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveCar = false;
		}

		try {
			carYearText = Integer.parseInt(carYear.getText());
		} catch (Exception e) {
			informationTxt.setText("CAR YEAR MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveCar = false;
		}

		try {
			carPriceText = Integer.parseInt(carPrice.getText());
		} catch (Exception e) {
			informationTxt.setText("CAR PRICE MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveCar = false;
		}

		try {
			carQuantityText = Integer.parseInt(carQuantity.getText());
		} catch (Exception e) {
			informationTxt.setText("CAR QUANTITY MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveCar = false;
		}

		if (saveCar) {
			try {
				carDAO.editCar(carIDText, carBrand.getText(), carName.getText(), carColor.getText(), carYearText,
						carPriceText, carQuantityText);
				informationTxt.setText("successfully edited Employee");
				informationTxt.setFill(Color.GREEN);
			} catch (Exception e) {
				informationTxt.setText("Information invalid");
				informationTxt.setFill(Color.BLACK);
				e.getStackTrace();
			}
		}

	}

	public void setupCar(CarObject car) {
		car = new CarObject(car.getCarID(), car.getCarBrand(), car.getCarName(), car.getCarColor(), car.getCarYear(),
				car.getCarPrice(), car.getCarQuantity());
		String carIDString = Integer.toString(car.getCarID());
		String carYearString = Integer.toString(car.getCarYear());
		String carPriceString = Integer.toString(car.getCarPrice());
		String carQuantityString = Integer.toString(car.getCarQuantity());
		carID.setText(carIDString);
		carBrand.setText(car.getCarBrand());
		carName.setText(car.getCarName());
		carColor.setText(car.getCarColor());
		carYear.setText(carYearString);
		carPrice.setText(carPriceString);
		carQuantity.setText(carQuantityString);

	}
}
