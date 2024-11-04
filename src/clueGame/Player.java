package clueGame;

/**
 * Player: An abstract class representing a player in the game, with attributes for name, 
 * color, location, and a hand of cards. It includes functionality for updating the player’s hand.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.util.*;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	private List<Card> hand = new ArrayList<Card>();

	// Constructor
	public Player(String name, String color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
	}
	// Update hand method adding a card to the hand list.
	abstract void updateHand(Card card);
	// Getter for the hand list.
	public List<Card> getHand() {
        return hand;
    }
    // Getter for the name of the player.
    public String getName() {
        return name;
    }
    // Getter for the color of the player.
    public String getColor() {
        return color;
    }
    // Getter for the row
    public int getRow() {
    	return row;
    }
    // Getter for the column
    public int getColumn() {
    	return column;
    }
}
