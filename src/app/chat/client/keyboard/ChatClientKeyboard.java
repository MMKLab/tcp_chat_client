package app.chat.client.keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ChatClientKeyboard implements Runnable{
	
	PrintStream outputToServer = null;
	public boolean keyboardActive = false;
	
	
	public ChatClientKeyboard(PrintStream outputToServer) {
		this.outputToServer = outputToServer;
		this.keyboardActive = true;
	}

	public void run() {
		BufferedReader consoleInputFromKeyboard = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			while(keyboardActive){
				outputToServer.println(consoleInputFromKeyboard.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
