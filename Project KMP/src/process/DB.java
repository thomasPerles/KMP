package process;

import java.util.ArrayList;

public class DB {
	
	private ArrayList<Triple> ts;
	
	public DB() {
		this.ts = new ArrayList<Triple>();
	}
	
	public ArrayList<Triple> getTs() {
		return ts;
	}

	public void addDB(Triple t) {
		this.ts.add(t);
	}
	
	public String toString() {
		String res = "";
		for (int i = 0; i < ts.size()-1; i++) {
			res += ts.get(i).toString() + " and ";
		}
		if (ts.size() != 0) return res + ts.get(ts.size()-1).toString();
		else return res;
	}
	
	public Result dbMatch(Triple pat) {
		Result r = new Result();
		Context c;
		for (int i = 0; i < r.getCs().size(); i++) {
			c = new Context();
			if (c.tripleMatch(pat, this.ts.get(i))) r.addResult(c);
		}
		return r;                                                                                                                                                                                                                                                                                                                                                                                                            
	}
	
	public Result dbMatch2(Triple pat, Context c) {
		Result r = new Result();
		for (int i = 0; i < ts.size(); i++) {
			Context c2 = c.cloneContext();
			if (c2.tripleMatch(pat, ts.get(i))) r.addResult(c2);
		}
		return r;
	}
	
	
	
	
	// TODO
	public Result answer(DB query) {
		// TODO
		//Result r = new Result(), 
		Result r0 = new Result(), r1;
		this.dbMatch2(query.getTs().get(0), new Context());
		for (int j = 1; j < query.getTs().size(); j++) {
			r1 = new Result();
			for (int i = 0; i < r0.getCs().size(); i++) {
				r1.mergeResult(this.dbMatch2(query.getTs().get(j), r0.getCs().get(i)));
			}
			r0 = r1;
		}
		return r0;
	}
}