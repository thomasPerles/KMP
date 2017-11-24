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
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append('\n').append(simpleToString()).append(" is an instance");
		if (kClass != null) {
			res.append("\nis instance of ").append(kClass.simpleToString());
		}
		return res.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof KInstance)) return false;
		KInstance kInstance = (KInstance) obj;
		return this.id.equals(kInstance.id) && this.kClass.equals(kInstance.kClass); 
	}
}
