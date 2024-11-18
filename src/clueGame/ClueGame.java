package clueGame;

/**
 * ClueGame: Builds the clue game visual.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ClueGame extends JFrame{
	private static Board board;
	// main
	public static void main(String[] args) {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		Board boardPanel = board;  // create the panel
		JFrame frame = new JFrame("Clue Game");
		frame.setLayout(new BorderLayout());  // Use BorderLayout for positioning panels
		frame.setSize(750, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(boardPanel, BorderLayout.CENTER);

		GameControlPanel controlPanel = new GameControlPanel();  // create the panel
		frame.add(controlPanel, BorderLayout.SOUTH); // put the panel in the frame

		GameCardPanel cardPanel = new GameCardPanel();  // create the panel
		frame.add(cardPanel,BorderLayout.EAST); // put the panel in the frame
		board.nextPlayer();
		controlPanel.setTurn(board.getCurrentPlayer(), board.getRoll());
		frame.setVisible(true); // make it visible
		JOptionPane.showMessageDialog(frame, "You are Diego.\nCan you find the solution\nbefore the Computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}


}

