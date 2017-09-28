package process;

import java.io.Serializable;

public class Triple implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String s, l, d;
	
	public Triple(String a, String b, String c) {
		this.s = a;
		this.l = b;
		this.d = c;
	}

	@Override
	public String toString() {
		return "(" + s + " " + l + " " + d + ")";
	}

	public String getS() {
		return s;
	}

	public String getL() {
		return l;
	}

	public String getD() {
		return d;
	}
	
	
	//v2
	public boolean equal(Triple t) {
		return this.s == t.getS() && this.l == t.getL() && this.d == t.getD();
	}
}