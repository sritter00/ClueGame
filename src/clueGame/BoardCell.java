package clueGame;

import java.awt.Color;
import java.awt.Graphics;

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
	private Character initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean doorway;
	private char secretPassage;
	private boolean occupied;
	private Color color;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	// Setter for occupied.
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	// Getter for occupied.
	public boolean getOccupied() {
		return occupied;
	}
	// Draw method for drawing of the cells.
	public void draw(Graphics g, int cellWidth, int cellHeight) {
		int x = column * cellWidth;
		int y = row * cellHeight;
		g.setColor(color);
		g.fillRect(x, y, cellWidth, cellHeight);
		if(initial.equals('W')) {
			g.setColor(Color.BLACK);
			g.drawRect(x, y, cellWidth, cellHeight);
		}
		if(this.isDoorway()) {
			if(getDoorDirection() == DoorDirection.DOWN) {
				g.setColor(Color.BLUE);
				g.drawRect(x, y + cellHeight-cellHeight/7, cellWidth, cellHeight/10);
				g.fillRect(x, y + cellHeight-cellHeight/7, cellWidth, cellHeight/10);
			}
			if(getDoorDirection() == DoorDirection.UP) {
				g.setColor(Color.BLUE);
				g.drawRect(x, y, cellWidth, cellHeight/10);
				g.fillRect(x, y, cellWidth, cellHeight/10);
			}
			if(getDoorDirection() == DoorDirection.RIGHT) {
				g.setColor(Color.BLUE);
				g.drawRect(x+cellWidth-cellWidth/10, y, cellWidth/10, cellHeight);
				g.fillRect(x+cellWidth-cellWidth/10, y, cellWidth/10, cellHeight);
			}
			if(getDoorDirection() == DoorDirection.LEFT) {
				g.setColor(Color.BLUE);
				g.drawRect(x, y, cellWidth/10, cellHeight);
				g.fillRect(x, y, cellWidth/10, cellHeight);
				
			}
		}

	}
	// Draw method for the room labels.
	public void drawLabel(Graphics g, int cellWidth, int cellHeight, String roomName) {
		int x = column * cellWidth + cellWidth / 4;
		int y = row * cellHeight + cellHeight / 2;
		g.setColor(Color.blue);
		g.drawString(roomName, x, y);
	}
	// Boolean for whether location is a doorway or not.
	public boolean isDoorway() {
		return doorway;
	}
	// Constructor
	public BoardCell(int row, int column, Character initial){
		this.row = row  ;
		this.column = column;
		this.initial = initial;
		if(initial.equals('W'))	{
			color = Color.YELLOW;
		}else if(initial.equals('X')) {
			color = Color.BLACK;
		}else {
			color = Color.GRAY;
		}
		adjList = new HashSet<BoardCell>();
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
		doorway = true;
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
