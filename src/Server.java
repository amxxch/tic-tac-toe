import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * The Server class manages the game server, accepting client connections
 * and handling game logic for a multi-client environment.
 */

public class Server {
	private ServerSocket serverSocket;
	private ServerBoard board;
	int playerCount = 0;
	
	/**
     * Constructs a Server with the specified ServerSocket.
     *
     * @param serverSocket 	the ServerSocket to listen for client connections
     */
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.board = new ServerBoard();
	}
	
	private Set<PrintWriter> writers = new HashSet<>();
	
	/**
     * Starts the server to accept client connections and handle them
     * using a fixed thread pool.
     */
	public void start() {
		var pool = Executors.newFixedThreadPool(200);
		int clientCount = 1;
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				pool.execute(new Handler(socket));
				System.out.println("Connected to client " + clientCount++);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
     * The Handler class implements Runnable to handle communication
     * with a connected client.
     */
	public class Handler implements Runnable {
		private Socket socket;
		private Scanner input;
		private PrintWriter output;

		/**
         * Constructs a Handler for the specified socket.
         *
         * @param socket 	the socket for the client connection
         */
		public Handler(Socket socket) {
			this.socket = socket;
		}
		
		/**
         * Runs the handler to process client input and manage game events.
         */
		@Override
		public void run() {
			System.out.println("Connected: " + socket);
			try {
				input = new Scanner(socket.getInputStream());
				output = new PrintWriter(socket.getOutputStream(), true);

				writers.add(output);

				while (input.hasNextLine()) {
					var command = input.nextLine();
					System.out.println("Server Received: " + command);

					if (command.startsWith("name")) {
						board.resetBoard();
						playerCount++;

						if ((playerCount == 1)) {
							output.println("P1 joined");
							System.out.println("P1 joined");
							System.out.println(playerCount);
						}
						else if (playerCount == 2) {
							for (PrintWriter writer : writers) {
								writer.println("P2 joined");
							}
							System.out.println("P2 joined");
							playerCount  = 0;
							System.out.println(playerCount);
						}
					}
					
					if (command.startsWith("move")) {
						String[] msg = command.split(" ");
						
						String playerName = msg[1];
						int row = Integer.parseInt(msg[2]);
						int col = Integer.parseInt(msg[3]);
						
						System.out.println(row);
						System.out.println(col);
						
						if (board.getGrid(row, col) == null) {
							for (PrintWriter writer : writers) {
								writer.println(command);
							}
							board.updateBoard(playerName, row, col);
							
							String result = board.checkWin();
							System.out.println(result);
							if (!result.equals("continue")) {
								if (result.equals("X")) {
									for (PrintWriter writer : writers) {
										writer.println("result X");
									}
									System.out.println("result X");
								} else if (result.equals("O")) {
									for (PrintWriter writer : writers) {
										writer.println("result O");
									}
									System.out.println("result O");
								} else if (result.equals("DRAW")) {
									for (PrintWriter writer : writers) {
										writer.println("result draw");
									}
									System.out.println("result draw");
								}
							}
						}					
					}
					
					if (command.startsWith("exit")) {
						playerCount = 0;
						for (PrintWriter writer : writers) {
							writer.println(command);
						} 
					}
					
					if (command.startsWith("restart")) {
						for (PrintWriter writer : writers) {
							writer.println(command);
						}
					}
					
					if (command.startsWith("ready")) {
						board.resetBoard();
						for (PrintWriter writer : writers) {
							writer.println("start new game");
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (output != null) {
					writers.remove(output);
				}
			}
		}
	}
}
