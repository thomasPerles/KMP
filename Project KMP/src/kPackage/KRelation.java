package kPackage;

import java.util.ArrayList;

public class KRelation extends KObject {

	private ArrayList<Qualifier> qualifiers = new ArrayList<Qualifier>();
	
	public KRelation(String id) {
		super(id);
	}

	public ArrayList<Qualifier> getQualifiers() {
		return qualifiers;
	}
}