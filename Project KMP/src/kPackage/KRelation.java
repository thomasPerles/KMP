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
	
//	@Override
//	public String toString() {
//		StringBuilder res = new StringBuilder();
//		res.append('\n').append(simpleToString()).append(" is a relation");
//		res.append("\n domain is ").append(domain.simpleToString());
//		res.append("\n range is ").append(range.simpleToString());
//		return res.toString();
//	}
	
	@Override
	public String toString() {
		return id;
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
	
	/**
	 * Creates all the symmetric relations using the two lists
	 * 
	 * @param as
	 * @param bs
	 * @return
	 */
	public ArrayList<Triple> symmetric(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		for (int i = 0; i < as.size(); i++) {
			res.add(new Triple(bs.get(i), this, as.get(i)));
		}
		return res;
	}
	
	/**
	 * Creates the symmetric relation of the Triple
	 *  
	 * @param t
	 * @return
	 */
	public Triple symmetric(Triple t) {
		return new Triple(t.getDestination(), this, t.getSource());
	}
	
	/**
	 * Creates all the reflexive relations using the list
	 *  
	 * @param as
	 * @return
	 */
	public ArrayList<Triple> reflexive(ArrayList<KModel> as) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		for (int i = 0; i < as.size(); i++) {
			res.add(new Triple(as.get(i), this, as.get(i)));
		}
		return res;
	}
	
	/*
	functional
	inverseFunctional
	*/
	
	/**
	 * Creates all the transitive relations using the lists
	 * 
	 * @param as
	 * @param bs
	 * @return
	 */
	public ArrayList<Triple> transitive(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>();
		KModel source = null, dest = null, source2 = null, dest2 = null;
		if (as.size() > 1) {			
			for (int i = 0; i < as.size()-1; i++) {
				source = as.get(i);
				dest = bs.get(i);
				for (int j = i+1; j < as.size(); j++) {
					source2 = as.get(j);
					dest2 = bs.get(j);
					if (source.equals(source2) && !dest.equals(dest2)) res.add(new Triple(dest, this, dest2));
					if (source.equals(dest2) && !dest.equals(source2)) res.add(new Triple(dest, this, source2));
					if (dest.equals(source2) && !source.equals(dest2)) res.add(new Triple(source, this, dest2));
					if (dest.equals(dest2) && !source.equals(source2)) res.add(new Triple(source, this, source2));
				}
			}
		}
		return res;
	}
	
	/**
	 * Creates the transitive relation generating by the two Triples
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public Triple transitive(Triple t1, Triple t2) {
		Triple res = null;
		KModel t1source = t1.getSource(), t1dest = t1.getDestination(), t2source = t2.getSource(), t2dest = t2.getDestination();
		if (t1source.equals(t2source) && !t1dest.equals(t2dest)) res = new Triple(t1dest, this, t2dest);
		if (t1source.equals(t2dest) && !t1dest.equals(t2source)) res = new Triple(t1dest, this, t2source);
		if (t1dest.equals(t2source) && !t1source.equals(t2dest)) res = new Triple(t1source, this, t2dest);
		if (t1dest.equals(t2dest) && !t1source.equals(t2source)) res = new Triple(t1source, this, t2source);
		return res;
	}
	
	public boolean hasProperty() {
		return symmetric || transitive || reflexive || functional || inverseFunctional;
	}
	
	/**
	 * Creates the inferences generating by the properties of the KRelation
	 * 
	 * @param as
	 * @param bs
	 * @return
	 */
	public ArrayList<Triple> applyProperties(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>(), tmp;
		if (isSymmetric()) {
			tmp = symmetric(as, bs);
			for (Triple t : tmp) {
				if (!res.contains(t)) res.add(t); 
			}
		}
		if (isReflexive()) {
			tmp = reflexive(as);
			for (Triple t : tmp) {
				if (!res.contains(t)) res.add(t); 
			}
			tmp = reflexive(bs);
			for (Triple t : tmp) {
				if (!res.contains(t)) res.add(t); 
			}
		}
		/*
		if (isFunctional()) res.add(functional(a, b));
		if (isInverseFunctional()) res.add(inverseFunctional(a, b));
		*/
		if (isTransitive()) {
			tmp = transitive(as, bs);
			for (Triple t : tmp) {
				if (!res.contains(t)) res.add(t); 
			}
		}
		return res;
	}

	
	private ArrayList<KRelation> equivalentR = new ArrayList<KRelation>(), inheritsR = new ArrayList<KRelation>(), differentR = new ArrayList<KRelation>();
	
	public boolean hasPropertyOnRelation() {
		return !(equivalentR.isEmpty() && inheritsR.isEmpty() && differentR.isEmpty());
	}
	
	//TODO /!\ identical sinon equivalence => symetrique, reflexif, transitif pour les 2 relations 
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

	/**
	 * Creates the inferences generating by the properties on the relation
	 * 
	 * @param as
	 * @param bs
	 * @return
	 */
	public ArrayList<Triple> applyPropertiesOnRelation(ArrayList<KModel> as, ArrayList<KModel> bs) {
		ArrayList<Triple> res =  new ArrayList<Triple>(), tmp;
		if (!equivalentR.isEmpty()) {
			tmp = equivalentR(as);
			for (Triple t : tmp) {
				if (!res.contains(t)) res.add(t); 
			}
		}
		if (!inheritsR.isEmpty()) {
			tmp = inheritsR(as);
			for (Triple t : tmp) {
				if (!res.contains(t)) res.add(t); 
			}
		}
		if (!differentR.isEmpty()) {
			tmp = differentR(as);
			for (Triple t : tmp) {
				if (!res.contains(t)) res.add(t); 
			}
		};
		return res;
	}
	
	/**
	 * Creates the inferences using the properties
	 * 
	 * @param db
	 * @return
	 */
	public ArrayList<Triple> applyPropertiesRelation(DB db) {
		ArrayList<Triple> res = new ArrayList<Triple>(), tmp;
		ArrayList<KModel> domains = new ArrayList<KModel>(), ranges = new ArrayList<KModel>();
		ArrayList<Triple> ts = db.findEveryTripleWith(this);
		for (Triple t : ts) {
			domains.add(t.getSource());
			ranges.add(t.getDestination());
		}
		res.addAll(applyProperties(domains, ranges));
		tmp = applyPropertiesOnRelation(domains, ranges);
		for (Triple t : tmp) {
			if (!res.contains(t)) res.add(t); 
		}
		return res;
	}
}