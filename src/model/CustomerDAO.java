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

	public ResultSet returnCustomer() {
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

}
