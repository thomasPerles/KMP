package main;

import process.*;
import process2.AllDataToLocalFile;

import java.io.*;
import terminal.Terminal;

public final class Main {

	public static void mainDeclarationTripleDbAlldatatolocalfile() {
		Triple t1 = new Triple("John","worksFor","ENSISA");
		Triple t2 = new Triple("ENSISA","isAt","Mulhouse");
		Triple t3 = new Triple("ENSISA","isAt","?X"); 
		System.out.println(t1.toString());

		DB db = new DB();
		db.addDB(t1); 
		db.addDB(t2); 
		db.addDB(t3); 
		db.addDB(new Triple("Amy","worksFor","ENSISA")); 
		db.addDB(new Triple("Amy","is","SoftEng"));
		System.out.println(db + "\n");
		
		/*
		Context c = new Context();
		c.addContext("?X","Mulhouse");
		System.out.println(c.toString());
		System.out.println(c.getVariable("?X"));
		System.out.println(c.valueMatch("?X", "a"));
		
		System.out.println(c.tripleMatch(new Triple("ENSISA","isAt","?X"),t2) + " with ");
		System.out.println(c.toString() + "\n");

		Triple pat = new Triple("ENSISA","isAt","?X");
		System.out.println(pat.toString() + "\n");
		System.out.println(db.dbMatch(pat) + "\n");
		*/
		
		DB pat1 = new DB();
		pat1.addDB(new Triple("?X","isAt","?Y"));
		pat1.addDB(new Triple("?Z","worksFor","?X"));
		pat1.addDB(new Triple("?Z","is","SoftEng"));
		System.out.println(pat1 + "\n");
		System.out.println(db.answer(pat1));
		
		AllDataToLocalFile data = new AllDataToLocalFile();
		data.addTriple(t1);
		data.addTriple(t2);
		data.addTriple(t3);
		data.addDB(db);
		data.addDB(pat1);

		//serialisation
		System.out.println("\nserialization");
		try {
			data.writeObject("tmp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//deserialization
		System.out.println("\n\ndeserialization");
		AllDataToLocalFile data2 = new AllDataToLocalFile();
		try {
			data2 = data2.readObject(data2, "tmp");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("data read from " + data2.createPath("tmp"));
	}
	
	public static void mainTerminal() {
		// Terminal terminal = new Terminal(System.in, System.out);
		// new Thread(terminal).start();
	}
	
	private static void mainProcessV2() {
		DB db = new DB();
		db.addDB(new Triple("laurent", "isa", "man"));
		db.addDB(new Triple("laurent", "married", "sophie"));
		db.addDB(new Triple("married", "is", "symetric"));
		System.out.println("-- Facts --\n" + db.toString() + "\n");
		
		DB pat = new DB();
		pat.addDB(new Triple("?X", "isa", "man"));
		pat.addDB(new Triple("?X", "married", "sophie"));
		System.out.println("Query : " + pat.toString() + "\n");
		
		Result res = db.answer(pat);
		System.out.println("Results : " + res.toString() + "\n");
		
		DB preRule1 = new DB(), postRule1 = new DB();
		preRule1.addDB(new Triple("?Y", "isa", "?X"));
		postRule1.addDB(new Triple("?X", "is", "type"));
		Rule rule1 = new Rule(preRule1, postRule1);
		
		DB preRule2 = new DB(), postRule2 = new DB();
		preRule2.addDB(new Triple("?X", "is", "symetric"));
		preRule2.addDB(new Triple("?Y", "?X", "?Z"));
		postRule2.addDB(new Triple("?Z", "?X", "?Y"));
		Rule rule2 = new Rule(preRule2, postRule2);
		
		Rules rs = new Rules();
		rs.addRule(rule1);
		rs.addRule(rule2);
		rs.inference(db);
		System.out.println("\n-- Inference --\nRules : \n" + rs.toString() + "New Facts :\n" + db.toString() + "\n");
		
		AllDataToLocalFile data = new AllDataToLocalFile();
		data.addDB(db);
		data.addDB(pat);
		data.addResult(res);
		data.addRules(rs);
		
		//serialisation
		System.out.println("\nserialization");
		try {
			data.writeObject("firstExample");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//deserialization
		System.out.println("\n\ndeserialization");
		AllDataToLocalFile data2 = new AllDataToLocalFile();
		try {
			data2 = data2.readObject(data2, "firstExample");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("data read from " + data2.createPath("firstExample"));
	}
	
	private static void mainSerializationStudents() {
		DB db = new DB();
		//class declarations
		db.addDB(new Triple("humain", "isa", "classe"));
		db.addDB(new Triple("matiere", "isa", "classe"));
		db.addDB(new Triple("UE", "isa", "classe"));
		db.addDB(new Triple("note", "isa", "classe"));
		//class inheritance
		db.addDB(new Triple("eleve", "isa", "humain"));
		db.addDB(new Triple("enseignant", "isa", "humain"));
		//classes relation 
		db.addDB(new Triple("eleve", "etudieAvec", "enseignant"));
		db.addDB(new Triple("enseignant", "enseigneA", "eleve"));
		db.addDB(new Triple("eleve", "etudieEn", "matiere"));
		db.addDB(new Triple("matiere", "estDansL'UE", "UE"));
		//problem with the relation 
		db.addDB(new Triple("eleve", "aEu20En", "matiere"));
		db.addDB(new Triple("eleve", "aEu19En", "matiere"));
		db.addDB(new Triple("eleve", "aEu18En", "matiere"));
		db.addDB(new Triple("eleve", "aEu17En", "matiere"));
		//instanciation
		db.addDB(new Triple("thomas", "aEu20En", "ICG"));
		db.addDB(new Triple("thomas", "aEu17En", "Maths"));
		db.addDB(new Triple("thomas", "etudieAvec", "M.Thiry"));
		db.addDB(new Triple("ICG", "estDansL'UE", "informatique"));
		
		System.out.println("-- Facts --\n" + db.toString() + "\n");
		
		DB pat = new DB();
		pat.addDB(new Triple("?X", "aEu20En", "?Y"));
		pat.addDB(new Triple("?Y", "estDansL'UE", "informatique"));
		System.out.println("Query : " + pat.toString() + "\n");
		
		Result res = db.answer(pat);
		System.out.println("Results : " + res.toString() + "\n");
		
		DB preRule1 = new DB(), postRule1 = new DB();
		preRule1.addDB(new Triple("?Y", "isa", "?X"));
		postRule1.addDB(new Triple("?X", "is", "type"));
		Rule rule1 = new Rule(preRule1, postRule1);
		
		DB preRule2 = new DB(), postRule2 = new DB();
		preRule2.addDB(new Triple("?X", "is", "symetric"));
		preRule2.addDB(new Triple("?Y", "?X", "?Z"));
		postRule2.addDB(new Triple("?Z", "?X", "?Y"));
		Rule rule2 = new Rule(preRule2, postRule2);
		
		Rules rs = new Rules();
		rs.addRule(rule1);
		rs.addRule(rule2);
		
		/*
		AllDataToLocalFile data = new AllDataToLocalFile();
		data.addDB(db);
		data.addDB(pat);
		data.addResult(res);
		data.addRules(rs);
		
		//serialisation
		System.out.println("\nserialization");
		try {
			data.writeObject("studentSerialization");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//deserialization
		System.out.println("\n\ndeserialization");
		AllDataToLocalFile data2 = new AllDataToLocalFile();
		try {
			data2 = data2.readObject(data2, "studentSerialization");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("data read from " + data2.createPath("studentSerialization"));
		*/
	}
	
	private static void mainSerializationEvent() {
		DB db = new DB();
		//classes relation 
		db.addDB(new Triple("groupeDeMusique", "jouePour", "festival"));
		db.addDB(new Triple("societeIngeSon", "travailleLeSonPour", "festival"));
		db.addDB(new Triple("societeIngeLumiere", "travailleLaLumierePour", "festival"));
		db.addDB(new Triple("societePhotographe", "FilmeLe", "festival")); 
		//instanciation
		db.addDB(new Triple("Noumatrouff", "isa", "festival"));
		db.addDB(new Triple("RockingButterflies", "isa", "groupeDeMusique"));
		db.addDB(new Triple("ENSISAWerner", "isa", "societeIngeSon"));
		db.addDB(new Triple("ENSISALumiere", "isa", "societeIngeLumiere"));
		db.addDB(new Triple("ENSISAPostBac", "isa", "societePhotographe")); 
		db.addDB(new Triple("RockingButterflies", "jouePour", "Noumatrouff"));
		db.addDB(new Triple("ENSISAWerner", "travailleLeSonPour", "Noumatrouff"));
		db.addDB(new Triple("ENSISALumiere", "travailleLaLumierePour", "Noumatrouff"));
		db.addDB(new Triple("ENSISAPostBac", "FilmeLe", "Noumatrouff")); 
		
		System.out.println("-- Facts --\n" + db.toString() + "\n");
		
		DB pat = new DB();
		pat.addDB(new Triple("?Z", "isa", "?Y"));
		pat.addDB(new Triple("?X", "jouePour", "?Z"));
		pat.addDB(new Triple("?X", "?W", "groupeDeMusique"));
		System.out.println("Query : " + pat.toString() + "\n");
		
		Result res = db.answer(pat);
		System.out.println("Results : " + res.toString() + "\n");
		
		DB preRule1 = new DB(), postRule1 = new DB();
		preRule1.addDB(new Triple("?Y", "isa", "?X"));
		postRule1.addDB(new Triple("?X", "is", "type"));
		Rule rule1 = new Rule(preRule1, postRule1);
		
		DB preRule2 = new DB(), postRule2 = new DB();
		preRule2.addDB(new Triple("?X", "is", "symetric"));
		preRule2.addDB(new Triple("?Y", "?X", "?Z"));
		postRule2.addDB(new Triple("?Z", "?X", "?Y"));
		Rule rule2 = new Rule(preRule2, postRule2);
		
		Rules rs = new Rules();
		rs.addRule(rule1);
		rs.addRule(rule2);
		
		/*
		AllDataToLocalFile data = new AllDataToLocalFile();
		data.addDB(db);
		data.addDB(pat);
		data.addResult(res);
		data.addRules(rs);
		
		//serialisation
		System.out.println("\nserialization");
		try {
			data.writeObject("eventSerialization");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//deserialization
		System.out.println("\n\ndeserialization");
		AllDataToLocalFile data2 = new AllDataToLocalFile();
		try {
			data2 = data2.readObject(data2, "eventSerialization");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("data read from " + data2.createPath("eventSerialization"));
		*/
	}
	
	public static void main(String[] args) {
		//mainDeclarationTripleDbAlldatatolocalfile();
		//mainTerminal();
		//mainProcessV2();
		//mainSerializationStudents();
		mainSerializationEvent();
	}
}