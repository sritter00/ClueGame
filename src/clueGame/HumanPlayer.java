package clueGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	// Updates hand of cards for the human player.
	public void updateHand(Card card) {
		hand.add(card);
	}
	// Disproves a suggestion made.
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
}
