package kPackage;

public class KRelation extends KObject {

	private KClass domain = null, range = null;
	
	public KRelation(String id) {
		this.id = id;
	}

	public KRelation(String id, KClass domain, KClass range) {
		this.id = id;
		this.domain = domain;
		this.range = range;
	}
}
