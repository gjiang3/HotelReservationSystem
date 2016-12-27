/**
 * Hotel Reservation System
 * CS157A Group Project
 * @author Arjun Nayak, Guohua Jiang, Peter Stadler
 * @version 1.00
 */

public class Room {
	private int roomId;
	private double costPerNight;
	private String roomType;
	
	public Room(int roomId, double costPerNight, String roomType) {
		this.roomId = roomId;
		this.costPerNight = costPerNight;
		this.roomType = roomType;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public double getCostPerNight() {
		return costPerNight;
	}
	
	public String getRoomType() {
		return roomType;
	}
	
	public String toString() {
		return roomType + "\n" + " $" + costPerNight + "/night";
	}
}
