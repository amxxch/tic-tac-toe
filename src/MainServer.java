import java.io.IOException;
import java.net.ServerSocket;

/**
 * The MainServer class is the entry point for the Tic Tac Toe server application.
 * It initializes the server, listens for incoming client connections, and manages
 * the server lifecycle.
 */
public class MainServer {

	/**
     * The main method that starts the server.
     * 
     *  @param args command-line arguments
     */
	public static void main(String[] args) throws IOException {
		
		System.out.println("Server is Running...");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Server Stopped.");
			}
		}));

		try (var listener = new ServerSocket(5001)) {
			Server myServer = new Server(listener);
			myServer.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
