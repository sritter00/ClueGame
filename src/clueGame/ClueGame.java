package clueGame;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
public class ClueGame extends JFrame{
	public static void main(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

	}
	
	
}
