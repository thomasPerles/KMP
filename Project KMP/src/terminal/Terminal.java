package terminal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import language.SyntaxVerifier;

public class Terminal implements Runnable {
	
	private InputStream in;
	private PrintStream out;
	private Scanner scanner;
	private SyntaxVerifier sv;
	private boolean hasQuit;
	
	public Terminal(InputStream in, PrintStream out, SyntaxVerifier sv) {
		this.in = in;
		this.out = out;
		scanner = new Scanner(in);
		this.sv = sv;
		sv.setTerminal(this);
		hasQuit = false;
	}
	
	public void prompt() {
		out.print(System.lineSeparator() + "> ");
	}
	
	public String[] consume() {
		String[] consumed = scanner.nextLine().split(" ");
		return consumed;
	}
	
	public void println(String token) {
		out.println(token);
	}
	
	public void print(String token) {
		out.print(token);
	}
	
	private void quit() {
		hasQuit = true;
	}

	@Override
	public void run() {
		
		while(!hasQuit) {
			prompt();
			String tokens[] = consume();
			switch(tokens[tokens.length - 1]) {
			case "help":
				int length = tokens.length - 1;
				sv.suggest(Arrays.copyOf(tokens, length));
				break;
			default:
				sv.verify(tokens);
			}
		}
	}
}
