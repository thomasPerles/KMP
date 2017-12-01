package test;

import static org.junit.Assert.*;

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
		db.newStatement(new String[] { "Laurent", "isMarried", "Sophie" });
		assertTrue(db.getDBKObject().size() == 3 && db.getDBTriple().size() == 2);
	}

	@Test
	public void duplicationTest() {
		db.newStatement(new String[] { "Alice", "hasBrother", "Bob" });
		assertEquals(db.getDBKObject().size(), 3);
		assertEquals(db.getDBTriple().size(), 1);
		db.newStatement(new String[] { "Alice", "hasBrother", "Bob" });
		assertEquals(3, db.getDBKObject().size());
		assertEquals(1, db.getDBTriple().size());
	}

	@Test
	public void symmetricDuplicationTest() {
		db.newStatement(new String[] { "Alice", "hasSibling", "Bob" });
		assertEquals(3, db.getDBKObject().size());
		assertEquals(1, db.getDBTriple().size());
		db.newStatement(new String[] { "Bob", "hasSibling", "John" });
		assertEquals(2, db.getDBTriple().size());
		assertEquals(4, db.getDBKObject().size());
		db.setRelation_symmetric("hasSibling");
		assertEquals(4, db.getDBKObject().size());
		assertEquals(4, db.getDBTriple().size());
	}

	@Test
	public void transitiveTest() {
		db.setRelation_transitive("hasSibling");
		db.newStatement(new String[] {"Alice", "hasSibling", "Bob"});
		assertEquals(3, db.getDBKObject().size());
		assertEquals(1, db.getDBTriple().size());
		db.newStatement(new String[] {"Bob", "hasSibling", "Charles"});
		assertEquals(4, db.getDBKObject().size());
		assertEquals(3, db.getDBTriple().size());
		/*
		 * ["Alice", "hasSibling", "Bob"]
		 * ["Alice", "hasSibling", "Charles"]
		 * ["Bob", "hasSibling", "Charles"]
		 * ["Bob", "hasSibling", "Alice"]
		 * ["Charles", "hasSibling", "Alice"]
		 * ["Charles", "hasSibling", "Bob"]
		 */
	}

	
	
	
	/*
	@Test
	public void identicalRTest() {
		db.newStatement(new String[] {"A", "R1", "B"});
		db.newStatement(new String[] {"C", "R2", "D"});
		db.setRelation_identicalR("R1", "R2");
		assertEquals(6, db.getDBKObject().size());
		assertEquals(4, db.getDBTriple().size());
		db.newStatement(new String[] {"E", "R3", "F"});
		db.setRelation_identicalR("R1", "R3");
		assertEquals(9, db.getDBKObject().size());
		assertEquals(9, db.getDBTriple().size());
	}
	
	@Test
	public void identicalRTestTransitive() {
		db.newStatement(new String[] {"A", "R1", "B"});
		db.newStatement(new String[] {"B", "R2", "C"});
		db.setRelation_transitive("R1");
		db.setRelation_identicalR("R1", "R2");
		assertEquals(6, db.getDBKObject().size());
		assertEquals(6, db.getDBTriple().size());
	}
	
	@Test
	public void identicalRTestClassInstance() {
		db.newStatement(new String[] {"I1", "is", "C1"});
		db.newStatement(new String[] {"I2", "is", "C2"});
		db.newStatement(new String[] {"I1", "R1", "I2"});
		db.newStatement(new String[] {"A", "R2", "B"});
		db.setRelation_identicalR("R1", "R2");
		//TODO
		//assertTrue( A is C1, B is C2, R2.domain = C1, R2.range = C2 )
	}
	
	@Test
	public void identicalRTestInherits() {
		db.newStatement(new String[] {"R1", "inherits", "R"});
		db.setRelation_identicalR("R1", "R2");
		//TODO
		//assertTrue( R2 inherits R )
	}
	
	@Test
	public void identicalRTestDifferent() {
		db.newStatement(new String[] {"R1", "different", "R"});
		db.setRelation_identicalR("R1", "R2");
		//TODO
		//assertTrue( R2 different R )
	}
	
	@Test
	public void identicalRTestInheritsDifferent() {
		db.newStatement(new String[] {"R1", "inherits", "R"});
		db.setRelation_identicalR("R1", "R2");
		db.newStatement(new String[] {"R", "different", "R3"});
		//TODO
		//assertTrue( R2 inherits R, R1 different R3, R2 different R3 )
	}
	
	@Test
	public void identicalRTestFutureStatement() {
		db.setRelation_identicalR("R1", "R2");
		db.newStatement(new String[] {"A", "R1", "B"});
		//TODO
		//assertTrue( A R2 B )
	}
	*/
	
}
