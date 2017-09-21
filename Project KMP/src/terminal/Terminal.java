package terminal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Terminal {
	
	InputStream in;
	PrintStream out;
	
	public Terminal(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
	}
	
	public void prompt() {
		out.println("\n$ ");
	}
	
	public String consume() {
		Scanner scanner = new Scanner(in);
		String typed = scanner.nextLine();
		scanner.close();
		return typed;
	}
	
}
