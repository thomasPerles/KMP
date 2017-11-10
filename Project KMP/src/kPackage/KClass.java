package kPackage;

import java.util.ArrayList;

public class KClass extends KModel {

	private ArrayList<KInstance> instances = null;
	
	public KClass(String id) {
		this.id = id;
		this.instances = new ArrayList<>();
	}
	
	public void addInstance(KInstance instance) {
		this.instances.add(instance);
	}
	
	public boolean hasInstance(KInstance instance) {
		return this.instances.contains(instance);
	}
}