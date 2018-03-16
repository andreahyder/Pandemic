import java.io.IOException;
import Actions.*;
import java.lang.Thread;
import java.net.Socket;
import java.util.ArrayList;

import Actions.Action;

import java.net.ServerSocket;

public class ServerComm {
	static ServerSocket listener;
	static ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
	static ArrayList<String[]> messageQueue = new ArrayList<String[]>();
	static String response = null;
	static ArrayList<Action> possibleActions = initializePossibleActions();
	static void setupConnection(int port) throws IOException{
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
					if (!messageQueue.isEmpty()){
						String[] currentMessage = messageQueue.remove(0);
						//String[] args = currentMessage.split("/");
						String toExecute = currentMessage[1];
						for (Action a: possibleActions){
							if(a.getName().equals(toExecute)){
								a.execute(currentMessage);
							}
						}
					}
				}
				
			}
			
		});
		while(true){
			Socket client = listener.accept();
			ClientThread newClientThread = new ClientThread(client, i);
			clientList.add(newClientThread);
			newClientThread.start();
			i++;
		}		
	}
	void sendMessage(String message, int clientNumber){
		for (ClientThread t: clientList){
			if (t.getClientNumber() == clientNumber){
				t.send(message);
			}
		}
	}
	static void addToActionQueue(String pString, int client){
		pString = client + "/"+ pString;
		String[] temp = pString.split("/");
		if(temp[1].matches("data")){
			response = temp[2];
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
		toReturn.add(new Discard());
		toReturn.add(new EndTurn());
		return toReturn;
	}
}
