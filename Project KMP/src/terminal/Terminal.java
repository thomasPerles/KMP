package terminal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Terminal implements Runnable {
	
	private InputStream in;
	private PrintStream out;
	private Scanner scanner;
	private boolean hasQuit;
	
	public Terminal(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
		scanner = new Scanner(in);
		hasQuit = false;
	}
	
	public void prompt() {
		out.print(System.lineSeparator() + "> ");
	}
	
	public String consume() {
		String consumed = scanner.nextLine();
		return consumed;
	}
	
	public void interpret(String line) {
		
	}

	private void displayOptions(String line) {
		
	}
	
	private void quit() {
		hasQuit = true;
	}

	@Override
	public void run() {
		while(!hasQuit) {
			prompt();
			System.out.println("Command consumed: " + consume());
		}
	}
}
