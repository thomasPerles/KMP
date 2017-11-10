package kPackage;

public class KInstance extends KModel {

	private KClass kClass = null;
	
	public KInstance(String id) {
		this.id = id;
	}

	public KInstance(String id, KClass kClass) {
		this.id = id;
		this.kClass = kClass;
	}
}
