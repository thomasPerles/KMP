package model;

import java.util.ArrayList;

public class KClass extends KObject {

	private KClass equivalentTo;
	private KClass inheritsFrom;
	private KClass inverseOf;
	private ArrayList<KInstance> kInstances = new ArrayList<KInstance>();
	private KMap<KRelation, KClass> relationshipsWithClasses = new KMap<KRelation, KClass>();
	
	public KClass(String id) {
		super(id);
	}

	public KClass getEquivalentTo() {
		return equivalentTo;
	}

	public KClass getInheritsFrom() {
		return inheritsFrom;
	}

	public KClass getInverseOf() {
		return inverseOf;
	}

	public ArrayList<KInstance> getkInstances() {
		return kInstances;
	}

	public KMap<KRelation, KClass> getRelationshipsWithClasses() {
		return relationshipsWithClasses;
	}
}