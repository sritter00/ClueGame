package clueGame;

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

	public Room getRoom(BoardCell cell) {
		Room newRoom = new Room();
		return newRoom;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}

	public void loadSetupConfig () {

	}
	public void setConfigFiles (String CsvFile, String TxtFile) {

	}


	public void loadLayoutConfig() {

	}
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
	public BoardCell getCell(int row, int column) {
		return grid[row][column]; 
	}
	public Room getRoom(char c) {
		Room newRoom = new Room();
		return newRoom;
	}
}