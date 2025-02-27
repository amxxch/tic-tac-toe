import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 

/**
 * The ControlBar class represents a menu bar for the Tic Tac Toe game,
 * providing control options such as exiting the game and viewing instructions.
 */
public class ControlBar extends JMenuBar implements ActionListener {
	private JMenu control, help;
	private JMenuItem exit, instructions;
	
	/**
     * Constructs a ControlBar and initializes the menu items.
     */
	public ControlBar() {
		control = new JMenu("Control");
		exit = new JMenuItem("Exit");
		control.setFont(new Font("SF Pro", Font.PLAIN, 18));
		exit.setFont(new Font("SF Pro", Font.PLAIN, 18));
		control.add(exit);
		
		help = new JMenu("Help");
		instructions = new JMenuItem("Instructions");
		help.setFont(new Font("SF Pro", Font.PLAIN, 18));
		instructions.setFont(new Font("SF Pro", Font.PLAIN, 18));
		help.add(instructions);
		
		add(control);
		add(help);
		
		instructions.addActionListener(this);
		
	}
	
	/**
     * Handles menu item actions.
     *
     * @param event 	The action event triggered by a menu item.
     */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == instructions) {
			showInstructionsDialog();
		}
		// exit action is written in controller
	}
	
	/**
     * Displays a dialog with instructions for the game.
     */
	private void showInstructionsDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Instructions", true); // true for modal
        dialog.setSize(850, 250);
        dialog.setLayout(new FlowLayout());
        dialog.setFont(new Font("SF Pro", Font.PLAIN, 14));

        JTextArea instructionsArea = new JTextArea(
        		"\n" +
        		"Some information about the game:\n" +
        		"- The move is not occupied by any mark.\n" +
        		"- The move is made in the player's turn.\n" +
        		"- The move is made within the 3 x 3 board.\n" +
        		"\n" +
        		"The game would continue and switch among the opposite player until it reaces either one of the following conditions: \n" +
        		"- Player 1 wins.\n" +
        		"- Player 2 wins.\n" +
        		"- Draw.\n" +
        		"- One of the players leaves the game."
        );
        
        instructionsArea.setBackground(new Color(0, 0, 0, 0));
        instructionsArea.setOpaque(false);
        instructionsArea.setCaretColor(new Color(255, 255, 255, 0));
        
        
        JButton yesButton = new JButton("Okay");
        yesButton.addActionListener(e -> dialog.dispose());
       
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(instructionsArea);
        
        yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(yesButton);

        dialog.add(centerPanel, BorderLayout.CENTER); 
              
        dialog.add(centerPanel);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
	
	 /**
     * Returns the exit menu item.
     *
     * @return the exit menu item
     */
	public JMenuItem getExitButton() {
		return exit;
	}
}
