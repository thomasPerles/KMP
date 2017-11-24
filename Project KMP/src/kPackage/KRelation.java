package kPackage;

import java.util.ArrayList;

import process.DB;

public class KRelation extends KObject {

	private KClass domain = null, range = null;
	
	public KRelation() {}
	
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

	
	
	
	
	
	
	
	
	private boolean symmetric = false, reflexive = false, functional, inverseFunctional, transitive = false;
	//symmetric = false means asymmetric
	//reflexive = false means irreflexive

	public boolean isSymmetric() {
		return symmetric;
	}

	public void setSymmetric(boolean symmetric) {
		this.symmetric = symmetric;
	}

	public boolean isReflexive() {
		return reflexive;
	}

	public void setReflexive(boolean reflexive) {
		this.reflexive = reflexive;
	}

	public boolean isFunctional() {
		return functional;
	}

	public void setFunctional(boolean functional) {
		this.functional = functional;
	}

	public boolean isInverseFunctional() {
		return inverseFunctional;
	}

	public void setInverseFunctional(boolean inverseFunctional) {
		this.inverseFunctional = inverseFunctional;
	}

	public boolean isTransitive() {
		return transitive;
	}

	public void setTransitive(boolean transitive) {
		this.transitive = transitive;
	}
	
	public ArrayList<Triple> symmetric(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		for (int i = 0; i < as.size(); i++) {
			res.add(new Triple(bs.get(i), this, as.get(i)));
		}
		return res;
	}
	
	public Triple symmetric(Triple t) {
		return new Triple(t.getDestination(), this, t.getSource());
	}
	
	// /!\ for xRy -> add xRx and yRy 
	public ArrayList<Triple> reflexive(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		for (int i = 0; i < as.size(); i++) {
			res.add(new Triple(as.get(i), this, as.get(i)));
			res.add(new Triple(bs.get(i), this, bs.get(i)));
		}
		return res;
	}
	
	/*
	functional
	inverseFunctional
	*/
	
	public ArrayList<Triple> transitive(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		for (int i = 0; i < as.size(); i++) {
			KModel a = as.get(i), b = bs.get(i);
			for (int j = i; j < as.size(); j++) {
				if (a == as.get(j)) res.add(new Triple(b, this, bs.get(j)));
				if (a == bs.get(j)) res.add(new Triple(b, this, as.get(j)));
				if (b == as.get(j)) res.add(new Triple(a, this, bs.get(j)));
				if (b == bs.get(j)) res.add(new Triple(a, this, as.get(j)));
			}
		}
		return res;
	}
	
	public boolean hasProperty() {
		return symmetric || transitive || reflexive || functional || inverseFunctional;
	}
	
	public ArrayList<Triple> applyProperties(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		if (isSymmetric()) res.addAll(symmetric(as, bs));
		if (isReflexive()) res.addAll(reflexive(as, bs));
		/*
		if (isFunctional()) res.add(functional(a, b));
		if (isInverseFunctional()) res.add(inverseFunctional(a, b));
		*/
		if (isTransitive()) res.addAll(transitive(as, bs));
		return res;
	}

	
	private ArrayList<KRelation> equivalentR = new ArrayList<KRelation>(), inheritsR = new ArrayList<KRelation>(), differentR = new ArrayList<KRelation>();
	
	public boolean hasPropertyOnRelation() {
		return !(equivalentR.isEmpty() && inheritsR.isEmpty() && differentR.isEmpty());
	}
	
	public ArrayList<Triple> equivalentR(ArrayList<KModel> as) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		//TODO
		return res;
	}
	
	public ArrayList<Triple> inheritsR(ArrayList<KModel> as) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		//TODO
		return res;
	}
	
	public ArrayList<Triple> differentR(ArrayList<KModel> as) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		//TODO
		return res;
	}

	public ArrayList<Triple> applyPropertiesOnRelation(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		if (!equivalentR.isEmpty()) res.addAll(equivalentR(as));
		if (!inheritsR.isEmpty()) res.addAll(inheritsR(as));
		if (!differentR.isEmpty()) res.addAll(differentR(as));
		return res;
	}
	
	public ArrayList<Triple> applyPropertiesRelation(DB db) {
		ArrayList<Triple> res = new ArrayList<Triple>();
		ArrayList<KModel> domains = new ArrayList<KModel>(), ranges = new ArrayList<KModel>();
		ArrayList<Triple> ts = db.findEveryTripleWith(this);
		for (Triple t : ts) {
			domains.add(t.getSource());
			ranges.add(t.getDestination());
		}
		res.addAll(applyProperties(domains, ranges));
		res.addAll(applyPropertiesOnRelation(domains, ranges));
		return res;
	}
}