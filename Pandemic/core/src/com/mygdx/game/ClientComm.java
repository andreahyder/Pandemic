package com.mygdx.game;

import java.io.IOException;
import java.lang.Thread;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.mygdx.game.Actions.*;

import java.net.ServerSocket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;

public class ClientComm {
	static Socket clientSocket;
	static ServerThread ct;
	public static ArrayList<String[]> messageQueue = new ArrayList<String[]>();
	public static ArrayList<Action> possibleActions = initializePossibleActions();
	static void setupConnection(String ip, int port) throws UnknownHostException, IOException{
		clientSocket = new Socket(ip, port);
		ct = new ServerThread(clientSocket);
		ct.start();
		/*
		new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					if (messageQueue.isEmpty()){
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
					else{
						String[] currentMessage = messageQueue.remove(0);
						String toExecute = currentMessage[0];
						for (Action a: possibleActions){
							if(a.getName().equals(toExecute)){
								a.execute(currentMessage);
								break;
							}
						}
					}
				}
				
			}
			
		}).start();
		*/
	}
	void send(String message){
		ct.send(message);
	}
	static void addToActionQueue(String pString){
		String[] temp = pString.split("/");
		messageQueue.add(temp);
	}
	static ArrayList<Action> initializePossibleActions(){
		ArrayList<Action> toReturn = new ArrayList<Action>();
		toReturn.add(new UpdatePlayerLocation());
		toReturn.add(new RemoveCube());
		toReturn.add(new AskForConsent());
		toReturn.add(new AskForDiscard());
		toReturn.add(new RemoveCardFromHand());
		toReturn.add(new AddCardToHand());
		toReturn.add(new AddDiseaseCubeToCity());
		toReturn.add(new AddInfectionCardToDiscard());
		toReturn.add(new IncOutbreakCounter());
		toReturn.add(new IncInfectionRate());
		toReturn.add(new ClearInfectionDiscard());
		toReturn.add(new NotifyTurn());
		toReturn.add(new StartGame());
		return toReturn;
	}
}
