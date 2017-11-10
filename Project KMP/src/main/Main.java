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

	}
	
	
	public static void main(String[] args) {
		mainDeclarationTripleDbAlldatatolocalfile();
	}
}