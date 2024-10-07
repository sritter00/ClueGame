package experiment;
import java.util.*;

public class TestBoard {
	private Set<TestBoardCell> setAdjList = new HashSet<TestBoardCell>();
	//constructor
	public TestBoard() {
		//empty
	}
	//calculates legal targets for a move from startCell of length pathlength.
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		//Don't really know what to put here also don't think we have to until next assignment not sure though
	}
	//getter for target value
	public Set<TestBoardCell> getTargets() {
		return setAdjList;
	}
	//returns the cell from the board at row, col.
	public TestBoardCell getCell(int row, int column) {
		TestBoardCell cell = new TestBoardCell(row, column);
		return cell; 
	}
}
