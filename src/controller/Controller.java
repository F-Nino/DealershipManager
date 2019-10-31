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
	private int carText;
	private boolean carTextCorrect = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Calling here to check if table exists.
		carDAO = new CarDAO();
	}

	@FXML
	private TextField carName;

	@FXML
	private TextField carColor;

	@FXML
	private TextField carYear;

	@FXML
	private Text informationTxt;

	@FXML
	private TableColumn<CarObject, Integer> carIDColumn;
	
	@FXML
	private TableColumn<CarObject, String> carNameColumn;
	
	@FXML
	private TableColumn<CarObject, String> carColorColumn;
	
	@FXML
	private TableColumn<CarObject, Integer> carYearColumn;
	
	@FXML
	private TableView<CarObject> carTableView;

	@FXML
	public void myAction() {
		try {
			carText = Integer.parseInt(carYear.getText());
			carTextCorrect = true;
			carDAO.addNewCar(carName.getText(), carColor.getText(), carText);
			informationTxt.setText("successfully added new car");
			informationTxt.setFill(Color.GREEN); 
		} catch (Exception e) {
			informationTxt.setText("YEAR MUST BE A NUMBER");
			informationTxt.setFill(Color.BLACK); 
		}

	}
	
	@FXML
	public void loadCarDetails() throws SQLException {
		carIDColumn.setCellValueFactory(new PropertyValueFactory<CarObject,Integer>("carID"));  
		carNameColumn.setCellValueFactory(new PropertyValueFactory<CarObject,String>("carName"));  
		carColorColumn.setCellValueFactory(new PropertyValueFactory<CarObject,String>("carColor")); 
		carYearColumn.setCellValueFactory(new PropertyValueFactory<CarObject,Integer>("carYear"));
		ResultSet carsQuery = carDAO.returnCars();
		if(carsQuery == null) {
			System.out.println("that sucks");
		}
		else {
			ArrayList<CarObject> co = new ArrayList<CarObject>();
			System.out.print("here");
			while(carsQuery.next()) {
				System.out.println(carsQuery.getInt(1));
				System.out.println(carsQuery.getString(2));
				System.out.println(carsQuery.getString(3));
				System.out.println(carsQuery.getInt(4));
				co.add(new CarObject(carsQuery.getInt(1), carsQuery.getString(2), carsQuery.getString(3), carsQuery.getInt(4)));
			}
			for (CarObject coo : co) {
				carTableView.getItems().add(coo);
			}
		}
		

	}
}
