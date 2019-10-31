package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarDAO extends BaseDAO {
	
    public CarDAO(){
        System.out.println("CarDAO Instantiated");
    }


	public void addNewCar(String carName, String carColor, int carYear) {

		try (Connection connection = this.getConnection()) {

			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO car(carName, carColor, carYear)" + " VALUES ('" + carName
							+ "', '" + carColor + "','" + carYear + "' )");
			preparedStatement.execute();
			System.out.print("\nConnected to database!\nNew student was added successfully\n");
			// lblDisplay.setText();
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}
}
