package clueGame;

/**
 * TestBoard: Initializes the grid, calculating movement targets based on a start cell and path length.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.io.*;
import java.util.*;
import java.lang.Character;
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
	private Map<Character, Room> roomMap = new HashMap<>();

	private static Board theInstance = new Board();

	// Getter for the room.
	public Room getRoom(BoardCell cell) {
		System.out.println(cell.getInitial());
		char ch = cell.getInitial();
		Room newRoom = roomMap.get(ch);
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
	// Setter for configuration files.
	public void setConfigFiles (String CsvFile, String TxtFile) {
		setConfigFiles = TxtFile;
		layoutConfigFiles = CsvFile;
	}
	// Load setup configuration.
	public void loadSetupConfig () throws BadConfigFormatException {
		try {
			File file = new File(setConfigFiles);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] rows = line.split(", ");
				char firstChar = line.charAt(0);
				char secondChar = line.charAt(1);
				if(!(firstChar == '/' && secondChar == '/')) { //Check if commented out
					if(rows.length != 3) {
						throw new BadConfigFormatException();
					}
					Character ch = Character.valueOf(rows[2].charAt(0));
					Room newRoom = new Room();
					newRoom.setName(rows[1]);
					roomMap.put(ch, newRoom);
				}

			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(setConfigFiles + " not found, change file name to correct file name or create the correct file");
		}

	}
	// Loader for the layout configuration.
	public void loadLayoutConfig() throws BadConfigFormatException{
		numRows = 0;
		numColumns = 0;
		//Get rows and columns
		try {
			File file = new File(layoutConfigFiles);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] rows = line.split(",");
				// Get column count from the first row
				if (numColumns == 0) {
					numRows = rows.length; 
				}

				numColumns++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(layoutConfigFiles + " not found, change file name to correct file name or create the correct file");
		}
		//set up the board
		grid = new BoardCell[numRows][numColumns];
		int curColumn = 0;
		try {
			File file = new File(layoutConfigFiles);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] rows = line.split(",");
				int curRow = 0;
				for(String currentIndex : rows) {
					if(currentIndex.length() > 2) { //throw error if formated wrong
						throw new BadConfigFormatException();
					}
					grid[curRow][curColumn] = new BoardCell(curRow, curColumn, currentIndex.charAt(0));
					
					if(currentIndex.length() == 2) { // check if there is a special characteristics of the specific cell
						if(currentIndex.charAt(1) == '<') {
							grid[curRow][curColumn].setDoorDirection(DoorDirection.LEFT);
						}else if(currentIndex.charAt(1) == '>') { 
							grid[curRow][curColumn].setDoorDirection(DoorDirection.RIGHT);
						}else if(currentIndex.charAt(1) == 'v') {
							grid[curRow][curColumn].setDoorDirection(DoorDirection.DOWN);
						}else if(currentIndex.charAt(1) == '^') {
							grid[curRow][curColumn].setDoorDirection(DoorDirection.UP);
						}else if(Character.isLetter(currentIndex.charAt(1))) {
							grid[curRow][curColumn].setSecretPassage(currentIndex.charAt(1));
						}else if(currentIndex.charAt(1) == '*') {
							grid[curRow][curColumn].setRoomCenter(true);
							roomMap.get(currentIndex.charAt(0)).setCenterCell(grid[curRow][curColumn]);
						}else if(currentIndex.charAt(1) == '#') {
							grid[curRow][curColumn].setRoomLabel(true);
							roomMap.get(currentIndex.charAt(0)).setLabelCell(grid[curRow][curColumn]);
						}else { //throw exception if we don't know what the second char is in the cell
							throw new BadConfigFormatException();
						}
					}
					curRow++; // new Row after we get entry of room char names
				}
				curColumn++; // new Column at end of next line
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(layoutConfigFiles + " not found, change file name to correct file name or create the correct file");
		}
	}
	// constructor is private to ensure only one can be created
	private Board() {
		super();


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
		
		try {
			this.loadSetupConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// Getter for the cell.
	public BoardCell getCell(int row, int column) {
		return grid[row][column]; 
	}
	// Getter for the room.
	public Room getRoom(char ch) {
		Room newRoom = roomMap.get(ch);
		return newRoom;
	}
}