package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;

/**
 * Player: An abstract class representing a player in the game, with attributes for name, 
 * color, location, and a hand of cards. It includes functionality for updating the player’s hand.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

import java.util.*;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	private List<Card> hand = new ArrayList<Card>();
	private boolean justMoved = false;
	// Constructor
	public Player(String name, String color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
	}
	// Update hand method adding a card to the hand list.
	abstract void updateHand(Card card);
	// Method for disproving a suggestion
	abstract Card disproveSuggestion(Solution suggestion);
	// Getter for the hand list.
	public List<Card> getHand() {
        return hand;
    }
    // Getter for the name of the player.
    public String getName() {
        return name;
    }
    // Getter for the color of the player.
    public String getColor() {
        return color;
    }
    // Getter for the row.
    public int getRow() {
    	return row;
    }
    // Getter for the column.
    public int getColumn() {
    	return column;
    }
    // Setter for the row.
    public void setRow(int row) {
    	this.row = row;
    }
    // Setter for the column.
    public void setColumn(int col) {
    	this.column = col;
    }
    // Equals method returning a boolean if the color matches the player name or not.
    public boolean equals(Player player) {
    	if(this.name.equals(player.getName())  && this.color.equals(player.getColor()) ) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }
    // Draw method for drawing of the player icon in a cell.
    public void draw(Graphics g, int cellWidth, int cellHeight) {
    	int drawX = column * cellWidth;
        int drawY = row * cellHeight;
        Color color = null;
        try {
		    Field field = Class.forName("java.awt.Color").getField(getColor().toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null;
		}
        g.setColor(color);
        g.fillOval(drawX, drawY, cellWidth, cellHeight);
        g.setColor(Color.BLACK);
        g.drawOval(drawX, drawY, cellWidth, cellHeight);
    }
    // Method for the boolean for the player having moved.
	public boolean isJustMoved() {
		return justMoved;
	}
	// Setter for the player just having moved.
	public void setJustMoved(boolean justMoved) {
		this.justMoved = justMoved;
	}
}
