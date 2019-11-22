package domain;

public class EmployeeObject {

	private int employeeID;
	private String title;
	private String password;
	private String firstName;
	private String lastName;
	private String position;
	private int salary;
	private int adminPrivileges;

	public EmployeeObject(int employeeID, String password, String title, String firstName, String lastName,
			String position, int salary, int adminPrivileges) {
		this.employeeID = employeeID;
		this.password = password;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.salary = salary;
		this.adminPrivileges = adminPrivileges;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAdminPrivileges() {
		return adminPrivileges;
	}

	public void setAdminPrivileges(int adminPrivileges) {
		this.adminPrivileges = adminPrivileges;
	}

}