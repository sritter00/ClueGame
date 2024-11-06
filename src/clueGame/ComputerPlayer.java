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
	private Set<BoardCell> seenCells= new HashSet<>();
	// Constructor
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		hand = new ArrayList<Card>();
		seenCells= new HashSet<>();
		currentRoom = null;
	}
	public void updateHand(Card card) {
		hand.add(card);
		seenCards.add(card);
	}
	// Getter for the hand list.
	public List<Card> getHand() {
		return hand;
	}
	// Adds a card to the seen card set
	public void updateSeen(Card seenCard) {
		seenCards.add(seenCard);
	}
	//updates the seen cells 
	public void updateSeenRoom(BoardCell newSeenCell) {
		seenCells.add(newSeenCell);
	}
	
	// setter for current Room using specific room
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	// setter for current Room using row column
	public void setCurrentRoom(Board board) {
		this.currentRoom = board.getRoom(super.getRow(), super.getColumn());
	}
	// getter for the current Room
	public Room getCurrentRoom() {
		return currentRoom;
	}
	//selects target for the computer player
	public BoardCell selectTarget(Board board, int pathLength) {
		board.calcTargets(board.getCell(super.getRow(), super.getColumn()), pathLength  );
		List<BoardCell> targets = new ArrayList<>(board.getTargets());
		for(BoardCell cell : targets) {
			if(cell.isRoomCenter() && !seenCells.contains(cell)) { // if not been seen and room then return the cell
				return cell;
			}
		}
		Random rand = new Random();
		int randInt = rand.nextInt(targets.size());
		while(seenCells.contains(targets.get(randInt))) {
			randInt = rand.nextInt(targets.size());
		}
		return targets.get(randInt); // if no rooms in list return random location
	}
	// Returns a suggestion based off of seen cards
	public Solution createSuggestion(Board board) {
		boolean foundWeapon = false;
		boolean foundPerson = false;
		Card weaponCard = null;
		Card roomCard = null;
		Card personCard = null;
		Card CurrentCard = null;
		List<Card> cardArrayList = new ArrayList<>(board.getCardList());
		Random rand = new Random();
		for(Card card : seenCards) {
			cardArrayList.remove(card);
		}
		int randInt = rand.nextInt(cardArrayList.size());
	while(!(foundWeapon && foundPerson)) {
			randInt = rand.nextInt(cardArrayList.size());
			CurrentCard = cardArrayList.get(randInt);
			if(CurrentCard.getType().equals(CardType.PERSON) && foundPerson == false) {
				personCard = board.getCard(cardArrayList.get(randInt).getCardName());
				foundPerson = true;
			}else if(CurrentCard.getType().equals(CardType.WEAPON) && foundWeapon == false) {
				weaponCard = board.getCard(cardArrayList.get(randInt).getCardName());
				foundWeapon = true;
			}
		}
	roomCard = board.getCard(getCurrentRoom().getName());
	return new Solution(roomCard , personCard, weaponCard);
	}
	
	// disproves a suggestion
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

}
