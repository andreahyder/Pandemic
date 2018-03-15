package network;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class NetworkManager {

	public static void main(String[] args) throws Exception {
		
		String[] plzwork = {"Pablo", "Santiago"};
				
		Server testServer = new Server(6010);
				
		testServer.waitForClient(testServer.getServerSocket());
				
	}
}