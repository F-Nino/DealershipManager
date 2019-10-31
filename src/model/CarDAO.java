package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CarDAO extends BaseDAO {

	public CarDAO() {
		System.out.println("CarDAO Instantiated");
	}

	public void addNewCar(String carName, String carColor, int carYear) {

		try (Connection connection = this.getConnection()) {

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO car(carName, carColor, carYear)" + " VALUES ('" + carName + "', '"
							+ carColor + "','" + carYear + "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew student was added successfully\n");
			// lblDisplay.setText();
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	public ResultSet returnCars() {
		String SQL = "Select * from car";
		try {
			Connection connection = this.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rset = stmt.executeQuery((SQL));
			return rset;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
