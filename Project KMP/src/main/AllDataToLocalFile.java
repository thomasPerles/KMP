package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import process.Context;
import process.DB;
import process.Result;
import process.Triple;

public class AllDataToLocalFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Context> contexts = new ArrayList<Context>();
	private ArrayList<DB> dbs = new ArrayList<DB>();
	private ArrayList<Result> results = new ArrayList<Result>();
	private ArrayList<Triple> triples = new ArrayList<Triple>();
	
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
	
	public String createPath(String fileName) {
		return System.getProperty("user.dir") + "/dataSerialized/" + fileName;
	}
	
<<<<<<< Updated upstream
	public void writeObject(String fileName) throws IOException {
=======
	public void writeInLocalFile(String fileName) throws IOException {
>>>>>>> Stashed changes
		FileOutputStream fileOut = new FileOutputStream(createPath(fileName));
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
		System.out.printf("data serialized in /dataSerialized/" + fileName);
	}
	
<<<<<<< Updated upstream
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
=======
	public AllDataToLocalFile readInLocalFile(AllDataToLocalFile data, String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(createPath(fileName));
		ObjectInputStream in = new ObjectInputStream(fileIn);
		System.out.println(in.readObject());
		data = (AllDataToLocalFile) in.readObject();
		//faire un parser
		in.close();
		fileIn.close();
		System.out.println("\ndata read from " + createPath(fileName));
>>>>>>> Stashed changes
		return data;
	}

	@Override
	public String toString() {
		return "AllDataToLocalFile [contexts=" + contexts + ", dbs=" + dbs + ", results=" + results + ", triples="
				+ triples + "]";
	}
}