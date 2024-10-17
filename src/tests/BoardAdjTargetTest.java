package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(2, 3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(5, 8)));
		assertTrue(testList.contains(board.getCell(22, 24)));
		// now test the patio 
		testList = board.getAdjList(21, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(20, 10)));
		
		// one more room, the royal kitchen
		testList = board.getAdjList(22, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1, 23)));
		assertTrue(testList.contains(board.getCell(19, 7)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(4, 22);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(1, 23)));
		assertTrue(testList.contains(board.getCell(4, 19)));
		assertTrue(testList.contains(board.getCell(5, 20)));
		assertTrue(testList.contains(board.getCell(4, 21)));
		
		testList = board.getAdjList(20, 22);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 21)));
		assertTrue(testList.contains(board.getCell(19, 22)));
		assertTrue(testList.contains(board.getCell(22, 24)));
		
		testList = board.getAdjList(15, 20);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(14, 20)));
		assertTrue(testList.contains(board.getCell(19, 15)));
		assertTrue(testList.contains(board.getCell(16, 20)));
		assertTrue(testList.contains(board.getCell(15, 24)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(12, 28);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(12, 27)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(13, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(12, 20)));
		assertTrue(testList.contains(board.getCell(13, 21)));
		assertTrue(testList.contains(board.getCell(14, 20)));

		// Test adjacent to walkways
		testList = board.getAdjList(7, 13);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(7, 14)));
		assertTrue(testList.contains(board.getCell(7, 12)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInRoyalDiningRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(12, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(13, 12)));
		assertTrue(targets.contains(board.getCell(9, 7)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 3), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(14, 11)));
		assertTrue(targets.contains(board.getCell(11, 11)));	
		assertTrue(targets.contains(board.getCell(8, 8)));
		assertTrue(targets.contains(board.getCell(7, 7)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(12, 3), 4);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(8, 5)));	
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(14, 10)));	
	}
	
	@Test
	public void testTargetsInSpa() {
		// test a roll of 1
		board.calcTargets(board.getCell(22, 24), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(20, 22)));
		assertTrue(targets.contains(board.getCell(2, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(22, 24), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(20, 19)));
		assertTrue(targets.contains(board.getCell(19, 21)));	
		assertTrue(targets.contains(board.getCell(19, 23)));
		assertTrue(targets.contains(board.getCell(2, 3)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(22, 24), 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(20, 20)));
		assertTrue(targets.contains(board.getCell(19, 22)));	
		assertTrue(targets.contains(board.getCell(18, 23)));
		assertTrue(targets.contains(board.getCell(2, 3)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(8, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 18)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(8, 17), 3);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(9, 15)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(8, 17), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 16)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 1)));
		assertTrue(targets.contains(board.getCell(11, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 2), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 2), 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 6)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(12, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(15, 7).setOccupied(true);
		board.calcTargets(board.getCell(13, 7), 4);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		assertFalse( targets.contains( board.getCell(15, 7))) ;
		assertFalse( targets.contains( board.getCell(17, 7))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(12, 20).setOccupied(true);
		board.getCell(8, 18).setOccupied(true);
		board.calcTargets(board.getCell(8, 17), 1);
		board.getCell(12, 20).setOccupied(false);
		board.getCell(8, 18).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 16)));	
		assertTrue(targets.contains(board.getCell(12, 20)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(12, 15).setOccupied(true);
		board.calcTargets(board.getCell(12, 20), 3);
		board.getCell(12, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(8, 19)));	
		assertTrue(targets.contains(board.getCell(8, 15)));

	}
}