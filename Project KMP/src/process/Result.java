package process;

import java.io.Serializable;
import java.util.ArrayList;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
}