package process;

import java.io.Serializable;
import java.util.ArrayList;

import kPackage.KModel;
import kPackage.KObject;
import kPackage.KRelation;
import kPackage.Triple;

public class Context implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<KObject> vars;
	ArrayList<KObject> vals;
	
	public Context() {
		this.vars = new ArrayList<KObject>();
		this.vals = new ArrayList<KObject>();
	}
	
	public void addContext(KObject var, KObject val) {
		this.vars.add(var);
		this.vals.add(val);
	}
	
	public String toString() {
		String res = "{";
		for (int i = 0; i < vars.size()-1; i++) {
			res += this.vars.get(i).getId() + ":" + this.vals.get(i).getId() + ", ";
		}
		if (this.vars.size() != 0) return res + this.vars.get(this.vars.size()-1).getId() + ":" + this.vals.get(this.vals.size()-1).getId() + "}";
		else return res + "}";
	}
	
	public boolean hasVariable(KObject var) {
		return this.vars.contains(var);
	}
	
	public KObject getVariable(KObject var) {
		for (int i = 0; i < this.vars.size(); i++) {
			if (this.vars.get(i).getId().equals(var.getId())) return this.vals.get(i);
		}
		return null;
	}
	
	public Context cloneContext() {
		Context r = new Context();
		r.vars.addAll(this.vars);
		r.vals.addAll(this.vals);
		return r;
	}
	
	public boolean valueMatch(KObject pat, KObject exp) {
		if (pat.getId().toCharArray()[0] == '?') {
			if (hasVariable(pat)) return getVariable(pat).equals(exp);
			else {
				addContext(pat, exp);
				return true;
			}
		} else return pat.equals(exp);
	}
	
	public boolean tripleMatch(Triple pat, Triple exp) {
		if(valueMatch(pat.getSource(), exp.getSource()))
			if(valueMatch(pat.getLink(), exp.getLink()))
				return valueMatch(pat.getDestination(), exp.getDestination());
		return false;
	}
	
	
	
	//v2
	public Triple evaluateTriple(Triple t) {
		KModel s = null, d = null;
		KRelation l = null;
		if (t.getSource().getId().toCharArray()[0] == '?') 
			s = (KModel) this.getVariable(t.getSource());
		else
			s = t.getSource();
		if (t.getLink().getId().toCharArray()[0] == '?') 
			l = (KRelation) this.getVariable(t.getLink());
		else
			l = t.getLink();
		if (t.getDestination().getId().toCharArray()[0] == '?') 
			d = (KModel) this.getVariable(t.getDestination());
		else
			d = t.getDestination();
		return new Triple(s, l, d);
	}
}