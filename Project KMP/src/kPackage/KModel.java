package kPackage;

public class KModel extends KObject {
	
	public KModel() {
		
	}
	
	public KModel(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return id;
	}

}
