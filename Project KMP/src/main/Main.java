package main;

import process.DB;
import process.Triple;
import java.io.*;
import terminal.Terminal;

public final class Main {

	public static void main(String[] args) {
//		Triple t1 = new Triple("John","worksFor","ENSISA");
//		Triple t2 = new Triple("ENSISA","isAt","Mulhouse");
//		Triple t3 = new Triple("ENSISA","isAt","?X"); 
//		//System.out.println(t1.toString());
//
//		DB db = new DB();
//		db.addDB(t1); 
//		db.addDB(t2); 
//		db.addDB(t3); 
//		db.addDB(new Triple("Amy","worksFor","ENSISA")); 
//		db.addDB(new Triple("Amy","is","SoftEng"));
//		System.out.println(db + "\n");
//		
//		/*
//		Context c = new Context();
//		c.addContext("?X","Mulhouse");
//		System.out.println(c.toString());
//		System.out.println(c.getVariable("?X"));
//		System.out.println(c.valueMatch("?X", "a"));
//		
//		System.out.println(c.tripleMatch(new Triple("ENSISA","isAt","?X"),t2) + " with ");
//		System.out.println(c.toString() + "\n");
//
//		Triple pat = new Triple("ENSISA","isAt","?X");
//		System.out.println(pat.toString() + "\n");
//		System.out.println(db.dbMatch(pat) + "\n");
//		*/
//		
//		DB pat1 = new DB();
//		pat1.addDB(new Triple("?X","isAt","?Y"));
//		pat1.addDB(new Triple("?Z","worksFor","?X"));
//		pat1.addDB(new Triple("?Z","is","SoftEng"));
//		System.out.println(pat1 + "\n");
//		System.out.println(db.answer(pat1));
//		
//		/*
//		ArrayList<String> tests = new ArrayList<String>();
//		tests.add("123"); tests.add("456"); tests.add("789");
//		String test = "456";
//		System.out.println("vars contains var : " + tests.contains(test));
//		System.out.println("== : " + (tests.get(0) == test) + " or " + (tests.get(1) == test));
//		*/
		
		Terminal terminal = new Terminal(System.in, System.out);
		new Thread(terminal).start();

	}

}
