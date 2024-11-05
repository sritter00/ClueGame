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
	public void TestHandleSuggestion() {
		Card personCard = board.getCard("Johann");
		Card roomCard = board.getCard("Guest House");
		Card weaponCard = board.getCard("Glock 19");
		Card personCard1 = board.getCard("Diego");
		Card roomCard1 = board.getCard("Spa");
		Card weaponCard1 = board.getCard("Broken Bottle");
		HumanPlayer player0 = new HumanPlayer("Player0", "Purple", 0, 0);
		ComputerPlayer player1 = new ComputerPlayer("Player1", "Green", 0, 0);
		ComputerPlayer player2 = new ComputerPlayer("Player2", "Red", 0, 0);
		ComputerPlayer player3 = new ComputerPlayer("Player3", "Yellow", 0, 0);
		player0.updateHand(weaponCard);
		player1.updateHand(roomCard);
		player2.updateHand(personCard);
		player2.updateHand(personCard1);
		player1.updateHand(weaponCard1);
		player2.updateHand(roomCard1);
		Set<Player> playerList = new HashSet<>();
		playerList.add(player0);
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		board.setPlayerList(playerList);
		assertEquals(null ,board.handdleSuggestion(board.getCard("John"),board.getCard("Royal Dinin Room") , board.getCard("TV Remote"), player0));//no players can disprove, ensure null returned
		assertEquals(null , board.handdleSuggestion(board.getCard("John"),board.getCard("Royal Dinin Room") , board.getCard("Glock 19"), player0));//only the suggesting player can disprove, ensure null.
		assertEquals(board.getCard("Guest House") ,board.handdleSuggestion(board.getCard("Johann"),board.getCard("Guest House") , board.getCard("Glock 19"), player0));// Do a query that player 1 and 2 can disprove, ensure player 1 disproves 
	}
	@Test
	public void TestComputerSuggestion() {
		
	}
}
