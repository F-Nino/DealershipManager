package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.CarObject;
import domain.EmployeeObject;

public class EmployeeDAO extends BaseDAO {

	public EmployeeDAO() {
		System.out.println("EmployeeDAO Instantiated");
	}

	public boolean validateUniqueFields(int employeeID, String position, String firstName, String lastName,
			int adminPrivileges) {
		ResultSet employeeQuery = returnEmployees();
		try {
			while (employeeQuery.next()) {
				if (employeeID == employeeQuery.getInt("employeeid") || (firstName.isEmpty()) || (lastName.isEmpty())
						|| (position.isEmpty())) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void addNewEmployee(int employeeID, String title, String password, String firstName, String lastName,
			String position, int salary, int adminPrivileges) {
		boolean addedEmployee = validateUniqueFields(employeeID, position, firstName, lastName, adminPrivileges);
		if (!addedEmployee) {
			throw new NullPointerException("not adding Employee");
		}
		try (Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO employee(employeeid, passWord, title, firstName, lastName, position, salary, adminPriviliges)"
							+ " VALUES ('" + employeeID + "', '" + password + "', '" + title + "', '" + firstName
							+ "', '" + lastName + "', '" + position + "', '" + salary + "', '" + adminPrivileges
							+ "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew employee was added successfully\n");
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	public ResultSet returnEmployees() {
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

	public void deleteItem(EmployeeObject selectedItem) {
		String SQL = "DELETE FROM 'employee' WHERE 'employeeid' = " + selectedItem.getEmployeeID();
		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM employee WHERE employeeid = " + selectedItem.getEmployeeID());
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	public void editEmployee(int employeeID, String password, String title, String firstName, String lastName,
			String position, int salary, int adminPrivileges) {
		boolean editEmployee = true;

		if ((firstName.isEmpty()) || (lastName.isEmpty()) || (position.isEmpty())) {
			editEmployee = false;
		}

		if (editEmployee) {
			try (Connection connection = this.getConnection()) {
				PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employee SET passWord = '"
						+ password + "', title = '" + title + "', firstName = '" + firstName + "', lastName = '"
						+ lastName + "', position = '" + position + "', salary = " + salary + ", adminPriviliges = "
						+ adminPrivileges + " WHERE employeeid = " + employeeID);
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
