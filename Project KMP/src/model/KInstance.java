package model;

public class KInstance extends KObject {

	private KClass kInstanceOf;
	private KMap<KRelation, KInstance> relationshipsWithInstances = new KMap<KRelation, KInstance>(); 
	
	public KInstance(String id) {
		super(id);
	}

	public KClass getkInstanceOf() {
		return kInstanceOf;
	}

	public KMap<KRelation, KInstance> getRelationshipsWithInstances() {
		return relationshipsWithInstances;
	}
}