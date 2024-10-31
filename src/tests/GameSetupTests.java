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
	assertEquals(CardType.PERSON, board.getCard("Diego").getType());
	}
}
