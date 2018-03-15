/*package network;
import java.io.*;
import java.net.*;
public class SocServer implements Runnable
{
    ServerSocket serverSocket;
    PrintStream streamToClient;
    BufferedReader streamFromClient;
    Socket fromClient;
    static int count = 0;
    Thread thread;
    //int port = 8881;
    public SocServer()
    {
        try{
            serverSocket = new ServerSocket(6659);
            
        }
        catch(Exception e)
        {
            System.out.println("Socket could not be created"+e);
        }
        thread = new Thread(this);
        thread.start();
    }
    public void run()
    {
        try{
            while(true)
            {
                fromClient = serverSocket.accept();
                count++;    
                System.out.println("Client connection number "+count);
                streamFromClient = new BufferedReader(new InputStreamReader((fromClient.getInputStream())));
                streamToClient = new PrintStream(fromClient.getOutputStream());
                String str = streamFromClient.readLine();
                System.out.println("Client connection name "+str);
                streamToClient.println("Welcome "+str);
            }
        }
        catch(Exception e){
            System.out.println("Exception "+e);         
        }
        finally{
            try{
                fromClient.close();
            }
            catch(Exception e)
            {
                System.out.println("could not close connection "+e);
            }
        }
         
    }
public static void main(String args[])  
{
    new SocServer();
}
}
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocServer {
	
	public static int port;
	
	public SocServer( int port, String msg ) throws IOException{
		int index = 0;
		for(int i = 0; i < msg.length(); i++){
			if(msg.charAt(i) == '/'){
				index = i;
				break;
			}
		}
		System.out.println(msg.length());
		
		System.out.println(index);
		connectToClient(port, msg, index);
	}
		
	private void connectToClient(int port, String msg, int index) throws IOException{
		System.out.println("S : Server is started.");
		
		
		
		//int port = 6235; //Same port number as clients will user
		ServerSocket ss = new ServerSocket( port );
		
		System.out.println("S : Server is waiting for client request.");
		Socket s = ss.accept(); // Create a socket object when a request is received;
		
		System.out.println("S : Client Connected");
		
		BufferedReader br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		String str = br.readLine();
		
		System.out.println("S : Client Data : ");
		
		//String nickName = str.substring(2, 10);
		System.out.println(str.substring(0, 12));
		System.out.println(msg.substring(0, index));
		System.out.println(str.substring(12));
		System.out.println(msg.substring(index + 1, msg.length() - 1));
		
		String nickName = str.substring(0, 12) + msg.substring(0, index) + ' ' + str.substring(12) + msg.substring(index + 1);		
		
		OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
		PrintWriter out =  new PrintWriter(os);
		out.println(nickName);
		out.flush();
		System.out.println("S : Data sent from Server to Client: " + nickName);
		
		s.close();
		ss.close();
	}

	public static void main(String[] args) throws Exception
	{
		System.out.println("S : Server is started.");
		
		
		
		int port = 6235; //Same port number as clients will user
		ServerSocket ss = new ServerSocket( port );
		
		System.out.println("S : Server is waiting for client request.");
		Socket s = ss.accept(); // Create a socket object when a request is received;
		
		System.out.println("S : Client Connected");
		
		BufferedReader br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		String str = br.readLine();
		
		System.out.println("S : Client Data : ");
		
		//String nickName = str.substring(2, 10);
		String nickName = str.substring(0, 11) + msg.substring(0, index) + str.substring(12, str.length() - 1 + msg.substring(index + 1, msg.length() - 1);		
		
		OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
		PrintWriter out =  new PrintWriter(os);
		out.println(nickName);
		out.flush();
		System.out.println("S : Data sent from Server to Client");
		
		s.close();
		ss.close();
	}

}*/