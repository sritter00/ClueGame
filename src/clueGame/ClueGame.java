package clueGame;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
public class ClueGame extends JFrame{
	private static Board board;
	
	public static void main(String[] args) {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		Board panel = board;  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
	
	
}
