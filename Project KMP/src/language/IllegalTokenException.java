package language;

public class IllegalTokenException extends Exception {

	private String token;
	
	IllegalTokenException(String token) {
		this.token = token;
	}
	
	@Override
	public String getMessage() {
		return this.token + " is an illegal token!";
	}
}
