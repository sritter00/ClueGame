package experiment;

/**
 * TestBoard: Initializes the grid, calculating movement targets based on a start cell and path length.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.util.*;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;


	// Constructor initializing board size and adjacency list.
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				grid[row][col] = new TestBoardCell(row, col);

			}
		}
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if(grid.length > row + 1) {
					grid[row][col].addAdjacency(grid[row+1][col] );
				}
				if(col - 1 >=  0) {
					grid[row][col].addAdjacency(grid[row][col-1]);
				}
				if (row -1 >= 0) {
					grid[row][col].addAdjacency(grid[row-1][col]);
				}
				if(grid[0].length > col + 1) {
					grid[row][col].addAdjacency(grid[row][col+1]);
				}
			}
		}

		targets = new HashSet<>();
		visited = new HashSet<>();
	}

	// Calculates legal targets for a move from startCell of length path length.
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		visited.add(startCell);
		for(TestBoardCell curAdjCell : startCell.getAdjList()) {
			if(!visited.contains(curAdjCell)) {
				DFS(curAdjCell, pathLength-1);
			}
		}
	}
	
	// Recursive function that removes and adds the starting cell based on the input of the path length.
	public void DFS(TestBoardCell startCell, int pathLength) {
		visited.add(startCell);
		if(startCell.getOccupied()) {
			visited.remove(startCell);
			return;	
		}
		if(startCell.isRoom()) {
			targets.add(startCell);
			visited.remove(startCell);
			return;
		}
		if(pathLength == 0) {	
			targets.add(startCell);
			visited.remove(startCell);
			return;
		}

		for(TestBoardCell curAdjCell : startCell.getAdjList()) {
			if(!visited.contains(curAdjCell)) {
				DFS(curAdjCell, pathLength-1);
			}
		}
		visited.remove(startCell);
	}

	// Getter for target value
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	// Returns the cell from the board at row, col.
	public TestBoardCell getCell(int row, int column) {
		return grid[row][column]; 
	}
}
