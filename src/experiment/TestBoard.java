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
		if(grid.length > row + 1) {
			grid[row][column].addAdjacency(grid[row+1][column]);
		}
		if(column - 1 > 0) {
			grid[row][column].addAdjacency(grid[row][column-1]);
		}
		if (row -1 > 0) {
			grid[row][column].addAdjacency(grid[row-1][column]);
		}
		if(grid[0].length > column + 1) {
			grid[row][column].addAdjacency(grid[row][column+1]);
		}
		return grid[row][column]; 
	}
}
