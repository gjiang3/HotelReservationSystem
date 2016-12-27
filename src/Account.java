import java.util.ArrayList;

/**
 * Hotel Reservation System
 * CS157A Group Project
 * @author Arjun Nayak, Guohua Jiang, Peter Stadler
 * @version 1.00
 */

/**
 * A user's account.
 */
public class Account {
	final private String username; // cannot be changed once account is created
	private String firstName;
	private String lastName;
	private String role;
	private int age;
	private String gender;
	private ArrayList<Reservation> reservations;

	public Account(String firstName, String lastName, String username, String role, int age, String gender) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.age = age;
		this.gender = gender;
		reservations = new ArrayList<Reservation>();
	}
	
	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}
	
	public String toString(){
		return username + "  " + firstName + "  " + lastName + "  " + role + "  " + age + "  " + gender;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getRole() {
		return role;
	}
	
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
}
