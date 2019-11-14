package domain;

public class EmployeeObject {

	private int employeeID;
	private String password;
	private String firstName;
	private String lastName;
	private int adminPrivileges;

	public EmployeeObject(int employeeID, String password, String firstName, String lastName, int adminPrivileges) {
		this.employeeID = employeeID;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.adminPrivileges = adminPrivileges;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAdminPrivileges() {
		return adminPrivileges;
	}

	public void setAdminPrivileges(int adminPrivileges) {
		this.adminPrivileges = adminPrivileges;
	}

}