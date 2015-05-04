
public class Test {

	public static void main(String[] args) {

		TCPServer tcp = new TCPServer(new ClientMessageRecieved() {
			
			@Override
			public void messageReceived(String message) {
				System.out.println(message);
			}
		});
		
		tcp.start();
	}

}
