package clueGame;


//Authors: Amelia Atiles and Connor Price
public class BoardCell {

	private int row;
	private int column;
	private char initial;
	private char doorChar;
	private DoorDirection direction;
	private int index;
	/*
	 * Easier to just use a string type as the 3rd parameter
	 * because some cells will have more than one character for doors
	 * like BU for example.
	 */
	public BoardCell(int row, int column, String characters)
	{
		this.row = row;
		this.column = column;
		// BoardCell is guaranteed to have at least one character
		initial = characters.charAt(0);
		direction = DoorDirection.NONE;
		// if this BoardCell has two characters, check for a door.
		if(characters.length() == 2)
		{
			// accounting for Baldwin's room placements
			if(characters.charAt(1) != 'N')
			doorChar = characters.charAt(1);
		}
		// method to set door 
		setDoor();
	}
	public BoardCell(int row, int column)
	{
		this.row = row;
		
	}
	// setter methods
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setInitial(char initial) {
		this.initial = initial;
	}
	public void setDoorChar(char doorChar) {
		this.doorChar = doorChar;
	}
	public void setDirection(DoorDirection direction) {
		this.direction = direction;
	}
	// getter methods as well as booleans for doorways,pathways,and rooms
	public int getRow() 
	{
		return row;
	}
	public int getColumn() 
	{
		return column;
	}
	
	public char getInitial()
	{
		return initial;
	}
	public DoorDirection getDoorDirection()
	{
		return direction;
	}
	
	public boolean isPathway()
	{
		if(initial == Board.WALKWAY_CHAR)
		{
			return true;
		}
		return false;

	}
	public boolean isRoom()
	{
		if (initial != Board.WALKWAY_CHAR && initial!= Board.CLOSET_CHAR && !isDoorway())
		{
			return true;
		}
		return false;

	}
	public boolean isDoorway()
	{
		if(direction != DoorDirection.NONE)
		{
			return true;
		}
		return false;
	}
	public void setDoor()
	{
		switch(doorChar)
		{
		case 'U':
			direction = DoorDirection.UP;
			break;
		case 'D':
			direction = DoorDirection.DOWN;
			break;
		case 'R':
			direction = DoorDirection.RIGHT;
			break;
		case 'L':
			direction = DoorDirection.LEFT;
			break;
		default:
			direction = DoorDirection.NONE;
			break;
				
		}
	}
	// Hashcode for Comparing Cells
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;   
	}


	//Override Equals method  to compare BoardCells if need be.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardCell other = (BoardCell) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

			
}
