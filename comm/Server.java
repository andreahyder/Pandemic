import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}*/
	
	private int port;
	
	public Server(int port) throws IOException{
		
		this.port = port;
		
		ServerSocket ss = new ServerSocket( port );
	}
	
	public static void sendToClient(String methodName, String[] args, ServerSocket ss) throws IOException{
		
		Socket s = ss.accept();
		
		String msg = methodName;
		
		for(int i = 0; i < args.length; i++){
			msg += "/";
			msg += args[i];
		}
		
		//String nickName = str.substring(0, 12) + msg.substring(0, index) + ' ' + str.substring(12) + msg.substring(index + 1);		
		
		
		
		OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
		PrintWriter out =  new PrintWriter(os);
		out.println(msg);
		out.flush();
		System.out.println("S : Data sent from Server to Client: " + msg);
		
		s.close();
		ss.close();		
		
	}
	
	
	public static String waitForClient(ServerSocket ss) throws IOException{
		
		
		Socket s = ss.accept();
	
		BufferedReader br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
		String str = br.readLine();
		
		System.out.println("S : Data received from Client to Server");
		
		return str;		
		
	}

}