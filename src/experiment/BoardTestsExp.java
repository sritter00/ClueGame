package experiment;

/**
 * BoardTestsExp: Uses JUnit tests to verify adjacency lists and movement targets in different board scenarios.
 * 
 * author: Carter Gorling
 * author: Shane Ritter
 */

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach // Run before each test, @BeforeAll would work just as well
	public void setUp() {
		// board should create adjacency list
		board = new TestBoard();
	}
	
	/**
	 * Test adjacencies for several different locations
	 * Test centers and edges
	 */
	@Test
	public void testAdjacency() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}
	
	/**
	 * Test targets with several rolls and start locations
	 */
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	/**
	 * Test target calculation with both occupied cells and rooms.
	 * Starting from (0,3) with a roll of 3, checks valid targets based on room and occupied status.
	 */
	@Test
	public void testTargetsMixed() {
		// set up occupied cells.
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	/**
	 * Test target calculation when entering a room.
	 * Movement stops upon entering the room starting from (2,2) with a roll of 2.
	 */
	@Test
	public void testTargetsRoom() {
	    TestBoardCell roomCell = board.getCell(1, 2);
	    roomCell.setRoom(true);
	    TestBoardCell startCell = board.getCell(2, 2);
	    board.calcTargets(startCell, 2);
	    Set<TestBoardCell> targets = board.getTargets();
	    
	    // Player must stop in the room, but can move to other cells with exact movement
	    assertEquals(5, targets.size());
	    assertTrue(targets.contains(board.getCell(0, 2)));
	    assertTrue(targets.contains(roomCell)); // Entering the room should stop movement
	    assertTrue(targets.contains(board.getCell(3, 2)));
	    assertTrue(targets.contains(board.getCell(2, 0)));
	    assertTrue(targets.contains(board.getCell(2, 1)));
	}
	
	/**
	 * Test target calculation avoiding occupied cells.
	 * Ensures occupied cells are not included when starting from (2,2) with a roll of 2.
	 */
	@Test
	public void testTargetsOccupied() {
	    TestBoardCell occupiedCell = board.getCell(1, 2);
	    occupiedCell.setOccupied(true); // Occupied by another player
	    TestBoardCell startCell = board.getCell(2, 2);
	    board.calcTargets(startCell, 2);
	    Set<TestBoardCell> targets = board.getTargets();
	    
	    // Targets should avoid occupied cell
	    assertEquals(4, targets.size());
	    assertTrue(targets.contains(board.getCell(0, 2)));
	    assertTrue(targets.contains(board.getCell(3, 2)));
	    assertTrue(targets.contains(board.getCell(2, 0)));
	    assertTrue(targets.contains(board.getCell(2, 1)));
	    assertFalse(targets.contains(occupiedCell)); // Occupied cell should not be a target
	}
}
