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
	public static void send(String message){
		ct.send(message); 
	}
	public static void addToActionQueue(String pString){
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
		toReturn.add(new AddCardToStash());
		toReturn.add(new AddCubeToStash());
		toReturn.add(new AddResearchStation());
		toReturn.add(new AirportSighting());
		toReturn.add(new BorrowedTimeActivate());
		toReturn.add(new Capture());
		toReturn.add(new Chat());
		toReturn.add(new CureDisease());
		toReturn.add(new DecrementActions());
		toReturn.add(new DecrementActionsBio());
		toReturn.add(new EradicateDisease());
		toReturn.add(new GiveConsent());
		toReturn.add(new HiddenPocket());
		toReturn.add(new MobileHospitalResponse());
		toReturn.add(new NotifyTurnTroubleShooter());
		toReturn.add(new Print());
		toReturn.add(new PromptNewAssignment());
		toReturn.add(new ReexaminedResearch());
		toReturn.add(new RemoveCardFromStash());
		toReturn.add(new RemoveCubeFromStash());
		toReturn.add(new RemoveInfectionCardFromDiscard());
		toReturn.add(new RemovePlayerCardFromDiscard());
		toReturn.add(new RemoveResearchStation());
		toReturn.add(new ResetActions());
		toReturn.add(new SpecialOrdersActivate());
		toReturn.add(new UnacceptableLoss());
		toReturn.add(new UpdateRole());
		toReturn.add(new VirulentStrainChosen());
		toReturn.add(new VirulentStrainEpidemic());
		toReturn.add(new AddQuarantine());
		toReturn.add(new RemoveQuarantine());
		toReturn.add(new UpdateQuarantine());
		return toReturn;
	}
}
