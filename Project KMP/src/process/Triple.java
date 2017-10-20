package process;

import java.io.Serializable;

import kPackage.KModel;
import kPackage.KRelation;

public class Triple implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KRelation l;
	private KModel s, d;
	
	public Triple(KModel a, KRelation b, KModel c) {
		this.s = a;
		this.l = b;
		this.d = c;
	}

	@Override
	public String toString() {
		return "(" + s + " " + l + " " + d + ")";
	}

	public KModel getS() {
		return s;
	}

	public KRelation getL() {
		return l;
	}

	public KModel getD() {
		return d;
	}
	
	
	//v2
	public boolean equal(Triple t) {
		return this.s == t.getS() && this.l == t.getL() && this.d == t.getD();
	}
}