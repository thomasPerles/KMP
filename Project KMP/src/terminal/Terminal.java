package terminal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import language.IllegalWordCountException;
import language.StringProcessor;

public class Terminal implements Runnable {

	private InputStream in;
	private PrintStream out;
	private Scanner scanner;
	private StringProcessor sv;
	private boolean hasQuit;

	public Terminal(InputStream in, PrintStream out, StringProcessor sv) {
		this.in = in;
		this.out = out;
		this.sv = sv;
		scanner = new Scanner(in);
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

	public void println(String[] tokens) {
		out.println();
		for (String token : tokens) {
			out.print(token + ' ');
		}
	}

	public void print(String token) {
		out.print(token);
	}

	public void print(int token) {
		out.print(token);
	}

	public void print(char cToken) {
		out.print(cToken);
	}

	private void quit() {
		hasQuit = true;
	}

	@Override
	public void run() {

		while (!hasQuit) {
			prompt();
			String tokens[] = consume();
			try {
				sv.process(tokens);
			} catch (IllegalWordCountException e) {
				System.err.println(e.getMessage());
				;
			}
		}
	}
}
