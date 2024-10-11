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
	private int numColumns = 0;
	private int numRows = 0;
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
           super() ;
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
	public Object getRoom(char c) {
		// TODO Auto-generated method stub
		return null;
	}
}