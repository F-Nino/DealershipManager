package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.CarObject;
import domain.EmployeeObject;

public class CustomerDAO extends BaseDAO {

	public CustomerDAO() {
		System.out.println("CarDAO Instantiated");
	}

	public ResultSet returnSpecificCustomer(int customerid) {
		String SQL = "Select * from customer where customerid = " + customerid;
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
	
	
	public ResultSet returnCars() {
		String SQL = "Select * from customer";
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

}
