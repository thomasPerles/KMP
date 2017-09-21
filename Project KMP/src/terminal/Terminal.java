package terminal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Terminal {
	
	InputStream in;
	PrintStream out;
	Scanner scanner;
	
	public Terminal(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
		scanner = new Scanner(in);
	}
	
	public void prompt() {
		out.println("\n$ ");
	}
	
	public String consume() {
		String typed = scanner.nextLine();
		return typed;
	}
	
	public void interpret(String line) {
		
	}

	private void displayOptions(String line) {
		
	}
}
