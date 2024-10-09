package experiment;
/**
 * TestBoard: Initializes the grid, calculating movement targets based on a start cell and path length.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.util.*;

public class TestBoard {
	private Set<TestBoardCell> setAdjList = new HashSet<TestBoardCell>();
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	//constructor
	public TestBoard() {
		//empty
	}
	//calculates legal targets for a move from startCell of length pathlength.
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		//Don't really know what to put here also don't think we have to until next assignment not sure though
	}
	//getter for target value
	public Set<TestBoardCell> getTargets() {
		return setAdjList;
	}
	//returns the cell from the board at row, col.
	public TestBoardCell getCell(int row, int column) {
		TestBoardCell cell = new TestBoardCell(row, column);
		return cell; 
	}
}
