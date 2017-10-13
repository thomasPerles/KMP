package process;

import java.io.Serializable;

public class Rule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DB pre, post;
	
	public Rule(DB pre, DB post) {
		this.pre = pre;
		this.post = post;
	}

	@Override
	public String toString() {
		return pre + " => " + post;
	}
	
	public DB evaluateRule(DB db) {
		return db.answer(this.pre).evaluate(this.post);
	}
}
