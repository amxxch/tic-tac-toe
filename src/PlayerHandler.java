import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.*;

/**
 * The PlayerHandler class manages the communication between the client and the server
 * for a Tic Tac Toe game. It handles player actions, updates the game view, and
 * manages the game state.
 */
public class PlayerHandler {
	
	private View currentView;
	private String player;
	private int playerCount = 0;
	
	private ActionListener submitListener;
	private ActionListener boardListener;
	private ActionListener exitListener;
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	/**
     * Constructs a PlayerHandler with the specified game view.
     *
     * @param view the game view associated with this PlayerHandler
     */
	public PlayerHandler(View view) {
		this.currentView = view;
	}
	
	/**
     * Starts the connection to the game server and sets up action listeners
     * for player interactions.
     */
	public void start() {
		try {
			this.socket = new Socket("127.0.0.1", 5001);
			this.in = new Scanner(socket.getInputStream());
			this.out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		submitListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				out.println("name");
			}
		};
		currentView.getSubmitButton().addActionListener(submitListener);
		
		boardListener = addButtonAl();
		
		exitListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				out.println("exit " + player);
			}
		};
		currentView.getControlBar().getExitButton().addActionListener(exitListener);
		
		Thread handler = new ClientHandler(socket);
		handler.start();
		
	}
	
	/**
     * Creates an ActionListener for handling board button actions.
     *
     * @return an ActionListener for the game board buttons
     */
	private ActionListener addButtonAl() {
		ActionListener boardButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JButton clickedButton = (JButton) actionEvent.getSource();
				int row = -1;
				int col = -1;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						if (currentView.getBoardButton(i, j) == clickedButton) {
							row = i;
							col = j;
							break;
						}
					}
				}
				if (row != -1 && col != -1 && currentView.getTurn() == player) {
					out.println("move " + player + " " + row + " " + col);
					currentView.removeButtonAl(row, col);
				}
			}
		};
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton button = currentView.getBoardButton(i, j);
	            for (ActionListener al : button.getActionListeners()) {
	                button.removeActionListener(al);
	            }
	            button.addActionListener(boardButtonListener);
			}
		}
		return boardButtonListener;
	}

	/**
     * The ClientHandler class is a thread that listens for messages from the server
     * and updates the game view accordingly.
     */
	class ClientHandler extends Thread {
		private Socket socket;
		private View view;

		/**
         * Constructs a ClientHandler with the specified socket and game view.
         *
         * @param socket the socket for the client connection
         */
		public ClientHandler(Socket socket) {
			this.socket = socket;
			this.view = currentView;
		}

		/**
         * Runs the thread to listen for messages from the server.
         */
		@Override
		public void run() {
			try {
				readFromServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
         * Reads messages from the server and updates the game view accordingly.
         *
         * @throws Exception if an error occurs while reading from the server
         */
	    public void readFromServer() throws Exception {
			try {
				while (in.hasNextLine()) {
					var command = in.nextLine();
//					System.out.println("Client Received: " + command);
					
					if (command.startsWith("P1 joined")) {
						this.view.setPlayer("X");
						player = "X";
						this.view.changeNameUI();
					}
					else if (command.startsWith("P2 joined")) {
						if (player == "X") {
							this.view.updateTurn("X");
							this.view.updateMessageTitle("Player 2 has joined. Your turn to move");
						}
						else {
							this.view.setPlayer("O");
							player = "O";
							this.view.changeNameUI();
							this.view.updateMessageTitle("Waiting for your opponent to move");
						}
					}
					
					if (command.startsWith("move")) {
						String[] commands = command.split(" ");
						
						String playerMoved = commands[1];
						int row = Integer.parseInt(commands[2]);
						int col = Integer.parseInt(commands[3]);
						
						this.view.playerMove(playerMoved, row, col);
						String nextPlayer = (playerMoved.equals("X") ? "O" : "X");
						this.view.updateTurn(nextPlayer);
						
						if (player.equals(playerMoved)) {
							this.view.updateMessageTitle("Vaild move, wait for your opponent.");
						}
						else {
							this.view.updateMessageTitle("Your opponent has moved, now is your turn.");
						}
					}
				
					
					if (command.startsWith("exit")) {
						String[] msg = command.split(" ");
						String playerName = msg[1];
						this.view.disabledBoard();
						playerCount = 0;
						if (!player.equals(playerName)) {
							this.view.playerLeft();
						} else {
							System.exit(0);
						}
					}
					
					if (command.startsWith("restart")) {
						String[] msg = command.split(" ");
						String playerName = msg[1];
						playerCount++;
						
						if (playerCount == 1) {
							this.view.updateMessageTitle("Wait for your opponent to continue.");
						} else if (playerCount == 2) {
							out.println("ready");
							playerCount = 0;

						}
						
					}
					
					if (command.startsWith("start new game")) {
						this.view.updateTurn("X");
						this.view.enabledBoard();
						if (player == "X") {
							this.view.updateMessageTitle("Your turn to move");
						} else {
							this.view.updateMessageTitle("Waiting for your opponent to move");
						}
					}
					
					
					if (command.startsWith("result")) {
						this.view.updateTurn("Stop");
						String[] msg = command.split(" ");
						String result = msg[1];
						
						boolean isRestart = this.view.result(result);
						if (isRestart) {
							out.println("restart " + player);
							this.view.resetBoard();
							boardListener = addButtonAl();
							this.view.updateScore();
						} else {
							out.println("exit " + player);
						}
					}
						
					out.flush();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
	    }
	}
}