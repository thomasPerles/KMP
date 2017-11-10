package language;

public class IllegalWordCountException extends Exception {
	
	@Override
	public String getMessage() {
		return "Number of string tokens is a pair number!";
	}
}
