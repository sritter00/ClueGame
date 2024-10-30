package clueGame;

/**
 * Card: Represents a card in the game, with a name and type, and includes an equals method to compare cards by name and type.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

public class Card {
	private String cardName;
	private CardType type;

	// Constructor
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	// Getter for name of card.
	public String getCardName() {
		return cardName;
	}
	// Getter for type of card.
	public CardType getType() {
		return type;
	}
	// Equals method
	public boolean equals(Card target) {
		// Needs to be built
		return false;
	}
}
