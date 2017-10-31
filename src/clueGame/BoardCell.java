package clueGame;


//Authors: Amelia Atiles and Connor Price
public class BoardCell {

	private int cellRow;
	private int cellColumn;
	private char cellInitial;
	private char doorChar;
	private DoorDirection direction;
	private int index;
	/*
	 * Easier to just use a string type as the 3rd parameter
	 * because some cells will have more than one character for doors
	 * like BU for example.
	 */
	//primary constructor
	public BoardCell(int row, int column, String characters)
	{
		this.cellRow = row;
		this.cellColumn = column;
		// BoardCell is guaranteed to have at least one character
		cellInitial = characters.charAt(0);
		direction = DoorDirection.NONE;
		// if this BoardCell has two characters, check for a door.
		if(characters.length() == 2)
		{
			// accounting for Baldwin's room placements
			if(characters.charAt(1) != 'N')
			{
			doorChar = characters.charAt(1);
			}
		}
		// method to set door 
		setDoor();
	}
	// secondary constructor
	public BoardCell(int row, int column)
	{
		this.cellRow = row;
	}
	// setter methods
	public void setRow(int row) 
	{
		this.cellRow = row;
	}
	public void setColumn(int column) 
	{
		this.cellColumn = column;
	}
	public void setInitial(char initial) 
	{
		this.cellInitial = initial;
	}
	public void setDoorChar(char doorChar) 
	{
		this.doorChar = doorChar;
	}
	public void setDirection(DoorDirection direction) 
	{
		this.direction = direction;
	}
	// getter methods as well as booleans for doorways,pathways,and rooms
	public int getRow() 
	{
		return cellRow;
	}
	public int getColumn() 
	{
		return cellColumn;
	}
	
	public char getInitial()
	{
		return cellInitial;
	}
	public DoorDirection getDoorDirection()
	{
		return direction;
	}
	
	//check if this cell is a pathway
	public boolean isPathway()
	{
		if(cellInitial == Board.WALKWAY_CHARACTER)
		{
			return true;
		}
		return false;
	}
	//check if this cell is a room
	public boolean isRoom()
	{
		if (cellInitial != Board.WALKWAY_CHARACTER && cellInitial!= Board.CLOSET_CHARACTER && !isDoorway())
		{
			return true;
		}
		return false;
	}
	// check if this cell is a doorway
	public boolean isDoorway()
	{
		if(direction != DoorDirection.NONE)
		{
			return true;
		}
		return false;
	}
	// depending on the secondary character, set the door direction for this cell
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
