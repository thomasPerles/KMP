package model;

import java.util.ArrayList;

public class KRelation extends KObject {

	private KRelation equivalentTo;
	private KRelation inheritsFrom;
	private KRelation inverseOf;
	private ArrayList<Qualifier> qualifiers = new ArrayList<Qualifier>();
	private KMap<KClass, KClass> relationBetweenClasses = new KMap<KClass, KClass>();
	private KMap<KInstance, KInstance> relationBetweenInstances = new KMap<KInstance, KInstance>();
	
	public KRelation(String id) {
		super(id);
	}
	
	public KRelation getEquivalentTo() {
		return equivalentTo;
	}

	public KRelation getInheritsFrom() {
		return inheritsFrom;
	}

	public KRelation getInverseOf() {
		return inverseOf;
	}

	public ArrayList<Qualifier> getQualifiers() {
		return qualifiers;
	}

	public KMap<KClass, KClass> getRelationBetweenClasses() {
		return relationBetweenClasses;
	}

	public void setRelationBetweenClasses(KMap<KClass, KClass> relationBetweenClasses) {
		this.relationBetweenClasses = relationBetweenClasses;
	}

	public KMap<KInstance, KInstance> getRelationBetweenInstances() {
		return relationBetweenInstances;
	}

	public void setRelationBetweenInstances(KMap<KInstance, KInstance> relationBetweenInstances) {
		this.relationBetweenInstances = relationBetweenInstances;
	}
}