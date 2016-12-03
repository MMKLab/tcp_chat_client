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
			Socket socketForServerCommunication = new Socket("localhost",
					portNumber); // localhost -> because it connects locally

			BufferedReader inputFromServer = new BufferedReader(
					new InputStreamReader(
							socketForServerCommunication.getInputStream()));//init inputstream
			
			PrintStream outputToServer = new PrintStream(
					socketForServerCommunication.getOutputStream());//init ouputstream
			
			ChatClientKeyboard keyboardInit = new ChatClientKeyboard(outputToServer);
									// instance for keyboard thread, we need to
									// save the reference so later we can turn
									// it off if chat is over
			
			new Thread(keyboardInit).start();//init Thread
			
			String textFromServer = null;

			while ((textFromServer = inputFromServer.readLine()) != null) {
				System.out.println(textFromServer); //showing on console the text from group chat
				if (textFromServer.indexOf("Goodbye Mr.") == 0) {
					keyboardInit.setKeyboardActive(false); //turning off keyboard reading in run method
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
