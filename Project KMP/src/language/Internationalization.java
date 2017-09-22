package language;

import java.util.Locale;
import java.util.ResourceBundle;

import terminal.Terminal;

public class Internationalization {
	
	public static void main(String[] args) {
		ResourceBundle bundleEN = ResourceBundle.getBundle("language/ApplicationMessages_Natural");
		ResourceBundle bundleFR = ResourceBundle.getBundle("language/ApplicationMessages_Natural", Locale.FRANCE);
		ResourceBundle bundleMath = ResourceBundle.getBundle("language/ApplicationMessages_Math");
		
		SyntaxVerifier sv = new SyntaxVerifier(bundleEN);
		Terminal terminal = new Terminal(System.in, System.out, sv);
		new Thread(terminal).start();
	}
	

}
