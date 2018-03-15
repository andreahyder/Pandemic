import java.io.IOException;
import java.lang.Thread;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;

public class ServerComm {
	static ServerSocket listener;
	static ArrayList<ClientThread> clientList = new ArrayList<ClientThread>();
	static void setupConnection(int port) throws IOException{
		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
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
}
