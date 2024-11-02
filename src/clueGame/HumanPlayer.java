package clueGame;

import java.util.ArrayList;
import java.util.List;

/**
 * HumanPlayer: Child class of player, that represents a human-controlled player using GUI functionality.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 * 
 */

public class HumanPlayer extends Player {
	private List<Card> hand = new ArrayList<Card>();
	// Constructor
	public HumanPlayer(String name, String color, int row, int column) {
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
