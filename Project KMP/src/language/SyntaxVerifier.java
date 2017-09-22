package language;

import java.util.HashMap;

public class SyntaxVerifier {
	
	HashMap<String, String[]> starters;
	
	public SyntaxVerifier() {
		starters = starters();
	}
	
	private HashMap<String, String[]> starters() {
		HashMap<String, String[]> starters = new HashMap<String, String[]>();
		starters.put("Declaration", new String[] {"Class", "Instance", "Relation"});
		starters.put("Display", new String[] {"Show", "Triple"});
		return starters;
	}
	
}
