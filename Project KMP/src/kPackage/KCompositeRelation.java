package kPackage;

public class KCompositeRelation extends KRelation {

	private KRelation leftLink, rightLink;
	private KModel model;
	
	public KCompositeRelation(String id) {
		super(id);
	}
	
	public KCompositeRelation(KRelation leftLink, KModel model, KRelation rightLink) {
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
	
//	@Override
//	public String simpleToString() {
//		StringBuilder res = new StringBuilder();
//		res.append(leftLink.simpleToString()).append(' ').append(model.simpleToString()).append(' ').append(rightLink.simpleToString());
//		return res.toString();
//	}
	
	@Override
	public String toString() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof KCompositeRelation)) return false;
		KCompositeRelation tripleR = (KCompositeRelation) obj;
		return this.leftLink.equals(tripleR.leftLink) && this.model.equals(tripleR.model) && this.rightLink.equals(tripleR.rightLink);
	}
}
