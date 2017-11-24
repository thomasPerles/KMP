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
	
	public ArrayList<Triple> checkProperties(Triple triple) {
		ArrayList<Triple> triples = explodeTriple(triple), result = new ArrayList<Triple>();
		for (Triple t : triples) result.addAll(t.applyPropertiesTriple(this));
		return result;
	}
	
	public ArrayList<Triple> explodeTriple(Triple triple) {
		ArrayList<Triple> res = new ArrayList<Triple>();
		ArrayList<KObject> kObjects = getKObjectsOfTriple(triple);
		for (int i = 0; i < kObjects.size()-1; i=+2) {
			res.add(new Triple((KModel) kObjects.get(i), (KRelation) kObjects.get(i+1), (KModel) kObjects.get(i+2)));
		}
		return res;
	}
	
	private ArrayList<KObject> getKObjectsOfTriple(Triple triple) {
		ArrayList<KObject> res = new ArrayList<KObject>();
		res.add(triple.getSource());
		if (triple.getLink() instanceof KCompositeRelation) res.addAll(getKObjectsOfKCompositeRelation((KCompositeRelation) triple.getLink()));
		else res.add(triple.getLink());
		res.add(triple.getDestination());
		return res;
	}

	private ArrayList<KObject> getKObjectsOfKCompositeRelation(KCompositeRelation kcr) {
		ArrayList<KObject> res = new ArrayList<KObject>();
		res.add(kcr.getLeftLink());
		if (kcr.getModel() instanceof KCompositeModel) res.addAll(getKObjectsOfKCompositeModel((KCompositeModel) kcr.getModel()));
		else res.add(kcr.getModel());
		res.add(kcr.getRightLink());
		return res;
	}

	private ArrayList<KObject> getKObjectsOfKCompositeModel(KCompositeModel kcm) {
		ArrayList<KObject> res = new ArrayList<KObject>();
		res.add(kcm.getSource());
		if (kcm.getLink() instanceof KCompositeRelation) res.addAll(getKObjectsOfKCompositeRelation((KCompositeRelation) kcm.getLink()));
		else res.add(kcm.getLink());
		res.add(kcm.getDestination());
		return res;
	}

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
	
	public ArrayList<Triple> setRelation_symmetric(String relation) {
		//TODO
		return null;
	}

	public ArrayList<Triple> findEveryTripleWith(KObject kObject) {
		ArrayList<Triple> res = new ArrayList<Triple>();
		for (Triple t : dbTriple) {
			if (t.getSource().getId() == kObject.getId() || t.getLink().getId() == kObject.getId() || t.getDestination().getId() == kObject.getId()) res.add(t);
		}
		return res;
	}
}