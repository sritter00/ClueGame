package clueGame;

/**
 * TestBoard: Initializes the grid, calculating movement targets based on a start cell and path length.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;
import experiment.TestBoardCell;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private int numColumns = 28;
	private int numRows = 25;
	private String layoutConfigFiles;
	private String setConfigFiles;
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();

	// Getter for the room.
	public Room getRoom(BoardCell cell) {
		Room newRoom = new Room();
		return newRoom;
	}
	// Getter for number of rows.
	public int getNumRows() {
		return numRows;
	}
	// Getter for number of columns.
	public int getNumColumns() {
		return numColumns;
	}
	// Load setup configuration.
	public void loadSetupConfig () {

	}
	// Setter for configuration files.
	public void setConfigFiles (String CsvFile, String TxtFile) {

	}
	// Loader for the layout configuration.
	public void loadLayoutConfig() {

	}
	// Setter for the layout configuration file.
	public void setLayoutConfigFile(String layoutConfigFile) {
		this.layoutConfigFiles = layoutConfigFile;
	}
	// constructor is private to ensure only one can be created
	private Board() {
		super();
		grid = new BoardCell[numRows][numColumns];
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				grid[row][col] = new BoardCell(row, col, 'W');

			}
		}
		
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize()
	{
	}
	// Getter for the cell.
	public BoardCell getCell(int row, int column) {
		return grid[row][column]; 
	}
	// Getter for the room.
	public Room getRoom(char c) {
		Room newRoom = new Room();
		return newRoom;
	}
}