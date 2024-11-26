package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private int numColumns = 0;
	private int numRows = 0;
	private int numPlayers = 0;
	private int currentPlayerIndex;
	private int currentRoll;
	private int cellWidth;
	private int cellHeight;
	private String layoutConfigFiles = null;
	private String setConfigFiles = null;
	private boolean humanTurnDone = true;
	private Set<BoardCell> targets = new HashSet<>();
	private Set<BoardCell> visited = new HashSet<>();
	private Set<Card> cardList = new HashSet<>();
	private Map<Character, Room> roomMap = new HashMap<>();
	private List<Player> playerList = new ArrayList<>();
	private static Board theInstance = new Board();
	private BoardCell[][] grid;
	private Player currentPlayer;
	private Solution gameSolution = null;
	private Solution suggestion;
	private Card disprovenCard;
	private Player humanPlayer;
	private Player disprovePlayer;
	private boolean endGame;
	private boolean canMakeAcusation;
	private boolean playerWon;
	// Constructor is private to ensure only one can be created.
	private Board() {
		super();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleBoardClick(e.getX(), e.getY());
			}
		});
	}
	public void endGame() {
		endGame = true;
	}
	public boolean getEndGame() {
		return endGame;
	}
	// Getter for the adjList
	public Set<BoardCell> getAdjList(int row, int col){
		return grid[row][col].getAdjList();
	}
	// Getter for the room.
	public Room getRoom(BoardCell cell) {
		char cellInitial = cell.getInitial();
		Room newRoom = roomMap.get(cellInitial);
		return newRoom;
	}
	// Getter for the room using row column
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
	// This method returns the only Board
	public static Board getInstance() {
		return theInstance;
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
	// Setter for player List
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
	// Getter for the playerList
	public List<Player> getPlayerList() {
		return playerList;
	}
	// Getter for the card list
	public Set<Card> getCardList(){
		return cardList;
	}
	// Setter for the card List
	public void setCardList(Set<Card> cardList) {
		this.cardList = cardList;
	}
	// Getter for the random roll.
	public int getRoll() {
		return currentRoll;
	}
	// Getter for the current player.
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	// Getter for the target list
	public Set<BoardCell> getTargets() {
		return targets;
	}
	// Returns boolean for if the human turn is done.
	public boolean humanTurnDone() {
		return humanTurnDone;
	}
	public Card getDisprovenCard() {
		return disprovenCard;
	}
	public Solution getSuggestion() {
		return suggestion;
	}
	public Player getHumanPlayer() {
		return humanPlayer;
	}
	public boolean canMakeAcusation() {
		return canMakeAcusation;
	}
	public boolean playerWon() {
		return playerWon;
	}
	public void setPlayerWon(boolean playerWon) {
		this.playerWon = playerWon;
	}
	public Player getPlayer(Card card) {
		for(Player player : playerList) {
			if(player.getName().equals(card.getCardName())) {
				return player;
			}
		}
		return null;
	}
	// Handles the move for the human player whether they made a valid move or not.
	private void handleBoardClick(int x, int y) {
		if(y/cellHeight >= numRows || x/cellWidth >= numColumns) { // Error user clicks out of bounds
			JOptionPane.showMessageDialog(this, "Invalid move!", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
			BoardCell clickedCell = grid[y/cellHeight][x/cellWidth];
			if (isValidTarget(clickedCell)) {
				canMakeAcusation = false;
				grid[getCurrentPlayer().getRow()][getCurrentPlayer().getColumn()].setOccupied(false);
				getCurrentPlayer().setColumn(clickedCell.getColumn());
				getCurrentPlayer().setRow(clickedCell.getRow());
				grid[getCurrentPlayer().getRow()][getCurrentPlayer().getColumn()].setOccupied(true);
				for (BoardCell target : targets) { // dehighlights targets once user is done moving
					target.setHighlighted(false);
				}
				repaint();
				if (grid[getCurrentPlayer().getRow()][getCurrentPlayer().getColumn()].isRoomCenter()) {
	                String room = getRoom(grid[getCurrentPlayer().getRow()][getCurrentPlayer().getColumn()]).getName();
	                SuggestionDialog suggestionDialog = new SuggestionDialog(
	                    SwingUtilities.getWindowAncestor(this), cardList, room 
	                );
	          
	                suggestionDialog.setVisible(true); // Display the dialog
	                suggestion = suggestionDialog.getSuggestion();
	                if(suggestion != null) {
	                	disprovenCard = handdleSuggestion(suggestion.getPerson(), suggestion.getRoom(), suggestion.getWeapon(), playerList.get(0));
	                	ClueGame.getCardPanel().updateSeen(disprovenCard, disprovePlayer);
	                }
	                ClueGame.getControlPanel().setGuess(Board.getInstance().getSuggestion().getRoom().getCardName() + " , " 
							+ Board.getInstance().getSuggestion().getPerson().getCardName() + " , "
							+ Board.getInstance().getSuggestion().getWeapon().getCardName());
	                if (Board.getInstance().getDisprovenCard() != null) {
	                	 ClueGame.getControlPanel().setGuessResult("Guess Disproven by: " + disprovePlayer.getName() + ", Card: " + disprovenCard.getCardName(), disprovePlayer);
					}else {
						 ClueGame.getControlPanel().setGuessResult("Guess Not Disproven");
					}
	                // move suggested player to location
	                grid[getPlayer(suggestion.getPerson()).getRow()][getPlayer(suggestion.getPerson()).getColumn()].setOccupied(false);
	    			getPlayer(suggestion.getPerson()).setColumn(currentPlayer.getColumn());
	    			getPlayer(suggestion.getPerson()).setRow(currentPlayer.getRow());
	    			getPlayer(suggestion.getPerson()).setJustMoved(true);
	    			grid[getPlayer(suggestion.getPerson()).getRow()][getPlayer(suggestion.getPerson()).getColumn()].setOccupied(true);
					repaint();
	            }
				
				currentPlayer.setJustMoved(false);
				humanTurnDone = true; //the human turn is done so set the value to true
			} else {
				JOptionPane.showMessageDialog(this, "Invalid move!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	// Check for the cell that the human clicked and whether it's in the target list of valid moves.
	private boolean isValidTarget(BoardCell clickedCell) {
		for(BoardCell cell : targets) {
			if(cell.equals(clickedCell)) {
				return true;
			}
		}
		return false;
	}

	// Calculates legal targets for a move from startCell of length path length.
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(startCell);
		if(currentPlayer != null) {
			if(currentPlayer.isJustMoved()) {
				targets.add(startCell);
			}
		}
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
	// Load setup configuration.
	public void loadSetupConfig () throws BadConfigFormatException {
		int curColumn = 1;
		roomMap = new HashMap<>();
		numPlayers = 0;
		cardList = new HashSet<>();
		playerList = new ArrayList<>();

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
							humanPlayer = playerList.get(0);
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
	// Handles a suggestion made
	public Card handdleSuggestion(Card personCard, Card roomCard, Card weaponCard, Player suggester) {
		Solution suggestion1 = new Solution(roomCard, personCard, weaponCard);
		int playerIndex = playerList.indexOf(suggester);
		for(int index = playerIndex; index < playerList.size(); index++) {
			if(playerList.get(index).equals(suggester)) {
				continue;
			}
			if(playerList.get(index).disproveSuggestion(suggestion1) != null) {
				disprovePlayer =  playerList.get(index);
				return playerList.get(index).disproveSuggestion(suggestion1);
			}
		}
		for(int index = 0; index < playerIndex; index++) {
			if(playerList.get(index).equals(suggester)) {
				continue;
			}
			if(playerList.get(index).disproveSuggestion(suggestion1) != null) {
				disprovePlayer =  playerList.get(index);
				return playerList.get(index).disproveSuggestion(suggestion1);
			}
		}
		
		return null; 
	}
	// Painting method handling the painting of the cells, players, rooms.
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		cellWidth = getWidth() / numColumns;
		cellHeight = getHeight() / numRows;
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
	// Random roll for the moves of players.
	public void roll() {
		Random rand = new Random();
		currentRoll = rand.nextInt(7);
		while(currentRoll == 0) {
			currentRoll = rand.nextInt(7);
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
	// Method for advancing which player is playing.
	public void nextPlayer() {
		List<Player> playerArrayList = new ArrayList<>(playerList);
		currentPlayer = playerArrayList.get(currentPlayerIndex);
		suggestion = null;
		disprovenCard = null;
		if (currentPlayer.getClass() == new HumanPlayer(null, null, 0 , 0).getClass()) {
			humanTurnDone = false;
			canMakeAcusation = true;
			targets = new HashSet<>();
			roll();
			calcTargets(grid[currentPlayer.getRow()][currentPlayer.getColumn()], currentRoll);
			// Highlight possible moves
			highlightTargets(targets);
		} else {
			ComputerPlayer computerPlayer = (ComputerPlayer) playerArrayList.get(currentPlayerIndex);
			// Handle computer player turn
			makeComputerMove(computerPlayer);
		}
		// Advance to next player
		currentPlayerIndex = (currentPlayerIndex + 1) % playerArrayList.size();
	}
	// Method for moving the computer player.
	private void makeComputerMove(ComputerPlayer player) {
		// Computer selects target and moves
		if(player.getAccusation() != null) { // If computer made an accusation assume he won and end game
			playerWon = false;
			endGame=true;
		}
		targets = new HashSet<>();
		roll();
		BoardCell target = player.selectTarget(this, currentRoll);
		grid[player.getRow()][player.getColumn()].setOccupied(false);
		player.setColumn(target.getColumn());
		player.setRow(target.getRow());
		grid[player.getRow()][player.getColumn()].setOccupied(true);
		player.setCurrentRoom(this);
		repaint();
		if(player.getCurrentRoom().getLabelCell() != null) {
			suggestion = player.createSuggestion(this);
			grid[getPlayer(suggestion.getPerson()).getRow()][getPlayer(suggestion.getPerson()).getColumn()].setOccupied(false);
			getPlayer(suggestion.getPerson()).setColumn(currentPlayer.getColumn());
			getPlayer(suggestion.getPerson()).setRow(currentPlayer.getRow());
			getPlayer(suggestion.getPerson()).setJustMoved(true);
			grid[getPlayer(suggestion.getPerson()).getRow()][getPlayer(suggestion.getPerson()).getColumn()].setOccupied(true);
			repaint();
			disprovenCard = handdleSuggestion(suggestion.getPerson(), suggestion.getRoom(), suggestion.getWeapon(), player);
			if(disprovenCard == null && !(player.getHand().contains(suggestion.getRoom()) )) {
				player.setAccusation(suggestion);
			}
			player.updateSeen(disprovenCard);
		}
		currentPlayer.setJustMoved(false);
	}
	// Highlights the cells for the valid targets which the player can move to.
	private void highlightTargets(Set<BoardCell> targets) {
		for (BoardCell target : targets) { // goes through targets and highlights them for player to select
			target.setHighlighted(true);
		}
		repaint(); // Trigger a redraw to show highlights
	}
}