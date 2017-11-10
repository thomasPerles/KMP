package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import kPackage.Triple;
import process.Context;
import process.DB;
import process.Result;
import process.Rule;
import process.Rules;

public class AllDataToLocalFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Context> contexts = new ArrayList<Context>();
	private ArrayList<DB> dbs = new ArrayList<DB>();
	private ArrayList<Result> results = new ArrayList<Result>();
	private ArrayList<Triple> triples = new ArrayList<Triple>();
	private ArrayList<Rule> rules = new ArrayList<Rule>(); //a supprimer ?
	private ArrayList<Rules> listRules = new ArrayList<Rules>();
	
	public AllDataToLocalFile() {}
	
	public AllDataToLocalFile(ArrayList<Context> contexts, ArrayList<DB> dbs, ArrayList<Result> results, ArrayList<Triple> triples) {
		this.contexts = contexts;
		this.dbs = dbs;
		this.results = results;
		this.triples = triples;
	}
	
	public void addContext(Context c) {
		this.contexts.add(c);
	}
	
	public void addDB(DB db) {
		this.dbs.add(db);
	}
	
	public void addResult(Result r) {
		this.results.add(r);
	}
	
	public void addTriple(Triple t) {
		this.triples.add(t);
	}
	
	//TODO a supprimer ?
	public void addRule(Rule r) {
		this.rules.add(r);
	}
	
	public void addRules(Rules r) {
		this.listRules.add(r);
	}

	public ArrayList<Context> getContexts() {
		return contexts;
	}

	public ArrayList<DB> getDbs() {
		return dbs;
	}

	public ArrayList<Result> getResults() {
		return results;
	}

	public ArrayList<Triple> getTriples() {
		return triples;
	}
	
	public ArrayList<Rule> getRules() {
		return rules;
	}

	public ArrayList<Rules> getListRules() {
		return listRules;
	}

	public String createPath(String fileName) {
		return System.getProperty("user.dir") + "/dataSerialized/" + fileName;
	}
	
	public void writeObject(String fileName) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(createPath(fileName));
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
		System.out.printf("data serialized in /dataSerialized/" + fileName);
	}
	
	public AllDataToLocalFile readObject(AllDataToLocalFile data, String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(data.createPath(fileName));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			data = (AllDataToLocalFile) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("\ndata read from " + createPath(fileName));
		return data;
	}

	@Override
	public String toString() {
		return "AllDataToLocalFile [contexts=" + contexts + ", dbs=" + dbs + ", results=" + results + ", triples="
				+ triples + ", rules=" + rules + ", listRules=" + listRules + "]";
	}
}