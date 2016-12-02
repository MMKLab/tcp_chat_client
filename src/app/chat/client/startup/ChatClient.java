package app.chat.client.startup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import app.chat.client.keyboard.ChatClientKeyboard;

public class ChatClient {

	public static void main(String[] args) {
		int portNumber = 8090; // port which server will use for listening!

		/*
		 * If the main method has a port number as a parameter, it will use that
		 * number instead of 8090
		 */
		if (args.length > 0) {
			portNumber = Integer.parseInt(args[0]);
		}
		try {
			Socket socketForServerCommunication = new Socket("localhost", portNumber); //localhost -> because it connects locally
			
			BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socketForServerCommunication.getInputStream()));
			String textFromServer = null;
			
			PrintStream outputToServer = new PrintStream(socketForServerCommunication.getOutputStream());
			ChatClientKeyboard keyboardInit = new ChatClientKeyboard(outputToServer);
			new Thread(keyboardInit).start();
			
			while((textFromServer = inputFromServer.readLine()) != null){
				System.out.println(textFromServer);
				if(textFromServer.indexOf("Goodbye Mr.") == 0){
					keyboardInit.keyboardActive = false; 
					break;
				}
			}
			socketForServerCommunication.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
}
