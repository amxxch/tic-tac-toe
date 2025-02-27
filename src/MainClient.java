import javax.swing.SwingUtilities;

/**
 * The MainClient class serves as the entry point for the Tic Tac Toe client application.
 * It initializes the user interface and manages the communication with the server.
 */
public class MainClient {

	 /**
     * The main function that triggers the client's user interface and application.
     *
     * @param args command-line arguments
     */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View view = new View();
				PlayerHandler controller = new PlayerHandler(view);
				controller.start();
			}
		});
	}
}
