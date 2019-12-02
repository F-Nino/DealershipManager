package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.CarObject;
import domain.EmployeeObject;

public class CarDAO extends BaseDAO {

	public CarDAO() {
		System.out.println("CarDAO Instantiated");
	}

	public boolean validateUniqueFields(String carBrand, String carName, String carColor, int carYear, int carPrice,
			int carQuantity) {
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

	public void addNewCar(String carBrand, String carName, String carColor, int carYear, int carPrice,
			int carQuantity) {
		System.out.println("here1");
		boolean addCar = validateUniqueFields(carBrand, carName, carColor, carYear, carPrice, carQuantity);
		if (!addCar) {
			throw new NullPointerException("not adding car");
		}
		try (Connection connection = this.getConnection()) {
			carBrand = carBrand.substring(0, 1).toUpperCase() + carBrand.substring(1);
			carName = carName.substring(0, 1).toUpperCase() + carName.substring(1);
			carColor = carColor.substring(0, 1).toUpperCase() + carColor.substring(1);
			
			System.out.println("car brand : " + carBrand);
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO car(carBrand, carName, carColor, carYear, carPrice, carQuantity)"
							+ " VALUES ('" + carBrand + "', '" + carName + "', '" + carColor + "', '" + carYear + "', '"
							+ carPrice + "', '" + carQuantity + "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew car was added successfully\n");
		} catch (SQLException e) {
			System.out.print("add car error message + " + e.getMessage());
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

	public void deleteItem(CarObject selectedItem) {
		String SQL = "DELETE FROM 'car' WHERE 'carid' = " + selectedItem.getCarID();
		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM car WHERE carid = " + selectedItem.getCarID());
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}

	}

	public void editCar(int carID, String carBrand, String carName, String carColor, int carYear, int carPrice,
			int carQuantity) {
		// CarObject co = new CarObject(carID, carBrand, carName, carColor, carYear,
		// carPrice, carQuantity);
		boolean editCar = true;

		if ((carBrand.isEmpty()) || (carName.isEmpty()) || (carColor.isEmpty())) {
			editCar = false;
		}

		if (editCar) {
			try (Connection connection = this.getConnection()) {
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE car SET carBrand = '" + carBrand + "', carName = '" + carName
								+ "', carColor = '" + carColor + "', carYear = " + carYear + ", carPrice = " + carPrice
								+ ", carQuantity = " + carQuantity + " WHERE carid = " + carID);
				preparedStatement.execute();
				System.out.print("\nConnected to database!\n employee was edited successfully\n");
			} catch (SQLException e) {
				System.out.print(e.getMessage());
			}
		} else {
			throw new NullPointerException("not adding Employee");
		}
	}

	public ResultSet returnSpecificCar(int carid) {
		String SQL = "Select * from car where carid = " + carid;
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

	public ResultSet returnCarsFromCart(ResultSet cartQuery) throws SQLException {
		
		String SQL = "Select * from car where carid = ";
		while(cartQuery.next()) {
			SQL += cartQuery.getInt("carid");
			if(!cartQuery.isLast()) {
				SQL += " OR carid = ";
			} 
		}
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

	public void decreaseQuantity(ArrayList<CarObject> carObjectArrayList) {
		try (Connection connection = this.getConnection()) {
			for(CarObject co : carObjectArrayList) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"UPDATE car SET carQuantity = " + (co.getCarQuantity() - 1) + " WHERE carid = " + co.getCarID());
				preparedStatement.execute();
			}
			System.out.print("\nConnected to database!\n employee was edited successfully\n");
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
		
	}

}
