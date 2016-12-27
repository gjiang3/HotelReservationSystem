import java.util.Date;

/**
 * Hotel Reservation System
 * CS157A Group Project
 * @author Arjun Nayak, Guohua Jiang, Peter Stadler
 * @version 1.00
 */

/**
 * A user's Complaint.
 */
public class RoomService {
	private final int taskId;
	private final String task;
	private final String username;

	public RoomService(int taskId, String task, String username) {
		this.taskId = taskId;
		this.task = task;
		this.username = username;
		
	}
	public int getTaskId() {
		return taskId;
	}
	
	public String getTask() {
		return task;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return "Task ID: " + taskId + " \nCustomer ID: " + username + " \nTask Description: " + task; 
	}
}
