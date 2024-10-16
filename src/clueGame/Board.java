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
			int curColumn = 1;
			File file = new File(setConfigFiles);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] rows = line.split(", "); // Split file with , and put it into a string
				char firstChar = line.charAt(0);
				char secondChar = line.charAt(1);
				if(!(firstChar == '/' && secondChar == '/')) { //Check if commented out
					if(rows.length != 3) {
						String message = "---"+line + "--- is not formated properly in: " + file +" at line (" + curColumn +")";
						scanner.close();
						throw new BadConfigFormatException(message);
					}
					if(!Character.isLetter(rows[2].charAt(0))) {
						String message = rows[2].charAt(0) + " is not a letter in stetup file: " + file;
						scanner.close();
						throw new BadConfigFormatException(message);
					}
					Character ch = Character.valueOf(rows[2].charAt(0));
					Room newRoom = new Room();
					newRoom.setName(rows[1]);
					roomMap.put(ch, newRoom);
				}
				curColumn++;
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
				String[] rows = line.split(","); // Split file with , and put it into a string
				// Get column count from the first row
				
				if (numColumns == 0) {
					numRows = rows.length; 
				}
				if (numRows != rows.length) { // check if there is a different number of rows if there is throw error
					String message = "Different number of rows/columns in file: " + file;
					scanner.close();
					throw new BadConfigFormatException(message);
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
				for(String currentIndex : rows) { // goes threw rows in the csv file and creates cells named appropriately
					if(currentIndex.length() > 2) { //throw error if formated wrong
						String message = "length of cell is more than two: " + currentIndex + " in file: " + file + " at (" + curRow +"," + curColumn+ ")"; 
						scanner.close();
						throw new BadConfigFormatException(message);
					}
					if (!roomMap.containsKey(currentIndex.charAt(0))) { // throw error if the room name is not in the setup file
						String message = currentIndex + " in " + file + " at (" + curRow +"," + curColumn + ")," + " is not a room in the setup file: " + setConfigFiles  ;
						scanner.close();
						throw new BadConfigFormatException(message);
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
							String message = "bad format of: " + currentIndex + " in file: " + file + " at (" + curRow +"," + curColumn+ ")";
							scanner.close();
							throw new BadConfigFormatException(message);
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
			new BadConfigFormatException();  // throw default error if no error was found and something went wrong
		}
		
		try {
			this.loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			new BadConfigFormatException(); // throw default error if no error was found and something went wrong
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