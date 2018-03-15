/*package network;
import java.io.*;
import java.net.*;
public class SocClient
{
    PrintStream streamToServer;
    BufferedReader streamFromServer;
    Socket toServer;
    //int port = 8001;
    public SocClient()
    {
        connectToServer();
    }
    private void connectToServer()
    {
        try{
            String name;
            toServer = new Socket("132.206.52.43",6234);
            //port++;
            streamFromServer = new BufferedReader(new InputStreamReader((toServer.getInputStream())));
            streamToServer = new PrintStream(toServer.getOutputStream());
            System.out.println("Enter Connection Name");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            name = reader.readLine();   
            streamToServer.println(name);
            String str = streamFromServer.readLine();
            System.out.println("The Server Says "+str);         
        }
        catch(Exception e)
        {
                System.out.println("Exception "+e);
        }       
    }
    public static void main(String args[])
    {
        new SocClient();
    }
}
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.Socket;

public class SocClient {
	
	public static PrintWriter out;
    public static BufferedReader br;
    //public static Socket s;
    public static String ip;
    public static String returnedMessage;
    
    public SocClient(int port, String msg, String ip) throws Exception
    {
        try {
        	returnedMessage = connectToServer(port, msg, ip, 0);
        	}
        catch (BindException e){
				e.printStackTrace();
        }
        catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    private String connectToServer(int port, String msg, String ip, int sendOrGet) throws Exception
    {
    	//ip = "132.206.52.43"; 	//Server IP, localhost if on same computer as server
		//int port = 9992;			//Some unused port number
		
		Socket s = new Socket( ip, port );
		String nickName = "";
				
		if(sendOrGet == 1){
		
			OutputStreamWriter os 	= new OutputStreamWriter(s.getOutputStream());//converts data into stream format, sends to s's output Stream
		
			out = new PrintWriter(os);
			out.println(msg);
			os.flush();
			os.close();
			
			System.out.println("C : Data sent to Server: " + msg);
		}
				
		else{
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			nickName = br.readLine();
			
			System.out.println("C : Data received from Server: " + nickName);
		}
		
		
		s.close();
		
		return nickName;
    }

	public static void main(String[] args) throws Exception //Exception to handle used port number
	{
		ip = "132.206.52.41"; 	//Server IP, localhost if on same computer as server
		//int port = 9992;			//Some unused port number
		
		Socket s = new Socket( ip, 6008 );
		
		String str = "andreaaaaaaaa a a a aa";
		
		OutputStreamWriter os 	= new OutputStreamWriter(s.getOutputStream());//converts data into stream format, sends to s's output Stream
		out = new PrintWriter(os);
		out.println(str);
		os.flush();
				
		
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String nickName = br.readLine();
		
		System.out.println("C : Data from Server " + nickName);
		
		os.close();
		s.close();
	}

}*/