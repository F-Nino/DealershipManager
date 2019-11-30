package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
