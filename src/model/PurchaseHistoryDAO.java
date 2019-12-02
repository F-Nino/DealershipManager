package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import domain.CarObject;
import domain.CustomerObject;

public class PurchaseHistoryDAO extends BaseDAO {

	public PurchaseHistoryDAO() {
		System.out.println("EmployeeDAO Instantiated");
	}

	public ResultSet returnHistory() {
		String SQL = "Select * from history";
		try {
			Connection connection = this.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery((SQL));
			return rset;
		} catch (SQLException e) {
			System.out.println("returned null");
			// TODO Auto-generated catch block
			return null;
		}
	}

	public void addNewPurchase(int[] carIDs, int customerID) {
		Calendar calendar = Calendar.getInstance();
		java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());
		try (Connection connection = this.getConnection()) {

			String query = " insert into history (carid, customerid, datePurchased)" + " values (?, ?, ?)";

			PreparedStatement preparedStmt = connection.prepareStatement(query);
			for(int i = 0; i < carIDs.length; i++) {
				preparedStmt.setInt(1, carIDs[i]);
				preparedStmt.setInt(2, customerID);
				preparedStmt.setDate(3, currentDate);
				preparedStmt.execute();
			}
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}

	}
}
