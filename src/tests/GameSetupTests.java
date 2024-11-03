package tests;

/*
 * This program tests that players and cards are set up properly 
 */

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.*;
public class GameSetupTests {

	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void TestCards() { // Checks if cards are loaded
		assertEquals("Guest House", board.getCard("Guest House").getCardName());
		assertEquals(CardType.ROOM, board.getCard("Guest House").getType());
		assertEquals("Knife", board.getCard("Knife").getCardName());
		assertEquals(CardType.WEAPON, board.getCard("Knife").getType());
		assertEquals("Diego", board.getCard("Diego").getCardName());
		assertEquals(CardType.PERSON, board.getCard("Diego").getType());
		assertEquals("Spa", board.getCard("Spa").getCardName());
		assertEquals(CardType.ROOM, board.getCard("Spa").getType());
		assertEquals("Brick", board.getCard("Brick").getCardName());
		assertEquals(CardType.WEAPON, board.getCard("Knife").getType());
		assertEquals("Johann", board.getCard("Johann").getCardName());
		assertEquals(CardType.PERSON, board.getCard("Johann").getType());
	}
	@Test
	public void TestSolution() { // Checks if solution is loaded
		int numSamePerson = 0;
		int numSameRoom = 0;
		int numSameWeapon = 0;
		Solution currentSolution = board.getSolution();
		for(int i = 0; i < 1000; i++) {
			assertTrue(currentSolution.getPerson().getType() == CardType.PERSON);// one person card
			assertTrue(currentSolution.getRoom().getType() == CardType.ROOM);//one room card
			assertTrue(currentSolution.getWeapon().getType() == CardType.WEAPON);// one weapon card
			if(currentSolution.getPerson().getCardName().equals("Diego") ) {
				numSamePerson++;
			}
			if(currentSolution.getRoom().getCardName().equals("Patio") ) {
				numSameRoom++;
			}
			if(currentSolution.getWeapon().getCardName().equals("Rope")) {
				numSameWeapon++;
			}
			
			board.generateSolution();
			currentSolution = board.getSolution();
		}
		assertTrue(numSamePerson <= 250 && numSamePerson >= 100); // make sure average number of times the same is about 1/6 of 1000
		assertTrue(numSameRoom <= 200 && numSameRoom >= 70); // make sure average number of times is about 1/9 of 1000
		assertTrue(numSameWeapon <= 200 && numSameWeapon >= 70); // make sure average number of times is about 1/9 of 1000
	}
	@Test
	public void TestPlayersAreLoaded() {// Checks if we load Players into playerlist properly (1 human 5 Computers)
		Player computerPlayer = new ComputerPlayer("Blank", "No Color", 0, 0);
		Class<?> computerClass = computerPlayer.getClass();
		Player humanPlayer = new HumanPlayer("Blank", "No Color", 0, 0);
		Class<?> humanClass = humanPlayer.getClass();
		int numHumanClass = 0;
		int numCompClass = 0;
		List<Player> playerList = new ArrayList<>(board.getPlayerList());
		assertTrue(playerList.size() == 6);
		for(Player player : playerList) {//Checks the class of each player in playerlist and counts the amount of different subclasses
			if(player.getClass().equals(computerClass)) {
				numCompClass++;
			}
			if(player.getClass().equals(humanClass)) {
				numHumanClass++;
			}		
		}
		assertEquals(5, numCompClass);// There are 5 computer players
		assertEquals(1, numHumanClass);//There is 1 Human player
		
	}
	@Test
	public void TestCardsDealt() { // Checks if the cards are dealt properly 
		List<Player> playerList = new ArrayList<>(board.getPlayerList());
		List<Card> cardList = new ArrayList<>();
		Set<Card> cardSet = new HashSet<>();
		int totalCards = 0;
		int totalCardsPerPlayer = 0;
		for(Player player : playerList) {
			for(Card card : player.getHand()) {
				totalCards++;
				totalCardsPerPlayer++;
				cardList.add(card);
				cardSet.add(card);
			}
		assertEquals(4, totalCardsPerPlayer);//Makes sure that there is only 4 cards per player
		totalCardsPerPlayer = 0;
		}
		assertEquals(totalCards, 24);//Checks if total cards is 24
		assertEquals(cardSet.size(), cardList.size());//Checks if there all distinct cards
	}
	
}
