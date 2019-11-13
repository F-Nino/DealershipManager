package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.CarObject;

public class CarDAO extends BaseDAO {

	public CarDAO() {
		System.out.println("CarDAO Instantiated");
	}

	public boolean validateUniqueFields(String carBrand, String carName, String carColor, int carYear, int carPrice) {
		ResultSet carsQuery = returnCars();
		try {
			while (carsQuery.next()) {
				if (carBrand.equals(carsQuery.getString(2)) && carName.equals(carsQuery.getString(3))
						&& carColor.equals(carsQuery.getString(4)) && carYear == carsQuery.getInt(5)
						|| (carName.isEmpty()) || (carColor.isEmpty()) || (carBrand.isEmpty())) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void addNewCar(String carBrand, String carName, String carColor, int carYear, int carPrice) {
		boolean addCar = validateUniqueFields(carBrand, carName, carColor, carYear, carPrice);
		if (!addCar) {
			throw new NullPointerException("not adding car");
		}
		try (Connection connection = this.getConnection()) {
			carBrand = carBrand.substring(0, 1).toUpperCase() + carBrand.substring(1);
			carName = carName.substring(0, 1).toUpperCase() + carName.substring(1);
			carColor = carColor.substring(0, 1).toUpperCase() + carColor.substring(1);
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO car(carBrand, carName, carColor, carYear, carPrice)" + " VALUES ('" + carBrand + "', '"
							+ carName + "', '" + carColor + "', '" + carYear + "', '" + carPrice + "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew car was added successfully\n");
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
