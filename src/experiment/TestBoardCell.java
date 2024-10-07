package experiment;
import java.util.*;

public class TestBoardCell {
	private boolean room;
	private boolean occupied;
	private int row;
	private int column;
	private ArrayList<TestBoardCell> adjList = new ArrayList<TestBoardCell>();
	
	//Constructor
	public TestBoardCell(int row, int column){
		row = this.row;
		column = this.column;
	}
	// Setter to add a cell to the cell adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	//getter for the add adjacency list
	public ArrayList<TestBoardCell> getAdjList(){
		return adjList;
	}
	//setter for if the cell is a room
	public void setRoom(boolean room) {
		room = this.room;
	}
	//getter for if the cell is a room
	public boolean isRoom() {
		return room;
	}
	//setter for if the cell is occupied
	public void setOccupied(boolean occupied) {
		occupied = this.occupied;
	}
	//getter for if the cell is occupied
	public boolean getOccupied() {
		return occupied;
	}
	
	
}
