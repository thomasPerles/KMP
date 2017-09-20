package process;

public class Triple {
	
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
}