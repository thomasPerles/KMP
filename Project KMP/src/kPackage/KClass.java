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
	
//	@Override
//	public String toString() {
//		StringBuffer res = new StringBuffer();
//		res.append('\n').append(simpleToString()).append(" is a class");
//		if (instances.size() > 0) {
//			res.append("\nhas instances :");
//			for (KInstance instance : instances) {
//				res.append("\n\t").append(instance.simpleToString());
//			} 
//		}
//		return res.toString();
//	}
	
	@Override
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof KClass)) return false;
		KClass kClass = (KClass) obj;
		return this.id.equals(kClass.id) && this.instances.equals(kClass.instances); 
	}
	
}