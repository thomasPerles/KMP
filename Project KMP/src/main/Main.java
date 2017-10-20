package main;

import kPackage.KClass;
import kPackage.KInstance;
import kPackage.KRelation;
import process.DB;
import process.Result;
import process.Rule;
import process.Rules;
import process.Triple;

public final class Main {

	public static void mainDeclarationTripleDbAlldatatolocalfile() {
		KInstance john = new KInstance("John");
		KInstance ensisa = new KInstance("ENSISA");
		KInstance mulhouse = new KInstance("Mulhouse");
		KInstance _x = new KInstance("?X");
		KInstance amy = new KInstance("Amy");
		KInstance softEng = new KInstance("SoftEng");
		KInstance _y = new KInstance("?Y");
		KInstance _z = new KInstance("?Z");
		KRelation worksFor = new KRelation("worksFor");
		KRelation isAt = new KRelation("isAt");
		KRelation isa = new KRelation("isA");
		
		Triple t1 = new Triple(john, worksFor, ensisa);
		Triple t2 = new Triple(ensisa, isAt, mulhouse);
		Triple t3 = new Triple(ensisa, isAt, _x);
		System.out.println(t1.toString());

		DB db = new DB();
		db.addDB(t1); 
		db.addDB(t2); 
		//db.addDB(t3); 
		db.addDB(new Triple(amy, worksFor, ensisa)); 
		db.addDB(new Triple(amy, isa, softEng));
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
		pat1.addDB(new Triple(_x, isAt, _y));
		pat1.addDB(new Triple(_z, worksFor, _x));
		pat1.addDB(new Triple(_z, isa, softEng));
		System.out.println(pat1 + "\n");
		System.out.println(db.answer(pat1));
		/*
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
		*/
	}
	
	public static void mainTerminal() {
		// Terminal terminal = new Terminal(System.in, System.out);
		// new Thread(terminal).start();
	}
	
	private static void mainProcessV2() {
		KInstance laurent = new KInstance("laurent");
		KInstance man = new KInstance("man");
		KInstance sophie = new KInstance("sophie");
		KInstance marriedInstance = new KInstance("married");	//TODO
		KInstance _x = new KInstance("?X");
		KInstance _y = new KInstance("?Y");
		KInstance type = new KInstance("type");
		KInstance _z = new KInstance("?Z");
		
		KClass symetric = new KClass("symetric");
		
		KRelation isa = new KRelation("isa");
		KRelation married = new KRelation("married");
		KRelation is = new KRelation("is");
		KRelation _xRelation = new KRelation("?X");		//TODO
		
		DB db = new DB();
		db.addDB(new Triple(laurent, isa, man));
		db.addDB(new Triple(laurent, married, sophie));
		db.addDB(new Triple(marriedInstance, is, symetric));	//TODO
		System.out.println("-- Facts --\n" + db.toString() + "\n");
		
		DB pat = new DB();
		pat.addDB(new Triple(_x, isa, man));
		pat.addDB(new Triple(_x, married, sophie));
		System.out.println("Query : " + pat.toString() + "\n");
		
		Result res = db.answer(pat);
		System.out.println("Results : " + res.toString() + "\n");
		
		DB preRule1 = new DB(), postRule1 = new DB();
		preRule1.addDB(new Triple(_y, isa, _x));
		postRule1.addDB(new Triple(_x, is, type));
		Rule rule1 = new Rule(preRule1, postRule1);
		
		DB preRule2 = new DB(), postRule2 = new DB();
		preRule2.addDB(new Triple(_x, is, symetric));
		preRule2.addDB(new Triple(_y, _xRelation, _z));
		postRule2.addDB(new Triple(_z, _xRelation, _y));
		Rule rule2 = new Rule(preRule2, postRule2);
		
		Rules rs = new Rules();
		rs.addRule(rule1);
		rs.addRule(rule2);
		rs.inference(db);
		System.out.println("\n-- Inference --\nRules : \n" + rs.toString() + "New Facts :\n" + db.toString() + "\n");
		
		/*
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
		*/
	}
	
	private static void mainSerializationStudents() {
		KClass humain = new KClass("humain");
		KClass matiere = new KClass("matiere");
		KClass ue = new KClass("UE");
		KClass note = new KClass("note");
		KClass eleve = new KClass("eleve");
		KClass enseignant = new KClass("enseignant");
		
		KRelation isa = new KRelation("isa");
		KRelation etudieAvec = new KRelation("etudieAvec");
		KRelation enseigneA = new KRelation("enseigneA");
		KRelation etudieEn = new KRelation("etudieEn");
		KRelation estDansLUE = new KRelation("estDansL'UE");
		KRelation aEu20En = new KRelation("aEu20En");
		KRelation aEu19En = new KRelation("aEu19En");
		KRelation aEu18En = new KRelation("aEu18En");
		KRelation aEu17En = new KRelation("aEu17En");
		
		KInstance thomas = new KInstance("thomas");
		KInstance icg = new KInstance("ICG");
		KInstance maths = new KInstance("Maths");
		KInstance mThiry = new KInstance("M.Thiry");
		KInstance informatique = new KInstance("informatique");
		KInstance _x = new KInstance("?X");
		KInstance _y = new KInstance("?Y");
		KInstance _z = new KInstance("?Z");
		
		DB db = new DB();
		/*
		//class declarations
		db.addDB(new Triple("humain", "isa", "classe"));
		db.addDB(new Triple("matiere", "isa", "classe"));
		db.addDB(new Triple("UE", "isa", "classe"));
		db.addDB(new Triple("note", "isa", "classe"));
		*/
		//class inheritance
		db.addDB(new Triple(eleve, isa, humain));
		db.addDB(new Triple(enseignant, isa, humain));
		//classes relation 
		db.addDB(new Triple(eleve, etudieAvec, enseignant));
		db.addDB(new Triple(enseignant, enseigneA, eleve));
		db.addDB(new Triple(eleve, etudieEn, matiere));
		db.addDB(new Triple(matiere, estDansLUE, ue));
		//problem with the relation 
		db.addDB(new Triple(eleve, aEu20En, matiere));
		db.addDB(new Triple(eleve, aEu19En, matiere));
		db.addDB(new Triple(eleve, aEu18En, matiere));
		db.addDB(new Triple(eleve, aEu17En, matiere));
		//instanciation
		db.addDB(new Triple(thomas, aEu20En, icg));
		db.addDB(new Triple(thomas, aEu17En, maths));
		db.addDB(new Triple(thomas, etudieAvec, mThiry));
		db.addDB(new Triple(icg, estDansLUE, informatique));
		
		System.out.println("-- Facts --\n" + db.toString() + "\n");
		
		DB pat = new DB();
		pat.addDB(new Triple(_x, aEu20En, _y));
		pat.addDB(new Triple(_y, estDansLUE, informatique));
		System.out.println("Query : " + pat.toString() + "\n");
		
		Result res = db.answer(pat);
		System.out.println("Results : " + res.toString() + "\n");
		
		DB preRule1 = new DB(), postRule1 = new DB();
		preRule1.addDB(new Triple(_y, isa, _x));
		//postRule1.addDB(new Triple(_x, is, type));
		Rule rule1 = new Rule(preRule1, postRule1);
		
		/*
		DB preRule2 = new DB(), postRule2 = new DB();
		preRule2.addDB(new Triple("?X", "is", "symetric"));
		preRule2.addDB(new Triple(_y, _x, _z));
		postRule2.addDB(new Triple(_z, _x, _y));
		Rule rule2 = new Rule(preRule2, postRule2);
		*/
		
		Rules rs = new Rules();
		rs.addRule(rule1);
		//rs.addRule(rule2);
		
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
		/*
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
		*/
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
		mainProcessV2();				//TODO
		//mainSerializationStudents();
		//mainSerializationEvent();
	}
}