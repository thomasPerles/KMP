package kPackage;

public class KRelation extends KObject {

	private KClass domain = null, range = null;
	
	public KRelation() {
		
	}
	
	public KRelation(String id) {
		this.id = id;
	}

	public KRelation(String id, KClass domain, KClass range) {
		this.id = id;
		this.domain = domain;
		this.range = range;
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append('\n').append(simpleToString()).append(" is a relation");
		res.append("\n domain is ").append(domain.simpleToString());
		res.append("\n range is ").append(range.simpleToString());
		return res.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(!(obj instanceof KRelation)) return false;
		KRelation kRelation = (KRelation) obj;
		return this.id.equals(kRelation.id) && this.domain.equals(kRelation.domain) && this.range.equals(kRelation.range);
	}
	
}
