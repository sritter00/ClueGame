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
		grid = new TestBoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				grid[row][col] = new TestBoardCell(row, col);
			}
		}
		targets = new HashSet<>();
		visited = new HashSet<>();
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
		if(grid.length > row + 1) {
			TestBoardCell cellRight = new TestBoardCell(row+1, column);
			cell.addAdjacency(cellRight);
		}
		if(column + 1 > 0) {
			TestBoardCell cellDown = new TestBoardCell(row, column - 1);
			cell.addAdjacency(cellDown);
		}
		if (row -1 < 0) {
			TestBoardCell cellLeft = new TestBoardCell(row - 1 , column);
			cell.addAdjacency(cellLeft);
		}
		if(grid[0].length > column+1) {
			TestBoardCell cellUp = new TestBoardCell(row, column + 1);
			cell.addAdjacency(cellUp);
		}
		return cell; 
	}
}
