import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

/**
 * The ScoreBoard class represents a panel that displays the scores
 * of the player, computer, and the number of draws in a Tic Tac Toe game.
 */
public class ScoreBoard extends JPanel {
	private int p1Score;
	JLabel p1ScoreLabel;
	private int p2Score;
	JLabel p2ScoreLabel;
	private int drawScore;
	JLabel drawScoreLabel;
	
	/**
     * Constructs a ScoreBoard and initializes the score display.
     */
	public ScoreBoard() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(320, 600));
		
		JPanel scoreSection = new JPanel();
		scoreSection.setLayout(new GridLayout(3, 2));
		
		JLabel playerWinLabel = new JLabel("Player 1 Wins:");
		p1ScoreLabel = new JLabel(String.valueOf(p1Score));
		playerWinLabel.setFont(new Font("SF Pro", Font.PLAIN, 18));
		p1ScoreLabel.setFont(new Font("SF Pro", Font.PLAIN, 18));
		scoreSection.add(playerWinLabel);
		scoreSection.add(p1ScoreLabel);
		
		JLabel comWinLabel = new JLabel("Player 2 Wins:");
		p2ScoreLabel = new JLabel(String.valueOf(p2Score));
		comWinLabel.setFont(new Font("SF Pro", Font.PLAIN, 18));
		p2ScoreLabel.setFont(new Font("SF Pro", Font.PLAIN, 18));
		scoreSection.add(comWinLabel);
		scoreSection.add(p2ScoreLabel);
		
		
		JLabel drawWinLabel = new JLabel("Draws:");
		drawScoreLabel = new JLabel(String.valueOf(drawScore));
		drawWinLabel.setFont(new Font("SF Pro", Font.PLAIN, 18));
		drawScoreLabel.setFont(new Font("SF Pro", Font.PLAIN, 18));
		scoreSection.add(drawWinLabel);
		scoreSection.add(drawScoreLabel);
		
		add(scoreSection);
		
	}
 	
	/**
     *  Increment the player 1's score by 1 and updates the score display.
     */
	public void addP1Score() {
		p1Score += 1;
		p1ScoreLabel.setText(String.valueOf(p1Score));
	}
	
	/**
     *  Increment the player 1's score by 1 and updates the score display.
     */
	public void addP2Score() {
		p2Score += 1;
		p2ScoreLabel.setText(String.valueOf(p2Score));
	}
	
	/**
     *  Increment the number of draw by 1 and updates the number display.
     */
	public void addDrawScore() {
		drawScore += 1;
		drawScoreLabel.setText(String.valueOf(drawScore));
	}
}
