package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.BoardCell;

public class Board {
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private int numCol = 0;
	private int numRows = 0;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
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
    }
}