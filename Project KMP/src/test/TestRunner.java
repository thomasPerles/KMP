package test;

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
	public void newStatementTest() {
		db.setRelation_symmetric("isMarried");
		db.newStatement(new String[]{"Laurent", "isMarried", "Sophie"});
		assertTrue(db.getDBKObject().size == 3 && db.getDBTriple().size == 2);
	}

}
