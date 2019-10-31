package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO extends BaseDAO {
	
	public UserDAO() {
		System.out.println("User DAO instantiated");
		
	}
	
	public void addNewUser(String fName,String lName, String username, String password, String email, String age, String DOB) {
		try(Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(fName, lName, username, password, email, age, DOB)" +
					" VALUES ('" + fName +"', '"+ lName +"','"+ username +"', '"+password +"','"+email+ "',"+age+"',"+DOB+" )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew student was added successfully\n");
	          
		}catch(SQLException e){
			System.out.println(e.getMessage());
			
		}
	}
}
