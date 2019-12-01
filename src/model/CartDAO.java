package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.CarObject;
import domain.CustomerObject;

public class CartDAO extends BaseDAO {

	public CartDAO() {
		System.out.println("CartDAO Instantiated");
	}

	public void addNewItemToCart(CarObject car, CustomerObject customer) {
		try (Connection connection = this.getConnection()) {

			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cart(customerid, carid)"
					+ " VALUES ('" + customer.getCustomerID() + "', '" + car.getCarID() + "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew cart item was added successfully\n");
		} catch (SQLException e) {
			throw new NullPointerException("Already in Cart");
		}
	}

	public ResultSet returnCarsForSpecificUser(CustomerObject currentCustomer) {
		String SQL = "Select * from cart where customerid = " + currentCustomer.getCustomerID();
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

	public void removeCarFromCart(int carID, int customerID) {
		String SQL = "DELETE FROM 'cart' WHERE 'carid' = " + carID + " AND WHERE customerid = " + customerID;
		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM cart WHERE carid = " + carID + " AND customerid = " + customerID);
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	public void removeCarsFromCart(ArrayList<CarObject> carObjectArrayList, int customerID) {
		try {
			Connection connection = this.getConnection();
			for (CarObject co : carObjectArrayList) {
				PreparedStatement preparedStatement = connection.prepareStatement(
						"DELETE FROM cart WHERE carid = " + co.getCarID() + " AND customerid = " + customerID);
				preparedStatement.execute();
			}
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

}
