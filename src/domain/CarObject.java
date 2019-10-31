package domain;

public class CarObject {

	private int carID;
	private String carName;
	private String carColor;
	private int carYear;

	public CarObject(int carID, String carName, String carColor, int carYear) {
		super();
		this.carID = carID;
		this.carName = carName;
		this.carColor = carColor;
		this.carYear = carYear;
	}

	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		this.carID = carID;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public int getCarYear() {
		return carYear;
	}

	public void setCarYear(int carYear) {
		this.carYear = carYear;
	}

}
