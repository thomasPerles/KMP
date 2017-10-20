package kPackage;

public class TripleR extends KRelation {

	private KRelation left, right;
	private KModel center;
	
	public TripleR(String id) {
		super(id);
	}

	public KRelation getLeft() {
		return left;
	}

	public KRelation getRight() {
		return right;
	}

	public KModel getCenter() {
		return center;
	}

	
}
