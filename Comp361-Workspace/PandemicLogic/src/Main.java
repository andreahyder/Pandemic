import Actions.*;
import Server.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import java.io.PrintWriter;
import java.net.*;

public class Main {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException{
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					ServerComm.setupConnection(6000);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		/*TimeUnit.SECONDS.sleep(3);
		Socket mySocket = new Socket("132.206.52.33", 6000);
		new ClientThread(mySocket, 1).start();
	    PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);
	    Socket mySocket2 = new Socket("132.206.52.33", 6000);
	    PrintWriter out2 = new PrintWriter(mySocket2.getOutputStream(), true);*/
	    //out.println("Print/aaaaa");
	    //TimeUnit.SECONDS.sleep(2);
	    //out2.println("Print/plzwork");
	    
	}
}