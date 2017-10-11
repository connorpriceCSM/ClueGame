package clueGame;

//Authors: Amelia Atiles and Connor Price
public class BoardCell {

	private int row;
	private int column;
	private char initial;
	private char doorChar;
	private DoorDirection direction;
	public BoardCell(int row, int column)
	{
		this.row = row;
		this.column = column;

	}
	// getter methods as well as booleans for
	public int getRow() 
	{
		return row;
	}
	public int getColumn() 
	{
		return column;
	}
	// needed to verify tests
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
		
		return false;

	}
	public boolean isRoom()
	{
		return false;

	}
	public boolean isDoorway()
	{
		return false;
	}
	public void setDoor()
	{
		
	}
}
