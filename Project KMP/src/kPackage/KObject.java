package kPackage;

public abstract class KObject {

	protected String id;

	public String getId() {
		return id;
	}

	public String simpleToString() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof KObject)) return false;
		KObject kObject = (KObject) obj;
		return this.id.equals(kObject.id);
	}
}
