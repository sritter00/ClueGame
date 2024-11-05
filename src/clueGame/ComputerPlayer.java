package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * ComputerPlayer: Child class of Player that represents a computer-controlled player using AI logic.
 * 
 * author: Shane Ritter
 * authpr: Carter Gorling
 * 
 */

public class ComputerPlayer extends Player {
	private List<Card> hand = new ArrayList<Card>();
	private Set<Card> seenCards = new HashSet<>();
	private Room currentRoom = null;
	// Constructor
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		hand = new ArrayList<Card>();
	}
	public void updateHand(Card card) {
		hand.add(card);
	}
	public Card disproveSuggestion(Solution suggestion) {
		List<Card> matchingCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.equals(suggestion.getPerson()) || card.equals(suggestion.getWeapon()) || card.equals(suggestion.getRoom())) {
                matchingCards.add(card);
            }
        }
        if (matchingCards.isEmpty()) {
            return null;
        } else if (matchingCards.size() == 1) {
            return matchingCards.get(0);
        } else {
            Random random = new Random();
            return matchingCards.get(random.nextInt(matchingCards.size()));
        }
	}
	// Getter for the hand list.
	public List<Card> getHand() {
	    return hand;
	}
	// Adds a card to the seen card set
	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}
	// Returns a suggestion based off of seen cards
	public Solution createSuggestion() {
		
	}
	// setter for current Room
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	// getter for the current Room
	public Room getCurrentRoom() {
		return currentRoom;
	}

}
