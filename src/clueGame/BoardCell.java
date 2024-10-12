package clueGame;

/**
* BoardCell: Initializes a cell in a 4x4 grid, managing adjacencies, room status, and occupancy.
* 
* author: Shane Ritter
* author: Carter Gorling
*/

import java.util.*;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean doorway;
	private char secretPassage;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	public boolean isDoorway() {
		return doorway;
	}
	
	// Constructor
	public BoardCell(int row, int column, char initial){
		row = this.row;
		column = this.column;
		initial = this.initial;
	}
	// Setter to add a cell to the cell adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	// Getter for initial of the cell.
	public char getInitial() {
		return initial;
	}
	// Getter for the add adjacency list
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	// Getter for the door direction.
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	// Setter for door direction.
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	// Getter for the room label.
	public boolean isLabel() {
		return roomLabel;
	}
	// Setter for room label.
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	// Getter for the room center boolean.
	public boolean isRoomCenter() {
		return roomCenter;
	}
	// Setter for the center of the room.
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	// Getter for the secret passage.
	public char getSecretPassage() {
		return secretPassage;
	}
	// Setter for the secret passage.
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	// Getter for row of cell
	public int getRow() {
		return row;
	}
	// Getter for the column of the cell
	public int getColumn(){
		return column;
	}
}
