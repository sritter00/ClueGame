package clueGame;

/**
 * ClueGame: Builds the clue game visual.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.*;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{
	private static Board board;
	private static JFrame frame;
	private static Board boardPanel;
	private static GameControlPanel controlPanel;
	private static GameCardPanel cardPanel;
	
	// Getter for the board panel.
	public static Board getBoardPanel() {
		return boardPanel;
	}
	// Getter for the control panel.
	public static GameControlPanel getControlPanel() {
		return controlPanel;
	}
	// Getter for the card panel.
	public static GameCardPanel getCardPanel() {
		return cardPanel;
	}
	// Method that handles the end game dialog for the human player.
	private static void checkGameEnd() {
        if (board.getEndGame()) {
            if (board.playerWon()) {
                JOptionPane.showMessageDialog(frame, "Congratulations, you won!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "You lost. Better luck next time!", "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            // Exit the game after the dialog is closed
            System.exit(0);
        }
    }
	// main
	public static void main(String[] args) {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		board.generateSolution();
		board.deal();
		boardPanel = board;  // create the panel
		frame = new JFrame("Clue Game");
		frame.setLayout(new BorderLayout());  // Use BorderLayout for positioning panels
		frame.setSize(750, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(boardPanel, BorderLayout.CENTER);

		controlPanel = new GameControlPanel();  // create the panel
		frame.add(controlPanel, BorderLayout.SOUTH); // put the panel in the frame

		cardPanel = new GameCardPanel();  // create the panel
		frame.add(cardPanel,BorderLayout.EAST); // put the panel in the frame
		board.nextPlayer();
		controlPanel.setTurn(board.getCurrentPlayer(), board.getRoll());
		frame.setVisible(true); // make it visible
		JOptionPane.showMessageDialog(frame, "You are Diego.\nCan you find the solution\nbefore the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		// Start the game loop using a Swing Timer
		Timer gameLoopTimer = new Timer(100, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        checkGameEnd();
		    }
		});
		gameLoopTimer.start();
	}



}

