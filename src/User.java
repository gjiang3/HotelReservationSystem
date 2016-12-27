
public class User extends Account{
	private String password;
	private String question;
	private String answer;
	public User(String firstName, String lastName, String username, String role, int age, String gender, String password, String question, String answer) {
		super(firstName, lastName, username, role, age, gender);
		// TODO Auto-generated constructor stub
		this.setPassword(password);
		this.setQuestion(question);
		this.setAnswer(answer);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
