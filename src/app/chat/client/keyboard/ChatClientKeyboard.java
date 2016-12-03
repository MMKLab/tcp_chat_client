package app.chat.client.keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ChatClientKeyboard implements Runnable {

	PrintStream outputToServer = null;
	boolean keyboardActive = false; // we made synchronized getters and setters
									// for this one because we must limit that
									// only one thread can access it one at a
									// time, e.g. Thread A reads this as false
									// and wants to change it to true, but for
									// Thread B this change can be invisible at
									// critical time of reading it

	public synchronized void setKeyboardActive(boolean keyboardActive) {
		this.keyboardActive = keyboardActive;
	}

	public synchronized boolean isKeyboardActive() {
		return this.keyboardActive;
	}

	public ChatClientKeyboard(PrintStream outputToServer) {
		this.outputToServer = outputToServer;
	}

	public void run() {
		BufferedReader consoleInputFromKeyboard = new BufferedReader(
				new InputStreamReader(System.in));
		this.keyboardActive = true; // when starting a thread, we assume that
									// keyboard thread is working

		try {
			while (isKeyboardActive()) {
				outputToServer.println(consoleInputFromKeyboard.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
