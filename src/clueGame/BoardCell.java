package clueGame;

//Authors: Amelia Atiles and Connor Price
public class BoardCell {

	private int row;
	private int column;
	private char initial;
	private char doorChar;
	private DoorDirection direction;
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
		if(initial == 'P')
		{
			return true;
		}
		return false;

	}
	public boolean isRoom()
	{
		if(initial != 'P' && !isDoorway())
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
}
