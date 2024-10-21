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
		
		
		Set<BoardCell> testList = board.getAdjList(15, 20);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(14, 20)));
		assertTrue(testList.contains(board.getCell(15, 19)));
		assertTrue(testList.contains(board.getCell(16, 20)));
		assertTrue(testList.contains(board.getCell(15, 24)));
		
		testList = board.getAdjList(20, 22);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 21)));
		assertTrue(testList.contains(board.getCell(19, 22)));
		assertTrue(testList.contains(board.getCell(22, 24)));
		
		testList = board.getAdjList(4, 20);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(4, 21)));
		assertTrue(testList.contains(board.getCell(1, 23)));
		assertTrue(testList.contains(board.getCell(4, 19)));
		assertTrue(testList.contains(board.getCell(5, 20)));
		
		
		
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
		assertTrue(testList.contains(board.getCell(13, 19)));
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
		assertTrue(targets.contains(board.getCell(13, 10)));
		assertTrue(targets.contains(board.getCell(9, 7)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 3), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(14, 11)));
		assertTrue(targets.contains(board.getCell(12, 11)));	
		assertTrue(targets.contains(board.getCell(8, 8)));
		assertTrue(targets.contains(board.getCell(7, 7)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(12, 3), 4);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(8, 5)));	
		assertTrue(targets.contains(board.getCell(7, 8)));
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
		assertTrue(targets.contains(board.getCell(20, 20)));
		assertTrue(targets.contains(board.getCell(19, 21)));	
		assertTrue(targets.contains(board.getCell(19, 23)));
		assertTrue(targets.contains(board.getCell(2, 3)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(22, 24), 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(20, 19)));
		assertTrue(targets.contains(board.getCell(19, 22)));	
		assertTrue(targets.contains(board.getCell(18, 23)));
		assertTrue(targets.contains(board.getCell(2, 3)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(20, 10), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(19, 10)));
		assertTrue(targets.contains(board.getCell(21, 10)));	
		assertTrue(targets.contains(board.getCell(21, 13)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(20, 10), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(17, 10)));
		assertTrue(targets.contains(board.getCell(22, 9)));
		assertTrue(targets.contains(board.getCell(19, 8)));	
		assertTrue(targets.contains(board.getCell(21, 8)));
		assertTrue(targets.contains(board.getCell(21, 13)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(20, 10), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(16, 10)));
		assertTrue(targets.contains(board.getCell(17, 9)));
		assertTrue(targets.contains(board.getCell(23, 9)));	
		assertTrue(targets.contains(board.getCell(18, 8)));
		assertTrue(targets.contains(board.getCell(21, 13)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(3, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(3, 18)));
		assertTrue(targets.contains(board.getCell(4, 19)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(3, 19), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(6, 19)));
		assertTrue(targets.contains(board.getCell(2, 13)));
		assertTrue(targets.contains(board.getCell(1, 23)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(3, 19), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(6, 18)));
		assertTrue(targets.contains(board.getCell(5, 21)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(17, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(18, 19)));
		assertTrue(targets.contains(board.getCell(17, 20)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(17, 19), 3);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(20, 19)));
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(14, 19)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(17, 19), 4);
		targets= board.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(board.getCell(13, 19)));
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(14, 20)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 1 down
		board.getCell(16, 17).setOccupied(true);
		board.getCell(17, 17).setOccupied(true);
		board.calcTargets(board.getCell(17, 19), 4);
		board.getCell(16, 17).setOccupied(false);
		board.getCell(17, 17).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(15, 24)));
		assertTrue(targets.contains(board.getCell(18, 22)));	
		assertFalse(targets.contains( board.getCell(16, 16))) ;
		assertFalse(targets.contains(board.getCell(21, 13)));
		
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(21, 13).setOccupied(true);
		board.calcTargets(board.getCell(20, 10), 1);
		board.getCell(21, 13).setOccupied(false);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(20, 9)));	
		assertTrue(targets.contains(board.getCell(19, 10)));	
		assertTrue(targets.contains(board.getCell(21, 13)));	

		// check leaving a room with a blocked doorway
		board.getCell(19, 7).setOccupied(true);
		board.calcTargets(board.getCell(22, 2), 3);
		board.getCell(19, 7).setOccupied(false);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(1, 23)));


	}
}
