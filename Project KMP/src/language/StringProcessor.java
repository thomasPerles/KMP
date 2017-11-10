package language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import kPackage.KCompositeModel;
import kPackage.KCompositeRelation;
import kPackage.KModel;
import kPackage.KRelation;
import kPackage.Triple;
import terminal.Terminal;

public class StringProcessor {
	
	private ResourceBundle bundle;
	private Terminal terminal;
	private ArrayList<String[]> patterns;
	
	public StringProcessor(ResourceBundle bundle) {
		this.bundle = bundle;
		this.patterns = new ArrayList<String[]>();
		loadGrammar();
	}
	
	private void loadGrammar() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/language/grammar.txt")));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] splittedLine = line.split("\\|");
				if(splittedLine.length > 1) {
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
		for(String word : pattern) {
			terminal.print(' ' + word);
		}
	}

	private void suggest(String[] tokens) {
		for(String[] pattern : patterns) {
			for(String token : tokens) {
				if(Arrays.asList(pattern).contains(token)) {
					printSuggestion(pattern);
				}
			}
		}
	}
	
	private KRelation buildKRelation(String[] tokens) {
		if(tokens.length > 1) {
			KRelation leftLink = new KRelation(tokens[0]);
			KRelation rightLink = new KRelation(tokens[tokens.length - 1]);
			tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
			KModel model = buildKCompositeModel(tokens);
			return new KCompositeRelation(leftLink, model, rightLink);
		}
		return new KRelation(tokens[0]);
	}
	
	private KModel buildKCompositeModel(String[] tokens) {
		if(tokens.length > 1) {
			KModel source = new KModel(tokens[0]);
			KModel destination = new KModel(tokens[tokens.length - 1]);
			tokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
			KRelation link = buildKRelation(tokens);
			return new KCompositeModel(source, link, destination);
		}
		return new KModel(tokens[0]);
	}
	
	private Triple buildTriple(String [] tokens) {
		KCompositeModel temp = (KCompositeModel) buildKCompositeModel(tokens);
		return new Triple(temp.getSource(), temp.getLink(), temp.getDestination());
	}

	public void process(String[] tokens) throws IllegalWordCountException {
		
		if(tokens.length % 2 != 1) {
			throw new IllegalWordCountException();
		}
		// Pre verification of submitted tokens. 
		for(String token : tokens) {
			if(token.equals("help")) { // to localize
				suggest(tokens);
			}
		}
		// If ok
		Triple triple = buildTriple(tokens);
		terminal.println(triple.toString());
		
		// Send it off to the database for validation and CRUD operations
	}

	
}
