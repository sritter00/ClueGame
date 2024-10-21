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
		Set<BoardCell> testList = board.getAdjList(3, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(8, 5)));
		assertTrue(testList.contains(board.getCell(24, 22)));
		// now test the patio 
		testList = board.getAdjList(13, 21);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(10, 20)));
		
		// one more room, the royal kitchen
		testList = board.getAdjList(2, 22);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(23, 1)));
		assertTrue(testList.contains(board.getCell(7, 19)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		
		
		Set<BoardCell> testList = board.getAdjList(20, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(20, 14)));
		assertTrue(testList.contains(board.getCell(19, 15)));
		assertTrue(testList.contains(board.getCell(20, 16)));
		assertTrue(testList.contains(board.getCell(24, 15)));
		
		testList = board.getAdjList(22, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(21, 20)));
		assertTrue(testList.contains(board.getCell(22, 19)));
		assertTrue(testList.contains(board.getCell(24, 22)));
		
		testList = board.getAdjList(20, 4);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(21, 4)));
		assertTrue(testList.contains(board.getCell(23, 1)));
		assertTrue(testList.contains(board.getCell(19, 4)));
		assertTrue(testList.contains(board.getCell(20, 5)));
		
		
		
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(28, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(27, 12)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(20, 13);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 12)));
		assertTrue(testList.contains(board.getCell(19, 13)));
		assertTrue(testList.contains(board.getCell(20, 14)));

		// Test adjacent to walkways
		testList = board.getAdjList(13, 7);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(14, 7)));
		assertTrue(testList.contains(board.getCell(12, 7)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInRoyalDiningRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(3, 12), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(10, 13)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(3, 12), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(11, 14)));
		assertTrue(targets.contains(board.getCell(11, 12)));	
		assertTrue(targets.contains(board.getCell(8, 8)));
		assertTrue(targets.contains(board.getCell(7, 7)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(3, 12), 4);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(7, 8)));
		assertTrue(targets.contains(board.getCell(5, 8)));	
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(10, 14)));	
	}
	
	@Test
	public void testTargetsInSpa() {
		// test a roll of 1
		board.calcTargets(board.getCell(24, 22), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(22, 20)));
		assertTrue(targets.contains(board.getCell(3, 2)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(24, 22), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(20, 20)));
		assertTrue(targets.contains(board.getCell(21, 19)));	
		assertTrue(targets.contains(board.getCell(23, 19)));
		assertTrue(targets.contains(board.getCell(3, 2)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(24, 22), 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(19, 20)));
		assertTrue(targets.contains(board.getCell(22, 19)));	
		assertTrue(targets.contains(board.getCell(23, 18)));
		assertTrue(targets.contains(board.getCell(3, 2)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(10, 20), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 19)));
		assertTrue(targets.contains(board.getCell(10, 21)));	
		assertTrue(targets.contains(board.getCell(13, 21)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(10, 20), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(9, 22)));
		assertTrue(targets.contains(board.getCell(8, 19)));	
		assertTrue(targets.contains(board.getCell(8, 21)));
		assertTrue(targets.contains(board.getCell(13, 21)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(10, 20), 4);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(10, 16)));
		assertTrue(targets.contains(board.getCell(9, 17)));
		assertTrue(targets.contains(board.getCell(9, 23)));	
		assertTrue(targets.contains(board.getCell(8, 18)));
		assertTrue(targets.contains(board.getCell(13, 21)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(19, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(18, 3)));
		assertTrue(targets.contains(board.getCell(19, 4)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(19, 3), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(19, 6)));
		assertTrue(targets.contains(board.getCell(13, 2)));
		assertTrue(targets.contains(board.getCell(23, 1)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(19, 3), 4);
		targets= board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(19, 7)));
		assertTrue(targets.contains(board.getCell(18, 6)));
		assertTrue(targets.contains(board.getCell(21, 5)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(19, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(20, 17)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(19, 17), 3);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(19, 20)));
		assertTrue(targets.contains(board.getCell(16, 17)));
		assertTrue(targets.contains(board.getCell(19, 14)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(19, 17), 4);
		targets= board.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(board.getCell(19, 13)));
		assertTrue(targets.contains(board.getCell(18, 16)));
		assertTrue(targets.contains(board.getCell(20, 14)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 1 down
		board.getCell(17, 16).setOccupied(true);
		board.getCell(17, 17).setOccupied(true);
		board.calcTargets(board.getCell(19, 17), 4);
		board.getCell(17, 16).setOccupied(false);
		board.getCell(17, 17).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(24, 15)));
		assertTrue(targets.contains(board.getCell(22, 18)));	
		assertFalse(targets.contains( board.getCell(16, 16)));
		assertFalse(targets.contains(board.getCell(13, 21)));
		
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(13, 21).setOccupied(true);
		board.calcTargets(board.getCell(10, 20), 1);
		board.getCell(13, 21).setOccupied(false);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(9, 20)));	
		assertTrue(targets.contains(board.getCell(10, 19)));	
		assertTrue(targets.contains(board.getCell(13, 21)));	

		// check leaving a room with a blocked doorway
		board.getCell(7, 19).setOccupied(true);
		board.calcTargets(board.getCell(2, 22), 3);
		board.getCell(7, 19).setOccupied(false);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(23, 1)));


	}
}
