package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
}
