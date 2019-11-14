package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.CarObject;

public class EmployeeDAO extends BaseDAO {

	public EmployeeDAO() {
		System.out.println("EmployeeDAO Instantiated");
	}

	public boolean validateUniqueFields(int employeeID, String firstName, String lastName, int adminPrivileges) {
		ResultSet employeeQuery = returnCustomers();
		try {
			while (employeeQuery.next()) {
				if (employeeID == employeeQuery.getInt("employeeid") || (firstName.isEmpty()) || (lastName.isEmpty())) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void addNewEmployee(int employeeID, String password, String firstName, String lastName,
			int adminPrivileges) {
		boolean addedEmployee = validateUniqueFields(employeeID, firstName, lastName, adminPrivileges);
		if (!addedEmployee) {
			throw new NullPointerException("not adding Employee");
		}
		try (Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO employee(employeeid, passWord, firstName, lastName, adminPriviliges)"
							+ " VALUES ('" + employeeID + "', '" + password + "', '" + firstName + "', '" + lastName
							+ "', '" + adminPrivileges + "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew employee was added successfully\n");
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	public ResultSet returnCustomers() {
		String SQL = "Select * from employee";
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