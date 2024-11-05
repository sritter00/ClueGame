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
		assertEquals(board.getCard("Johann") ,board.handdleSuggestion(board.getCard("Johann"),board.getCard("Guest House") , board.getCard("Glock 19"), player0));// Do a query that player 1 and 2 can disprove, ensure player 1 disproves 
	}
	@Test
	public void TestComputerSuggestion() {
		//Set up for all tests
		Card personCard = board.getCard("Johann");
		Card roomCard = board.getCard("Guest House");
		Card weaponCard = board.getCard("Glock 19");
		Card personCard1 = board.getCard("Diego");
		Card roomCard1 = board.getCard("Spa");
		Card weaponCard1 = board.getCard("Broken Bottle");
		Card roomCard2 = board.getCard("Patio");
		Card roomCard3 = board.getCard("Spa");
		Card roomCard4 = board.getCard("Royal Hall");
		Card weaponCard2 = board.getCard("TV Remote");
		Card personCard2 = board.getCard("Sophia");
		
		ComputerPlayer player1 = new ComputerPlayer("Player1", "Green", 13, 21); // room should be in patio
		ComputerPlayer player2 = new ComputerPlayer("Player2", "Red", 26, 22);//room should be Spa
		Set<Card> cardList = new HashSet<>();
		player1.setCurrentRoom(board);
		
		assertEquals(board.getRoom('P') , player1.getCurrentRoom());//Room matches current location
		
		
		//Set up for next test
		player2.updateSeen(personCard);
		player2.updateSeen(roomCard);
		player2.updateSeen(weaponCard);
		cardList.add(personCard);
		cardList.add(roomCard);
		cardList.add(weaponCard);
		cardList.add(personCard1);
		cardList.add(roomCard1);
		cardList.add(weaponCard1);
		cardList.add(roomCard2);
		cardList.add(roomCard4);
		cardList.add(roomCard3);
		
		board.setCardList(cardList);
		player2.setCurrentRoom(board);
		Solution sugTest = new Solution(roomCard3, personCard1, weaponCard1);
		Solution sugTest1 = new Solution(roomCard3 , personCard2, weaponCard2);
		
		assertEquals(sugTest.getPerson(), player2.createSuggestion(board).getPerson());//If only one weapon not seen, it's selected
		assertEquals(sugTest.getWeapon(),player2.createSuggestion(board).getWeapon());//If only one person not seen, it's selected 
		assertEquals(sugTest.getRoom(),player2.createSuggestion(board).getRoom());//Room should be the room player is in
		
		//Set up for last test
		cardList.add(weaponCard2);
		cardList.add(personCard2);
		board.setCardList(cardList);
		
		assertTrue(sugTest.getPerson() == player2.createSuggestion(board).getPerson() || sugTest1.getPerson() == player2.createSuggestion(board).getPerson());//If multiple weapons not seen, one of them is randomly selected
		assertTrue(sugTest.getWeapon().equals(player2.createSuggestion(board).getWeapon()) || sugTest1.getPerson().equals( player2.createSuggestion(board).getPerson()));//If multiple persons not seen, one of them is randomly selected
		assertTrue(sugTest.getRoom().equals(player2.createSuggestion(board).getRoom()) && sugTest1.getRoom().equals( player2.createSuggestion(board).getRoom()));//Room should be the room player is in
	}
	
	@Test
	public void TestComputerPlayerSelect() {
		
		
		
	}
}
