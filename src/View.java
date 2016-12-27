import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;

/**
 * Hotel Reservation System
 * CS157A Group Project
 * @author Arjun Nayak, Guohua Jiang, Peter Stadler
 * @version 1.00
 */

/**
 * The view manager. Contains the frame and all different panels.
 */
public class View {
	private Model model;
	private JPanel cards;
	private CardLayout cardLayout;
	private JFrame frame;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	final View view = this;
	private JList allUsers;
	
	private JTextField firstName1;
	private JTextField lastName1;
	private JPasswordField password1;
	private JPasswordField password12;
	private JComboBox ageComboBox1;
	private JComboBox genderComboBox1;
	private JTextField securityQuestion1;
	private JTextField securityAnswer1;
	private List<String> ageArr ;
	private List<String> genderArr;

	/**
	 * Constructs the frame and adds panels to CardLayout.
	 */
	public View(Model model) {
		this.model = model;
		frame = new JFrame("Hotel Reservation System");
		cards = new JPanel(cardLayout = new CardLayout());

		// add panels to the card layout
		cards.add(getLoginPanel(), "Login");
		cards.add(getRegisterPanel(), "Register");
		cards.add(getForgotPasswordPanel(), "Forgot Password");
		cards.add(getWelcomePanel("Customer"), "Customer");
		cards.add(getWelcomePanel("Manager"), "Manager");
		cards.add(getWelcomePanel("Room Attendant"), "Room Attendant");

		// customer panels
		cards.add(getMakeReservationPanel(), "Book");
		cards.add(getReceiptPanel(), "Receipt");
		cards.add(getCustViewCancelPanel(), "View/Cancel");
		cards.add(getOrderRoomServicePanel(), "Order");
		cards.add(getFileComplaintPanel(), "File Complaint");

		// employee panels
		cards.add(getReservationsPanel(), "Reservations");
		cards.add(getRoomServicePanel(), "Tasks");
		cards.add(getStatisticsPanel(), "Statistics");
		cards.add(getArchivePanel(), "Archive");
		cards.add(getUsersPanel(), "Users");
		cards.add(getAddEmployeePanel(), "AddEmployee");
		cards.add(getSettingPanel(), "Setting");

		cards.add(getComplaintsPanel(), "Complaints");		
		cards.add(getViewRoomServicePanel(), "View Room Service");

		frame.add(cards); // add the panel with card layout to the frame

		// below are the frame's characteristics
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Gets the model of the view manager.
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Switches the current panel.
	 * @param panelName
	 */
	public void switchPanel(String panelName) {
		if (panelName.equals("Login") )
			frame.setSize(400, 350);
		else if(panelName.equals("Forgot Password"))
			frame.setSize(400, 200);
		else 
			frame.setSize(600, 500);
		frame.setLocationRelativeTo(null);
		cardLayout.show(cards, panelName);
	}
	
	private JPanel getSettingPanel(){
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.weighty = 1;
		c.gridwidth = 4;
		final JLabel instructions = panel.addLabel("Account Setting", 24, "center", Color.white, Color.black, 0, 0);
		
		c.weightx = 0;
		c.insets = new Insets(5,25,5,25);
		c.gridwidth = 1;
		
		
		
		panel.addLabel("First name:", 12, "left", null, null, 0, 1);
		panel.addLabel("Last name:", 12, "left", null, null, 0, 2);
		panel.addLabel("Password:", 12, "left", null, null, 0, 3);
		panel.addLabel("Confirm Password:", 12, "left", null, null, 0, 4);
		panel.addLabel("Age:", 12, "left", null, null, 0, 5);
		panel.addLabel("Gender:", 12, "left", null, null, 0, 6);
		panel.addLabel("Security Question:", 12, "left", null, null, 0, 7);
		panel.addLabel("Security Answer:", 12, "left", null, null, 0, 8);
		JButton goback = new JButton("Back");
		panel.addComponent(goback, 0, 9);

		panel.setFont(new Font("Tahoma", Font.BOLD, 16));
		goback.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel(model.getCurrentRole());
				
			}
			
		});
		c.weightx = 1;
		c.gridwidth = 3;
		firstName1 = new JTextField();
		panel.addComponent(firstName1, 1, 1);
		
		lastName1 = new JTextField();
		panel.addComponent(lastName1, 1, 2);
		
		password1 = new JPasswordField();
		panel.addComponent(password1, 1, 3);
		
		password12 = new JPasswordField();
		panel.addComponent(password12, 1, 4);
		
		
		ageArr = new ArrayList<String>();
		ageArr.add("Select Age");
		for (int i = 18; i < 100; ++i)
			ageArr.add(String.valueOf(i));
//		@SuppressWarnings({ "rawtypes", "unchecked" })
		ageComboBox1 = new JComboBox(ageArr.toArray());
		panel.addComponent(ageComboBox1, 1, 5);
		
		genderArr = new ArrayList<String>();
		genderArr.add("Select Gender");
		genderArr.add("F");
		genderArr.add("M");
		genderComboBox1 = new JComboBox(genderArr.toArray());
		panel.addComponent(genderComboBox1, 1, 6);
		
		securityQuestion1 = new JTextField();
		panel.addComponent(securityQuestion1, 1, 7);

		securityAnswer1 = new JTextField();
		panel.addComponent(securityAnswer1, 1, 8);
		
		JButton registerBtn = new JButton("Change");
		registerBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String errors = "<html>";

				String first = firstName1.getText();
				if (first.isEmpty()) {
					firstName1.setText("");
					validEntry = false;
					errors += "First name cannot be empty<br>";
				}else if(first.length() > 15){
					firstName1.setText("");
					validEntry = false;
					errors += "First name cannot exceed 15 characters<br>";
				}

				String last = lastName1.getText();
				if (last.isEmpty()) {
					lastName1.setText("");
					validEntry = false;
					errors += "Last name cannot be empty<br>";
				}else if(last.length() > 15){
					lastName1.setText("");
					validEntry = false;
					errors += "Last name cannot exceed 15 characters<br>";
				}


				String pass = new String(password1.getPassword()); 
				String pass2 = new String(password12.getPassword());
				if (pass.length() > 20 ) {
					password1.setText("");
					password12.setText("");
					validEntry = false;
					errors += "Password cannot exceed 20 characters<br>";
				}else if(pass.isEmpty()){
					password1.setText("");
					password12.setText("");
					validEntry = false;
					errors += "Password cannot be empty<br>";
				}else if(!pass.equals(pass2)){
					password1.setText("");
					password12.setText("");
					validEntry = false;
					errors += "Passwords do not match<br>";
				}

				Integer age = null;
				try {
					age = Integer.parseInt((String)ageComboBox1.getSelectedItem());
				}
				catch (Exception e) {
					validEntry = false;
					errors += "Age must be selected<br>";
				}

				String gen = (String)genderComboBox1.getSelectedItem();
				int genIndex = 0;
				if (gen.equals("F")){
					genIndex = 1;
					gen = "F";
				}
					
				else if (gen.equals("M")){
					gen = "M";
					genIndex = 2;
				}
				else {
					validEntry = false;
					errors += "Gender must be selected<br>";
				}

				String secQuestion = securityQuestion1.getText();
				if (secQuestion.length() > 50 ) {
					securityQuestion1.setText("");
					validEntry = false;
					errors += "Security Question cannot exceed 50 characters<br>";
				}else if(secQuestion.isEmpty()){
					securityQuestion1.setText("");
					validEntry = false;
					errors += "Security Question cannot be empty<br>";
				}

				String secAnswer = securityAnswer1.getText();
				if (secAnswer.length() > 30 ) {
					securityAnswer1.setText("");
					validEntry = false;
					errors += "Security Answer cannot exceed 30 characters<br>";
				}else if(secAnswer.isEmpty()){
					securityAnswer1.setText("");
					validEntry = false;
					errors += "Security Answer cannot be empty<br>";
				}

				if (validEntry) {
					panel.clearComponents();
					if (model.changeAccountInfo(pass, first, last, age, gen, secQuestion, secAnswer)){
						User user = model.getAccountInfo(model.getCurrentUser().getUsername());
						firstName1.setText(user.getFirstName());						
						lastName1.setText(user.getLastName());
						password1.setText(user.getPassword());
						password12.setText(user.getPassword());
						ageComboBox1.setSelectedItem(ageArr.get(user.getAge() - 17));
						genderComboBox1.setSelectedItem(genderArr.get(genIndex));
						securityQuestion1.setText(user.getQuestion());
						securityAnswer1.setText(user.getAnswer());
						
						view.switchPanel(model.getCurrentRole());
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), errors + "</html>", "Registration failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(registerBtn, 1, 9);
		
		
		return panel;
	}
	
	
	private JPanel getAddEmployeePanel(){
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.weighty = 1;
		c.gridwidth = 4;
		final JLabel instructions = panel.addLabel("Add Employee", 24, "center", Color.white, Color.black, 0, 0);
		
		c.weightx = 0;
		c.insets = new Insets(5,25,5,25);
		c.gridwidth = 1;
		panel.addLabel("Account type:", 12, "left", null, null, 0, 1);
		panel.addLabel("First name:", 12, "left", null, null, 0, 2);
		panel.addLabel("Last name:", 12, "left", null, null, 0, 3);
		panel.addLabel("Username:", 12, "left", null, null, 0, 4);
		panel.addLabel("Password:", 12, "left", null, null, 0, 5);
		panel.addLabel("Confirm Password:", 12, "left", null, null, 0, 6);
		panel.addLabel("Age:", 12, "left", null, null, 0, 7);
		panel.addLabel("Gender:", 12, "left", null, null, 0, 8);
		panel.addLabel("Security Question:", 12, "left", null, null, 0, 9);
		panel.addLabel("Security Answer:", 12, "left", null, null, 0, 10);
		panel.addNavigationButton("Back", 16, "Users", 0, 11);
		
		c.weightx = 1;
		c.gridwidth = 3;
		
		List<String> accountType = new ArrayList<String>();
		accountType.add("Select Role");
		accountType.add("Manager");
		accountType.add("Room Attendant");
		final JComboBox accountTypeComboBox = new JComboBox(accountType.toArray());
		panel.addComponent(accountTypeComboBox, 1, 1);
		
		final JTextField firstName = new JTextField();
		panel.addComponent(firstName, 1, 2);

		final JTextField lastName = new JTextField();
		panel.addComponent(lastName, 1, 3);

		final JTextField username = new JTextField();
		panel.addComponent(username, 1, 4);

		final JPasswordField password = new JPasswordField();
		panel.addComponent(password, 1, 5);
		
		final JPasswordField password2 = new JPasswordField();
		panel.addComponent(password2, 1, 6);

		List<String> age = new ArrayList<String>();
		age.add("Select Age");
		for (int i = 18; i < 100; ++i)
			age.add(String.valueOf(i));
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox ageComboBox = new JComboBox(age.toArray());
		panel.addComponent(ageComboBox, 1, 7);

		List<String> gender = new ArrayList<String>();
		gender.add("Select Gender");
		gender.add("Female");
		gender.add("Male");
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox genderComboBox = new JComboBox(gender.toArray());
		panel.addComponent(genderComboBox, 1, 8);

		final JTextField securityQuestion = new JTextField();
		panel.addComponent(securityQuestion, 1, 9);

		final JTextField securityAnswer = new JTextField();
		panel.addComponent(securityAnswer, 1, 10);

		JButton registerBtn = new JButton("Add");
		registerBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String errors = "<html>";
				
				String type = (String)accountTypeComboBox.getSelectedItem();
				if (type.equals("Manager"))
					type = "Manager";
				else if (type.equals("Room Attendant"))
					type = "Room Attendant";
				else {
					validEntry = false;
					errors += "Account type must be selected<br>";
				}
				

				String first = firstName.getText();
				if (first.isEmpty()) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot be empty<br>";
				}else if(first.length() > 15){
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot exceed 15 characters<br>";
				}

				String last = lastName.getText();
				if (last.isEmpty()) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot be empty<br>";
				}else if(last.length() > 15){
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot exceed 15 characters<br>";
				}

				String login = username.getText();
				if(login.isEmpty()){
					username.setText("");
					validEntry = false;
					errors += "Username cannot be empty<br>";
				}else if (login.length() > 12 ) {
					username.setText("");
					validEntry = false;
					errors += "Username cannot exceed 12 characters<br>";
				}else if(model.checkUserExistence(login)){
					username.setText("");
					validEntry = false;
					errors += "Username has been taken<br>";
				}

				String pass = new String(password.getPassword()); 
				String pass2 = new String(password2.getPassword());
				if (pass.length() > 20 ) {
					password.setText("");
					password2.setText("");
					validEntry = false;
					errors += "Password cannot exceed 20 characters<br>";
				}else if(pass.isEmpty()){
					password.setText("");
					password2.setText("");
					validEntry = false;
					errors += "Password cannot be empty<br>";
				}else if(!pass.equals(pass2)){
					password.setText("");
					password2.setText("");
					validEntry = false;
					errors += "Passwords do not match<br>";
				}

				Integer age = null;
				try {
					age = Integer.parseInt((String)ageComboBox.getSelectedItem());
				}
				catch (Exception e) {
					validEntry = false;
					errors += "Age must be selected<br>";
				}

				String gen = (String)genderComboBox.getSelectedItem();
				if (gen.equals("Female"))
					gen = "F";
				else if (gen.equals("Male"))
					gen = "M";
				else {
					validEntry = false;
					errors += "Gender must be selected<br>";
				}

				String secQuestion = securityQuestion.getText();
				if (secQuestion.length() > 50 ) {
					securityQuestion.setText("");
					validEntry = false;
					errors += "Security Question cannot exceed 50 characters<br>";
				}else if(secQuestion.isEmpty()){
					securityQuestion.setText("");
					validEntry = false;
					errors += "Security Question cannot be empty<br>";
				}

				String secAnswer = securityAnswer.getText();
				if (secAnswer.length() > 30 ) {
					securityAnswer.setText("");
					validEntry = false;
					errors += "Security Answer cannot exceed 30 characters<br>";
				}else if(secAnswer.isEmpty()){
					securityAnswer.setText("");
					validEntry = false;
					errors += "Security Answer cannot be empty<br>";
				}

				if (validEntry) {
					panel.clearComponents();
					if (model.addAccount(login, pass, first, last, age, gen, type, secQuestion, secAnswer)){
						ArrayList<Account> users = model.getAllUsers();
						allUsers.setListData(users.toArray());
						view.switchPanel("Users");
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), errors + "</html>", "Registration failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(registerBtn, 1, 11);
		
		
		return panel;
	}

	private JPanel getArchivePanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.gridwidth = 2;
		c.ipady = 30;
		panel.addLabel("Archive", 24, "center", Color.white, Color.black, 0, 0);

		c.ipady = 0;

		c.insets = new Insets(10,10,10,10);
		panel.addLabel("<html>Enter a date. Reservations, room service requests, and "
				+ "complaints will be archived from this date.</html>", 12, "left", null, null, 0, 1);

		c.gridwidth = 1;
		c.weighty = 1;
		panel.addLabel("Date to archive from (MM/DD/YYYY):", 12, "left", null, null, 0, 2);
		c.gridwidth = 1;
		final JTextField date = new JTextField();
		panel.addComponent(date, 1, 2);

		c.gridwidth = 2;
		JButton archiveBtn = new JButton("Archive");
		archiveBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		archiveBtn.addActionListener(new ActionListener() {
			@Override() 
			public void actionPerformed(ActionEvent e) {
				String archiveDate = date.getText();
				GregorianCalendar cal;
				if (archiveDate.length() == 10) {
					cal = isValidDateFormat(archiveDate);
					if (cal != null) {
						int response = JOptionPane.showConfirmDialog(
								new JFrame(), "Are you sure you want to archive?",
								"Confirmation", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.NO_OPTION) ;
						if (response == JOptionPane.YES_OPTION) {
							if(!model.archive(cal.getTime())) 
								JOptionPane.showMessageDialog(new JFrame(), 
										"An unexpected error has occurred. Please contact your system admin.", "Error", 
										JOptionPane.ERROR_MESSAGE);	
							else {
								JOptionPane.showMessageDialog(new JFrame(), "Archive Successful", "Result", JOptionPane.DEFAULT_OPTION);
								panel.clearComponents();
								switchPanel("Manager");
							}
						}
					}
					else 
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: Invalid format.", "Error",
								JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.addComponent(archiveBtn, 0, 3);

		panel.addNavigationButton("Back to main menu", 12, "Manager", 0, 4);
		return panel;
	}

	private JPanel getLoginPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.weighty = 1;
		c.gridwidth = 2;
		c.ipady = 15;
		panel.addLabel("Welcome", 36, "center", Color.white, Color.black, 0, 0);

		c.insets = new Insets(10,15,5,15);
		c.weightx = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		panel.addLabel("Username:", 20, "center", null, null, 0, 2);
		final JTextField usernameField = new JTextField();
		usernameField.setMargin(new Insets(5,5,5,5));
		panel.addComponent(usernameField, 1, 2);

		c.insets = new Insets(5,15,5,15);

		panel.addLabel("Password:", 20, "center", null, null, 0, 3);
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setMargin(new Insets(5,5,5,5));
		panel.addComponent(passwordField, 1, 3);

		c.gridwidth = 2;
		JButton loginBtn = new JButton("Login");
		loginBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());

				if ( username.length() > 12) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: Entered user username is invalid.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (!model.checkUserExistence(username)) {	
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Username does not exist in the system.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (!model.checkUserPassword(username, password)) {
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Password is incorrect.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					panel.clearComponents();
					model.setCurrentUser(username);
					view.switchPanel(model.getCurrentRole());
				}
			}
		});
		panel.addComponent(loginBtn, 0, 4);

		c.gridwidth = 1;
		panel.addNavigationButton("Register", 14, "Register", 0, 5);
		panel.addNavigationButton("Forgot Password?", 14, "Forgot Password", 1, 5);
		

		return panel;
	}

	private JPanel getRegisterPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.weighty = 1;
		c.gridwidth = 4;
		final JLabel instructions = panel.addLabel("Registration", 24, "center", Color.white, Color.black, 0, 0);
		
		c.weightx = 0;
		c.insets = new Insets(5,25,5,25);
		c.gridwidth = 1;
		panel.addLabel("First name:", 12, "left", null, null, 0, 1);
		panel.addLabel("Last name:", 12, "left", null, null, 0, 2);
		panel.addLabel("Username:", 12, "left", null, null, 0, 3);
		panel.addLabel("Password:", 12, "left", null, null, 0, 4);
		panel.addLabel("Confirm Password:", 12, "left", null, null, 0, 5);
		panel.addLabel("Age:", 12, "left", null, null, 0, 6);
		panel.addLabel("Gender:", 12, "left", null, null, 0, 7);
		panel.addLabel("Security Question:", 12, "left", null, null, 0, 8);
		panel.addLabel("Security Answer:", 12, "left", null, null, 0, 9);
		panel.addNavigationButton("Back", 16, "Login", 0, 10);
		
		c.weightx = 1;
		c.gridwidth = 3;
		final JTextField firstName = new JTextField();
		panel.addComponent(firstName, 1, 1);

		final JTextField lastName = new JTextField();
		panel.addComponent(lastName, 1, 2);

		final JTextField username = new JTextField();
		panel.addComponent(username, 1, 3);

		final JPasswordField password = new JPasswordField();
		panel.addComponent(password, 1, 4);
		
		final JPasswordField password2 = new JPasswordField();
		panel.addComponent(password2, 1, 5);

		List<String> age = new ArrayList<String>();
		age.add("Select Age");
		for (int i = 18; i < 100; ++i)
			age.add(String.valueOf(i));
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox ageComboBox = new JComboBox(age.toArray());
		panel.addComponent(ageComboBox, 1, 6);

		List<String> gender = new ArrayList<String>();
		gender.add("Select Gender");
		gender.add("Female");
		gender.add("Male");
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox genderComboBox = new JComboBox(gender.toArray());
		panel.addComponent(genderComboBox, 1, 7);

		final JTextField securityQuestion = new JTextField();
		panel.addComponent(securityQuestion, 1, 8);

		final JTextField securityAnswer = new JTextField();
		panel.addComponent(securityAnswer, 1, 9);

		JButton registerBtn = new JButton("Register");
		registerBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String errors = "<html>";

				String first = firstName.getText();
				if (first.isEmpty()) {
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot be empty<br>";
				}else if(first.length() > 15){
					firstName.setText("");
					validEntry = false;
					errors += "First name cannot exceed 15 characters<br>";
				}

				String last = lastName.getText();
				if (last.isEmpty()) {
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot be empty<br>";
				}else if(last.length() > 15){
					lastName.setText("");
					validEntry = false;
					errors += "Last name cannot exceed 15 characters<br>";
				}

				String login = username.getText();
				if(login.isEmpty()){
					username.setText("");
					validEntry = false;
					errors += "Username cannot be empty<br>";
				}else if (login.length() > 12 ) {
					username.setText("");
					validEntry = false;
					errors += "Username cannot exceed 12 characters<br>";
				}else if(model.checkUserExistence(login)){
					username.setText("");
					validEntry = false;
					errors += "Username has been taken<br>";
				}

				String pass = new String(password.getPassword()); 
				String pass2 = new String(password2.getPassword());
				if (pass.length() > 20 ) {
					password.setText("");
					password2.setText("");
					validEntry = false;
					errors += "Password cannot exceed 20 characters<br>";
				}else if(pass.isEmpty()){
					password.setText("");
					password2.setText("");
					validEntry = false;
					errors += "Password cannot be empty<br>";
				}else if(!pass.equals(pass2)){
					password.setText("");
					password2.setText("");
					validEntry = false;
					errors += "Passwords do not match<br>";
				}

				Integer age = null;
				try {
					age = Integer.parseInt((String)ageComboBox.getSelectedItem());
				}
				catch (Exception e) {
					validEntry = false;
					errors += "Age must be selected<br>";
				}

				String gen = (String)genderComboBox.getSelectedItem();
				if (gen.equals("Female"))
					gen = "F";
				else if (gen.equals("Male"))
					gen = "M";
				else {
					validEntry = false;
					errors += "Gender must be selected<br>";
				}

				String secQuestion = securityQuestion.getText();
				if (secQuestion.length() > 50 ) {
					securityQuestion.setText("");
					validEntry = false;
					errors += "Security Question cannot exceed 50 characters<br>";
				}else if(secQuestion.isEmpty()){
					securityQuestion.setText("");
					validEntry = false;
					errors += "Security Question cannot be empty<br>";
				}

				String secAnswer = securityAnswer.getText();
				if (secAnswer.length() > 30 ) {
					securityAnswer.setText("");
					validEntry = false;
					errors += "Security Answer cannot exceed 30 characters<br>";
				}else if(secAnswer.isEmpty()){
					securityAnswer.setText("");
					validEntry = false;
					errors += "Security Answer cannot be empty<br>";
				}

				if (validEntry) {
					panel.clearComponents();
					if (model.addAccount(login, pass, first, last, age, gen, "Customer", secQuestion, secAnswer)){
						model.setCurrentUser(login);
						view.switchPanel(model.getCurrentRole());
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), errors + "</html>", "Registration failure", JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(registerBtn, 1, 10);

		return panel;
	}

	private JPanel getForgotPasswordPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.gridwidth = 2;
		panel.addLabel("Retrieve Password", 24, "center", Color.white, Color.black, 0, 0);

		c.insets = new Insets(20, 20, 20, 20);
		c.ipady = 25;

		c.weighty = 1;
		c.gridwidth = 1;
		panel.addLabel("Enter your username:", 16, "left", null, null, 0, 2);

		final JTextField userField = new JTextField();
		panel.addComponent(userField, 1, 2);


		panel.addNavigationButton("Back", 16, "Login", 1, 5);

		JButton submitButton = new JButton("Enter");
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isValid = true;
				String error = "";
				String user_name = userField.getText();
				if(user_name.isEmpty()){
					userField.setText("");
					isValid = false;
					error = "Username cannont be empty.";
				}else if(user_name.length() > 12){
					userField.setText("");
					isValid = false;
					error = "Username cannont exceed 12 characters."; 
				}else if(!model.checkUserExistence(user_name)){
					userField.setText("");
					isValid = false;
					error = "Username does not exists in the system."; 
				}
				
				if(isValid){
					String securityQuestion = model.getSecurityQuestion(user_name);
					String answer = JOptionPane.showInputDialog(new JFrame(), securityQuestion);
					if(answer != null){
						if(answer.isEmpty()){
							JOptionPane.showMessageDialog(new JFrame(), "Anwser cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
						}else if(answer.length() > 30){
							JOptionPane.showMessageDialog(new JFrame(), "Answer cannont exceed 30 characters.", "Error" , JOptionPane.ERROR_MESSAGE);
						}else if(model.checkUserSecurityAnwser(user_name, answer)){
							JOptionPane.showMessageDialog(new JFrame(), "Your password is " + model.getPassword(user_name));
						}else{
							JOptionPane.showMessageDialog(new JFrame(), "Incorrect answer.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}else{
					JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
				
		});
		panel.addComponent(submitButton, 0, 5);

		return panel;
	}

	private JPanel getWelcomePanel(String role) {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.weighty = 1;
		c.insets = new Insets(10,10,10,10);

		final JLabel profile = new JLabel();
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event)
			{
				if (model.getCurrentUser() != null) {
					Account user = model.getCurrentUser();
					profile.setText("<html>Username: " + user.getUsername() 
					+ "<br>Name: " + user.getFirstName() + " " + user.getLastName()
					+ "<br>Role: " + user.getRole());
				}
			}
		});
		panel.addComponent(profile, 0, 0);

		panel.addSignOutButton(16, "Login", 1, 0);

		c.gridwidth = 2;
		JButton setting = new JButton("Setting");
		setting.setFont(new Font("Tahoma", Font.BOLD, 16));
		setting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				User user = model.getAccountInfo(model.getCurrentUser().getUsername());
				firstName1.setText(user.getFirstName());
				lastName1.setText(user.getLastName());
				password1.setText(user.getPassword());
				password12.setText(user.getPassword());
				ageComboBox1.setSelectedItem(ageArr.get(user.getAge()-17));
				int index = (user.getGender() == "F") ? 1 : 2;
				genderComboBox1.setSelectedItem(genderArr.get(index));
				securityQuestion1.setText(user.getQuestion());
				securityAnswer1.setText(user.getAnswer());
				switchPanel("Setting");
				
			}
		});
		
		
		if (role.equalsIgnoreCase("manager")) {
			panel.addNavigationButton("Reservations", 16, "Reservations", 0, 1);
			panel.addNavigationButton("Complaints", 16, "Complaints", 0, 2); 
			panel.addNavigationButton("Manage System User", 16, "Users", 0, 3);
			panel.addNavigationButton("Statistics", 16, "Statistics", 0, 4);
			panel.addNavigationButton("Archive Database", 16, "Archive", 0, 5);
			panel.addComponent(setting, 0, 6);
		}
		else if (role.equalsIgnoreCase("customer")) {
			panel.addNavigationButton("Book a reservation", 16, "Book", 0, 1);
			panel.addNavigationButton("View/Cancel Reservations", 16, "View/Cancel", 0, 2);
			panel.addNavigationButton("Room Service Request", 16, "Order", 0, 3);
			panel.addNavigationButton("File Complaint", 16, "File Complaint", 0, 4);
			JButton rating = new JButton("Rating");
			rating.setFont(new Font("Tahoma", Font.BOLD, 16));
			rating.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
//					System.out.println("Hello");
					String[] rate = {"Select a rating", "1", "2", "3", "4" , "5"};
					JComboBox ratingSelector = new JComboBox(rate);
					int result = JOptionPane.showConfirmDialog(null, ratingSelector, "Rating of our overall servce", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						String r = (String)ratingSelector.getSelectedItem();
						if(r.equals("Select a rating")){
							JOptionPane.showMessageDialog(new JFrame(), "Please select a rating.", "Error", JOptionPane.ERROR_MESSAGE);
						}else{
							int rt = Integer.parseInt(r);
							String username = model.getCurrentUser().getUsername();
						
							model.updateRating(username, rt);
							
							
						}
	                }
					
				}
				
			});
			panel.addComponent(rating, 0, 5);
			panel.addComponent(setting, 0, 6);
		}
		else if (role.equalsIgnoreCase("room attendant")) {
			panel.addNavigationButton("Handel room request", 16, "Tasks", 0, 1);
			panel.addComponent(setting, 0, 2);
		}
		
		
		

		return panel;
	}

	private JPanel getMakeReservationPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.gridwidth = 2;
		c.ipady = 30;
		panel.addLabel("Reserve a Room", 24, "center", Color.white, Color.black, 0, 0);

		c.insets = new Insets(10, 10, 10, 10);
		c.gridwidth = 1;
		c.ipady = 0;
		panel.addLabel("Check-in (MM/DD/YYYY):", 12, "left", null, null, 0, 1);
		panel.addLabel("Check-out (MM/DD/YYYY):", 12, "left", null, null, 1, 1);

		c.gridwidth = 1;
		final JTextField checkIn = new JTextField();
		panel.addComponent(checkIn, 0, 2);

		final JTextField checkOut = new JTextField();
		panel.addComponent(checkOut, 1, 2);

		c.gridwidth = 2;
		c.weighty = 1;
		final JList list = new JList();
		list.setCellRenderer(new MyListCellThing());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(12);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 4);

		c.weighty = 0;

		JButton searchBtn = new JButton("Search for rooms");
		searchBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		searchBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override() 
			public void actionPerformed(ActionEvent e) {
				list.setListData(new String[0]);
				String in = checkIn.getText();
				String out = checkOut.getText();
				GregorianCalendar inCal;
				GregorianCalendar outCal;
				if (in.length() == 10 && out.length() == 10) {
					inCal = isValidDateFormat(in);
					outCal = isValidDateFormat(out);
					if (inCal != null && outCal != null) {
						if (inCal.before(Model.TODAY) || outCal.before(Model.TODAY))
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Check-in and check-out dates must prior to today.", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (outCal.before(inCal))
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Check-in date must be before check-out date.", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (inCal.equals(outCal))
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Check-in and check-out cannot be the same day.", "Error",
									JOptionPane.ERROR_MESSAGE);
						else {
							if (model.getAvailRooms(in, out) != null)
								list.setListData(model.getAvailRooms(in, out).toArray());
						}
					}
					else
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: Invalid formats.", "Error",
								JOptionPane.ERROR_MESSAGE);
				} else
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: Invalid date format.", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(searchBtn, 0, 3);

		c.gridwidth = 2;
		JButton confirmBtn = new JButton("Book");
		confirmBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Room room = (Room)list.getSelectedValue();
				if (room != null) {
					if (model.addReservation(room.getRoomId(), checkIn.getText(), checkOut.getText())) {
						int response = JOptionPane.showConfirmDialog(
								new JFrame(), "<html>Do you want to make this reservation?</html>",
										"Confirmation", JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {
							list.setListData(new String[0]);
							panel.clearComponents();
							switchPanel("Receipt");
						}
					}
				}
				else
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: No room has been selected.", "Error",
							JOptionPane.ERROR_MESSAGE);
			}
		});
		panel.addComponent(confirmBtn, 0, 5);

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getReservations().isEmpty()) {
					panel.clearComponents();
					list.setListData(new String[0]);
					view.switchPanel("Customer");
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Error: You must complete your transaction.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.addComponent(backBtn, 0, 6);

		return panel;
	}

	private JPanel getReceiptPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.gridwidth = 1;
		c.ipady = 30;
		panel.addLabel("Receipt", 24, "center", Color.white, Color.black, 0, 0);

		c.ipady = 0;
		c.weighty = 1;
		c.insets = new Insets(10,10,10,10);
		final JTextArea receipt = new JTextArea();
		receipt.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(receipt,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) receipt.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		panel.addComponent(scrollPane, 0, 2);
		panel.addComponent(receipt);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!model.getReservations().isEmpty()) {
					Account user = model.getCurrentUser();
					String text = "Username: " + user.getUsername() + "\nName: " + user.getFirstName() 
					+ " " + user.getLastName() + "\nReservations made: " + model.getReservations().size();

					double cost = 0;
					int i = 1;
					for (Reservation r : model.getReservations()) {
						text += String.format("\n\nReservation # %d\n%s", i, r.toString());
						cost += r.getTotalCost();
						i++;
					}

					text += String.format("\n\nTotal: $%.2f", cost);

					receipt.setText(text);
				}
			};
		});

		c.weighty = 0;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.clearResrvations();
				panel.clearComponents();
				view.switchPanel("Customer");
			}
		});
		panel.addComponent(backBtn, 0, 3);
		return panel;
	}

	private JPanel getCustViewCancelPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.gridwidth = 1;
		c.ipady = 30;
		panel.addLabel("View or Cancel a Reservation", 24, "center", Color.white, Color.black, 0, 0);

		c.ipady = 0;
		c.insets = new Insets(10,10,10,10);
		panel.addLabel("<html>Below are all your reservations.<br>To cancel a "
				+ "reservation: Select the one you wish to cancel. Press cancel.<br>"
				+ "If the list is empty, then you have not made any reservations.</html>", 12, "left", 
				null, null, 0, 1);

		@SuppressWarnings("rawtypes")
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		c.weighty = 1;
		panel.addComponent(listScroller, 0, 2);
		panel.addComponent(list);

		model.addChangeListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				ArrayList<Reservation> notCanceled = new ArrayList<Reservation>();
				if (model.getCurrentUser() != null) {
					for (Reservation r : model.getCurrentUser().getReservations())
						if (!r.getCanceled())
							notCanceled.add(r);

					list.setListData(notCanceled.toArray());
				}
				else list.setListData(new Reservation[0]);
			}
		});

		c.weighty = 0;
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Are you sure you want to cancel this reservation?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						if (!model.cancelReservation((Reservation) list.getSelectedValue()))
							JOptionPane.showMessageDialog(new JFrame(), 
									"An unexpected error has occurred. Please contact your system admin.", "Error", 
									JOptionPane.ERROR_MESSAGE);;
					}
				}
			}
		});
		panel.addComponent(cancelButton, 0, 3);

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Customer");
			}
		});
		panel.addComponent(backBtn, 0, 4);
		return panel;
	}

	private JPanel getReservationsPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.gridwidth = 4;
		c.ipady = 30;
		c.weighty = 0;
		panel.addLabel("Reservations", 24, "center", Color.white, Color.black, 0, 0);

		c.ipady = 0;
		c.insets = new Insets(10,10,10,10);
		panel.addLabel("Enter a min and max and sort by room or customer.", 12, "left", 
				null, null, 0, 1);

		c.gridwidth = 1;
		panel.addLabel("Min cost (optional)", 12, "left", null, null, 0, 2);
		final JTextField minTF = new JTextField();
		panel.addComponent(minTF, 1, 2);

		panel.addLabel("Max cost (optional)", 12, "left", null, null, 2, 2);
		final JTextField maxTF = new JTextField();
		panel.addComponent(maxTF, 3, 2);

		c.gridwidth = 2;
		JButton roomBtn = new JButton("Order by Rooms");
		roomBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.addComponent(roomBtn, 0, 3);
		JButton customerBtn = new JButton("Order by Customer");
		roomBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.addComponent(customerBtn, 2, 3);

		c.weightx = 1;
		c.gridwidth = 4;
		c.weighty = 1;
		c.insets = new Insets(10,10,10,10);
		final JTextArea list = new JTextArea();
		list.setEditable(false);
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) list.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		panel.addComponent(scrollPane, 0, 4);
		panel.addComponent(list);

		roomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Double min = null, max = null;

				try {
					if (!minTF.getText().equals("")) 
						min = Double.parseDouble(minTF.getText());
					if (!maxTF.getText().equals(""))
						max = Double.parseDouble(maxTF.getText());

					ArrayList<Reservation> res = model.getReservations("roomType", min, max);
					if (res != null)
						list.setText(formatReservations(res));
					else 
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Invalid input(s).", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		customerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Double min = null, max = null;

				if (!minTF.getText().equals("")) 
					min = Double.parseDouble(minTF.getText());
				if (!maxTF.getText().equals(""))
					max = Double.parseDouble(maxTF.getText());

				ArrayList<Reservation> res = model.getReservations("customer", min, max);
				if (res != null)
					list.setText(formatReservations(res));
				else
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
			}
		});

		c.weighty = 0;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.clearComponents();
				view.switchPanel(model.getCurrentRole());
			}
		});
		panel.addComponent(backBtn, 0, 5);

		return panel;
	}

	private JPanel getUsersPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.gridwidth = 2;
		c.ipady = 30;
		c.weighty = 0;
		panel.addLabel("Mange System Users", 24, "center", Color.white, Color.black, 0, 0);

		allUsers = new JList();
		allUsers.setCellRenderer(new MyListCellThing());
		ArrayList<Account> users = model.getAllUsers();
		allUsers.setListData(users.toArray());
		allUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allUsers.setLayoutOrientation(JList.VERTICAL);
		allUsers.setVisibleRowCount(10);
		JScrollPane listScroller = new JScrollPane(allUsers);
		panel.addComponent(listScroller, 0, 1);
		
		c.gridwidth = 1;
		JButton addEmployee = new JButton("Add Employee");
		addEmployee.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.addComponent(addEmployee, 0, 2);
		addEmployee.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanel("AddEmployee");
			}
		});
		
		
		JButton delete = new JButton("Delete");
		delete.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.addComponent(delete, 1, 2);
		
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Account deletedUser = (Account)allUsers.getSelectedValue();
				if(deletedUser == null){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please, select a user.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else{
					String deletedUserName = deletedUser.getUsername();
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(new JFrame(), "Do you want to delete user " + deletedUserName + "?", "Confirmation", dialogButton, JOptionPane.QUESTION_MESSAGE);
					if(dialogResult == 0) {
						model.deleteUser(deletedUserName);
						allUsers.setListData(model.getAllUsers().toArray());
					} 
				}
			}
			
		});
		
		
		
		c.gridwidth = 5;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.clearComponents();
				view.switchPanel(model.getCurrentRole());
			}
		});
		panel.addComponent(backBtn, 0, 3);

		return panel;
	}
	

	/**
	 * Get view of room service requested by Customer
	 * @return panel
	 */
	private JPanel getViewRoomServicePanel(){
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();
		c.weightx = 1;
		c.weighty = 0;

		/** need to retrieve info from database **/
		String userID = "USERID";
		String roomID= "ROOMID";
		String task = "TASK";

		panel.addLabel("View of Room Service (CUSTOMER)", 16, "center", null, null, 0, 0);
		panel.addLabel("UserID:  " + userID, 16, "left", null, null, 0, 1);

		panel.addLabel("RoomID:  " + roomID, 16, "left", null, null, 0, 2);
		panel.addLabel("Task: "  + task, 16, "left", null, null, 0, 3);

		panel.addNavigationButton("CHANGE", 16, "Room Service", 0, 6);
		panel.addNavigationButton("BACK", 16, "Customer", 1, 6);
		panel.addNavigationButton("CANCEL", 16,"Customer", 2,6);
		return panel;
	}

	private JPanel getFileComplaintPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.weighty = 1;
		c.ipady = 30;
		panel.addLabel("File a Complaint", 24, "center", Color.white, Color.black, 0, 0);

		c.ipady = 0;
		c.insets = new Insets(10,10,10,10);
		panel.addLabel("<html>We apologize for any inconvenience. "
				+ "<br>Please file your complaint here and a hotel manager "
				+ "will contact you as soon as possible.<html>", 12, "left", null, null, 0, 1);

		final JTextArea complaint = new JTextArea();
		complaint.setWrapStyleWord(true);
		complaint.setLineWrap(true);
		panel.addComponent(complaint, 0, 2);

		c.weighty = 0;
		panel.addNavigationButton("Back", 16, "Customer", 0, 3);

		JButton SumbitButton = new JButton("Sumbit");
		SumbitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		SumbitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				String complaintTest = complaint.getText();
				if (complaintTest.isEmpty() || complaintTest.length() < 1 || complaintTest.length() > 100) 
					validEntry = false;

				if (validEntry) {
					panel.clearComponents();
					if (model.addComplaint(model.getCurrentUser().getUsername(), complaintTest)) {	
						JOptionPane.showMessageDialog(new JFrame(), "Your complaint has been filed.", 
								"Result", JOptionPane.DEFAULT_OPTION);
						view.switchPanel(model.getCurrentRole());
					}
					else
						JOptionPane.showMessageDialog(new JFrame(), 
								"An unexpected error has occurred. Please contact your system admin.", "Error", 
								JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(new JFrame(), "Error: Must be between 1 and 100 characters.", 
							"Error", JOptionPane.ERROR_MESSAGE);
			};
		});
		panel.addComponent(SumbitButton, 0, 4);

		return panel;
	}

	private JPanel getStatisticsPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.weighty = 1;
		c.ipady = 30;
		panel.addLabel("Statistics", 24, "center", Color.white, Color.black, 0, 0);

		c.ipady = 0;
		c.insets = new Insets(10,10,10,10);
//		panel.addLabel("<html>Here are several commonly calculated statistics.<html>", 12, "left", null, null, 0, 1);

		final JTextArea stats = new JTextArea();
		stats.setWrapStyleWord(true);
		stats.setLineWrap(true);
		stats.setLineWrap(true);
		stats.setWrapStyleWord(true);
		stats.setOpaque(false);
		stats.setEditable(false);
		stats.setBorder(new EmptyBorder(10,10,2,2));
		panel.addComponent(stats, 0, 2);

		c.weighty = 0;
		c.gridx = 1;
		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Manager");
			}
		});
		panel.addComponent(backBtn, 0, 3);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = model.getStatistics();
				if (output != null){
					stats.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		return panel;
	}

	private JPanel getComplaintsPanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.ipady = 30;
		c.gridwidth = 2;
		panel.addLabel("Customer Complaints", 24, "center", Color.white, Color.black, 0, 0);

		c.weightx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,10);
		panel.addLabel("Enter a complaint ID and solution to resolve the complaint.", 12, "left", null, null, 1, 1);

		panel.addLabel("Complaint ID: ", 12, "left", null, null, 1, 2);
		panel.addLabel("Solution (Between 1 and 100 chars):", 12, "left", null, null, 1, 4);

		final JTextField idTF = new JTextField();
		panel.addComponent(idTF, 1, 3);

		final JTextArea solTA = new JTextArea();
		solTA.setWrapStyleWord(true);
		solTA.setLineWrap(true);
		panel.addComponent(solTA, 1, 5);

		JButton submitBtn = new JButton("Resolve Complaint");
		submitBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.addComponent(submitBtn, 1, 6);

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel(model.getCurrentRole());
			}
		});
		panel.addComponent(backBtn, 0, 6);

		c.gridheight = 5;
		final JTextArea list = new JTextArea();
		list.setWrapStyleWord(true);
		list.setLineWrap(true);
		list.setEditable(false);
		panel.addComponent(list);
		JScrollPane listScroller = new JScrollPane(list);
		panel.addComponent(listScroller, 0, 1);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String output = "";
				ArrayList<Complaint> complaints = model.getComplaints();
				if (complaints != null){
					output += "Number of complaints: " + complaints.size();
					for (Complaint c : complaints)
						output += "\n\n" + c.toString();
					list.setText(output);
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Integer id = null;
				String solution = solTA.getText();

				try {
					if (!idTF.getText().equals(""))
						id = Integer.parseInt(idTF.getText());

					Complaint c = model.getComplaint(id);
					if (c == null)
						JOptionPane.showMessageDialog(new JFrame(), 
								"Error: Complaint does not exist.", "Error", 
								JOptionPane.ERROR_MESSAGE);
					else if (solution.length() < 1 || solution.length() > 100)
						JOptionPane.showMessageDialog(new JFrame(), 
								"Error: Solution must be between 1 and 100 characters.", "Error", 
								JOptionPane.ERROR_MESSAGE);
					else {
						if (c.getResolvedBy() == null) {
							panel.clearComponents();
							model.updateComplaint(id, model.getCurrentUser().getUsername(), solution);
							JOptionPane.showMessageDialog(new JFrame(), "Complaint resolved", "Result", JOptionPane.DEFAULT_OPTION);
						}
						else {
							JOptionPane.showMessageDialog(new JFrame(), 
									"Error: The complaint has already been resolved.", "Error", 
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Invalid input(s).", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			};
		});

		return panel;
	}

	private JPanel getRoomServicePanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.ipady = 30;
		panel.addLabel("Room Service Request", 24, "center", Color.white, Color.black, 0, 0);

		c.weightx = 0;
		c.ipady = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,10);
		panel.addLabel("Select a task", 12, "left", null, null, 0, 1);

		@SuppressWarnings("rawtypes")
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		c.weighty = 1;
		panel.addComponent(listScroller, 0, 2);

		model.addChangeListener(new ChangeListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void stateChanged(ChangeEvent e) {
				ArrayList<RoomService> current = model.getRoomService();
				if (current != null) 
					list.setListData(current.toArray());
				else {
					list.setListData(new RoomService[0]);
					JOptionPane.showMessageDialog(new JFrame(), 
							"An unexpected error has occurred. Please contact your system admin.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton submitBtn = new JButton("Complete");
		submitBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!list.isSelectionEmpty()) {
					int response = JOptionPane.showConfirmDialog(new JFrame(),
							"Are you sure you want to complete this task?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						RoomService rs = (RoomService)list.getSelectedValue();
						if (!model.resolveTask(rs.getTaskId()))
							JOptionPane.showMessageDialog(new JFrame(), 
									"An unexpected error has occurred. Please contact your system admin.", "Error", 
									JOptionPane.ERROR_MESSAGE);
					}
				}
			};
		});
		panel.addComponent(submitBtn, 0, 3);

		panel.addNavigationButton("Back to main menu", 12, "Room Attendant" , 0, 4);

		return panel;
	}


	private GregorianCalendar isValidDateFormat(String input) {
		try {
			dateFormatter.setLenient(false);
			GregorianCalendar cal = new GregorianCalendar();
			Date d = dateFormatter.parse(input);
			cal.setTime(d);

			return cal;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private int checkDaysBetween(GregorianCalendar checkIn, GregorianCalendar checkOut) {
		GregorianCalendar temp = (GregorianCalendar) checkIn.clone();
		int count = 0;
		while (!temp.equals(checkOut)) {
			temp.add(Calendar.DATE, 1);
			count++;
		}
		return count;
	}

	private String formatReservations(ArrayList<Reservation> res) {
		String result = "Total reservations: " + res.size();
		if (!res.isEmpty()) {
			for (Reservation r : res) {
				String in = dateFormatter.format(r.getStartDate());
				String out = dateFormatter.format(r.getEndDate());

				result += "\n\nReservation # " + r.getReservationId()
				+ "\nUsername: " + r.getCustomer()
				+ "\nRoom: " + r.getRoom().getRoomType()
				+ "\nStart: " + in
				+ "\nEnd: " + out
				+ "\nCost: $" + Double.toString(r.getTotalCost());

				if (r.getCanceled())
					result += "\nThis reservation has been canceled";
			}
		}
		return result;
	}

	private String formatUsers(ArrayList<Account> users) {
		String result = "Total users: " + users.size();
		if (!users.isEmpty()) {
			for (Account a : users) {
				result += "\n\nUsername: " + a.getUsername()
				+ "\nName: " + a.getFirstName() + " " + a.getLastName()
				+ "\nUser role: " + a.getRole();
				if (a.getRole().equals("Customer")) {
					int count = 0;
					for (Reservation r : a.getReservations())
						if (!r.getCanceled()) count++;
					result += "\nNumber of Reservations: " + count;
				}
			}
		}
		return result;
	}

	private JPanel getOrderRoomServicePanel() {
		final BasicPanel panel = new BasicPanel(this);
		GridBagConstraints c = panel.getConstraints();

		c.ipady = 30;
		c.gridwidth = 2;
		panel.addLabel("Room Service Request", 24, "center", Color.white, Color.black, 0, 0);

		c.ipady = 0;
		c.insets = new Insets(10,10,10,10);
		panel.addLabel("<html>Description: (Please, include your room number in the descriotion)</html>", 12, "left", null, null, 0, 1);
		
		JTextArea textArea = new JTextArea(10, 20);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		panel.addComponent(scrollPane, 0, 2);
		
		JButton submitBtn = new JButton("Request");
		submitBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = textArea.getText();
				if(s.isEmpty()){
					JOptionPane.showMessageDialog(new JFrame(), 
							"Description cannot be empty.", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}else{
					int response = JOptionPane.showConfirmDialog(
							new JFrame(), "Do you want to request a room servce?",
							"Confirmation", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.NO_OPTION) ;
					if (response == JOptionPane.YES_OPTION) {
						model.addRoomService(model.getCurrentUser().getUsername(), s);
						textArea.setText("");
						JOptionPane.showMessageDialog(new JFrame(), 
								"Your request has been made.");
					}
					
				}

			}
		});
		panel.addComponent(submitBtn, 0, 3);

		

		JButton backBtn = new JButton("Back to main menu");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.switchPanel("Customer");
				textArea.setText("");
			}
		});
		panel.addComponent(backBtn, 0, 4);

		return panel;
	}
}