import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer extends Thread {

	
	private final static int PORT = 1234;
	private ClientMessageRecieved callback;
	
	private ServerSocket serverSocket;
	private Socket socket;
	
	private PrintWriter out;
	private BufferedReader in;
	
	private boolean running;
	
	public TCPServer(ClientMessageRecieved callback) {
		this.callback = callback;
		
		running = false;
		
	}
	
	@Override
	public void run() {
		
		super.run();
		
		running = true;
		
		System.out.println("Waiting for Client...");
		try {
			// create Socket and get Client
			serverSocket = new ServerSocket(PORT);
			socket = serverSocket.accept();
			
			System.out.println("Client connected!");
			
			try {
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				while (running) {
					String message = in.readLine();
					if (message != null) {
						callback.messageReceived(message);
					}
				}
			} catch (Exception ex) {
				System.out.println("Error!");
			} finally {
				socket.close();
				serverSocket.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	
	public void sendMessage(String message) {
		
		if (out != null && !out.checkError()) {
			out.println(message);
			out.flush();
		}
	}
	
	
	
}
