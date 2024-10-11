package experiment;

/**
 * TestBoardCell: Initializes a cell in a 4x4 grid, managing adjacencies, room status, and occupancy.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.util.*;

public class TestBoardCell {
	private boolean room;
	private boolean occupied;
	private int row;
	private int column;
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
	
	//Constructor
	public TestBoardCell(int row, int column){
		row = this.row;
		column = this.column;
	}
	// Setter to add a cell to the cell adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	// Getter for the add adjacency list
	public Set<TestBoardCell> getAdjList(){
		return adjList;
	}
	// Setter for if the cell is a room
	public void setRoom(boolean room) {
		this.room = room ;
	}
	// Getter for if the cell is a room
	public boolean isRoom() {
		return room;
	}
	// Setter for if the cell is occupied
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	// Getter for if the cell is occupied
	public boolean getOccupied() {
		return occupied;
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
