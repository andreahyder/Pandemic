import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

public class comm {
	public static void main(String[] args) throws UnknownHostException, IOException{
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					ServerComm.setupConnection(6666);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
		Socket mySocket = new Socket("192.168.0.1", 6666);
	    PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);
	    Socket mySocket2 = new Socket("192.168.0.1", 6666);
	    PrintWriter out2 = new PrintWriter(mySocket2.getOutputStream(), true);
	    out.println("aaaaaaaaaa");
	    out2.println("bbbbbbbbbbb");
	    
	}
}
