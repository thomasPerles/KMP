package language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import kPackage.KCompositeModel;
import kPackage.KCompositeRelation;
import kPackage.KModel;
import kPackage.KRelation;
import kPackage.Triple;
import process.DB;
import terminal.Terminal;

public class StringProcessor {

	private ResourceBundle bundle;
	private Terminal terminal;
	private ArrayList<String[]> patterns;
	private DB db;

	public StringProcessor(ResourceBundle bundle) {
		this.bundle = bundle;
		this.patterns = new ArrayList<String[]>();
		this.db = new DB();
		loadGrammar();
	}
	
	public void setDB(DB db) {
		this.db = db;
	}

	/**
	 * Loads internal grammar used by KMP. The grammar is (should be) EBNF
	 * compliant.
	 */
	private void loadGrammar() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/language/grammar.txt")));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] splittedLine = line.split("\\|=");
				if (splittedLine.length > 1) {
					String[] followers = splittedLine[1].split(" ");
					patterns.add(followers);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	private void printSuggestion(String[] pattern) {
		terminal.println("\t");
		for (String word : pattern) {
			terminal.print(' ' + word);
		}
	}

	/**
	 * Offers suggestions as to what the user may want to say according to
	 * submitted tokens.
	 * 
	 * @param tokens
	 *            An array of strings
	 */
	private void suggest(String[] tokens) {
		for (String[] pattern : patterns) {
			for (String token : tokens) {
				if (Arrays.asList(pattern).contains(token)) {
					printSuggestion(pattern);
				}
			}
		}
	}

	
	
	
	
	
	//TODO suppress build methods ?
	
	
	
	
	
	
	
	/**
	 * Recursive function used in building a database triple.
	 * 
	 * @param tokens
	 *            An array of strings
	 * @return An instance of KRelation
	 */
	private KRelation buildKCompositeRelation(String[] tokens) {
		if (tokens.length > 1) {
			KRelation leftLink = new KRelation(tokens[0]);
			KRelation rightLink = new KRelation(tokens[tokens.length - 1]);
			tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
			KModel model = buildKCompositeModel(tokens);
			return new KCompositeRelation(leftLink, model, rightLink);
		}
		return new KRelation(tokens[0]);
	}

	/**
	 * Recursive function used in building a database triple.
	 * 
	 * @param tokens
	 *            An array of strings
	 * @return An instance of KModel
	 */
	private KModel buildKCompositeModel(String[] tokens) {
		if (tokens.length > 1) {
			KModel source = new KModel(tokens[0]);
			KModel destination = new KModel(tokens[tokens.length - 1]);
			tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
			KRelation link = buildKCompositeRelation(tokens);
			return new KCompositeModel(source, link, destination);
		}
		return new KModel(tokens[0]);
	}

	/**
	 * Constructs a database triple whose contents depend on the structure of
	 * tokens submitted by the user.
	 * 
	 * @param tokens
	 *            An array of strings
	 * @return An instance of Triple
	 */
	private Triple buildTriple(String[] tokens) {
		KCompositeModel temp = (KCompositeModel) buildKCompositeModel(tokens);
		return new Triple(temp.getSource(), temp.getLink(), temp.getDestination());
	}

	/**
	 * Processes incoming tokens from the terminal. This is a multi-part process
	 * involving keyword identification, syntax verification, token parsing and
	 * feeding the information to the database.
	 * 
	 * @param tokens
	 * @throws IllegalWordCountException
	 *             - if the number of tokens is incoherent.
	 */
	public void process(String[] tokens) throws IllegalWordCountException {

		if (tokens.length % 2 != 1 && tokens.length != 2) {
			throw new IllegalWordCountException();
		}

		// keyword identification

		for (String token : tokens) {
			boolean keywordFound = false;

			switch (token) {
			case "has":
				addInstanceToClass(tokens[0], tokens[2]);
				break;
			case "isC":
				defineInstanceOfClass(tokens[0], tokens[2]);
				break;
			case "equivalentC":
				establishClassEquivalence(tokens[0], tokens[2]);
				break;
			case "inheritsC":
				establishClassInheritance(tokens[0], tokens[2]);
				break;
			case "differentC":
				seperateClasses(tokens[0], tokens[2]);
				break;
			case "equivalentI":
				establishInstanceEquivalence(tokens[0], tokens[2]);
				break;
			case "differentI":
				seperateInstances(tokens[0], tokens[2]);
				break;
			case "equivalentR":
				establishRelationEquivalence(tokens[0], tokens[2]);
				break;
			case "inheritsR":
				establishRelationInheritance(tokens[0], tokens[2]);
				break;
			case "differentR":
				seperateRelations(tokens[0], tokens[2]);
				break;
			case "isR":
				try {
					updateRelation(tokens[0], tokens[2]);
				} catch (IllegalTokenException e) {
					e.printStackTrace();
				}
			case "help":
				suggest(tokens);
				break;
			case "show":
				display(tokens);
				break;
			}

			if (token.charAt(0) == '?') {
				// db.request(tokens);
			}
			if (keywordFound)
				return;
		}

		// Send it off to the database for validation and CRUD operations
		// db.newStatement(tokens);
	}

	/**
	 * Tells the database to update the relation's qualifier status.
	 * 
	 * @param relation
	 *            A string representing a KRelation
	 * @param qualifier
	 *            A string representing a qualifier
	 * @throws IllegalTokenException
	 *             - if the qualifier token is not recognized
	 */
	private void updateRelation(String relation, String qualifier) throws IllegalTokenException {
		// TODO Auto-generated method stub
		for (String[] phrase : patterns) {
			if (Arrays.asList(phrase).contains(qualifier)) {
				Class[] params = new Class[1];
				params[0] = String.class;
				try {
					Method changeQualifierMethod = db.getClass().getMethod("setRelation_" + qualifier, params);
					changeQualifierMethod.invoke(db, relation);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		throw new IllegalTokenException(qualifier);
	}

	/**
	 * Tells the database that first and second token are two different
	 * relations. This is the default behavior for any two relations.
	 * 
	 * @param first
	 *            A string representing a KRelation
	 * @param second
	 *            A string representing a KRelation
	 */
	private void seperateRelations(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database that the first token now inherits the properties of
	 * the second token.
	 * 
	 * @param first
	 *            A string representing a KRelation
	 * @param second
	 *            A string representing a KRelation
	 */
	private void establishRelationInheritance(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database that the first and second token are the same relation.
	 * 
	 * @param first
	 *            A string representing a KRelation
	 * @param second
	 *            A string representing a KRelation
	 */
	private void establishRelationEquivalence(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database that the first and second token are different
	 * instances. This is the default behavior for any two instances.
	 * 
	 * @param first
	 *            A string representing a KInstance
	 * @param second
	 *            A string representing a KInstance
	 */
	private void seperateInstances(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database that the first and second tokens are the same
	 * instance.
	 * 
	 * @param first
	 *            A string representing a KInstance
	 * @param second
	 *            A string representing a KInstance
	 */
	private void establishInstanceEquivalence(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database that the first and second tokens are different and do
	 * not share any common properties or instances. This is the default
	 * behavior for any two classes.
	 * 
	 * @param first
	 *            A string representing a KClass
	 * @param second
	 *            A string representing a KClass
	 */
	private void seperateClasses(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database to assign an inheritance relation between the first
	 * and the second token.
	 * 
	 * @param first
	 *            A string representing a KClass
	 * @param second
	 *            A string representing a KClass
	 */
	private void establishClassInheritance(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database that the first token is an equivalent class to the
	 * second token.
	 * 
	 * @param first
	 *            A string representing a KClass
	 * @param second
	 *            A string representing a KClass
	 */
	private void establishClassEquivalence(String first, String second) {
		// TODO Auto-generated method stub

	}

	/**
	 * Tells the database that the first token is now (if not currently) to be
	 * recognized as an instance of the second token.
	 * 
	 * @param first
	 *            A string representing a KModel
	 * @param second
	 *            A string representing a KModel
	 */
	private void defineInstanceOfClass(String first, String second) {
		// TODO
	}

	/**
	 * Tells the database that the first token is to be recognised as a class
	 * whose instances include the second token.
	 * 
	 * @param first
	 *            A string representing a KModel
	 * @param second
	 *            A string representing a KModel
	 */
	private void addInstanceToClass(String first, String second) {
		// TODO Auto-generated method stub
	}

	/**
	 * Reads and displays information from database according to submitted
	 * tokens.
	 * 
	 * @param tokens
	 *            The tokens from which information is displayed.
	 */
	private void display(String[] tokens) {
		// TODO Auto-generated method stub
	}

}
