package tests;
import static org.junit.Assert.*;

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
	public void TestCards() {
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
	public void TestSolution() {
		int numSamePerson = 0;
		int numSameRoom = 0;
		int numSameWeapon = 0;
		Solution currentSolution = board.getSolution();
		for(int i = 0; i < 1000; i++) {
			assertTrue(currentSolution.getPerson().getType() == CardType.PERSON);
			assertTrue(currentSolution.getRoom().getType() == CardType.ROOM);
			assertTrue(currentSolution.getWeapon().getType() == CardType.WEAPON);
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
		System.out.print(numSameRoom);
		assertTrue(numSamePerson <= 250 && numSamePerson >= 100); // make sure average number of times the same is about 1/6 of 1000
		assertTrue(numSameRoom <= 200 && numSameRoom >= 70); // make sure average number of times is about 1/9 of 1000
		assertTrue(numSameWeapon <= 200 && numSameWeapon >= 70); // make sure average number of times is about 1/9 of 1000
	}
	
}
