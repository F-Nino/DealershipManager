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

	public void addNewUser(String fName, String lName, String username, String password, String email, String age,
			String DOB) {
		try (Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO user(fName, lName, username, password, email, age, DOB)"
							+ " VALUES ('" + fName + "', '" + lName + "','" + username + "', '" + password + "','"
							+ email + "','" + age + "','" + DOB + "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew user was added successfully\n");

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	public ResultSet grabUsers() {
		String SQL = "Select * from user";
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
