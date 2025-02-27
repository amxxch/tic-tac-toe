import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * The View class represents the user interface for the Tic Tac Toe game.
 * It handles the layout, displays game elements, and updates the UI based
 * on user interactions and game state changes.
 */
public class View extends JFrame {

	private JLabel messageTitle;
	private ScoreBoard scoreBoard;
	private JPanel boardPanel;
	private JButton[][] buttonArray = new JButton[3][3];
	private JButton submit;
	private JTextField nameField;
	private ControlBar control_bar;
	private String player;
	private String lastResult;
	private boolean isPlayerTurn;
	
	/**
     * Constructs the TicTacToeGame frame and initializes the UI components
     */
	public View() {

		setTitle("Tic Tac Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1020, 800);
		
		// North Panel (control bar + message)
		JPanel north_panel = new JPanel();
		north_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		control_bar = new ControlBar();
		this.setJMenuBar(control_bar);				
		
		messageTitle = new JLabel("Enter your player name...");
		messageTitle.setFont(new Font("SF Pro", Font.PLAIN, 18));
		north_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		north_panel.add(messageTitle, BorderLayout.CENTER);
		north_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		
		// West Panel (Game Board)
		JPanel west_panel = new JPanel();
		boardPanel = gameBoard();
		west_panel.add(boardPanel);
		
		
		// East Panel (Score Board)
		JPanel east_panel = new JPanel();
		east_panel.setLayout(new BoxLayout(east_panel, BoxLayout.Y_AXIS));
		scoreBoard = new ScoreBoard();
		JLabel title = new JLabel("Scores");
		title.setFont(new Font("SF Pro", Font.BOLD, 24));
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titlePanel.add(title);
		east_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		east_panel.add(titlePanel);
		east_panel.add(scoreBoard);
		
		
		// South Panel (Name + Time)
		JPanel south_panel = new JPanel();
		south_panel.setLayout(new BoxLayout(south_panel, BoxLayout.Y_AXIS));
		
		JPanel namePanel = new JPanel();
		JLabel nameLabel = new JLabel("Enter your name: ");
		nameField = new JTextField(10);
		submit = new JButton("Submit");
		nameLabel.setFont(new Font("SF Pro", Font.PLAIN, 18));
		nameField.setFont(new Font("SF Pro", Font.PLAIN, 18));
		submit.setFont(new Font("SF Pro", Font.PLAIN, 14));
		
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		namePanel.add(submit);
		
		TimerLabel timerLabel = new TimerLabel();
		timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		south_panel.add(namePanel, BorderLayout.CENTER);
		south_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		south_panel.add(timerLabel, BorderLayout.CENTER);
		south_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		
	
		add(south_panel, BorderLayout.SOUTH);
		add(north_panel, BorderLayout.NORTH);
		add(west_panel, BorderLayout.WEST);
		add(east_panel, BorderLayout.EAST);
		
		setVisible(true);
	}
	
	private JPanel gameBoard() {
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(3, 3));		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				final int row = i;
				final int col = j;
				buttonArray[row][col] = new JButton();
				buttonArray[row][col].setFont(new Font("Arial", Font.PLAIN, 120));
				buttonArray[row][col].setBorder(new LineBorder(Color.BLACK, 1));
				boardPanel.add(buttonArray[row][col]);
			}
		}
		
		boardPanel.setPreferredSize(new Dimension(650, 600));
		boardPanel.setVisible(true);
		return boardPanel;
	}
	
	/**
     * Updates the game board with the player's move.
     *
     * @param playerName 	the name of the player making the move (either "X" or "O")
     * @param row 			the row index of the move
     * @param col 			the column index of the move
     */
	public void playerMove(String playerName, int row, int col) {
		buttonArray[row][col].setText(playerName);
		if (playerName.equals("X")) {
			buttonArray[row][col].setForeground(Color.GREEN);
		} else {
			buttonArray[row][col].setForeground(Color.RED);
		}
	}
	
	/**
     * Updates the message title displayed at the top of the UI.
     *
     * @param text 	the new text to display in the title
     */
	public void updateMessageTitle(String text) {
		messageTitle.setText(text);
	}
	
	/**
     * Returns the submit button for entering the player's name.
     *
     * @return the submit button
     */
	public JButton getSubmitButton() {
		return submit;
	}
	
	/**
     * Returns the current player's identifier.
     *
     * @return the player's identifier (either X or O)
     */
	public String getPlayer() {
		return player;
	}
	
	/**
     * Sets the player's identifier.
     *
     * @param id 	the player's identifier (either X or O)
     */
	public void setPlayer(String id) {
		player = id;
	} 
	
	/**
     * Changes the user interface after the player enters their name.
     */
	public void changeNameUI() {
		String playerName = nameField.getText();
		nameField.setEnabled(false);
		submit.setEnabled(false);
		setTitle("Tic Tac Toe - Player: " + playerName);
		messageTitle.setText("WELCOME " + playerName);
	}
	
	/**
     * Disables all buttons on the game board.
     */
	public void disabledBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				final int row = i;
				final int col = j;
				buttonArray[row][col].setEnabled(false);
			}
		}
	}
	
	/**
     * Enables all buttons on the game board.
     */
	public void enabledBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				final int row = i;
				final int col = j;
				buttonArray[row][col].setEnabled(true);
			}
		}
	}
	
	/**
     * Resets the game board by clearing all texts in the buttons.
     */
	public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
            	final int row = i;
            	final int col = j;
                buttonArray[row][col].setText("");
            }
        }
    }
	
	/**
     * Updates the current player's turn based on the player name.
     *
     * @param playerName 	the name of the player whose turn it is
     */
	public void updateTurn(String playerName) {
		if (!playerName.equals("Stop")) {
			if (this.player == playerName) {
				this.isPlayerTurn = true;
			} else {
				this.isPlayerTurn = false;
			}
		}
	}
	
	/**
     * Returns the button at the specified row and column.
     *
     * @param row 	the row index of the button
     * @param col 	the column index of the button
     * @return the JButton at the specified location
     */
	public JButton getBoardButton(int row, int col) {
		return buttonArray[row][col];
	}
	
	 /**
     * Removes all action listeners from the specified button.
     *
     * @param row 	the row index of the button
     * @param col 	the column index of the button
     */
	public void removeButtonAl(int row, int col) {
		for (ActionListener al : buttonArray[row][col].getActionListeners()) {
            buttonArray[row][col].removeActionListener(al);
        }
	}

	/**
     * Displays the result of the game and prompts the player to play again.
     *
     * @param result 	the result of the game ("draw", "X", or "O")
     * @return true if the player chooses to play again, false otherwise
     */
	public boolean result(String result) {
        String message = "";
        lastResult = result;
        if (result.equals("draw")) {
            message = "It's a draw! Play again?";
        } else {
            if (result.equals(player)) {
            	message = "Congratulations. You win! Do you want to play again?";
            } else {
            	message = "You lose. Do you want to play again?";
            }
        }
        
        int option = JOptionPane.showOptionDialog(this, 
                message,
                "Game Over",
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE,
                null,  
                new Object[] {"Yes", "No"},
                null
        );
        
        if (option == JOptionPane.YES_OPTION) {
        	return true;
        } else {
        	return false;
        }
    }
	
	/**
     * Updates the score based on the last game result.
     */
	public void updateScore() {
		if (lastResult.equals("draw")) {
            scoreBoard.addDrawScore();
        } else {
            if (lastResult.equals("X")) {
            	scoreBoard.addP1Score();
            } else if (lastResult.equals("O")) {
            	scoreBoard.addP2Score();
            }
        }
	}
	
	/**
     * Displays a message when a player leaves the game.
     */
	public void playerLeft() {
        int option = JOptionPane.showOptionDialog(this, 
                "Game Ends. One of the players left",
                "Game Over",
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE,
                null,  
                new Object[] {"Okay"},
                null
        );
        if (option == JOptionPane.YES_OPTION || option == JOptionPane.CLOSED_OPTION) {
        	System.exit(0);
        }
    }
	
	/**
     * Returns the current player's turn.
     *
     * @return the current player (either X or O)
     */
	public String getTurn() {
		if (isPlayerTurn) return player;
		else return (player == "X" ? "O" : "X");
	}
	
	/**
     * Returns the control bar of the game.
     *
     * @return the ControlBar instance
     */
	public ControlBar getControlBar() {
		return control_bar;
	}

}
