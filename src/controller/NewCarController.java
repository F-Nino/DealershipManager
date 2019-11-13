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

public class NewCarController implements Initializable {

	private CarDAO carDAO;
	private int carYearText;
	private int carPriceText;

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

	}

	@FXML
	public void myAction() {
		boolean saveCar = true;
		try {
			carYearText = Integer.parseInt(carYear.getText());
		} catch (Exception e) {
			informationTxt.setText("YEAR MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveCar = false;
		}
		try {
			carPriceText = Integer.parseInt(carPrice.getText());
		} catch (Exception e) {
			informationTxt.setText("PRICE MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK);
			saveCar = false;
		}

		try {
			if (saveCar) {
				carDAO.addNewCar(carBrand.getText(), carName.getText(), carColor.getText(), carYearText, carPriceText);
				informationTxt.setText("successfully added new car");
				informationTxt.setFill(Color.GREEN);
			}
		} catch (Exception e) {
			informationTxt.setText("Information invalid");
			informationTxt.setFill(Color.BLACK);
		}

	}

}
