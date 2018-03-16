package com.mygdx.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.lang.Thread;

public class ServerThread extends Thread{
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	public ServerThread(Socket pSocket) throws IOException{
		clientSocket = pSocket;
		out = new PrintWriter(clientSocket.getOutputStream(), true);;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	@Override
	public void run(){
		String inputLine;
		//Loops for handling messages from client
		try {
			while ((inputLine = in.readLine()) != null){
				callback(inputLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	public void send(String outMessage){
		out.println(outMessage);
	}
	public void callback(String inMessage){
		ClientComm.addToActionQueue(inMessage);
	}
}