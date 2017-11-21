package kPackage;

import java.util.ArrayList;

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

	
	private boolean symetric, asymetric, transitive, reflexive, irreflexive, functional, inverseFunctional;

	public boolean isSymetric() {
		return symetric;
	}

	public void setSymetric(boolean symetric) {
		this.symetric = symetric;
	}

	public boolean isAsymetric() {
		return asymetric;
	}

	public void setAsymetric(boolean asymetric) {
		this.asymetric = asymetric;
	}

	public boolean isTransitive() {
		return transitive;
	}

	public void setTransitive(boolean transitive) {
		this.transitive = transitive;
	}

	public boolean isReflexive() {
		return reflexive;
	}

	public void setReflexive(boolean reflexive) {
		this.reflexive = reflexive;
	}

	public boolean isIrreflexive() {
		return irreflexive;
	}

	public void setIrreflexive(boolean irreflexive) {
		this.irreflexive = irreflexive;
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
	
	public Triple symetric(KModel a, KModel b) {
		return new Triple(b, this, a);
	}
	
	/*
	equivalentR
	inheritsR
	differentR
	asymetric
	transitive
	reflexive
	irreflexive
	functional
	inverseFunctional
	*/
	
	public boolean hasProperty() {
		return /*equivalentR || inheritsR || differentR ||*/ symetric || asymetric || transitive || reflexive || irreflexive || functional || inverseFunctional;
	}
	
	public ArrayList<Triple> applyProperties(KModel a, KModel b) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		if (isSymetric()) res.add(symetric(a, b));
		/*
		if (isAsymetric()) res.add(asymetric(a, b));
		if (isFunctional()) res.add(functional(a, b));
		if (isInverseFunctional()) res.add(inverseFunctional(a, b));
		if (isIrreflexive()) res.add(irreflexive(a, b));
		if (isReflexive()) res.add(reflexive(a, b));
		if (isTransitive()) res.add(transitive(a, b));
		*/
		return res;
	}
	
	public ArrayList<Triple> applyPropertiesOnEveryTriple(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		for (int i = 0; i < as.size(); i++) {
			res.addAll(applyProperties(as.get(i), bs.get(i)));
		}
		return res;
	}
}