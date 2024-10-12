package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	public Room() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BoardCell getCenterCell() {
		return centerCell;
	}
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	public BoardCell getLabelCell() {
		return labelCell;
	}
	public void setLabelCell(BoardCell lableCell) {
		this.labelCell = lableCell;
	}
}
