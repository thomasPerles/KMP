package language;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import terminal.Terminal;

public class SyntaxVerifier {
	
	private HashMap<String, String[]> starters;
	private HashMap<String, String[][]> followers;
	private ResourceBundle bundle;
	
	private String Starter;
	private String Declaration;
	private String Class;
	private String Instance;
	private String Relation;
	private String Display;
	private String Show;
	private String Triple;
	
	private Terminal terminal;
	
	public SyntaxVerifier(ResourceBundle bundle) {
		this.bundle = bundle;
		Starter = bundle.getString("Starter");
		Declaration = bundle.getString("Declaration");
		Class = bundle.getString("Class");
		Instance = bundle.getString("Instance");
		Relation = bundle.getString("Relation");
		Display = bundle.getString("Display");
		Show = bundle.getString("Show");
		starters = starters();
		followers = followers();
	}
	
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	
	private HashMap<String, String[]> starters() {
		HashMap<String, String[]> starters = new HashMap<String, String[]>();
		starters.put(Declaration, new String[] {Class, Instance, Relation});
		starters.put(Display, new String[] {Show, Triple});
		return starters;
	}
	
	
	private HashMap<String, String[][]> followers() {
		HashMap<String, String[][]> followers = new HashMap<String, String[][]>();
		String _class = bundle.getString("class"); 
		String instance = bundle.getString("instance");
		String is = bundle.getString("is");
		String relation = bundle.getString("relation");
		String between = bundle.getString("between");
		String and = bundle.getString("and");
		String show = bundle.getString("show");
		String has = bundle.getString("has");
		String tripleStart = "?[";
		String tripleEnd = "]";
		String wildcard1 = "?X";
		String wildcard2 = "?Y";
		String wildcard3 = "?Z";
		followers.put(Class, new String[][] {{_class, wildcard1}, {_class, wildcard1, has, wildcard2}});
		followers.put(Instance, new String[][] {{instance,  wildcard1}, {instance,  wildcard1, is, wildcard2}});
		followers.put(Relation, new String[][] {{relation, wildcard1}, {relation, wildcard1, between, wildcard2, and, wildcard3}});
		followers.put(Show, new String[][] {{show, wildcard1}});
		followers.put(Triple, new String [][] {{tripleStart, wildcard1, wildcard2, wildcard3, tripleEnd}});
		return followers;
	}

	public void suggest(String[] tokens) {
		switch(tokens.length) {
		case 0:
			for(String followerKey : followers.keySet()) {
				terminal.println(followers.get(followerKey)[0][0]);
			}
			break;
		default:
			for(String followerKey : followers.keySet()) {
				String keywords[][] = followers.get(followerKey);
				for(String[] option : keywords) {
					if(Arrays.asList(option).containsAll(Arrays.asList(tokens))) {
						for(String keyword : option) {
							terminal.print(' ' + keyword);
						}
						terminal.print(System.lineSeparator());
					}
				}
			}
		}
	}

	public void verify(String[] tokens) {
		
	}
	
}
