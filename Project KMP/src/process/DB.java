package process;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import kPackage.KClass;
import kPackage.KCompositeModel;
import kPackage.KCompositeRelation;
import kPackage.KInstance;
import kPackage.KModel;
import kPackage.KObject;
import kPackage.KRelation;
import kPackage.Triple;

public class DB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Triple> dbTriple = new ArrayList<Triple>();
	private ArrayList<KObject> dbKObject = new ArrayList<KObject>();
	
	public DB() {
		this.dbTriple = new ArrayList<Triple>();
	}
	
	public ArrayList<Triple> getTs() {
		return dbTriple;
	}

	public void addDB(Triple t) {
		this.dbTriple.add(t);
	}
	
	public String toString() {
		String res = "";
		for (int i = 0; i < dbTriple.size()-1; i++) {
			res += dbTriple.get(i).toString() + " and ";
		}
		if (dbTriple.size() != 0) return res + dbTriple.get(dbTriple.size()-1).toString();
		else return res;
	}
	
	public Result dbMatch(Triple pat) {
		Result r = new Result();
		Context c;
		for (int i = 0; i < r.getCs().size(); i++) {
			c = new Context();
			if (c.tripleMatch(pat, this.dbTriple.get(i))) r.addResult(c);
		}
		return r;                                                                                                                                                                                                                                                                                                                                                                                                            
	}
	
	public Result dbMatch2(Triple pat, Context c) {
		Result r = new Result();
		for (int i = 0; i < dbTriple.size(); i++) {
			Context c2 = c.cloneContext();
			if (c2.tripleMatch(pat, dbTriple.get(i))) r.addResult(c2);
		}
		return r;
	}
	
	//revoir le code C
	public Result answer(DB query) {
		Result r0 = this.dbMatch2(query.getTs().get(0), new Context());
		for (int j = 1; j < query.getTs().size(); j++) {
			Result r1 = new Result();
			for (int i = 0; i < r0.getCs().size(); i++) {
				r1.mergeResult(this.dbMatch2(query.getTs().get(j), r0.getCs().get(i)));
			}
			r0 = r1;
		}
		return r0;
	}
	
	
	
	//v2
	public DB evaluateDB(Context c) {
		DB r = new DB();
		for (int i = 0; i < this.dbTriple.size(); i++) {
			r.addDB(c.evaluateTriple(this.getTs().get(i)));
		}
		return r;
	}
	
	public void mergeDB(DB db) {
		for(int i = 0; i < db.getTs().size(); i++) {
			this.addDB(db.getTs().get(i));
		}
	}
	
	public boolean contains(Triple t) {
		for (int i = 0; i < this.dbTriple.size(); i++) {
			if (this.dbTriple.get(i).equal(t))
				return true;
		}
		return false;
	}
	
	public boolean unionDB(DB db) {
		boolean changed = false;
		for (int i = 0; i < db.getTs().size(); i++) {
			if (!this.contains(db.getTs().get(i))) {
				this.addDB(db.getTs().get(i));
				changed = true;
			}
		}
		return changed;
	}


	
	
	
	
	
	
	
	
	public ArrayList<Triple> getDBTriple() {
		return dbTriple;
	}

	public ArrayList<KObject> getDBKObject() {
		return dbKObject;
	}

	/**
	 * Creates the Triple (a Triple or a composition of triples)
	 * adds the new models in dbKObject
	 * adds the triple in dbTriple
	 * creates the inferences of the triple
	 * adds the new inferences in dbTriple
	 *  
	 * @param words
	 * 			a list of words, number impair to create a composition of Triple 
	 */
	public void newStatement(String[] words) {
		Triple triple = buildTriple(words);
		if (!dbTriple.contains(triple)) dbTriple.add(triple);
		addListTripleToDBTriple(checkProperties(triple));
	}
	
	/**
	 * Constructs a database triple whose contents depend on the structure of
	 * tokens submitted by the user.
	 * 
	 * @param tokens
	 *            An array of strings
	 * @return An instance of Triple
	 */
	private Triple buildTriple(String[] tokens) {
		KCompositeModel temp = (KCompositeModel) buildKCompositeModel(tokens);
		return new Triple(temp.getSource(), temp.getLink(), temp.getDestination());
	}
	
	/**
	 * Recursive function used in building a database triple.
	 * 
	 * @param tokens
	 *            An array of strings
	 * @return An instance of KModel
	 */
	private KModel buildKCompositeModel(String[] tokens) {
		if (tokens.length > 1) {
			KModel source = new KModel(tokens[0]);
			if (!dbKObject.contains(source)) dbKObject.add(source);
			KModel destination = new KModel(tokens[tokens.length - 1]);
			if (!dbKObject.contains(destination)) dbKObject.add(destination);
			tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
			KRelation link = buildKCompositeRelation(tokens);
			return new KCompositeModel(source, link, destination);
		}
		
		for(KObject ko : dbKObject) {
			if (ko.getId().equals(tokens[0]))
				if (ko instanceof KClass)
					return (KClass) ko;
				if (ko instanceof KObject)
					return (KInstance) ko;
				if (ko instanceof KModel)
					return (KModel) ko;
		}
		
		KModel kModel = new KModel(tokens[0]);
		if (!dbKObject.contains(kModel)) dbKObject.add(kModel);
		return kModel;
	}
	
	/**
	 * Recursive function used in building a database triple.
	 * 
	 * @param tokens
	 *            An array of strings
	 * @return An instance of KRelation
	 */
	private KRelation buildKCompositeRelation(String[] tokens) {
		if (tokens.length > 1) {
			KRelation leftLink = new KRelation(tokens[0]);
			if (!dbKObject.contains(leftLink)) dbKObject.add(leftLink);
			KRelation rightLink = new KRelation(tokens[tokens.length - 1]);
			if (!dbKObject.contains(rightLink)) dbKObject.add(rightLink);
			tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
			KModel model = buildKCompositeModel(tokens);
			return new KCompositeRelation(leftLink, model, rightLink);
		}
		
		for(KObject ko : dbKObject) {
			if(ko.getId().equals(tokens[0])) {
				if(ko instanceof KRelation)
					return (KRelation) ko;
			}
		}
		KRelation kRelation = new KRelation(tokens[0]);
		if (!dbKObject.contains(kRelation)) dbKObject.add(kRelation);
		return kRelation;
	}
	
	/**
	 * Splits the Triple/composite
	 * creates the inferences by checking the properties of the KObjects
	 * 
	 * @param triple
	 * @return
	 */
	public ArrayList<Triple> checkProperties(Triple triple) {
		ArrayList<Triple> triples = explodeTriple(triple), result = new ArrayList<Triple>();
		for (Triple t : triples) result.addAll(t.applyPropertiesTriple(this));
		return result;
	}
	
	/**
	 * Splits the Triple/composite to get a list of triples
	 * 
	 * @param triple
	 * @return
	 */
	public ArrayList<Triple> explodeTriple(Triple triple) {
		ArrayList<Triple> res = new ArrayList<Triple>();
		ArrayList<KObject> kObjects = getKObjectsOfTriple(triple);
		for (int i = 0; i < kObjects.size()-1; i=+2) {
			res.add(new Triple((KModel) kObjects.get(i), (KRelation) kObjects.get(i+1), (KModel) kObjects.get(i+2)));
		}
		return res;
	}
	
	/**
	 * Returns all the KClass, KInstance and KRelation (from source to destination) of the triple
	 * 
	 * @param triple
	 * @return
	 */
	private ArrayList<KObject> getKObjectsOfTriple(Triple triple) {
		ArrayList<KObject> res = new ArrayList<KObject>();
		res.add(triple.getSource());
		if (triple.getLink() instanceof KCompositeRelation) res.addAll(getKObjectsOfKCompositeRelation((KCompositeRelation) triple.getLink()));
		else res.add(triple.getLink());
		res.add(triple.getDestination());
		return res;
	}

	/**
	 * Returns the two KRelations and the KObjects (KClass, KInstance, KRelation) between
	 *  
	 * @param kcr
	 * @return
	 */
	private ArrayList<KObject> getKObjectsOfKCompositeRelation(KCompositeRelation kcr) {
		ArrayList<KObject> res = new ArrayList<KObject>();
		res.add(kcr.getLeftLink());
		if (kcr.getModel() instanceof KCompositeModel) res.addAll(getKObjectsOfKCompositeModel((KCompositeModel) kcr.getModel()));
		else res.add(kcr.getModel());
		res.add(kcr.getRightLink());
		return res;
	}

	/**
	 * Returns the two KClasses/KInstances and the KObjects (KClass, KInstance, KRelation) between
	 * 
	 * @param kcm
	 * @return
	 */
	private ArrayList<KObject> getKObjectsOfKCompositeModel(KCompositeModel kcm) {
		ArrayList<KObject> res = new ArrayList<KObject>();
		res.add(kcm.getSource());
		if (kcm.getLink() instanceof KCompositeRelation) res.addAll(getKObjectsOfKCompositeRelation((KCompositeRelation) kcm.getLink()));
		else res.add(kcm.getLink());
		res.add(kcm.getDestination());
		return res;
	}

	/**
	 * Adds to dbTriple only the triples not already in dbTriple
	 *  
	 * @param arr
	 */
	public void addListTripleToDBTriple(ArrayList<Triple> arr) {
		for (Triple triple : arr) {
			if (!dbTriple.contains(triple)) dbTriple.add(triple);
		}
	}
	
	public Context request(String[] words) {
		//TODO
		// pour faire des requêtes, ex : (?x, isMarried, ?y)
		return null;
	}
	
	public void establish() {
		//TODO
	}
	
	public void separate() {
		//TODO
	}
	
	public void define() {
		//TODO
	}
	
	/**
	 * Returns all the Triples containing the KObject
	 * 
	 * @param kObject
	 * @return
	 */
	public ArrayList<Triple> findEveryTripleWith(KObject kObject) {
		ArrayList<Triple> res = new ArrayList<Triple>();
		for (Triple t : dbTriple) {
			if (t.getSource().getId() == kObject.getId() || t.getLink().getId() == kObject.getId() || t.getDestination().getId() == kObject.getId()) res.add(t);
		}
		return res;
	}

	/**
	 * Searches the KRelation in dbKObject
	 * creates it if necessary
	 * sets it to symmetric
	 * adds the inferences generated by the property
	 * 
	 * @param relation
	 * @return
	 */
	public ArrayList<Triple> setRelation_symmetric(String relation) {
		ArrayList<Triple> res = new ArrayList<Triple>();
		boolean found = false;
		KRelation kr = null;
		for (KObject ko : dbKObject) {
			if (ko.getId() == relation) {
				found = true;
				kr = (KRelation) ko;
			}
		}
		if (!found) {
			kr = new KRelation(relation);
			kr.setSymmetric(true);
			dbKObject.add(kr);
		} else {
			ArrayList<Triple> triples = findEveryTripleWith(kr);
			Triple tmp;
			for (Triple triple : triples) {
				tmp = kr.symmetric(triple);
				if (!dbTriple.contains(tmp)) res.add(tmp);
			}
		}
		return res;
	}

	/**
	 * Searches the KRelation in dbKObject
	 * creates it if necessary
	 * sets it to transitive
	 * adds the inferences generated by the property
	 * 
	 * @param string
	 * @return
	 */
	public ArrayList<Triple> setRelation_transitive(String string) {
		ArrayList<Triple> res = new ArrayList<Triple>();
		boolean found = false;
		KRelation kr = null;
		for (KObject ko : dbKObject) {
			if (ko.getId() == string) {
				found = true;
				kr = (KRelation) ko;
			}
		}
		if (!found) {
			kr = new KRelation(string);
			kr.setTransitive(true);
			dbKObject.add(kr);
		} else {
			ArrayList<Triple> triples = findEveryTripleWith(kr);
			Triple tmp;
			if (triples.size() > 1) {
				for (int i = 0; i < triples.size(); i++) {
					for (int j = i+1; j < triples.size(); j++) {
						tmp = kr.transitive(triples.get(i), triples.get(j));
						if (!dbTriple.contains(tmp)) res.add(tmp);
					}
				}
			}
		}
		return res;
	}
}