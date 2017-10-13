package process;

import java.io.Serializable;
import java.util.ArrayList;

public class Rules implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Rule> rs = new ArrayList<Rule>();
	
	public Rules() {}
	
	public void addRule(Rule r) {
		this.rs.add(r);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.rs.size(); i++) {
			s.append(this.rs.get(i) + "\n");
		}
		return s.toString();
	}
	
	public DB evaluateRules(DB db) {
		DB r = new DB();
		for (int i = 0; i < this.rs.size(); i++) {
			r.mergeDB(this.rs.get(i).evaluateRule(db));
		}
		return r;
	}
	
	public void inference(DB db) {
		DB delta;
		boolean changed;
		do {
			delta = this.evaluateRules(db);
			changed = db.unionDB(delta);
		} while (changed);
	}
}
