package kPackage;

import java.util.ArrayList;

import kPackageUtils.KMap;

public class KRelation extends KObject {

	private ArrayList<Qualifier> qualifiers = new ArrayList<Qualifier>();
	private KMap<KClass, KClass> relationBetweenClasses = new KMap<KClass, KClass>();
	private KMap<KInstance, KInstance> relationBetweenInstances = new KMap<KInstance, KInstance>();
	
	public KRelation(String id) {
		super(id);
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