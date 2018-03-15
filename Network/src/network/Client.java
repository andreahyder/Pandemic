package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private String ip;
	private String playerName;
	private int port;
	private Socket clientSoc;
	
	public Client(int port, String ip, String playerName) throws UnknownHostException, IOException{
		this.port = port;
		this.ip = ip;
		this.playerName = playerName;
		this.clientSoc = new Socket(ip, port);
	}
	
	
	public void sendToServer(String methodName, String[] args, Socket clientSoc) throws IOException{
		
		PrintWriter out;
		
		String msg = methodName;
		
		for(int i = 0; i < args.length; i++){
			msg += "/";
			msg += args[i];
		}
		
		OutputStreamWriter os 	= new OutputStreamWriter(clientSoc.getOutputStream());//converts data into stream format, sends to s's output Stream
		
		out = new PrintWriter(os);
		out.println(msg);
		os.flush();
		os.close();
		
		System.out.println("C : Data sent from Client " + " to Server: " + msg);
		
	}
	
	public String waitForServer(Socket clientSoc) throws IOException{
		
		BufferedReader br;
		
		String nickName = "";
		
		br = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
		nickName = br.readLine();
		
		System.out.println("C : Data received from Server: " + nickName);
		
		return nickName;
		
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public Socket getClientSoc() {
		return this.clientSoc;
	}


	public void setClientSoc(Socket clientSoc, int port, String ip) throws UnknownHostException, IOException {
		this.clientSoc = new Socket(ip, port);
	}

}
