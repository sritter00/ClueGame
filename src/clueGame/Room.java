package clueGame;

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
	
	// Constructor
	public Room() {
		
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
