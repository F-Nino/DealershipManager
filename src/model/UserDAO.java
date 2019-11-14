package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends BaseDAO {
	
	public UserDAO() {
		System.out.println("User DAO instantiated");
		
	}
	
	public void addNewUser(String fName,String lName, String username, String password, String email, String age, String DOB) {
		try(Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(fName, lName, username, password, email, age, DOB)" +
					" VALUES ('" + fName +"', '"+ lName +"','"+ username +"', '"+password +"','"+email+ "','"+age+"','"+DOB+"' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew user was added successfully\n");
	          
		}catch(SQLException e){
			System.out.println(e.getMessage());
			
		}
	}
	
	public static void grabUsers() {
		final String DB_URL = "jdbc:mysql://localhost:3306/CarDealership?serverTimezone=UTC";
		final String SELECT_QUERY = "SELECT * FROM user";
		// Use try-catch resources to connect to and query the database
		try {
			Connection conn = DriverManager.getConnection(DB_URL, "root", "Au_150513");

			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery((SELECT_QUERY));

			// Retrieve ResultSet's meta data
			ResultSetMetaData mData = rset.getMetaData();
			int numberOfColumns = mData.getColumnCount();
			System.out.printf("A table of Users from User DBase:%n%n");

			// Display the names of the columns in the ResultSet
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.printf("%-8s\t", mData.getColumnName(i));
			}
			System.out.println();
			// Display the query results
			while (rset.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					System.out.printf("%-8s\t", rset.getObject(i));
				}
				System.out.println();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
