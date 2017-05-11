package http_client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class clientLauncher{
	static Socket clientSocket;
	static PrintWriter pw;
	static BufferedReader br;

	public static void main(String[] args) throws Exception {
		clientSocket = new Socket(InetAddress.getByName("example.org"), 80);
		
		Thread readingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					br = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
					String line; 
					while(true){
						line = br.readLine();
						if(line != null) System.out.println(line);
						if(line.equals("</html>")) break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		readingThread.start();
		
		pw = new PrintWriter(clientSocket.getOutputStream());
		pw.println("GET / HTTP/1.1");
		pw.println("Host: example.org");
		pw.println("");
		pw.flush();
		
	}
}
