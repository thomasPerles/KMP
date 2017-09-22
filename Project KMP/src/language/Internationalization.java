package language;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internationalization {
	
	public static void main(String[] args) {
		ResourceBundle bundleEN = ResourceBundle.getBundle("ApplicationMessages_Natural");
		ResourceBundle bundleFR = ResourceBundle.getBundle("ApplicationMessages_Natural", Locale.FRANCE);
		ResourceBundle bundleMath = ResourceBundle.getBundle("ApplicationMessages_Math");
	}
	

}
