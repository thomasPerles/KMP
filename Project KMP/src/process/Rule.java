package process;

public class Rule {

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
