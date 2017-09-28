package process;

import java.io.Serializable;
import java.util.ArrayList;

public class Context implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> vars, vals;
	
	public Context() {
		this.vars = new ArrayList<String>();
		this.vals = new ArrayList<String>();
	}
	
	public void addContext(String var, String val) {
		this.vars.add(var);
		this.vals.add(val);
	}
	
	public String toString() {
		String res = "{";
		for (int i = 0; i < vars.size()-1; i++) {
			res += this.vars.get(i) + ":" + this.vals.get(i) + ", ";
		}
		if (this.vars.size() != 0) return res + this.vars.get(this.vars.size()-1) + ":" + this.vals.get(this.vals.size()-1) + "}";
		else return res + "}";
	}
	
	public boolean hasVariable(String var) {
		return this.vars.contains(var);
	}
	
	public String getVariable(String var) {
		for (int i = 0; i < this.vars.size(); i++) {
			if (this.vars.get(i).equals(var)) return this.vals.get(i);
		}
		return null;
	}
	
	public Context cloneContext() {
		Context r = new Context();
		r.vars.addAll(this.vars);
		r.vals.addAll(this.vals);
		return r;
	}
	
	public boolean valueMatch(String pat, String exp) {
		if (pat.toCharArray()[0] == '?') {
			if (hasVariable(pat)) return getVariable(pat).equals(exp);
			else {
				addContext(pat, exp);
				return true;
			}
		} else return pat.equals(exp);
	}
	
	public boolean tripleMatch(Triple pat, Triple exp) {
		if(valueMatch(pat.getS(), exp.getS()))
			if(valueMatch(pat.getL(), exp.getL()))
				return valueMatch(pat.getD(), exp.getD());
		return false;
	}
	
	
	
	//v2
	public Triple evaluateTriple(Triple t) {
		String s = null, l = null, d = null;
		if (t.getS().toCharArray()[0] == '?') 
			s = this.getVariable(t.getS());
		else
			s = t.getS();
		if (t.getL().toCharArray()[0] == '?') 
			l = this.getVariable(t.getL());
		else
			l = t.getL();
		if (t.getD().toCharArray()[0] == '?') 
			d = this.getVariable(t.getD());
		else
			d = t.getD();
		return new Triple(s, l, d);
	}
}