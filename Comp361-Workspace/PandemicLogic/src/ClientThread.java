import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.lang.Thread;

public class ClientThread extends Thread{
	private int clientNumber;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	public ClientThread(Socket pSocket, int pClientNumber) throws IOException{
		clientSocket = pSocket;
		out = new PrintWriter(clientSocket.getOutputStream(), true);;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		clientNumber = pClientNumber;
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
		ServerComm.addToActionQueue(inMessage, clientNumber);
	}
	int getClientNumber(){
		return clientNumber;
	}
}