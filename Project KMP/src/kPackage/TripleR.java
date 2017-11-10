package kPackage;

public class TripleR extends KRelation {

	private KRelation leftLink, rightLink;
	private KModel model;
	
	public TripleR(String id) {
		super(id);
	}
	
	public TripleR(KRelation leftLink, KModel model, KRelation rightLink) {
		this.leftLink = leftLink;
		this.model = model;
		this.rightLink = rightLink;
	}

	public KRelation getLeftLink() {
		return leftLink;
	}

	public KRelation getRightLink() {
		return rightLink;
	}

	public KModel getModel() {
		return model;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("\n(").append(leftLink.simpleToString()).append(' ').append(model.simpleToString()).append(' ').append(rightLink.simpleToString()).append(')');
		return res.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof TripleR)) return false;
		TripleR tripleR = (TripleR) obj;
		return this.leftLink.equals(tripleR.leftLink) && this.model.equals(tripleR.model) && this.rightLink.equals(tripleR.rightLink);
	}
}
