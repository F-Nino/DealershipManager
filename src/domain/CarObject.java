package domain;

public class CarObject {

	private int carID;
	private String carBrand;
	private String carName;
	private String carColor;
	private int carYear;
	private int carPrice;

	public CarObject(int carID, String carBrand, String carName, String carColor, int carYear, int carPrice) {
		super();
		this.carID = carID;
		this.carBrand = carBrand;
		this.carName = carName;
		this.carColor = carColor;
		this.carYear = carYear;
		this.carPrice = carPrice;
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

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public int getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(int carPrice) {
		this.carPrice = carPrice;
	}
	
	

}
