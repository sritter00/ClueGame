package clueGame;

import java.util.ArrayList;
import java.util.List;

/**
 * ComputerPlayer: Child class of Player that represents a computer-controlled player using AI logic.
 * 
 * author: Shane Ritter
 * authpr: Carter Gorling
 * 
 */

public class ComputerPlayer extends Player {
	private List<Card> hand = new ArrayList<Card>();
	// Constructor
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		hand = new ArrayList<Card>();
	}
	public void updateHand(Card card) {
		hand.add(card);
	}
	// Getter for the hand list.
	public List<Card> getHand() {
	    return hand;
	}
}
