package clueGame;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
public class GameCardPanel extends JPanel{
	private JTextField weaponDisplaySeen;
	private JTextField peopleDisplaySeen;
	private JTextField roomDisplaySeen;
	private JTextField weaponDisplayHand;
	private JTextField peopleDisplayHand;
	private JTextField roomDisplayHand;
	private JPanel weaponDisplay;
	private JPanel roomDisplay;
	private JPanel peopleDisplay;
	
	
	public GameCardPanel() {
		setBorder(new TitledBorder("Known Cards"));
		setLayout(new GridLayout(3, 0)); // three rows
		peopleDisplay = new JPanel(new GridLayout(4, 0));

		peopleDisplayHand = new JTextField(5);
		peopleDisplaySeen = new JTextField(5);
		peopleDisplaySeen.setText("None");
		peopleDisplayHand.setText("None");
		peopleDisplay.setBorder(new TitledBorder("People:"));
		peopleDisplayHand.setEditable(false);
		peopleDisplaySeen.setEditable(false);
		peopleDisplay.add(new JLabel("In Hand:"));
		peopleDisplay.add(peopleDisplayHand);
		peopleDisplay.add(new JLabel("Seen:"));
		peopleDisplay.add(peopleDisplaySeen);

		weaponDisplay = new JPanel(new GridLayout(4, 0));

		weaponDisplayHand = new JTextField(5);
		weaponDisplaySeen = new JTextField(5);
		weaponDisplaySeen.setText("None");
		weaponDisplayHand.setText("None");
		weaponDisplayHand.setEditable(false);
		weaponDisplaySeen.setEditable(false);
		weaponDisplay.setBorder(new TitledBorder("Weapons:"));
		weaponDisplay.add(new JLabel("In Hand:"));
		weaponDisplay.add(weaponDisplayHand);
		weaponDisplay.add(new JLabel("Seen:"));
		weaponDisplay.add(weaponDisplaySeen);

		roomDisplay = new JPanel(new GridLayout(4, 0));

		roomDisplayHand = new JTextField(5);
		roomDisplaySeen = new JTextField(5);
		roomDisplaySeen.setText("None");
		roomDisplayHand.setText("None");
		roomDisplayHand.setEditable(false);
		roomDisplaySeen.setEditable(false);
		roomDisplay.setBorder(new TitledBorder("Rooms:"));
		roomDisplay.add(new JLabel("In Hand:"));
		roomDisplay.add(roomDisplayHand);
		roomDisplay.add(new JLabel("Seen:"));
		roomDisplay.add(roomDisplaySeen);

		add(peopleDisplay);
		add(roomDisplay);
		add(weaponDisplay);
	}

	public void updateSeen(Card card, Player player) {
		if(card.getType() == CardType.PERSON) {
				
		}
		if(card.getType() == CardType.ROOM) {
			
		}
		if(card.getType() == CardType.WEAPON) {
			
		}
	}
	public void updateHand(Card card) {
		if(card.getType() == CardType.PERSON) {			
			
		}
		if(card.getType() == CardType.ROOM) {
			
		}
		if(card.getType() == CardType.WEAPON) {
			
		}
	}



	public static void main(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		panel.updateHand(new Card("Example Card Person", CardType.PERSON));
		//		panel.updateHand(new Card("Example Card Person2", CardType.PERSON));
	}

	public static void main1(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

	}
}
