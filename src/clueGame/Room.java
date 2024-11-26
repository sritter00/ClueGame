package clueGame;

import java.util.HashSet;
import java.util.Set;

/**
 * Room: Initializes the room with the instance variables related to the room.
 * 
 * author: Shane Ritter
 * authpr: Carter Gorling
 */

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private Character secretPassage;
	private Set<BoardCell> doorWays = new HashSet<>();
	
	// Setter for the secret passage
	public void setSecretPassage(Character secretPassage) {
		this.secretPassage = secretPassage;
	}
	// Getter for the secret Passage
	public Character getSecretPassage(){
		return secretPassage;
	}
	// Constructor
	public Room() {
		name = null;
	}
	// Setter for adding a cell into the doorways list
	public void addDoorWay(BoardCell cell) {
		doorWays.add(cell);
	}
	// Getter for the doorways list
	public Set<BoardCell> getDoorWays(){
		return doorWays;
	}
	// Getter for name.
	public String getName() {
		return name;
	}
	// Setter for name.
	public void setName(String name) {
		this.name = name;
	}
	// Getter for the center cell.
	public BoardCell getCenterCell() {
		return centerCell;
	}
	// Setter for the center cell.
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	// Getter for the label cell.
	public BoardCell getLabelCell() {
		return labelCell;
	}
	// Setter for label cell.
	public void setLabelCell(BoardCell lableCell) {
		this.labelCell = lableCell;
	}
}
