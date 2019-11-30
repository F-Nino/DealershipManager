package domain;

import java.sql.Date;

public class PurchaseHistoryObject {

	private int carID;
	private int customerID;
	private Date datePurchased;

	public PurchaseHistoryObject(int carID, int customerID, Date datePurchased) {
		this.carID = carID;
		this.customerID = customerID;
		this.datePurchased = datePurchased;
	}

	public int getCarID() {
		return carID;
	}

	public void setCarID(int carID) {
		this.carID = carID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public Date getDatePurchased() {
		return datePurchased;
	}

	public void setDatePurchased(Date datePurchased) {
		this.datePurchased = datePurchased;
	}

}
