package process;

import java.io.Serializable;
import java.util.ArrayList;

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
	private ArrayList<KObject> dbKoject = new ArrayList<KObject>();
	
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


	
	
	
	
	
	
	
	
	public void newStatement(String[] words) {
		for (int i = 0; i < words.length; i++) {
			if (!dbKoject.contains(words[i])) {
				if (i % 2 == 0) dbKoject.add(new KModel (words[i]));
				else dbKoject.add(new KRelation (words[i]));
			}
		}
		/*
		analyser le triple
		-insertion de KObject
		-insertion des triples
		verification db
		-verification de kr, kc, ki
		-ajout de nouveaux triples
		*/
		//TODO
		//cf StringProcessor : build Triple, KCompositeRelation, KCompositeModel
		/**
		 * Recursive function used in building a database triple.
		 * 
		 * @param tokens
		 *            An array of strings
		 * @return An instance of KRelation
		 */
		/*
		private KRelation buildKCompositeRelation(String[] tokens) {
			if (tokens.length > 1) {
				KRelation leftLink = new KRelation(tokens[0]);
				KRelation rightLink = new KRelation(tokens[tokens.length - 1]);
				tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
				KModel model = buildKCompositeModel(tokens);
				return new KCompositeRelation(leftLink, model, rightLink);
			}
			return new KRelation(tokens[0]);
		}*/

		/**
		 * Recursive function used in building a database triple.
		 * 
		 * @param tokens
		 *            An array of strings
		 * @return An instance of KModel
		 */
		/*
		private KModel buildKCompositeModel(String[] tokens) {
			if (tokens.length > 1) {
				KModel source = new KModel(tokens[0]);
				KModel destination = new KModel(tokens[tokens.length - 1]);
				tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
				KRelation link = buildKCompositeRelation(tokens);
				return new KCompositeModel(source, link, destination);
			}
			return new KModel(tokens[0]);
		}*/

		/**
		 * Constructs a database triple whose contents depend on the structure of
		 * tokens submitted by the user.
		 * 
		 * @param tokens
		 *            An array of strings
		 * @return An instance of Triple
		 */
		/*
		private Triple buildTriple(String[] tokens) {
			KCompositeModel temp = (KCompositeModel) buildKCompositeModel(tokens);
			return new Triple(temp.getSource(), temp.getLink(), temp.getDestination());
		}*/
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
}