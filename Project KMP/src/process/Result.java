package process;

import java.util.ArrayList;

public class Result {

	private ArrayList<Context> cs;
	
	public Result() {
		this.cs = new ArrayList<Context>();
	}
	
	public ArrayList<Context> getCs() {
		return cs;
	}

	public void addResult(Context c) {
		this.cs.add(c);
	}
	
	public String toString() {
		String res = "{";
		for (int i = 0; i < this.cs.size()-1; i++) {
			res += this.cs.get(i) + ", ";
		}
		if (this.cs.size() != 0) return res + this.cs.get(this.cs.size()-1) + "}";
		else return res + "}";
	}
	
	public void mergeResult(Result c) {
		this.cs.addAll(c.getCs());
	}
	
	public static void main(String[] args) {
		Triple t1 = new Triple("John","worksFor","ENSISA"), t2 = new Triple("ENSISA","isAt","Mulhouse"), t3 = new Triple("ENSISA","isAt","?X"); 
		//System.out.println(t1.toString());

		DB db = new DB();
		db.addDB(t1); db.addDB(t2); db.addDB(t3); db.addDB(new Triple("Amy","worksFor","ENSISA")); db.addDB(new Triple("Amy","is","SoftEng"));
		System.out.println(db.toString() + "\n");
		
		/*
		Context c = new Context();
		c.addContext("?X","Mulhouse");
		System.out.println(c.toString());
		System.out.println(c.getVariable("?X"));
		System.out.println(c.valueMatch("?X", "a"));
		
		System.out.println(c.tripleMatch(new Triple("ENSISA","isAt","?X"),t2) + " with ");
		System.out.println(c.toString() + "\n");

		Triple pat = new Triple("ENSISA","isAt","?X");
		System.out.println(pat.toString() + "\n");
		System.out.println(db.dbMatch(pat) + "\n");
		*/
		
		DB pat1 = new DB();
		pat1.addDB(new Triple("?X","isAt","?Y"));
		pat1.addDB(new Triple("?Z","worksFor","?X"));
		pat1.addDB(new Triple("?Z","is","SoftEng"));
		System.out.println(pat1.toString() + "\n");
		System.out.println(db.answer(pat1));
		
		ArrayList<String> tests = new ArrayList<String>();
		tests.add("123"); tests.add("456"); tests.add("789");
		String test = "456";
		System.out.println("vars contains var : " + tests.contains(test));
		System.out.println("== : " + (tests.get(0) == test) + " or " + (tests.get(1) == test));
	}
}