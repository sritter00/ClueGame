package clueGame;

/**
 * GameControlPanel: Layout for the control panel with setters for turns and guesses
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.*;
import javax.swing.border.TitledBorder;


public class GameControlPanel extends JPanel {
	private JButton nextPlayerButton;
	private JButton accusationButton;
	private JTextField rollDisplay;
	private JTextField turnDisplay;
	private JTextField guessDisplay;
	private JTextField guessResultDisplay;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2, 0)); // 2 rows, flexible columns

		// Top Panel (1x4)
		JPanel topPanel = new JPanel(new GridLayout(1, 4));

		// Turn Panel with Label and TextField
		JPanel turnPanel = new JPanel(new GridLayout(2, 1));
		turnPanel.add(new JLabel("Turn:"));
		turnDisplay = new JTextField(15);
		turnDisplay.setEditable(false);
		turnPanel.add(turnDisplay);

		// Roll Panel with Label and TextField
		JPanel rollPanel = new JPanel(new GridLayout(2, 1));
		rollPanel.add(new JLabel("Roll:"));
		rollDisplay = new JTextField(5);
		rollDisplay.setEditable(false);
		rollPanel.add(rollDisplay);

		// Buttons Panel
		nextPlayerButton = new JButton("Next");
		// Add action listener to the button
		nextPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Board.getInstance().humanTurnDone()) {
					setGuessResult("");
					setGuess("");
					Board.getInstance().nextPlayer(); // Delegate logic to Board
					setTurn(Board.getInstance().getCurrentPlayer(), Board.getInstance().getRoll());
					if(Board.getInstance().getSuggestion() != null) {
						setGuess(Board.getInstance().getSuggestion().getRoom().getCardName() + " , " 
								+ Board.getInstance().getSuggestion().getPerson().getCardName() + " , "
								+ Board.getInstance().getSuggestion().getWeapon().getCardName());
						if(Board.getInstance().getCurrentPlayer().getClass() != HumanPlayer.class) {
							if (Board.getInstance().getDisprovenCard() != null) {
								setGuessResult("Guess Disproven");
							}else {
								setGuessResult("Guess Not Disproven");
							}
						}
					}



				}
			}
		});
		accusationButton = new JButton("Make Accusation");
		accusationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Board.getInstance().canMakeAcusation()) {
					AccusationDialog accusationDialog = new AccusationDialog(
		                    SwingUtilities.getWindowAncestor(Board.getInstance()), Board.getInstance().getCardList()
		                );
					accusationDialog.setVisible(true); // Display the dialog
					Solution accusation = accusationDialog.getAccusation();
					if(Board.getInstance().checkAccusation(accusation.getPerson(), accusation.getRoom(),accusation.getWeapon())) {
						Board.getInstance().setPlayerWon(true);
					}
					Board.getInstance().endGame();
				}else {
					JOptionPane.showMessageDialog(Board.getInstance(), "Can't Make Acusation Until the Start of Your Turn", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// Add components to Top Panel
		topPanel.add(turnPanel);
		topPanel.add(rollPanel);
		topPanel.add(nextPlayerButton);
		topPanel.add(accusationButton);
		// Bottom Panel (0x2)
		JPanel bottomPanel = new JPanel(new GridLayout(0, 2));

		// Guess Panel with Border
		JPanel guessPanel = new JPanel(new GridLayout(1, 0));
		guessPanel.setBorder(new TitledBorder("Guess"));
		guessDisplay = new JTextField(20);
		guessDisplay.setEditable(false);
		guessPanel.add(guessDisplay);

		// Guess Result Panel with Border
		JPanel guessResultPanel = new JPanel(new GridLayout(1, 0));
		guessResultPanel.setBorder(new TitledBorder("Response"));
		guessResultDisplay = new JTextField(20);
		guessResultDisplay.setEditable(false);
		guessResultPanel.add(guessResultDisplay);

		// Add panels to Bottom Panel
		bottomPanel.add(guessPanel);
		bottomPanel.add(guessResultPanel);

		// Add panels
		add(topPanel);
		add(bottomPanel);
	}


	// Setter for the Players turn.
	public void setTurn(Player player, int roll) {
		turnDisplay.setText(player.getName());
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(player.getColor().toLowerCase());
			color = (Color)field.get(null);
			turnDisplay.setBackground(color);;
		} catch (Exception e) {
			color = null;
		}
		rollDisplay.setText(String.valueOf(roll));
	}

	// Setter for the guess display.
	public void setGuess(String guess) {
		guessDisplay.setText(guess);
	}

	// Setter for the display of the guess result.
	public void setGuessResult(String result) {
		guessResultDisplay.setBackground(Color.WHITE);
		guessResultDisplay.setText(result);
	}
	// Setter for the display of the guess result.
	public void setGuessResult(String result, Player player) {
		guessResultDisplay.setText(result);
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(player.getColor().toLowerCase());
			color = (Color)field.get(null);
			guessResultDisplay.setBackground(color);;
		} catch (Exception e) {
			color = null;
		}
	}
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard","orange", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}