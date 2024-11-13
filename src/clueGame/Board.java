package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * TestBoard: Initializes the grid, calculating movement targets based on a start cell and path length.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.io.*;
import java.util.*;
import java.lang.Character;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel {
	private BoardCell[][] grid;
	private Set<BoardCell> targets = new HashSet<>();
	private Set<BoardCell> visited = new HashSet<>();
	private int numColumns = 0;
	private int numRows = 0;
	private String layoutConfigFiles = null;
	private String setConfigFiles = null;
	private Map<Character, Room> roomMap = new HashMap<>();
	private Set<Card> cardList = new HashSet<>();
	private Set<Player> playerList = new HashSet<>();
	private int numPlayers = 0;
	private static Board theInstance = new Board();
	private Solution gameSolution = null;
	private int dx;
	private int dy;
	// Getter for the adjList
	public Set<BoardCell> getAdjList(int row, int col){
		return grid[row][col].getAdjList();
	}
	// Calculates legal targets for a move from startCell of length path length.
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(startCell);
		for(BoardCell curAdjCell : startCell.getAdjList()) {
			if(!visited.contains(curAdjCell)) {
				DFS(curAdjCell, pathLength-1);
			}
		}
	}

	// Recursive function that removes and adds the starting cell based on the input of the path length.
	public void DFS(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		if(getRoom(startCell).getCenterCell() != null) {
			targets.add(startCell);
			visited.remove(startCell);
			return;
		}
		if(startCell.getOccupied()) {
			visited.remove(startCell);
			return;	
		}
		if(pathLength == 0) {	
			targets.add(startCell);
			visited.remove(startCell);
			return;
		}

		for(BoardCell curAdjCell : startCell.getAdjList()) {
			if(!visited.contains(curAdjCell)) {
				DFS(curAdjCell, pathLength-1);
			}
		}
		visited.remove(startCell); // removes startcell from visited when going back from the recursive function
	}

	//Getter for the target list
	public Set<BoardCell> getTargets() {
		return targets;
	}
	// Getter for the room.
	public Room getRoom(BoardCell cell) {
		char cellInitial = cell.getInitial();
		Room newRoom = roomMap.get(cellInitial);
		return newRoom;
	}
	//getter for the room using row column
	public Room getRoom(int row, int column) {
		BoardCell cell = grid[row][column];
		char cellInitial = cell.getInitial();
		Room newRoom = roomMap.get(cellInitial);
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
	public void setConfigFiles (String csvFile, String txtFile) {
		setConfigFiles = txtFile;
		layoutConfigFiles = csvFile;
	}
	// Load setup configuration.
	public void loadSetupConfig () throws BadConfigFormatException {
		int curColumn = 1;
		roomMap = new HashMap<>();
		numPlayers = 0;
		cardList = new HashSet<>();
		playerList = new HashSet<>();

		try {
			File file = new File(setConfigFiles);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.equals("")) {
					continue;
				}
				String[] rows = line.split(", "); // Split file with , and put it into a string
				char firstChar = line.charAt(0);
				char secondChar = line.charAt(1);
				if(!(firstChar == '/' && secondChar == '/')) { //Check if commented out
					if(rows[0].equals("Room") || rows[0].equals("Space")) {
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

						Character cellInitial = Character.valueOf(rows[2].charAt(0));
						Room newRoom = new Room();
						newRoom.setName(rows[1]);
						roomMap.put(cellInitial, newRoom);
						if(rows[0].equals("Room")) {
							cardList.add(new Card(rows[1], CardType.ROOM));
						}
					}else if(rows[0].equals("Weapon")) {
						cardList.add(new Card(rows[1], CardType.WEAPON));
					}else if(rows[0].equals("Player")) {
						cardList.add(new Card(rows[1], CardType.PERSON));
						if(numPlayers == 0) {
							playerList.add(new HumanPlayer(rows[1], rows[2], Integer.parseInt(rows[3]), Integer.parseInt(rows[4])));
							numPlayers++;
						}else {
							playerList.add(new ComputerPlayer(rows[1], rows[2], Integer.parseInt(rows[3]), Integer.parseInt(rows[4])));
							numPlayers++;
						}

					}else {
						String message = "---"+line + "--- is not formated properly in: " + file +" at line (" + curColumn +")";
						scanner.close();
						throw new BadConfigFormatException(message);
					}

				}
				curColumn++;
			}
			curColumn = 0; 
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(setConfigFiles + " not found, change file name to correct file name or create the correct file");
		}

	}
	// Loader for the layout configuration.
	public void loadLayoutConfig() throws BadConfigFormatException{
		numRows = 0;
		numColumns = 0;
		// Get rows and columns
		try {
			File file = new File(layoutConfigFiles);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] rows = line.split(","); // Split file with , and put it into a string
				// Get column count from the first row

				if (numColumns == 0) {
					numColumns = rows.length; 
				}
				if (numColumns != rows.length) { // check if there is a different number of rows if there is throw error
					String message = "Different number of rows/columns in file: " + file;
					scanner.close();
					throw new BadConfigFormatException(message);
				}
				numRows++; // Increment number of rows based of of line number
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(layoutConfigFiles + " not found, change file name to correct file name or create the correct file");
		}
		// Set up the board
		grid = new BoardCell[numRows][numColumns];
		int curRow = 0;
		try {
			File file = new File(layoutConfigFiles);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] rows = line.split(",");
				int curColumn = 0;
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
							roomMap.get(currentIndex.charAt(0)).setSecretPassage(currentIndex.charAt(1));
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
					curColumn++; // new Column after we get entry of room char names
				}
				curColumn = 0;
				curRow++; // new Row at end of next line
			}
			curRow = 0;
			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println(layoutConfigFiles + " not found, change file name to correct file name or create the correct file");
		}


	}
	// Constructor is private to ensure only one can be created.
	private Board() {
		super();
	}
	// This method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	/*
	 * Initialize the board (since we are using singleton pattern).
	 */
	public void initialize(){
		targets = new HashSet<>();
		visited = new HashSet<>();
		numColumns = 0;
		numRows = 0;
		roomMap = new HashMap<>();
		try {
			this.loadSetupConfig();
		}catch (BadConfigFormatException e) {
			new BadConfigFormatException(); // throw default error if no error was found and something went wrong
		}
		try {
			this.loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			new BadConfigFormatException(); // throw default error if no error was found and something went wrong
		}

		for (int row = 0; row < numRows; row++) { //goes through each row and column and adds adj depending on if statements
			for (int col = 0; col < numColumns; col++) {
				if(grid[row][col].isDoorway()) { // if its a doorway get the center of the room and add that to adj List
					if(grid[row][col].getDoorDirection() == DoorDirection.UP){
						char roomInitial = grid[row-1][col].getInitial();
						grid[row][col].addAdjacency(roomMap.get(roomInitial).getCenterCell());
						roomMap.get(roomInitial).addDoorWay(grid[row][col]);
					}
					if(grid[row][col].getDoorDirection() == DoorDirection.DOWN){
						char roomInitial = grid[row+1][col].getInitial();
						grid[row][col].addAdjacency(roomMap.get(roomInitial).getCenterCell());
						roomMap.get(roomInitial).addDoorWay(grid[row][col]);
					}
					if(grid[row][col].getDoorDirection() == DoorDirection.RIGHT){
						char roomInitial = grid[row][col+1].getInitial();
						grid[row][col].addAdjacency(roomMap.get(roomInitial).getCenterCell());
						roomMap.get(roomInitial).addDoorWay(grid[row][col]);
					}
					if(grid[row][col].getDoorDirection() == DoorDirection.LEFT){
						char roomInitial = grid[row][col-1].getInitial();
						grid[row][col].addAdjacency(roomMap.get(roomInitial).getCenterCell());
						roomMap.get(roomInitial).addDoorWay(grid[row][col]);
					}
				}
				if(grid[row][col].getInitial() == 'W') { // if its a walkway add all walkway adjacencies
					if(grid[0].length > col+1 && grid[row][col+1].getInitial() == 'W') {
						grid[row][col].addAdjacency(grid[row][col+1] );
					}
					if(row - 1 >=  0 && grid[row-1][col].getInitial() == 'W') {
						grid[row][col].addAdjacency(grid[row-1][col]);
					}
					if (col - 1 >= 0 && grid[row][col-1].getInitial() == 'W') {
						grid[row][col].addAdjacency(grid[row][col-1]);
					}
					if(grid.length > row + 1 && grid[row+1][col].getInitial() == 'W') {
						grid[row][col].addAdjacency(grid[row+1][col]);
					}
				}
			}
		}
		for(Map.Entry<Character, Room> entry : roomMap.entrySet()) {//For each room if it has center get all doorways and add that to the adjList
			for(BoardCell cell : entry.getValue().getDoorWays()) {
				if(entry.getValue().getCenterCell() != null) {
					entry.getValue().getCenterCell().addAdjacency(cell);
				}
				if(entry.getValue().getSecretPassage() != null) {
					entry.getValue().getCenterCell().addAdjacency(roomMap.get(entry.getValue().getSecretPassage()).getCenterCell());
				}
			}
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
	// Getter for the card list
	public Card getCard(String cardName){
		for(Card card : cardList) {
			if(cardName.equals(card.getCardName())) {
				Card newCard = card;
				return newCard; //Card Found return that card
			}
		}
		return new Card(null , null); //Card Not found return a null card
	}

	// Getter for the solution to the game
	public Solution getSolution() {
		return gameSolution;
	}
	// Setter for the solution to the game should only be used for tests
	public void setSolution(Solution gameSolution) {
		this.gameSolution = gameSolution;
	}
	// Generates a solution based off of the cards we have
	public void generateSolution() {
		// Generating a random solution
		List<Card> cardArrayList = new ArrayList<>(cardList);
		boolean gotPlayerCard = false;
		boolean gotRoomCard = false;
		boolean gotWeaponCard = false;
		Card CurrentCard = null;
		Card PlayerCard = null;
		Card RoomCard = null;
		Card WeaponCard = null;
		Random rand = new Random();
		while(!(gotPlayerCard && gotRoomCard && gotWeaponCard)) {
			int randInt = rand.nextInt(cardList.size());
			CurrentCard = cardArrayList.get(randInt);
			if(CurrentCard.getType() == CardType.PERSON && gotPlayerCard == false) {
				PlayerCard = CurrentCard;
				gotPlayerCard = true;
			}
			if(CurrentCard.getType() == CardType.ROOM && gotRoomCard == false) {
				RoomCard = CurrentCard;
				gotRoomCard = true;
			}
			if(CurrentCard.getType() == CardType.WEAPON && gotWeaponCard == false) {
				WeaponCard = CurrentCard;
				gotWeaponCard = true;
			}
		}

		gameSolution = new Solution(RoomCard, PlayerCard, WeaponCard);
	}
	// Setter for player List
	public void setPlayerList(Set<Player> playerList) {
		this.playerList = playerList;
	}
	// Getter for the playerList
	public Set<Player> getPlayerList() {
		return playerList;
	}
	// Checks if an accusation is correct
	public boolean checkAccusation(Card personCard, Card roomCard, Card weaponCard) {
		Solution solution = getSolution();
		if(solution.getPerson().equals(personCard) && solution.getRoom().equals(roomCard) && solution.getWeapon().equals(weaponCard)) {
			return true;
		}else {
			return false;
		}
	}
	// Deals cards to the players
	public void deal() {
		Random rand = new Random();
		for(Card card : cardList) {
			card.setIsDealt(false);
		}
		List<Card> cardArrayList = new ArrayList<>(cardList);
		double cardAmount = Math.floor((cardList.size()-3)/numPlayers); 
		for(Card card : cardArrayList) { // set the three solution cards as dealt
			if(gameSolution.getPerson().equals(card)) {
				card.setIsDealt(true);
				continue;
			}
			if(gameSolution.getWeapon().equals(card)) {
				card.setIsDealt(true);
				continue;
			}
			if(gameSolution.getRoom().equals(card)) {
				card.setIsDealt(true);
				continue;
			}
		}
		for(Player player : playerList) { // loops through player and gives them the cards they need
			for(int index = 0 ; index < cardAmount; index++) {// Loop through the distributed card amount and puts it into players hand
				int randInt = rand.nextInt(cardList.size());
				while(cardArrayList.get(randInt).isDealt()) {// if card is already dealt grab new card
					randInt = rand.nextInt(cardList.size());
				}
				player.updateHand(cardArrayList.get(randInt));
				cardArrayList.get(randInt).setIsDealt(true);
			}
		}
		for(Player player : playerList) {// check if there are any remaining cards that are not dealt and deal them to the player
			for(Card card : cardArrayList){
				if(card.isDealt()) {
					continue;
				}else {
					player.updateHand(card);
					card.setIsDealt(true);
					break;
				}		
			}
		}
	}
	//Getter for the card list
	public Set<Card> getCardList(){
		return cardList;
	}
	//Setter for the card List
	public void setCardList(Set<Card> cardList) {
		this.cardList = cardList;
	}
	//Handles a suggestion made
	public Card handdleSuggestion(Card personCard, Card roomCard, Card weaponCard, Player suggester) {
		List<Player> playerArrayList = new ArrayList<>(playerList);
		int playerIndex = playerArrayList.indexOf(suggester);
		for(int index = playerIndex; index < playerArrayList.size(); index++) {
			for(Card card : playerArrayList.get(index).getHand()) {
				if(playerArrayList.get(index).equals(suggester)) {
					break;
				}
				if(card.equals(weaponCard)|| card.equals(roomCard)|| card.equals(personCard)) {
					return card;
				}
			}
		}
		for(int index = 0; index < playerIndex; index++) {
			for(Card card : playerArrayList.get(index).getHand()) {
				if(playerArrayList.get(index).equals(suggester)) {
					break;
				}
				if(card.equals(weaponCard)|| card.equals(roomCard)|| card.equals(personCard)) {
					return card;
				}
			}
		}
		return null; 
	}
	// Painting method handling the painting of the cells, players, rooms.
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int cellWidth = getWidth() / numColumns;
		int cellHeight = getHeight() / numRows;

		// Draw cells
		for (int row = 0; row < numRows; row++) { 
			for (int col = 0; col < numColumns; col++) {
				grid[row][col].draw(g, cellWidth, cellHeight);
			}
		}

		// Draw room names
		drawRoomNames(g, cellWidth, cellHeight);
		

		// Draw players
		for (Player player : playerList) {
			player.draw(g, cellWidth, cellHeight);
		}
	}
	// Draw method for the names of the rooms.
	private void drawRoomNames(Graphics g, int cellWidth, int cellHeight) {
		for (int row = 0; row < numRows; row++) { 
			for (int col = 0; col < numColumns; col++) {
				if(grid[row][col].isLabel()) {	
					grid[row][col].drawLabel(g, cellWidth, cellHeight, getRoom(grid[row][col].getInitial()).getName());
				}
				
			}
		}
	}


}