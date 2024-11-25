package clueGame;

/**
 * GameCardPanel: Responsible for the panel showing the cards that have seen or are in hand.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
public class GameCardPanel extends JPanel{
	private JPanel weaponDisplaySeen;
	private JPanel peopleDisplaySeen;
	private JPanel roomDisplaySeen;
	private JPanel weaponDisplayHand;
	private JPanel peopleDisplayHand;
	private JPanel roomDisplayHand;
	private JPanel weaponDisplay;
	private JPanel roomDisplay;
	private JPanel peopleDisplay;

	private ArrayList<JTextField> peopleInHand = new ArrayList<>();
	private ArrayList<JTextField> roomsInHand = new ArrayList<>();
	private ArrayList<JTextField> weaponsInHand = new ArrayList<>();

	private ArrayList<JTextField> peopleSeen = new ArrayList<>();
	private ArrayList<JTextField> roomsSeen = new ArrayList<>();
	private ArrayList<JTextField> weaponsSeen = new ArrayList<>();

	private ArrayList<Card> cardsSeen = new ArrayList<>();
	
	private int numWeaponSeenDim = 1;
	private int numPeopleSeenDim = 1;
	private int numRoomSeenDim = 1;

	private int numRoomHandDim = 1;
	private int numPeopleHandDim = 1;
	private int numWeaponHandDim = 1;

	// Constructor
	public GameCardPanel() {
		setBackground(Color.GRAY);
		setBorder(new TitledBorder("Known Cards"));
		setLayout(new GridLayout(3, 0)); // three rows
		JTextField noneText = new JTextField(5);
		noneText.setText("None");
		noneText.setEditable(false);

		peopleDisplay = new JPanel(new GridLayout(4, 0));
		peopleDisplay.setBackground(Color.GRAY);
		peopleDisplayHand = new JPanel(new GridLayout(numPeopleHandDim,0));
		peopleDisplaySeen = new JPanel(new GridLayout(numPeopleSeenDim, 0));

		weaponDisplay = new JPanel(new GridLayout(4, 0));
		weaponDisplay.setBackground(Color.GRAY);
		weaponDisplayHand = new JPanel(new GridLayout(numWeaponHandDim,0));
		weaponDisplaySeen = new JPanel(new GridLayout(numWeaponSeenDim, 0));

		roomDisplay = new JPanel(new GridLayout(4, 0));
		roomDisplay.setBackground(Color.GRAY);
		roomDisplayHand = new JPanel(new GridLayout(numRoomHandDim,0));
		roomDisplaySeen = new JPanel(new GridLayout(numRoomSeenDim,0));

		add(peopleDisplay);
		add(roomDisplay);
		add(weaponDisplay);
		updatePanels();
		if(Board.getInstance().getHumanPlayer() != null) {
			for(Card card :Board.getInstance().getHumanPlayer().getHand()) {
				updateHand(card , Board.getInstance().getHumanPlayer());
			}
		}

	}
	// Updates the seen cards shown on the panel.
	public void updateSeen(Card card, Player player) {
		if(cardsSeen.contains(card)) {
			return;
		}
		String cardName = card.getCardName();
		JTextField cardAdd = new JTextField(5);
		cardAdd.setText(cardName);
		Color color;
		
		try {
			Field field = Class.forName("java.awt.Color").getField(player.getColor().toLowerCase());
			color = (Color)field.get(null);
			cardAdd.setBackground(color);;
		} catch (Exception e) {
			color = null;
		}
		if(color == Color.black) {
			cardAdd.setSelectedTextColor(Color.white);
		}
		cardAdd.setEditable(false);
		switch (card.getType()) {
		case CardType.PERSON:
			if (!peopleSeen.contains(cardAdd)) {
				if(peopleSeen.size() != 0 ) {
					numPeopleSeenDim++;
					peopleSeen.add(cardAdd);
				}else {
					peopleSeen.add(cardAdd);
				}
			}
			break;
		case CardType.ROOM:
			if (!roomsSeen.contains(cardAdd)) {
				if(roomsSeen.size() != 0) { 
					numRoomSeenDim++;
					roomsSeen.add(cardAdd);
				}else {
					roomsSeen.add(cardAdd);
				}
			}
			break;
		case CardType.WEAPON:
			if (!weaponsSeen.contains(cardAdd)) {
				if(weaponsSeen.size() != 0) {
					numWeaponSeenDim++;
					weaponsSeen.add(cardAdd);
				}else {
					weaponsSeen.add(cardAdd);
				}	
			}
			break;
		}
		cardsSeen.add(card);
		updatePanels();
	}
	// Updates the hand of cards that the player has.
	public void updateHand(Card card, Player player) {
		String cardName = card.getCardName();
		JTextField cardAdd = new JTextField(5);
		cardAdd.setText(cardName);
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(player.getColor().toLowerCase());
			color = (Color)field.get(null);
			cardAdd.setBackground(color);;
		} catch (Exception e) {
			color = null;
		}
		
		cardAdd.setEditable(false);
		
		switch (card.getType()) {
		case PERSON:
			if (peopleInHand.size() != 0) {
				numPeopleHandDim++;
				peopleInHand.add(cardAdd);
			}else {
				peopleInHand.add(cardAdd);
			}
			break;
		case ROOM:
			if(roomsInHand.size() != 0) {
				numRoomHandDim++;
				roomsInHand.add(cardAdd);
			}else {
				roomsInHand.add(cardAdd);
			}
			break;
		case WEAPON:
			if(weaponsInHand.size() !=0) {
				numWeaponHandDim++;
				weaponsInHand.add(cardAdd);
			}else {
				weaponsInHand.add(cardAdd);
			}
			break;
		}
		updatePanels();
	}
	// Updates the panels visual by removing all previous information and updating it with the newer cards.
	private void updatePanels() {
		peopleDisplayHand.removeAll();
		roomDisplayHand.removeAll();
		weaponDisplayHand.removeAll();
		peopleDisplaySeen.removeAll();
		roomDisplaySeen.removeAll();
		weaponDisplaySeen.removeAll();

		peopleDisplay.removeAll();
		weaponDisplay.removeAll();
		roomDisplay.removeAll();

		peopleDisplayHand = new JPanel(new GridLayout(numPeopleHandDim,0));
		peopleDisplaySeen = new JPanel(new GridLayout(numPeopleSeenDim, 0));

		weaponDisplayHand = new JPanel(new GridLayout(numWeaponHandDim,0));
		weaponDisplaySeen = new JPanel(new GridLayout(numWeaponSeenDim, 0));

		roomDisplayHand = new JPanel(new GridLayout(numRoomHandDim,0));
		roomDisplaySeen = new JPanel(new GridLayout(numRoomSeenDim,0));

		for(JTextField text : peopleInHand) {
			peopleDisplayHand.add(text);
		}
		for(JTextField text : weaponsInHand) {
			weaponDisplayHand.add(text);
		}
		for(JTextField text : roomsInHand) {
			roomDisplayHand.add(text);
		}
		for(JTextField text : peopleSeen) {
			peopleDisplaySeen.add(text);
		}
		for(JTextField text : weaponsSeen) {
			weaponDisplaySeen.add(text);
		}
		for(JTextField text : roomsSeen) {
			roomDisplaySeen.add(text);
		}
		if(peopleInHand.size() == 0) {
			JTextField noneText = new JTextField(5);
			noneText.setText("None");
			noneText.setEditable(false);
			peopleDisplayHand.add(noneText);
		}
		if(weaponsInHand.size() == 0) {
			JTextField noneText = new JTextField(5);
			noneText.setText("None");
			noneText.setEditable(false);
			weaponDisplayHand.add(noneText);
		}
		if(roomsInHand.size() == 0) {
			JTextField noneText = new JTextField(5);
			noneText.setText("None");
			noneText.setEditable(false);
			roomDisplayHand.add(noneText);
		}
		if(peopleSeen.size() == 0) {
			JTextField noneText = new JTextField(5);
			noneText.setText("None");
			noneText.setEditable(false);
			peopleDisplaySeen.add(noneText);
		}
		if(weaponsSeen.size() == 0) {
			JTextField noneText = new JTextField(5);
			noneText.setText("None");
			noneText.setEditable(false);
			weaponDisplaySeen.add(noneText);
		}
		if(roomsSeen.size() == 0) {
			JTextField noneText = new JTextField(5);
			noneText.setText("None");
			noneText.setEditable(false);
			roomDisplaySeen.add(noneText);
		}
		setBackground(Color.GRAY);

		peopleDisplay.setBackground(Color.GRAY);
		peopleDisplay.setBorder(new TitledBorder("People:"));
		peopleDisplay.add(new JLabel("In Hand:"));
		peopleDisplay.add(peopleDisplayHand);
		peopleDisplay.add(new JLabel("Seen:"));
		peopleDisplay.add(peopleDisplaySeen);

		weaponDisplay.setBackground(Color.GRAY);
		weaponDisplay.setBorder(new TitledBorder("Weapons:"));
		weaponDisplay.add(new JLabel("In Hand:"));
		weaponDisplay.add(weaponDisplayHand);
		weaponDisplay.add(new JLabel("Seen:"));
		weaponDisplay.add(weaponDisplaySeen);

		roomDisplay.setBackground(Color.GRAY);
		roomDisplay.setBorder(new TitledBorder("Rooms:"));
		roomDisplay.add(new JLabel("In Hand:"));
		roomDisplay.add(roomDisplayHand);
		roomDisplay.add(new JLabel("Seen:"));
		roomDisplay.add(roomDisplaySeen);

		peopleDisplay.revalidate();
		peopleDisplay.repaint();
		roomDisplay.revalidate();
		roomDisplay.repaint();
		weaponDisplay.revalidate();
		weaponDisplay.repaint();

	}
	// Dimension sizing for the visual.
	public Dimension getPreferredSize() {
		// Set a custom width while keeping the height flexible
		return new Dimension(150, super.getPreferredSize().height); // Adjust 200 to your preferred width
	}
	// main
	public static void main(String[] args) {
		GameCardPanel panel = new GameCardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// Sample cards to demonstrate update functionality
		panel.updateHand(new Card("Colonel Mustard", CardType.PERSON), new ComputerPlayer("Professor plum", "Green", 0, 0));
		panel.updateHand(new Card("Knife", CardType.WEAPON), new ComputerPlayer("Professor plum", "Green", 0, 0));
		panel.updateHand(new Card("Library", CardType.ROOM), new ComputerPlayer("Professor plum", "Green", 0, 0));
		Card card = new Card("Professor Plum", CardType.PERSON);
		panel.updateSeen(card, new ComputerPlayer("Professor plum", "Yellow", 0, 0));
		panel.updateSeen(card, new ComputerPlayer("Professor plum", "Yellow", 0, 0));
		panel.updateSeen(new Card("Candlestick", CardType.WEAPON), new ComputerPlayer("Example", "red", 0, 0));
		panel.updateSeen(new Card("Kitchen", CardType.ROOM), new ComputerPlayer("Example", "blue", 0, 0));
		panel.updateSeen(new Card("New Room", CardType.ROOM), new ComputerPlayer("Example", "white", 0, 0));

		GameCardPanel panel1 = new GameCardPanel();  // create the panel
		JFrame frame1 = new JFrame();  // create the frame 
		frame1.setContentPane(panel1); // put the panel in the frame
		frame1.setSize(180, 750);  // size the frame
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame1.setVisible(true); // make it visible
	}
}
