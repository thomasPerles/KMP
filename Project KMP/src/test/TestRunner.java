package test;

import static org.junit.Assert.assertTrue;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import language.StringProcessor;
import process.DB;

public class TestRunner {

	private StringProcessor sp;
	private DB db;
	
	@Before
	public void before() {
		sp = new StringProcessor(ResourceBundle.getBundle("language/ApplicationMessages_Natural"));
		db = new DB();
		sp.setDB(db);
	}
	
	@Test
	public void symmetricTest() {
		db.setRelation_symmetric("isMarried");
		db.newStatement(new String[]{"Laurent", "isMarried", "Sophie"});
		assertTrue(db.getDBKObject().size() == 3 && db.getDBTriple().size() == 2);
	}
	
	@Test
	public void transitiveTest() {
		db.setRelation_transitive("hasSibling");
		db.setRelation_symmetric("hasSibling");
		db.newStatement(new String[] {"Alice", "hasSibling", "Bob"});
		assertTrue(db.getDBKObject().size() == 3 && db.getDBTriple().size() == 2);
		db.newStatement(new String[] {"Bob", "hasSibling", "Charles"});
		assertTrue(db.getDBKObject().size() == 4 && db.getDBTriple().size() == 6);
		/*
		 * ["Alice", "hasSibling", "Bob"]
		 * ["Alice", "hasSibling", "Charles"]
		 * ["Bob", "hasSibling", "Charles"]
		 * ["Bob", "hasSibling", "Alice"]
		 * ["Charles", "hasSibling", "Alice"]
		 * ["Charles", "hasSibling", "Bob"]
		 */
	}

}
