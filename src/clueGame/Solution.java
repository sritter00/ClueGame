package clueGame;

/**
 * Solution: Holds the solution to the game, which consists of one room, one person, and one weapon card.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;

	// Constructor
	public Solution(Card room, Card person, Card weapon) {
		this.room = room; 
		this.person = person;
		this.weapon = weapon;
	}
	// Getter for room of the solution.
	public Card getRoom() {
		return room;
	}
	// Getter for person of the solution.
	public Card getPerson() {
		return person;
	}
	// Getter for the weapon of the solution.
	public Card getWeapon() {
		return weapon;
	}
}
