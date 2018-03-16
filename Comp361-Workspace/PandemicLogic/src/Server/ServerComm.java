package Server;

import Actions.*;

import java.io.IOException;
import java.lang.Thread;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.net.ServerSocket;

public class ServerComm {
	static ServerSocket listener;
	static ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
	static ArrayList<String[]> messageQueue = new ArrayList<String[]>();
	static String response = null;
	static ArrayList<Action> possibleActions = initializePossibleActions();
	public static void setupConnection(int port) throws IOException{ //Create a thread in main and run this cuz it's gonna loop
		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
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
						/*String[] currentMessage = messageQueue.remove(0);
						System.out.println(currentMessage[0] + currentMessage[1]);
						//String[] args = currentMessage.split("/");
						String toExecute = currentMessage[1];
						for (Action a: possibleActions){
							if(a.getName().equals(toExecute)){
								a.execute(currentMessage);
							}
						}*/
					}
					else{
						/*System.out.println("You got mail!");
						String[] hahaha = messageQueue.get(0);
						System.out.println(hahaha[1] + hahaha[2]);
						messageQueue.remove(0);*/
						String[] currentMessage = messageQueue.remove(0);
						//String[] args = currentMessage.split("/");
						String toExecute = currentMessage[1];
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
		while(true){
			System.out.println("Listening to connections.");
			Socket client = listener.accept();
			ClientThread newClientThread = new ClientThread(client, i);
			clientList.add(newClientThread);
			newClientThread.start();
			i++;
		}		
	}
	static void sendMessage(String message, int clientNumber){
		for (ClientThread t: clientList){
			if (t.getClientNumber() == clientNumber){
				t.send(message);
			}
		}
	}
	public static void addToActionQueue(String pString, int client){
		pString = client + "/"+ pString;
		String[] temp = pString.split("/");
		if(temp[1].matches("data")){
			changeResponse(temp[2]);
		}
		else{
			messageQueue.add(temp);
		}
	}
	static void changeResponse(String r){
		response = r;
	}
	static ArrayList<Action> initializePossibleActions(){
		ArrayList<Action> toReturn = new ArrayList<Action>();
		toReturn.add(new AddPlayer());
		toReturn.add(new Drive());
		toReturn.add(new DirectFlight());
		toReturn.add(new TreatDisease());
		toReturn.add(new ShareKnowledge());
		toReturn.add(new ToggleReady());
		toReturn.add(new ChangeName());
		toReturn.add(new EndTurn());
		toReturn.add(new Print());
		return toReturn;
	}
}
