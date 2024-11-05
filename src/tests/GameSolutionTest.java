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

public class GameSolutionTest {

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
	public void CheckAccusation(){
		Card personCard = board.getCard("Johann");
		Card roomCard = board.getCard("Guest House");
		Card weaponCard = board.getCard("Glock 19");
		Card wrongCardPerson = board.getCard("Diego");
		Card wrongCardRoom = board.getCard("Spa");
		Card wrongCardWeapon = board.getCard("Broken Bottle");
		Solution newSolution = new Solution(roomCard, personCard, weaponCard);
		board.setSolution(newSolution);
		assertTrue(board.checkAccusation(personCard, roomCard, weaponCard));
		assertFalse(board.checkAccusation(wrongCardPerson, roomCard, weaponCard));
		assertFalse(board.checkAccusation(personCard, wrongCardRoom, weaponCard));
		assertFalse(board.checkAccusation(personCard, roomCard, wrongCardWeapon));
	}
	@Test
	public void disproveSuggestion() {
		Card personCard = board.getCard("Johann");
		Card roomCard = board.getCard("Guest House");
		Card weaponCard = board.getCard("Glock 19");
		Solution suggestion = new Solution(roomCard, personCard, weaponCard);
		
		HumanPlayer playerOneMatch = new HumanPlayer("One Match", "Purple", 0, 0);
		playerOneMatch.updateHand(weaponCard);
		Card answer = playerOneMatch.disproveSuggestion(suggestion);
		assertTrue(answer.equals(weaponCard));
		
		HumanPlayer playerNoMatch = new HumanPlayer("No Match", "Purple", 0, 0);
		answer = playerNoMatch.disproveSuggestion(suggestion);
		assertEquals(answer, null);
		
		HumanPlayer playerMultipleMatch = new HumanPlayer("Two Match", "Purple", 0, 0);
		playerMultipleMatch.updateHand(weaponCard);
		playerMultipleMatch.updateHand(personCard);
		answer = playerMultipleMatch.disproveSuggestion(suggestion);
		assertTrue(answer.equals(weaponCard) || answer.equals(personCard));
	}	
	@Test
	public void HandleSuggestion() {
		
	}
}
