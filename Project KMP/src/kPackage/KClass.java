package kPackage;

import java.util.ArrayList;

public class KClass extends KObject {

	private KClass inheritsFrom;
	private ArrayList<KInstance> kInstances = new ArrayList<KInstance>();
	
	public KClass(String id) {
		super(id);
	}

	public KClass getInheritsFrom() {
		return inheritsFrom;
	}

	public ArrayList<KInstance> getkInstances() {
		return kInstances;
	}
}